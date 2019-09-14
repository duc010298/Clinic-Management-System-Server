package com.github.duc010298.cms.controller;

import com.github.duc010298.cms.dto.ReportFormNameDTO;
import com.github.duc010298.cms.entity.ReportFormEntity;
import com.github.duc010298.cms.repository.ReportFormRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/")
public class ApiController {

    private ReportFormRepository reportFormRepository;

    @Autowired
    public ApiController(ReportFormRepository reportFormRepository) {
        this.reportFormRepository = reportFormRepository;
    }

    @GetMapping("/GetAllReport")
    public List<ReportFormNameDTO> getAllReport() {
        List<ReportFormEntity> reportFormEntities = reportFormRepository.findAllByOrderByOrderNumberAsc();
        List<ReportFormNameDTO> ret = new ArrayList<>();
        for (ReportFormEntity report : reportFormEntities) {
            ReportFormNameDTO reportDTO = new ReportFormNameDTO();
            reportDTO.setId(report.getId());
            reportDTO.setReportName(report.getReportName());
            ret.add(reportDTO);
        }
        return ret;
    }
}
