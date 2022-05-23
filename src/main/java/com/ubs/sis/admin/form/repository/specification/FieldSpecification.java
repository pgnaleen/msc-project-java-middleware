package com.ubs.sis.admin.form.repository.specification;

import com.ubs.sis.admin.form.domain.custom_form.FormField;
import com.ubs.sis.admin.form.dto.filter.FieldFilterDto;
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
public class FieldSpecification implements Specification<FormField> {

    private FieldFilterDto filterDto;

    @Override
    public Predicate toPredicate(Root<FormField> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        if (filterDto.getIsCustom() != null) {
            predicates.add(criteriaBuilder.equal(root.get("isCustom"), filterDto.getIsCustom()));
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[]{}));
    }
}
