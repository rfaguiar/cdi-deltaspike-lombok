package br.com.livrarialib.jsf.annotation;

import javax.enterprise.inject.Stereotype;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;

@Named
@ViewScoped
@Documented
@Stereotype
@Target({TYPE, METHOD, FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ViewModel {}
