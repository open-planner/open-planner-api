package br.edu.ifpb.mestrado.openplanner.api.infrastructure.persistence.hibernate.specification;

import org.springframework.data.jpa.domain.Specification;

import com.github.fagnerlima.springspecificationtools.SpecFactory;
import com.github.fagnerlima.springspecificationtools.SpecOperation;

import br.edu.ifpb.mestrado.openplanner.api.domain.shared.BaseEntity;
import br.edu.ifpb.mestrado.openplanner.api.domain.shared.BaseEntity_;

public class BaseEntitySpecification {

    public static <T extends BaseEntity> Specification<T> idGreaterThan(Long id) {
        return new SpecFactory<T>().create(BaseEntity_.ID, id, SpecOperation.GREATER_THAN);
    }

    public static <T extends BaseEntity> Specification<T> positiveId() {
        return idGreaterThan(0L);
    }

    public static <T extends BaseEntity> Specification<T> excluded() {
        return new SpecFactory<T>().create(BaseEntity_.EXCLUDED, true);
    }

    public static <T extends BaseEntity> Specification<T> notExcluded() {
        return new SpecFactory<T>().create(BaseEntity_.EXCLUDED, false);
    }

    @SuppressWarnings("unchecked")
    public static <T extends BaseEntity> Specification<T> positiveIdAndNotExcluded() {
        return (Specification<T>) Specification
                .where(positiveId())
                .and(notExcluded());
    }

}
