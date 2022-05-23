package com.ubs.sis.admin.master.repository.specification;

import com.ubs.sis.admin.master.domain.AnomalyDetection;
import com.ubs.sis.admin.master.domain.AnomalyDetection_;
import com.ubs.sis.admin.master.domain.StructureMaster_;
import com.ubs.sis.admin.master.dto.filter.AnomalyDetectionFilterDto;
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
public class AnomalyDetectionSpecification implements Specification<AnomalyDetection> {

    private AnomalyDetectionFilterDto filterDto;

    @Override
    public Predicate toPredicate(Root<AnomalyDetection> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        if (filterDto.getDatasetCode() != null) {
            predicates.add(criteriaBuilder.equal(root.get(AnomalyDetection.FIELD_DATASET_CODE),
                    filterDto.getDatasetCode()));
        }

        if (filterDto.getDatasetLocation() != null) {
            predicates.add(criteriaBuilder.equal(root.get(AnomalyDetection.FIELD_DATASET_LOCATION),
                    filterDto.getDatasetLocation()));
        }

        if (filterDto.getAccessLogLocation() != null) {
            predicates.add(criteriaBuilder.equal(root.get(AnomalyDetection.FIELD_ACCESS_LOG_LOCATION),
                    filterDto.getAccessLogLocation()));
        }

        if (filterDto.getSearchQuery() != null) {
            Predicate likeDatasetCode = criteriaBuilder.like(criteriaBuilder.lower(root.get(AnomalyDetection_.DATASET_CODE)), "%" + filterDto.getSearchQuery().toLowerCase() + "%");
            Predicate likeDatasetLocation = criteriaBuilder.like(criteriaBuilder.lower(root.get(AnomalyDetection_.DATASET_LOCATION)), "%" + filterDto.getSearchQuery().toLowerCase() + "%");
            Predicate likeAccessLogLocation = criteriaBuilder.like(criteriaBuilder.lower(root.get(AnomalyDetection_.ACCESS_LOG_LOCATION)), "%" + filterDto.getSearchQuery().toLowerCase() + "%");
            predicates.add(criteriaBuilder.or(likeAccessLogLocation, likeDatasetCode, likeDatasetLocation));
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[]{}));
    }
}
