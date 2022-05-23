package com.ubs.sis.admin.master.repository.specification;

import com.ubs.commons.domain.BaseEntity_;
import com.ubs.sis.admin.master.domain.*;
import com.ubs.sis.admin.master.dto.filter.BaseAssignmentFilterDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;

@Getter
@AllArgsConstructor
public class EntityContextAssignmentSpecification<Entity extends MasterEntity,
        FilterDto extends BaseAssignmentFilterDto> implements Specification<Entity> {

    private FilterDto filterDto;


    @Override
    public Predicate toPredicate(Root<Entity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        Join<Entity, StructureMaster> smGateway = root.join(MasterEntity_.structureMaster, JoinType.INNER);
        return
                criteriaBuilder.or(criteriaBuilder.equal(smGateway.get(StructureMaster_.entityAssignmentId), filterDto.getContextAssignmentId())
                        , criteriaBuilder.isNull(smGateway.get(StructureMaster_.entityAssignment)))
                ;

    }
}
