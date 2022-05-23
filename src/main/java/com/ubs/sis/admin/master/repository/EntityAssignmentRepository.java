package com.ubs.sis.admin.master.repository;

import com.ubs.commons.repository.BaseRepository;
import com.ubs.sis.admin.master.domain.EntityAssignment;
import com.ubs.sis.admin.master.domain.StructureMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

public interface EntityAssignmentRepository extends BaseRepository<EntityAssignment> {
    boolean existsByTenantIdAndEntityAndParent(Long tenantId, StructureMaster structureMaster, EntityAssignment entityAssignment);

    @Query(value = " ?1 ",
            countQuery = " ?2 ",
            nativeQuery = true)
    Page<EntityAssignment> filterFrom(String query, String countQuery, Pageable pageable);
}
