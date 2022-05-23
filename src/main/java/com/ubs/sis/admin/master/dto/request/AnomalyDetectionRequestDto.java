package com.ubs.sis.admin.master.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class AnomalyDetectionRequestDto extends BaseAssignedRequestDto {

    private String datasetCode;
    private String datasetLocation;
    private String accessLogLocation;
    private MultipartFile dataset;

    @JsonIgnore
    public MultipartFile getBannerImage2() {
        return dataset;
    }

}
