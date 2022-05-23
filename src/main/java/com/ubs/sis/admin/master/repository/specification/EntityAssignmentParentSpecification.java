package com.ubs.sis.admin.master.repository.specification;

import com.ubs.commons.domain.BaseEntity_;
import com.ubs.sis.admin.master.domain.EntityAssignment;
import com.ubs.sis.admin.master.domain.EntityAssignment_;
import com.ubs.sis.admin.master.domain.StructureMaster;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Getter
public class EntityAssignmentParentSpecification implements Specification<EntityAssignment> {
    private final Long structureMasterId;
    private final Boolean parentActiveState;
    @Override
    public Predicate toPredicate(Root<EntityAssignment> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        if (structureMasterId != null) {
            Join<EntityAssignment, StructureMaster> gateway2 = root.join(EntityAssignment_.entity, JoinType.INNER);
            predicates.add(criteriaBuilder.equal(gateway2.get(BaseEntity_.id),
                    structureMasterId));

        }

        if (parentActiveState != null) {
            Join<EntityAssignment, EntityAssignment> gateway = root.join(EntityAssignment_.parent);
            predicates.add(criteriaBuilder.equal(gateway.get(EntityAssignment_.activeStatus),
                    parentActiveState));

        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[]{}));
    }
}
