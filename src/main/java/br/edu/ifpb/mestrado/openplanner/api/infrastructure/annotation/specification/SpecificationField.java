package br.edu.ifpb.mestrado.openplanner.api.infrastructure.annotation.specification;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import br.edu.ifpb.mestrado.openplanner.api.infrastructure.persistence.hibernate.specification.Operation;

@Retention(RUNTIME)
@Target({ FIELD })
public @interface SpecificationField {

    public String property() default "";

    public Operation operation() default Operation.EQUAL;

}
