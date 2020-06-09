package br.edu.ifpb.mestrado.openplanner.api.infrastructure.persistence.hibernate.specification;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.io.Serializable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target(TYPE)
public @interface SpecEntity {

    public Class<? extends Serializable> value();

}
