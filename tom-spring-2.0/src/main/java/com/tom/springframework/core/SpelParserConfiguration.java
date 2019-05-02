package com.tom.springframework.core;

import com.tom.springframework.expression.spel.SpelCompilerMode;

public class SpelParserConfiguration {

    private static final SpelCompilerMode defaultCompilerMode;

    static {
        String compilerMode = SpringProperties.getProperty("spring.expression.compiler.mode");
        defaultCompilerMode = (compilerMode != null ?
                SpelCompilerMode.valueOf(compilerMode.toUpperCase()) : SpelCompilerMode.OFF);
    }

    private final SpelCompilerMode compilerMode;

    private final ClassLoader compilerClassLoader;

    private final boolean autoGrowNullReferences;

    private final boolean autoGrowCollections;

    private final int maximumAutoGrowSize;

    public SpelParserConfiguration(SpelCompilerMode compilerMode, ClassLoader compilerClassLoader) {
        this(compilerMode, compilerClassLoader, false, false, Integer.MAX_VALUE);
    }

    public SpelParserConfiguration(SpelCompilerMode compilerMode, ClassLoader compilerClassLoader,
                                   boolean autoGrowNullReferences, boolean autoGrowCollections, int maximumAutoGrowSize) {

        this.compilerMode = (compilerMode != null ? compilerMode : defaultCompilerMode);
        this.compilerClassLoader = compilerClassLoader;
        this.autoGrowNullReferences = autoGrowNullReferences;
        this.autoGrowCollections = autoGrowCollections;
        this.maximumAutoGrowSize = maximumAutoGrowSize;
    }

}