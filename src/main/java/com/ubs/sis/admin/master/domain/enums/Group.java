package com.ubs.sis.admin.master.domain.enums;

import lombok.Getter;

public enum Group {

    STUDENT_GROUP(1, "Student Group"),
    UNIVERSITY_GROUP(2, "University Group"),
    CAMPUS_GROUP(3, "Campus Group"),
    ANOMALY_DETECTION_GROUP(4, "Anomaly Detection Group"),
    DEPARTMENT_GROUP(5, "Department Group"),
    DEGREE_TYPE_GROUP(6, "Degree Type Group"),
    PROGRAM_GROUP(7, "Program Group"),
    BATCH_GROUP(8, "Batch Group"),
    ACADEMIC_YEAR_GROUP(9, "Academic Year Group"),
    MMS_GROUP(10, "Major, Minor & Specialization Group"),
    SEMESTER_GROUP(11, "Semester Group"),
    RESOURCE_GROUP(12, "Resource Group"),
    COURSE_GROUP(13, "Course Group"),
    COURSE_OFFERING_GROUP(14, "Course Offering Group"),
    ACADEMIC_CALENDAR_GROUP(15, "Academic Year Group"),
    STUDY_PLAN_COURSE_GROUP(16, "Study Plan Course Group"),
    STUDY_PLAN_GROUP(17, "Study Plan Group"),
    ACADEMIC_CALENDAR_EVENT_GROUP(18, "Academic Calendar Event Group"),
    COURSE_OFFERING_COURSE_GROUP(19, "Course Offering Course Group");

    @Getter
    private final int groupId;

    @Getter
    private final String groupName;

    Group(int groupId, String groupName){
        this.groupId = groupId;
        this.groupName = groupName;
    }
}
