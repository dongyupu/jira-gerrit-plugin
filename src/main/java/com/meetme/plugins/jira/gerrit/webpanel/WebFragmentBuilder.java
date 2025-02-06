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

import com.atlassian.plugin.web.api.WebSection;
import com.atlassian.plugin.web.api.WebItem;

import static com.atlassian.plugin.util.Assertions.notNull;
import java.util.Map;
import java.util.HashMap;

public class WebFragmentBuilder {
    private String completeKey;
    private String label;
    private String title;
    private String styleClass;
    private String id;
    private Map<String, String> params;
    private final int weight;

    public WebFragmentBuilder(final int weight) {
        this.weight = weight;
        this.params = new HashMap<String, String>(0);
    }

    public WebFragmentBuilder(final String completeKey, final int weight) {
        this.completeKey = completeKey;
        this.weight = weight;
        this.params = new HashMap<String, String>(0);
    }

    public WebFragmentBuilder label(final String label) {
        this.label = label;
        return this;
    }

    public WebFragmentBuilder title(final String title) {
        this.title = title;
        return this;
    }

    public WebFragmentBuilder styleClass(final String styleClass) {
        this.styleClass = styleClass;
        return this;
    }

    public WebFragmentBuilder id(final String id) {
        this.id = id;
        return this;
    }

    public WebFragmentBuilder params(final Map<String, String> params) {
        this.params = new HashMap<String, String>(params);
        return this;
    }

    public WebFragmentBuilder addParam(final String key, final String value) {
        params.put(key, value);
        return this;
    }

    public WebItemBuilder webItem(final String section) {
        return new WebItemBuilder(this, section);
    }

    public WebItemBuilder webItem(final String section, final String entryPoint) {
        WebItemBuilder webItemBuilder = new WebItemBuilder(this, section);
        return webItemBuilder.entryPoint(entryPoint);
    }

    public WebSectionBuilder webSection(final String location) {
        return new WebSectionBuilder(this, location);
    }

    public static class WebItemBuilder {
        private final WebFragmentBuilder fragmentBuilder;
        private final String section;
        private String accessKey;
        private String entryPoint;
        private String url;

        public WebItemBuilder(final WebFragmentBuilder fragmentBuilder, final String section) {
            this.fragmentBuilder = fragmentBuilder;
            this.section = section;
        }

        public WebItemBuilder accessKey(final String accessKey) {
            this.accessKey = accessKey;
            return this;
        }

        public WebItemBuilder entryPoint(final String entryPoint) {
            this.entryPoint = entryPoint;
            return this;
        }

        public WebItemBuilder url(final String url) {
            this.url = url;
            return this;
        }

        public WebItem build() {
            return new WebItemImpl(
                    fragmentBuilder.completeKey,
                    fragmentBuilder.label,
                    fragmentBuilder.title,
                    fragmentBuilder.styleClass,
                    fragmentBuilder.id,
                    fragmentBuilder.params,
                    fragmentBuilder.weight,
                    notNull("section", section),
                    url,
                    accessKey,
                    entryPoint);
        }
    }

    public static class WebSectionBuilder {

        private final WebFragmentBuilder fragmentBuilder;
        private final String location;

        public WebSectionBuilder(final WebFragmentBuilder fragmentBuilder, final String location) {
            this.fragmentBuilder = fragmentBuilder;
            this.location = location;
        }

        public WebSection build() {
            return new WebSectionImpl(
                    fragmentBuilder.completeKey,
                    fragmentBuilder.label,
                    fragmentBuilder.title,
                    fragmentBuilder.styleClass,
                    fragmentBuilder.id,
                    fragmentBuilder.params,
                    fragmentBuilder.weight,
                    notNull("location", location));
        }
    }
}
