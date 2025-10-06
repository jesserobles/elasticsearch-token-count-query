/*
 * Copyright Elasticsearch B.V. and/or licensed to Elasticsearch B.V. under one
 * or more contributor license agreements. Licensed under the "Elastic License
 * 2.0", the "GNU Affero General Public License v3.0 only", and the "Server Side
 * Public License v 1"; you may not use this file except in compliance with, at
 * your election, the "Elastic License 2.0", the "GNU Affero General Public
 * License v3.0 only", or the "Server Side Public License, v 1".
 */
package org.elasticsearch.query.tokencount;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.PositionIncrementAttribute;

import java.io.IOException;

/**
 * Utility class for analyzing text and counting tokens.
 * Uses position increments for accurate counting, matching the behavior of TokenCountFieldMapper.
 */
public class TokenCountAnalyzer {

    /**
     * Analyzes the given text with the specified analyzer and counts the tokens.
     * This uses position increments to accurately count tokens, which properly handles
     * cases like synonyms and other multi-term tokens.
     *
     * @param analyzer The analyzer to use
     * @param text The text to analyze
     * @return The number of token positions produced by the analyzer
     * @throws IOException If an I/O error occurs during analysis
     */
    public static int countTokens(Analyzer analyzer, String text) throws IOException {
        if (analyzer == null) {
            throw new IllegalArgumentException("Analyzer cannot be null");
        }
        if (text == null || text.isEmpty()) {
            return 0;
        }

        // Implementation based on TokenCountFieldMapper.countPositions()
        // from org.elasticsearch.index.mapper.TokenCountFieldMapper
        try (TokenStream tokenStream = analyzer.tokenStream("field", text)) {
            int count = 0;
            PositionIncrementAttribute position = tokenStream.addAttribute(PositionIncrementAttribute.class);
            tokenStream.reset();
            while (tokenStream.incrementToken()) {
                count += position.getPositionIncrement();
            }
            tokenStream.end();
            count += position.getPositionIncrement();
            return count;
        }
    }
}
