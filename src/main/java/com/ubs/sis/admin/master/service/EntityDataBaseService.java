package com.ubs.sis.admin.master.service;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.ubs.commons.dto.mapper.BaseMapper;
import com.ubs.commons.dto.response.BaseResponseDto;
import com.ubs.commons.repository.BaseRepository;
import com.ubs.sis.admin.master.domain.MasterEntity;
import com.ubs.sis.admin.master.domain.enums.BusinessEntity;
import com.ubs.sis.admin.util.Globals;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.context.ApplicationContext;

import java.util.List;

public interface EntityDataBaseService {

    default <T extends BaseResponseDto> void setDataToMasters(List<T> responses, String setterField, String referenceFieldGetter,
                                                              List<Long> ids, BusinessEntity businessEntity) {
        setDataToMasters(responses, setterField, referenceFieldGetter, ids, businessEntity, null);
    }

    default <T extends BaseResponseDto> void setDataToMaster(T response, String setterField, String referenceFieldGetter,
                                                             Long id, BusinessEntity businessEntity, BaseMapper baseMapper) {
        if (id == null) {
            return;
        }

        setterField = StringUtils.defaultIfEmpty(setterField, Globals.KEY_DATA);

        MasterBaseService service = getMasterMapper(businessEntity);
        BaseRepository repository = service.getRepository();
        MasterEntity allById = (MasterEntity) repository.getById(id);

        BaseResponseDto res;
        if (baseMapper == null) {
            res = service.mapEntityToResponseDto(allById);
        } else {
            res = baseMapper.mapEntityToResponseDto(allById);
        }

        BeanWrapper beanWrapper = PropertyAccessorFactory.forBeanPropertyAccess(response);
        beanWrapper.setPropertyValue(setterField, res);
    }


    default <T extends BaseResponseDto> void setDataToMasters(List<T> responses, String setterField, String referenceFieldGetter,
                                                              List<Long> ids, BusinessEntity businessEntity, BaseMapper baseMapper) {
        if (ids != null && !ids.isEmpty()) {
            setterField = StringUtils.defaultIfEmpty(setterField, Globals.KEY_DATA);
            referenceFieldGetter = StringUtils.defaultIfEmpty(referenceFieldGetter, Globals.KEY_REFERENCE_ID);

            MasterBaseService service = getMasterMapper(businessEntity);
            BaseRepository repository = service.getRepository();
            List<MasterEntity> allById = repository.findAllById(ids);


            List<? extends BaseResponseDto> list;
            if (baseMapper == null) {
                list = service.mapEntityListToResponses(allById);
            } else {
                list = baseMapper.mapEntityListToResponseDtoList(allById);
            }
            ImmutableMap<Long, ? extends BaseResponseDto> kvMapForData = Maps.uniqueIndex(list, BaseResponseDto::getId);

            for (BaseResponseDto respons : responses) {
                BeanWrapper beanWrapper = PropertyAccessorFactory.forBeanPropertyAccess(respons);
                beanWrapper.setPropertyValue(setterField,
                        kvMapForData.get(beanWrapper.getPropertyValue(referenceFieldGetter))
                );


            }
        }
    }

    default MasterBaseService getMasterMapper(BusinessEntity businessEntity) {

        if (businessEntity.getServiceClass() != null) {
            return getApplicationContext().getBean(businessEntity.getServiceClass());
        }
        return getApplicationContext().getBean(AnomalyDetectionService.class);
    }


    ApplicationContext getApplicationContext();
}
