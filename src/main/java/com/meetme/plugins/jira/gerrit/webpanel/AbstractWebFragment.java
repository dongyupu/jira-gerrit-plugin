/*******************************************************************************
This file is licensed under the terms of the BSD 3-Clause License

Copyright 2023 Atlassian

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:

* Redistributions of source code must retain the above copyright notice, this
  list of conditions and the following disclaimer.

* Redistributions in binary form must reproduce the above copyright notice,
  this list of conditions and the following disclaimer in the documentation
  and/or other materials provided with the distribution.

* Neither the name of the copyright holder nor the names of its
  contributors may be used to endorse or promote products derived from
  this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*******************************************************************************/

package com.meetme.plugins.jira.gerrit.webpanel;

import com.atlassian.plugin.web.api.WebFragment;

import java.util.Map;

import javax.annotation.Nonnull;

public abstract class AbstractWebFragment implements WebFragment {
    private final String completeKey;
    private final String label;
    private final String title;
    private final String styleClass;
    private final String id;
    private final Map<String, String> params;
    private final int weight;

    protected AbstractWebFragment(
            final String completeKey,
            final String label,
            final String title,
            final String styleClass,
            final String id,
            final Map<String, String> params,
            final int weight) {
        this.completeKey = completeKey;
        this.label = label;
        this.title = title;
        this.styleClass = styleClass;
        this.id = id;
        this.params = params;
        this.weight = weight;
    }

    @Override
    public String getCompleteKey() {
        return completeKey;
    }

    @Override
    public String getLabel() {
        return label;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getStyleClass() {
        return styleClass;
    }

    @Override
    public String getId() {
        return id;
    }

    @Nonnull
    @Override
    public Map<String, String> getParams() {
        return params;
    }

    @Override
    public int getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return getClass().getName() + "{" + toStringOfFields() + "}";
    }

    protected String toStringOfFields() {
        return "completeKey=" + completeKey + ", label=" + label + ", title=" + title + ", styleClass=" + styleClass
                + ", id=" + id + ", params=" + params + ", weight=" + weight;
    }
}
