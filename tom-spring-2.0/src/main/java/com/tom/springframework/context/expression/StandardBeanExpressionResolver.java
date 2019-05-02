package com.tom.springframework.context.expression;

import com.tom.springframework.beans.factory.config.BeanExpressionContext;
import com.tom.springframework.beans.factory.config.BeanExpressionResolver;
import com.tom.springframework.core.SpelParserConfiguration;
import com.tom.springframework.expression.ExpressionParser;
import com.tom.springframework.expression.spel.standard.SpelExpressionParser;
import com.tom.springframework.util.StringUtils;

import java.beans.Expression;

public class StandardBeanExpressionResolver implements BeanExpressionResolver {

    private ExpressionParser expressionParser;

    public StandardBeanExpressionResolver(ClassLoader beanClassLoader) {
        this.expressionParser = new SpelExpressionParser(new SpelParserConfiguration(null, beanClassLoader));
    }

    @Override
    public Object evaluate(String value, BeanExpressionContext evalContext) {
        if (!StringUtils.hasLength(value)) {
            return value;
        }
//        try {
//            Expression expr = this.expressionCache.get(value);
//            if (expr == null) {
//                expr = this.expressionParser.parseExpression(value, this.beanExpressionParserContext);
//                this.expressionCache.put(value, expr);
//            }
//            StandardEvaluationContext sec = this.evaluationCache.get(evalContext);
//            if (sec == null) {
//                sec = new StandardEvaluationContext();
//                sec.setRootObject(evalContext);
//                sec.addPropertyAccessor(new BeanExpressionContextAccessor());
//                sec.addPropertyAccessor(new BeanFactoryAccessor());
//                sec.addPropertyAccessor(new MapAccessor());
//                sec.addPropertyAccessor(new EnvironmentAccessor());
//                sec.setBeanResolver(new BeanFactoryResolver(evalContext.getBeanFactory()));
//                sec.setTypeLocator(new StandardTypeLocator(evalContext.getBeanFactory().getBeanClassLoader()));
//                ConversionService conversionService = evalContext.getBeanFactory().getConversionService();
//                if (conversionService != null) {
//                    sec.setTypeConverter(new StandardTypeConverter(conversionService));
//                }
//                customizeEvaluationContext(sec);
//                this.evaluationCache.put(evalContext, sec);
//            }
//            return expr.getValue(sec);
        return null;
//        }
//        catch (Throwable ex) {
//            throw new BeanExpressionException("Expression parsing failed", ex);
        }

}