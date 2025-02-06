/*
 * Copyright 2012 MeetMe, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package com.meetme.plugins.jira.gerrit.webpanel;

import com.meetme.plugins.jira.gerrit.data.GerritConfiguration;
import com.meetme.plugins.jira.gerrit.data.IssueReviewsManager;

import com.atlassian.jira.issue.Issue;
import com.atlassian.plugin.web.Condition;
import com.sonymobile.tools.gerrit.gerritevents.GerritQueryException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

import static org.apache.commons.collections.CollectionUtils.isEmpty;

/**
 * Created by jhansche on 9/2/16.
 */
public class ShowReviewsWebPanelCondition implements Condition {

    private static final Logger log = LoggerFactory.getLogger(ShowReviewsWebPanelCondition.class);

    private static final String KEY_ISSUE = "issue";

    private final GerritConfiguration gerritConfiguration;
    private final IssueReviewsManager issueReviewsManager;

    public ShowReviewsWebPanelCondition(IssueReviewsManager reviewsManager, GerritConfiguration configurationManager) {

        issueReviewsManager = reviewsManager;
        gerritConfiguration = configurationManager;
    }

    @Override
    public void init(Map<String, String> map) {
    }

    @Override
    public boolean shouldDisplay(Map<String, Object> map) {

        if (map == null) {
            return false;
        }

        final Issue issue = (Issue) map.get(KEY_ISSUE);

        if (issue == null) {
            return false;
        }

        // Shall the system use the white list and does the issue belongs to a project, that uses gerrit:
        if (gerritConfiguration.getUseGerritProjectWhitelist() && !isGerritProject(issue)) {
            return false;
        }

        // Even though there are no reviews, the gerrit panel shall be displayed:
        if (gerritConfiguration.getShowsEmptyPanel()) {
            return true;
        }

        try {

            return !isEmpty(issueReviewsManager.getReviewsForIssue(issue));
        } catch (GerritQueryException gerritQueryException) {

            log.warn(gerritQueryException.getLocalizedMessage(), gerritQueryException);
            return false;
        }
    }

    private boolean isGerritProject(final Issue issue) {

        return issue.getProjectId() != null
                && !isEmpty(gerritConfiguration.getIdsOfKnownGerritProjects())
                && gerritConfiguration.getIdsOfKnownGerritProjects().contains(issue.getProjectId().toString());
    }
}
