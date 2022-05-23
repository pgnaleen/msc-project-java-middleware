package com.ubs.sis.admin.master.repository.specification;

import com.ubs.sis.admin.master.domain.ContextAssignmentBaseEntity_;
import com.ubs.sis.admin.master.domain.StructureMaster;
import com.ubs.sis.admin.master.dto.filter.StructureMasterFilterDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
public class StructureMasterSpecification implements Specification<StructureMaster> {

    private StructureMasterFilterDto filterDto;

    @Override
    public Predicate toPredicate(Root<StructureMaster> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        if (filterDto.getBusinessEntity() != null) {
            predicates.add(criteriaBuilder.equal(root.get(StructureMaster.FIELD_BUSINESS_ENTITY),
                    filterDto.getBusinessEntity()));
        }
        if (filterDto.getContextAssignmentId() != null) {
            predicates.add(
                    criteriaBuilder.or(criteriaBuilder.equal(root.get(ContextAssignmentBaseEntity_.entityAssignmentId), filterDto.getContextAssignmentId()),
                            criteriaBuilder.isNull(root.get(ContextAssignmentBaseEntity_.entityAssignmentId)))
            );
        }
        if (filterDto.isActiveStatus()) {
            predicates.add(criteriaBuilder.equal(root.get(StructureMaster.FIELD_ACTIVE_STATUS),
                filterDto.isActiveStatus()));
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[]{}));
    }
}
