package com.ubs.sis.admin.master.domain;

import com.ubs.commons.domain.BaseEntity;
import com.ubs.sis.admin.util.Globals;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = AnomalyDetection.TABLE)
@SQLDelete(sql = "UPDATE " + AnomalyDetection.TABLE + " SET " + BaseEntity.COL_DELETED + " = true WHERE id = ?")
@Where(clause = BaseEntity.COMMON_DELETE_CLAUSE)
public class AnomalyDetection extends MasterEntity {
    public static final String TABLE = "anomaly_detection";

    public static final String COL_DATASET_CODE = "dataset_code";
    public static final String FIELD_DATASET_CODE = "datasetCode";
    public static final String COL_DATASET_LOCATION = "dataset_location";
    public static final String FIELD_DATASET_LOCATION = "datasetLocation";
    public static final String COL_ACCESS_LOG_LOCATION = "access_log_location";
    public static final String FIELD_ACCESS_LOG_LOCATION = "accessLogLocation";


    @Column(name = COL_DATASET_CODE, length = 100)
    private String datasetCode;
    @Column(name = COL_DATASET_LOCATION, length = 1000)
    private String datasetLocation;
    @Column(name = COL_ACCESS_LOG_LOCATION, length = 1000)
    private String accessLogLocation;

    @Override
    public List<String> getSearchableCols() {
        return Arrays.asList(COL_DATASET_CODE);
    }

    @Override
    public String getTableName() {
        return TABLE;
    }
}
