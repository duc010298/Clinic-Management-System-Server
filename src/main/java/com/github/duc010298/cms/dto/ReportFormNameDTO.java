package com.github.duc010298.cms.dto;

import java.util.UUID;

public class ReportFormNameDTO {
    private UUID id;
    private String reportName;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }
}
