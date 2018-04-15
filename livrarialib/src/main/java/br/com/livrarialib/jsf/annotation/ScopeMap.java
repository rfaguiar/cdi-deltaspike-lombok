package br.com.livrarialib.jsf.annotation;

import javax.inject.Qualifier;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Qualifier
@Target({ElementType.METHOD, ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ScopeMap {
    ScopeMap.Scope value();

    enum Scope {
        REQUEST,SESSION,APPLICATION;
    }
}
