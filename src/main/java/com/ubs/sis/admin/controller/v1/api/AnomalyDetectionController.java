package com.ubs.sis.admin.controller.v1.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ubs.commons.dto.response.DeleteResponseDto;
import com.ubs.commons.dto.response.Response;
import com.ubs.sis.admin.auth.annotation.AssignGrant;
import com.ubs.sis.admin.auth.policy.Actions;
import com.ubs.sis.admin.auth.policy.Components;
import com.ubs.sis.admin.form.dto.response.FormStructureResponseDto;
import com.ubs.sis.admin.master.dto.filter.AnomalyDetectionFilterDto;
import com.ubs.sis.admin.master.dto.request.AnomalyDetectionRequestDto;
import com.ubs.sis.admin.master.dto.response.AnomalyDetectionResponseDto;
import com.ubs.sis.admin.master.service.AnomalyDetectionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Validated
@RequestMapping("/api/v1/anomaly-detections")
@CrossOrigin(exposedHeaders = "Access-Control-Allow-Origin")
public class AnomalyDetectionController {
    private static final Logger log = LoggerFactory.getLogger(AnomalyDetectionController.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private final AnomalyDetectionService anomalyDetectionService;

    public AnomalyDetectionController(AnomalyDetectionService anomalyDetectionService) {
        this.anomalyDetectionService = anomalyDetectionService;
    }

    @AssignGrant(component = Components.ANOMALY_DETECTION, action = Actions.CREATE)
    @PostMapping(value = "")
    public Response<AnomalyDetectionResponseDto> createTrainingSet(
            @Valid @ModelAttribute AnomalyDetectionRequestDto anomalyDetectionRequestDto) {

        try {
            log.info("AnomalyDetectionController::createTrainingSet - " + this.objectMapper.writeValueAsString(anomalyDetectionRequestDto));
        } catch (JsonMappingException jmex) {
            return new Response()
                    .setMessage(jmex.getMessage())
                    .setStatusCode(400);
        } catch (JsonProcessingException jpex) {
            return new Response()
                    .setMessage(jpex.getMessage())
                    .setStatusCode(400);
        }

        return anomalyDetectionService.create(anomalyDetectionRequestDto);
    }

    @AssignGrant(component = Components.ANOMALY_DETECTION, action = Actions.VIEW_LIST)
    @GetMapping(value = "")
    public Response<List<AnomalyDetectionResponseDto>> getDatasetDetails(
            @Valid AnomalyDetectionFilterDto filterDto) {

        return anomalyDetectionService.getList(filterDto);
    }

    @AssignGrant(component = Components.ANOMALY_DETECTION, action = Actions.VIEW_ONE)
    @GetMapping(value = "/{datasetId}")
    public Response<AnomalyDetectionResponseDto> getDatasetDetailById(
            @PathVariable long datasetId) {

        return anomalyDetectionService.getDtoById(datasetId);
    }

    @AssignGrant(component = Components.ANOMALY_DETECTION, action = Actions.DELETE)
    @DeleteMapping(value = "/{datasetId}")
    public Response<DeleteResponseDto> deleteDatasetDetail(@PathVariable long datasetId) {
        log.info("AnomalyDetectionController::deleteDatasetDetail - " + datasetId);
        return anomalyDetectionService.delete(datasetId);
    }

    @AssignGrant(component = Components.ANOMALY_DETECTION, action = Actions.VIEW_LIST)
    @GetMapping("/structure")
    public Response<FormStructureResponseDto> getStructure() {
        return anomalyDetectionService.getStructure();
    }
}
