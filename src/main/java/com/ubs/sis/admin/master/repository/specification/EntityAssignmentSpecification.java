package com.ubs.sis.admin.master.repository.specification;

import com.ubs.commons.domain.BaseEntity;
import com.ubs.commons.domain.BaseEntity_;
import com.ubs.commons.exception.GearsException;
import com.ubs.commons.exception.GearsResponseStatus;
import com.ubs.sis.admin.master.domain.*;
import com.ubs.sis.admin.master.domain.enums.BusinessEntity;
import com.ubs.sis.admin.master.dto.filter.EntityAssignmentFilterDto;
import com.ubs.sis.admin.util.StructureMasterUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
@Slf4j
public class EntityAssignmentSpecification implements Specification<EntityAssignment> {
    private static final String ENTITY_ASSIGNMENT_SHORT_NAME = "EA";
    private static final String PARENT_ENTITY_ASSIGNMENT_SHORT_NAME = "PEA";
    private static final String PARENT_STRUCTURE_MASTER_SHORT_NAME = "PSM";
    private static final String ENTITY_SHORT_NAME = "EN";
    private static final String STRUCTURE_MASTER_SHORT_NAME = "SM";
    private static final String INNER_JOIN_CLAUSE = " INNER JOIN ";
    private static final String LEFT_JOIN_CLAUSE = " LEFT JOIN ";
    private static final String ON_CLAUSE = " ON ";
    private static final String AND_CLAUSE = " AND ";
    private static final String DOT_CLAUSE = ".";

    private EntityAssignmentFilterDto filterDto;
    private MasterEntity baseEntity;

    public EntityAssignmentSpecification(EntityAssignmentFilterDto filterDto) {
        this.filterDto = filterDto;
        if (filterDto.getBaseEntity()!=null) {
            baseEntity=getMasterEntity(filterDto.getBaseEntity());
        }
    }

    @Override
    public Predicate toPredicate(Root<EntityAssignment> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        if (filterDto.getAssignedEntity() != null) {

            Join<EntityAssignment, EntityAssignment> gateway = root.join(EntityAssignment_.parent);
            Join<EntityAssignment, StructureMaster> gateway2 = gateway.join(EntityAssignment_.entity, JoinType.INNER);
            predicates.add(criteriaBuilder.equal(gateway2.get(StructureMaster_.businessEntity),
                    filterDto.getAssignedEntity()));
        }
        if (filterDto.getParentAssignmentId() != null) {
            Join<EntityAssignment, EntityAssignment> gateway = root.join(EntityAssignment_.parent);
            predicates.add(criteriaBuilder.equal(gateway.get(BaseEntity_.id),
                    filterDto.getParentAssignmentId()));
        }
        if (filterDto.getActiveStatus() != null) {
            List<Predicate> activeStatue = new ArrayList<>(Collections.singletonList(criteriaBuilder.equal(root.get(EntityAssignment_.activeStatus),
                    filterDto.getActiveStatus())));

            if (Boolean.TRUE.equals(filterDto.getActiveStatus())) {
                activeStatue.add(

                        criteriaBuilder.isNull(root.get(EntityAssignment_.activeStatus))
                );
            }
            predicates.add(criteriaBuilder.or(activeStatue.toArray(new Predicate[]{})));
        }

        Join<EntityAssignment, StructureMaster> smGateway = root.join(EntityAssignment_.entity, JoinType.INNER);
        if (filterDto.getBaseEntity() != null) {

            predicates.add(criteriaBuilder.equal(smGateway.get(StructureMaster_.businessEntity),
                    filterDto.getBaseEntity()));
        }
        if (filterDto.getContextAssignmentId() != null) {

            predicates.add(criteriaBuilder.equal(root.get(EntityAssignment_.entityAssignmentId),
                    filterDto.getContextAssignmentId()));
        }
        if (filterDto.getName() != null) {

            predicates.add(criteriaBuilder.equal(smGateway.get(StructureMaster_.name),
                    filterDto.getName()));
        }

        if (filterDto.getCode() != null) {

            predicates.add(criteriaBuilder.equal(smGateway.get(StructureMaster_.code),
                    filterDto.getCode()));
        }

        if (filterDto.getShortName() != null) {

            predicates.add(criteriaBuilder.equal(smGateway.get(StructureMaster_.shortName),
                    filterDto.getShortName()));
        }

        if (filterDto.getStructureMasterId() != null) {
            predicates.add(criteriaBuilder.equal(smGateway.get(BaseEntity_.id),
                    filterDto.getStructureMasterId()));
        }


        if (filterDto.getSearchQuery() != null) {
            Predicate likeShortName = criteriaBuilder.like(criteriaBuilder.lower(smGateway.get(StructureMaster_.shortName)), "%" + filterDto.getSearchQuery().toLowerCase() + "%");
            Predicate likeName = criteriaBuilder.like(criteriaBuilder.lower(smGateway.get(StructureMaster_.name)), "%" + filterDto.getSearchQuery().toLowerCase() + "%");
            Predicate likeCode = criteriaBuilder.like(criteriaBuilder.lower(smGateway.get(StructureMaster_.code)), "%" + filterDto.getSearchQuery().toLowerCase() + "%");
            predicates.add(criteriaBuilder.or(likeCode, likeShortName, likeName));
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[]{}));
    }

    public String createDynamicQuery(boolean includeSort) {
        StringBuilder q = buildSelect().append(buildFrom())
                .append(buildWhere());
        if (includeSort) {
            q.append(buildOrderBy());
        }

        return q.toString();
    }

    public String createDynamicCountQuery() {
        return buildSelectCount().append(buildFrom()).append(buildWhere()).toString();
    }

    private StringBuilder buildSelectCount() {
        return new StringBuilder("SELECT COUNT(1) ");
    }

    private StringBuilder buildSelect() {
        return new StringBuilder("SELECT ")
                .append(ENTITY_ASSIGNMENT_SHORT_NAME).append(".*");
    }

    private StringBuilder buildFrom() {
        StringBuilder from = new StringBuilder(" FROM ")
                .append(EntityAssignment.TABLE)
                .append(" ").append(ENTITY_ASSIGNMENT_SHORT_NAME).append(" ");
        if (filterDto.getBaseEntity() != null) {
            from.append(INNER_JOIN_CLAUSE).append(baseEntity.getTableName());
            from.append(" ").append(ENTITY_SHORT_NAME).append(" ");
            from.append(ON_CLAUSE).append(ENTITY_ASSIGNMENT_SHORT_NAME).append(DOT_CLAUSE).append(EntityAssignment.COL_REFERENCE_ID)
                    .append(" = ").append(ENTITY_SHORT_NAME).append(DOT_CLAUSE).append(BaseEntity.COL_ID);
        }
        from.append(" ").append(INNER_JOIN_CLAUSE).append(StructureMaster.TABLE).append(" ")
                .append(STRUCTURE_MASTER_SHORT_NAME)
                .append(ON_CLAUSE).append(STRUCTURE_MASTER_SHORT_NAME).append(DOT_CLAUSE).append(BaseEntity.COL_ID).append(" = ")
                .append(ENTITY_ASSIGNMENT_SHORT_NAME).append(DOT_CLAUSE).append(EntityAssignment.COL_ENTITY);
        if (filterDto.getAssignedEntity() != null) {

            from.append(" ").append(LEFT_JOIN_CLAUSE).append(EntityAssignment.TABLE).append(" ").append(PARENT_ENTITY_ASSIGNMENT_SHORT_NAME).append(" ")
                    .append(ON_CLAUSE).append(PARENT_ENTITY_ASSIGNMENT_SHORT_NAME).append(DOT_CLAUSE).append(BaseEntity.COL_ID).append(" = ")
                    .append(ENTITY_ASSIGNMENT_SHORT_NAME).append(DOT_CLAUSE).append(EntityAssignment.COL_ASSIGN_ENTITY);
            from.append(" ").append(INNER_JOIN_CLAUSE).append(StructureMaster.TABLE).append(" ").append(PARENT_STRUCTURE_MASTER_SHORT_NAME).append(" ")
                    .append(ON_CLAUSE).append(PARENT_STRUCTURE_MASTER_SHORT_NAME).append(DOT_CLAUSE).append(BaseEntity.COL_ID).append(" = ")
                    .append(PARENT_ENTITY_ASSIGNMENT_SHORT_NAME).append(DOT_CLAUSE).append(EntityAssignment.COL_ENTITY);
        }
        return from;
    }


    private StringBuilder buildWhere() {
        StringBuilder where = new StringBuilder();
        boolean setWhere = false;
        if (filterDto.getName() != null) {
            where.append(buildWhereCriteria(setWhere, STRUCTURE_MASTER_SHORT_NAME, StructureMaster.COL_NAME, filterDto.getName(), true));
            setWhere = true;
        }
        if (filterDto.getShortName() != null) {
            where.append(buildWhereCriteria(setWhere, STRUCTURE_MASTER_SHORT_NAME, StructureMaster.COL_SHORT_NAME, filterDto.getShortName(), true));
            setWhere = true;
        }
        if (filterDto.getCode() != null) {
            where.append(buildWhereCriteria(setWhere, STRUCTURE_MASTER_SHORT_NAME, StructureMaster.COL_CODE, filterDto.getCode(), true));
            setWhere = true;
        }
        if (filterDto.getStructureMasterId() != null) {
            where.append(buildWhereCriteria(setWhere, STRUCTURE_MASTER_SHORT_NAME, BaseEntity.COL_ID, filterDto.getStructureMasterId(), false));
            setWhere = true;
        }
        if (filterDto.getContextAssignmentId() != null) {
            where.append(buildWhereCriteria(setWhere, ENTITY_ASSIGNMENT_SHORT_NAME, ContextAssignmentBaseEntity.COL_ENTITY_ASSIGNMENT, filterDto.getContextAssignmentId(), false));
            setWhere = true;
        }
        if (filterDto.getActiveStatus() != null) {
            where.append(buildWhereCriteria(setWhere, ENTITY_ASSIGNMENT_SHORT_NAME, EntityAssignment.COL_ACTIVE_STATUS, filterDto.getActiveStatus(), false));
            setWhere = true;
        }
        if (filterDto.getParentAssignmentId() != null) {
            where.append(buildWhereCriteria(setWhere, ENTITY_ASSIGNMENT_SHORT_NAME, EntityAssignment.COL_ASSIGN_ENTITY, filterDto.getParentAssignmentId(), false));
            setWhere = true;

        }
        if (filterDto.getBaseEntity() != null) {
            where.append(buildWhereCriteria(setWhere, STRUCTURE_MASTER_SHORT_NAME, StructureMaster.COL_BUSINESS_ENTITY, filterDto.getBaseEntity(), true));
            setWhere = true;

        }
        if (filterDto.getAssignedEntity() != null) {
            where.append(buildWhereCriteria(setWhere, PARENT_STRUCTURE_MASTER_SHORT_NAME, StructureMaster.COL_BUSINESS_ENTITY, filterDto.getAssignedEntity(), true));
            setWhere = true;

        }

        where.append(buildWhereCriteria(setWhere, STRUCTURE_MASTER_SHORT_NAME, BaseEntity.COL_DELETED, false, false));
        setWhere = true;
        where.append(buildWhereCriteria(setWhere, ENTITY_ASSIGNMENT_SHORT_NAME, BaseEntity.COL_DELETED, false, false));

        if (filterDto.getBaseEntity() != null) {

            where.append(buildWhereCriteria(setWhere, ENTITY_SHORT_NAME, BaseEntity.COL_DELETED, false, false));
        }


        if (filterDto.getSearchQuery() != null) {
            if (setWhere) {
                where.append(AND_CLAUSE);
            }

            where.append(generateFullSearchQuery());
            setWhere = true;

        }
        if (setWhere) {
            return new StringBuilder(" WHERE ").append(where);
        }
        return where;
    }

    private String buildWhereCriteria(boolean setWhere,
                                      String shortName,
                                      String col,
                                      Object val,
                                      boolean isText) {
        StringBuilder where = new StringBuilder(" ");

        if (setWhere) {
            where.append(AND_CLAUSE);
        }
        where.append(shortName).append(DOT_CLAUSE).append(col).append(" = ");
        if (isText) {
            where.append("'");
        }
        where.append(val);
        if (isText) {
            where.append("'");
        }
        return where.toString();
    }


    private StringBuilder buildOrderBy() {
        StringBuilder order = new StringBuilder(" ORDER BY ");
        boolean sortFound = false;
        if (filterDto.getSortBy() != null) {
            for (String sKey : filterDto.getSortBy()) {
                if (sKey != null) {
                    sortFound = true;
                    setOrderKeyItem(order, sKey);
                }
            }
        }

        if (!sortFound) {
            order.append(ENTITY_ASSIGNMENT_SHORT_NAME).append(DOT_CLAUSE).append(BaseEntity.COL_ID).append(" ").append(Sort.Direction.DESC);
        }

        return order;
    }


    private String getSearchQuery(boolean setWhere,
                                  String shortName,
                                  String col,
                                  Object query) {
        StringBuilder searchQ = new StringBuilder(" ");
        if (setWhere) {
            searchQ.append(" OR ");
        }
        searchQ.append(shortName).append(DOT_CLAUSE).append(col).append(" ILIKE ").append("'%")
                .append(query)
                .append("%' ");
        return searchQ.toString();
    }

    private MasterEntity getMasterEntity(BusinessEntity businessEntity) {
        try {
            return businessEntity.getMasterEntityClass().getConstructor().newInstance();
        } catch (Exception e) {
            log.error("Error with constructor");
        }
        throw new GearsException(GearsResponseStatus.ASSIGNMENT_ERROR, "Cannot find/ initiate the entity class. check Business Entity class");
    }

    private String generateFullSearchQuery() {
        StringBuilder searchQ = new StringBuilder(" ( ");
        boolean foundSearchInEntity = false;
        if (filterDto.getBaseEntity() != null) {

            List<String> searchableCols = baseEntity.getSearchableCols();
            if (searchableCols != null && !searchableCols.isEmpty()) {
                foundSearchInEntity = true;
                for (int i = 0; i < searchableCols.size(); i++) {
                    searchQ.append(getSearchQuery(i > 0, ENTITY_SHORT_NAME, camelCaseToUnderscore(searchableCols.get(i)), filterDto.getSearchQuery()));
                }
            }

        }
        if (!foundSearchInEntity) {
            searchQ.append(

                    getSearchQuery(false, STRUCTURE_MASTER_SHORT_NAME, StructureMaster.COL_NAME, filterDto.getSearchQuery())
            );
            searchQ
                    .append(getSearchQuery(true, STRUCTURE_MASTER_SHORT_NAME, StructureMaster.COL_SHORT_NAME, filterDto.getSearchQuery()))
                    .append(getSearchQuery(true, STRUCTURE_MASTER_SHORT_NAME, StructureMaster.COL_CODE, filterDto.getSearchQuery()));

        }

        return searchQ.append(" ) ").toString();
    }


    private void setOrderKeyItem(StringBuilder order, String sKey) {
        if (sKey.toLowerCase().contains("active") || sKey.equals("id")) {

            order.append(ENTITY_ASSIGNMENT_SHORT_NAME).append(DOT_CLAUSE).append(camelCaseToUnderscore(sKey)).append(" ").append(filterDto.getSortDirection()).append(" ");
        } else {
            if (filterDto.getBaseEntity() != null) {

                order.append(ENTITY_SHORT_NAME).append(DOT_CLAUSE).append(camelCaseToUnderscore(sKey)).append(" ").append(filterDto.getSortDirection()).append(" ");
            } else {
                order.append(STRUCTURE_MASTER_SHORT_NAME).append(DOT_CLAUSE).append(StructureMasterUtil.getStructureMasterSortKey(sKey)).append(" ").append(filterDto.getSortDirection()).append(" ");

            }
        }
    }

    private String camelCaseToUnderscore(String value) {
        String regex = "([a-z])([A-Z])";
        String replacement = "$1_$2";
        return value.replaceAll(regex, replacement).toLowerCase();
    }

}
