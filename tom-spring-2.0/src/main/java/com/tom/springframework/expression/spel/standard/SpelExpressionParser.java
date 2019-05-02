package com.tom.springframework.expression.spel.standard;

import com.tom.springframework.core.SpelParserConfiguration;
import com.tom.springframework.expression.common.TemplateAwareExpressionParser;
import com.tom.springframework.util.Assert;

public class SpelExpressionParser extends TemplateAwareExpressionParser {

    private final SpelParserConfiguration configuration;

    public SpelExpressionParser(SpelParserConfiguration configuration) {
        Assert.notNull(configuration, "SpelParserConfiguration must not be null");
        this.configuration = configuration;
    }

}