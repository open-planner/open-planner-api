package br.edu.ifpb.mestrado.openplanner.api.infrastructure.persistence.hibernate.specification;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.io.Serializable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target(FIELD)
public @interface SpecJoin {

    public Class<? extends Serializable> entity();

}
