package com.ubs.sis.admin.master.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ubs.commons.dto.mapper.BaseMapper;
import com.ubs.commons.dto.response.Response;
import com.ubs.commons.exception.GearsException;
import com.ubs.commons.exception.GearsResponseStatus;
import com.ubs.commons.repository.BaseRepository;
import com.ubs.sis.admin.form.dto.response.FormStructureResponseDto;
import com.ubs.sis.admin.form.repository.*;
import com.ubs.sis.admin.form.service.impl.CustomFormsAbstractService;
import com.ubs.sis.admin.master.domain.AnomalyDetection;
import com.ubs.sis.admin.master.domain.enums.CustomFormSupportedEntity;
import com.ubs.sis.admin.master.dto.filter.AnomalyDetectionFilterDto;
import com.ubs.sis.admin.master.dto.mapper.AnomalyDetectionMapper;
import com.ubs.sis.admin.master.dto.request.AnomalyDetectionRequestDto;
import com.ubs.sis.admin.master.dto.request.StructureMasterRequestDto;
import com.ubs.sis.admin.master.dto.response.AnomalyDetectionResponseDto;
import com.ubs.sis.admin.master.repository.*;
import com.ubs.sis.admin.master.repository.specification.AnomalyDetectionSpecification;
import com.ubs.sis.admin.master.service.AnomalyDetectionService;
import lombok.SneakyThrows;
import net.minidev.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.File;

@Service
public class AnomalyDetectionServiceImpl extends CustomFormsAbstractService implements AnomalyDetectionService {
    private final Logger log = LoggerFactory.getLogger(AnomalyDetectionServiceImpl.class);

    private final StructureMasterRepository structureMasterRepository;
    private final AnomalyDetectionMapper modelMapper;
    private final AnomalyDetectionRepository anomalyDetectionRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private String anomalyDetectionCoreMLServerURL = "http://localhost:5000/model_prep_realtime_predictions";

    private final RestTemplate restTemplate;
    HttpHeaders headers = new HttpHeaders();


    public AnomalyDetectionServiceImpl(
            AnomalyDetectionMapper modelMapper,
            StructureMasterRepository structureMasterRepository,
            FormRepository formRepository,
            FormFieldRepository formFieldRepository,
            FormHasFieldRepository formHasFieldRepository,
            RecordValueRepository recordValueRepository,
            FormRecordRepository formRecordRepository,
            AnomalyDetectionRepository anomalyDetectionRepository,
            RestTemplate restTemplate) {
        super(formRepository,
                formFieldRepository, formHasFieldRepository,
                recordValueRepository,
                formRecordRepository);
        this.modelMapper = modelMapper;
        this.structureMasterRepository = structureMasterRepository;
        this.anomalyDetectionRepository = anomalyDetectionRepository;
        this.restTemplate = restTemplate;
    }

    @Override
    @SneakyThrows
    public void postCreateEntity(AnomalyDetection entity, AnomalyDetectionRequestDto requestDto) {
        log.info("machine learning model training started");

        File datasetLocation = new File(requestDto.getDatasetLocation());
        if (!datasetLocation.exists())
            throw new GearsException(GearsResponseStatus.NOT_FOUND, requestDto.getDatasetLocation() +
                    " file does not exists!");

        File accessLogLocation = new File(requestDto.getAccessLogLocation());
        if (!accessLogLocation.exists())
            throw new GearsException(GearsResponseStatus.NOT_FOUND, requestDto.getAccessLogLocation() +
                    " file does not exists!");

        headers.setContentType(MediaType.APPLICATION_JSON);
        JSONObject personJsonObject = new JSONObject();
        personJsonObject.put("datasetCode", entity.getDatasetCode());
        personJsonObject.put("datasetLocation", entity.getDatasetLocation());
        personJsonObject.put("accessLogLocation", entity.getAccessLogLocation());
        HttpEntity<String> request =
                new HttpEntity<>(personJsonObject.toString(), headers);

        String personResultAsJsonStr =
                restTemplate.postForObject(anomalyDetectionCoreMLServerURL, request, String.class);
        JsonNode root = objectMapper.readTree(personResultAsJsonStr);

        log.info(String.valueOf(root));

    }

    @Override
    public void preCreateEntity(AnomalyDetection entity, AnomalyDetectionRequestDto requestDto) {
        AnomalyDetectionService.super.preCreateEntity(entity, requestDto);


    }

    @Override
    public Response<FormStructureResponseDto> getStructure() {
        return getResponse(getFormStructureResponse(CustomFormSupportedEntity.ANOMALY_DETECTION_FORM));

    }

    @Override
    public BaseRepository<AnomalyDetection> getRepository() {
        return anomalyDetectionRepository;
    }

    @Override
    public BaseMapper<AnomalyDetection, AnomalyDetectionRequestDto, AnomalyDetectionResponseDto> getModelMapper() {
        return modelMapper;
    }

    @Override
    public AnomalyDetection validateAndMapToEntityForSave(AnomalyDetectionRequestDto requestDto) {
        AnomalyDetection entity = modelMapper.mapRequestDtoToEntity(requestDto);

        return entity;
    }

    @Override
    public AnomalyDetection validateAndMapToEntityForUpdate(AnomalyDetectionRequestDto requestDto, AnomalyDetection entity) {
        return null;
    }

    @Override
    public StructureMasterRequestDto getStructureMasterRequest(AnomalyDetection entity, AnomalyDetectionRequestDto requestDto) {
        return null;
    }

    @Override
    public StructureMasterRepository getStructureMasterRepository() {
        return structureMasterRepository;
    }

    @Override
    public EntityAssignmentRepository getEntityAssignmentRepository() {
        return null;
    }

    @Override
    public Specification<AnomalyDetection> createSpecification(AnomalyDetectionFilterDto filterDto) {
        return new AnomalyDetectionSpecification(filterDto);
    }
}
