/*
 * Copyright Elasticsearch B.V. and/or licensed to Elasticsearch B.V. under one
 * or more contributor license agreements. Licensed under the "Elastic License
 * 2.0", the "GNU Affero General Public License v3.0 only", and the "Server Side
 * Public License v 1"; you may not use this file except in compliance with, at
 * your election, the "Elastic License 2.0", the "GNU Affero General Public
 * License v3.0 only", or the "Server Side Public License, v 1".
 */
package org.elasticsearch.query.tokencount;

import org.elasticsearch.plugins.Plugin;
import org.elasticsearch.plugins.SearchPlugin;

import java.util.List;

/**
 * Plugin that registers the token_count query for server-side text analysis and token counting.
 */
public class TokenCountQueryPlugin extends Plugin implements SearchPlugin {

    @Override
    public List<QuerySpec<?>> getQueries() {
        return List.of(
            new QuerySpec<>(
                TokenCountQueryBuilder.NAME,
                TokenCountQueryBuilder::new,
                TokenCountQueryBuilder::fromXContent
            )
        );
    }
}
