package com.ubs.sis.admin.config;


import liquibase.changelog.IncludeAllFilter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;

@Slf4j
public class LiquibaseResourceFilter implements IncludeAllFilter {

    @Override
    public boolean include(String changeLogPath) {
        return FilenameUtils.isExtension(changeLogPath, "xml");
    }
}
