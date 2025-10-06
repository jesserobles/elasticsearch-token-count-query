/*
 * Copyright Elasticsearch B.V. and/or licensed to Elasticsearch B.V. under one
 * or more contributor license agreements. Licensed under the "Elastic License
 * 2.0", the "GNU Affero General Public License v3.0 only", and the "Server Side
 * Public License v 1"; you may not use this file except in compliance with, at
 * your election, the "Elastic License 2.0", the "GNU Affero General Public
 * License v3.0 only", or the "Server Side Public License, v 1".
 */
package org.elasticsearch.query.tokencount;

import org.apache.lucene.document.IntPoint;
import org.apache.lucene.search.Query;

/**
 * Helper class for creating Lucene queries based on token count comparisons.
 */
public class TokenCountQueryHelper {

    /**
     * Creates a Lucene query for the given field, token count, and operator.
     *
     * @param fieldName The field to query
     * @param tokenCount The token count to compare against
     * @param operator The comparison operator
     * @return A Lucene query
     */
    public static Query createQuery(String fieldName, int tokenCount, TokenCountQueryBuilder.Operator operator) {
        switch (operator) {
            case EQ:
                return IntPoint.newExactQuery(fieldName, tokenCount);
            case GT:
                return IntPoint.newRangeQuery(fieldName, tokenCount + 1, Integer.MAX_VALUE);
            case LT:
                return IntPoint.newRangeQuery(fieldName, Integer.MIN_VALUE, tokenCount - 1);
            case GTE:
                return IntPoint.newRangeQuery(fieldName, tokenCount, Integer.MAX_VALUE);
            case LTE:
                return IntPoint.newRangeQuery(fieldName, Integer.MIN_VALUE, tokenCount);
            default:
                throw new IllegalArgumentException("Unknown operator: " + operator);
        }
    }
}
