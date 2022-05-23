package com.ubs.sis.admin.master.service;

import com.ubs.commons.dto.response.BaseResponseDto;
import com.ubs.commons.dto.response.ErrorResponse;
import com.ubs.commons.exception.GearsException;
import com.ubs.commons.exception.GearsResponseStatus;
import com.ubs.commons.service.BaseService;
import com.ubs.sis.admin.master.domain.EntityAssignment;
import com.ubs.sis.admin.master.domain.MasterEntity;
import com.ubs.sis.admin.master.domain.StructureMaster;
import com.ubs.sis.admin.master.domain.enums.BusinessEntity;
import com.ubs.sis.admin.master.dto.filter.BaseAssignmentFilterDto;
import com.ubs.sis.admin.master.dto.filter.EntityAssignmentFilterDto;
import com.ubs.sis.admin.master.dto.request.BaseAssignedRequestDto;
import com.ubs.sis.admin.master.dto.request.StructureMasterRequestDto;
import com.ubs.sis.admin.master.repository.EntityAssignmentRepository;
import com.ubs.sis.admin.master.repository.StructureMasterRepository;
import com.ubs.sis.admin.master.repository.specification.EntityAssignmentParentSpecification;
import com.ubs.sis.admin.master.repository.specification.EntityAssignmentSpecification;
import com.ubs.sis.admin.master.repository.specification.EntityContextAssignmentSpecification;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import java.util.Collections;
import java.util.List;

public interface MasterBaseService
        <Entity extends MasterEntity, RequestDto extends BaseAssignedRequestDto,
                ResponseDto extends BaseResponseDto, FilterDto extends BaseAssignmentFilterDto>
        extends BaseService<Entity, RequestDto, ResponseDto, FilterDto> {

    default StructureMaster setDataToStructureMaster(StructureMaster structureMaster, StructureMasterRequestDto structureMasterRequestDto) {
        structureMaster.setName(ObjectUtils.defaultIfNull(structureMasterRequestDto.getName(), structureMaster.getName()));
        structureMaster.setName2L(ObjectUtils.defaultIfNull(structureMasterRequestDto.getName2L(), structureMaster.getName2L()));
        structureMaster.setBusinessEntity(ObjectUtils.defaultIfNull(structureMasterRequestDto.getBusinessEntity(), structureMaster.getBusinessEntity()));
        structureMaster.setTenantId(ObjectUtils.defaultIfNull(structureMasterRequestDto.getTenantId(), structureMaster.getTenantId()));
        structureMaster.setShortName(ObjectUtils.defaultIfNull(structureMasterRequestDto.getShortName(), structureMaster.getShortName()));
        structureMaster.setCode(ObjectUtils.defaultIfNull(structureMasterRequestDto.getCode(), structureMaster.getCode()));
        structureMaster.setActiveStatus(ObjectUtils.defaultIfNull(structureMasterRequestDto.isActiveStatus(), structureMaster.isActiveStatus()));
        structureMaster.setEntityAssignmentId(ObjectUtils.defaultIfNull(structureMasterRequestDto.getContextAssignmentId(), structureMaster.getEntityAssignmentId()));
        return structureMaster;
    }


    default StructureMaster saveToMaster(Entity entity, StructureMasterRequestDto data) {
        StructureMaster structureMaster = new StructureMaster(entity.getId());
        setDataToStructureMaster(structureMaster, data);
        getStructureMasterRepository().save(structureMaster);
        entity.setStructureMaster(structureMaster);
        getRepository().save(entity);
        return structureMaster;
    }

    default void validateForAssignments(List<StructureMaster> structureMasters, Boolean activeState) {
        if (structureMasters != null) {

            for (StructureMaster structureMaster : structureMasters) {
                long count = getEntityAssignmentRepository().count(new EntityAssignmentParentSpecification(structureMaster.getId(), activeState));
                if (count > 0) {
                    throw new GearsException(GearsResponseStatus.DATA_REMOVING_ERROR, new ErrorResponse(), "Some entities are assigned to this " + structureMaster.getBusinessEntity().name());
                }
                long assignmentCount = getEntityAssignmentRepository().count(new EntityAssignmentSpecification(
                        new EntityAssignmentFilterDto().setStructureMasterId(structureMaster.getId())
                ));
                if (assignmentCount > 0) {
                    throw new GearsException(GearsResponseStatus.DATA_REMOVING_ERROR, "This entity is assigned to some entities." +
                            " Please un assign them first. ");
                }
            }

        }
    }

    @Override
    default Page<Entity> findAll(FilterDto filterDto, PageRequest pageRequest) {
        Specification<Entity> specification =
                Specification.where(createAssignmentFilterSpecification(filterDto)).and(
                        this.createSpecification(filterDto)
                );

        return this.getRepository().findAll(specification, pageRequest);
    }

    private Specification<Entity> createAssignmentFilterSpecification(FilterDto filterDto) {
        if (filterDto.getContextAssignmentId() != null) {

            return new EntityContextAssignmentSpecification<>(filterDto);
        }
        return null;
    }

    @Override
    default void preCreateEntity(Entity entity, RequestDto requestDto) {
        BaseService.super.preCreateEntity(entity, requestDto);
        if (!BusinessEntity.AD.equals(getEntityType()) && requestDto.getContextAssignmentId() != null) {
            EntityAssignment entityAssignment = getEntityAssignmentRepository().findById(requestDto.getContextAssignmentId()).orElseThrow(
                    () -> new GearsException(GearsResponseStatus.BAD_REQUEST, "Assignment not found")
            );
            if (entityAssignment.getParent() != null && shouldAssignedToParentContextId()) {
                throw new GearsException(GearsResponseStatus.BAD_REQUEST, "Assignment not matching");
            }
        }

    }

    /**
     * @return where this entity should be assigned to parent or child context level
     */
    default boolean shouldAssignedToParentContextId() {
        return true;
    }


    BusinessEntity getEntityType();


    @Override
    default void postCreateEntity(Entity entity, RequestDto requestDto) {
        StructureMasterRequestDto structureMasterRequest = getStructureMasterRequestLocal(entity, requestDto);
        saveToMaster(entity, structureMasterRequest);
    }

    @Override
    default void preUpdateEntity(Entity entity, RequestDto requestDto) {
        StructureMaster structureMaster = entity.getStructureMaster();
        if (structureMaster != null) {
            boolean activeStateChanged = structureMaster.isActiveStatus()
                    && !getStructureMasterRequestLocal(entity, requestDto).isActiveStatus();

            requestDto.setActiveStatusChanged(structureMaster.isActiveStatus()
                    !=getStructureMasterRequestLocal(entity, requestDto).isActiveStatus());
            if (structureMaster.getId() != null
                    && activeStateChanged) {
                try {
                    if (structureMaster.getBusinessEntity().isRoot()) {
                        validateForActiveChildren(structureMaster);

                    } else {

                        validateForAssignments(Collections.singletonList(structureMaster), true);
                    }
                } catch (GearsException e) {
                    throw new GearsException(GearsResponseStatus.DATA_SAVING_ERROR, e.getMessage());
                }
            }

        }

    }

    default void validateForActiveChildren(StructureMaster structureMaster) {
        getEntityAssignmentRepository().findOne(
                new EntityAssignmentSpecification(
                        new EntityAssignmentFilterDto().setStructureMasterId(structureMaster.getId())
                )
        ).ifPresent(
                entityAssignment -> {
                    long count = getEntityAssignmentRepository().count(new EntityAssignmentSpecification(
                            new EntityAssignmentFilterDto()
                                    .setActiveStatus(true)
                                    .setParentAssignmentId(entityAssignment.getId())));
                    if (count > 0) {
                        throw new GearsException(GearsResponseStatus.DATA_SAVING_ERROR,
                                String.format("%d %s %s assigned to this", count,
                                        count > 1 ? "campuses" : "campus",
                                        count > 1 ? "are" : "is"
                                ));
                    }
                }
        );
    }

    @Override
    default void postUpdateEntity(Entity entity, RequestDto requestDto) {

        StructureMaster structureMaster = entity.getStructureMaster();
        boolean emptySM = structureMaster == null;
        structureMaster = emptySM ? new StructureMaster(entity.getId()) : structureMaster;
        structureMaster = setDataToStructureMaster(structureMaster, getStructureMasterRequestLocal(entity, requestDto));
        getStructureMasterRepository().save(structureMaster);
        if (emptySM) {
            entity.setStructureMaster(structureMaster);
            getRepository().save(entity);
        }
        if (requestDto.isActiveStatusChanged() && shouldAssignmentsChangeStatus()) {
            updateAssignmentStatus(entity, requestDto);
        }
    }




    private StructureMasterRequestDto getStructureMasterRequestLocal(Entity entity, RequestDto requestDto) {
        StructureMasterRequestDto structureMasterRequest = getStructureMasterRequest(entity, requestDto);
        structureMasterRequest.setContextAssignmentId(requestDto.getContextAssignmentId());
        return structureMasterRequest;
    }

    StructureMasterRequestDto getStructureMasterRequest(Entity entity, RequestDto requestDto);

    @Override
    default void preDelete(Entity entity) {
        StructureMaster structureMaster = entity.getStructureMaster();
        if (structureMaster != null && structureMaster.getBusinessEntity().isRoot()) {
            validateForActiveChildren(structureMaster);
        } else {
            validateEntityAssignmentsBeforeDelete(entity.getId());
        }
    }

    @Override
    default void postDelete(Entity entity) {
        StructureMaster structureMaster = entity.getStructureMaster();
        if (structureMaster != null) {
            getStructureMasterRepository().delete(structureMaster);
        }

    }

    default void validateEntityAssignmentsBeforeDelete(Long id) {
        Entity entity = getRepository().findById(id).orElse(null);
        if (entity != null && entity.getStructureMaster() != null) {
            validateForAssignments(Collections.singletonList(entity.getStructureMaster()), null);
        }
    }

    default void validateStructureMasterEntityForEntityType(Long structureMasterId, BusinessEntity businessEntity) {
        checkForNull(structureMasterId, businessEntity);

        StructureMaster structureMaster = getStructureMasterRepository().findById(structureMasterId).orElseThrow(
                () -> new GearsException(GearsResponseStatus.NOT_FOUND, String.format("%s : %d not found", businessEntity.name(), structureMasterId))
        );
        checkForType(businessEntity, structureMaster.getBusinessEntity(), true);

    }

    default void validateAssignmentEntityForEntityType(Long assignmentEntityId, BusinessEntity businessEntity, BusinessEntity parentBusinessEntity) {
        checkForNull(assignmentEntityId, businessEntity);
        EntityAssignment entityAssignment = getEntityAssignmentRepository().findById(assignmentEntityId).orElseThrow(
                () -> new GearsException(GearsResponseStatus.NOT_FOUND, String.format("%s : %d not found", businessEntity.name(), assignmentEntityId))
        );
        checkForType(businessEntity, entityAssignment.getBaseBusinessEntity(), true);
        if (parentBusinessEntity != null) {
            boolean error = checkForType(parentBusinessEntity, entityAssignment.getAssignedBusinessEntity(), false);
            if (error) {
                throw new GearsException(GearsResponseStatus.INVALID_DATA,
                        String.format("Passed assignment is not matching with expected parent." +
                                        " Actual parent : %s. But expected Parent: %s", entityAssignment.getAssignedBusinessEntity(),
                                parentBusinessEntity.name()));
            }
        }

    }

    private void checkForNull(Long id, BusinessEntity businessEntity) {
        if (id == null) {
            throw new GearsException(GearsResponseStatus.INVALID_DATA, String.format("%s : %d not found", businessEntity.name(), id));
        }
    }

    private boolean checkForType(BusinessEntity expected, BusinessEntity actual, boolean throwIfError) {
        boolean error = !expected.equals(actual);
        if (error && throwIfError) {
            throw new GearsException(GearsResponseStatus.INVALID_DATA,
                    String.format("Passed actual master object type %s. But expected %s type", actual.name(),
                            expected.name()));
        }
        return error;
    }

    default boolean shouldAssignmentsChangeStatus() {
        return false;
    }

    private void updateAssignmentStatus(Entity entity, RequestDto requestDto){
        EntityAssignmentFilterDto filterDto=new EntityAssignmentFilterDto();
        filterDto.setStructureMasterId(entity.getStructureMaster().getId());
        List<EntityAssignment> all = getEntityAssignmentRepository().findAll(new EntityAssignmentSpecification(filterDto));
        for (EntityAssignment entityAssignment : all) {
            entityAssignment.setActiveStatus(entity.getStructureMaster().isActiveStatus());
        }
        getEntityAssignmentRepository().saveAll(all);
    }
    StructureMasterRepository getStructureMasterRepository();

    EntityAssignmentRepository getEntityAssignmentRepository();
}
