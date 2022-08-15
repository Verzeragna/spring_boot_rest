package com.andreev.spring.rest.service;

import com.andreev.spring.rest.dao.report.Report;
import com.andreev.spring.rest.dao.report.ReportRepository;
import com.andreev.spring.rest.dao.schema.SchemaRepository;
import com.andreev.spring.rest.dto.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;
import java.util.Optional;

@Service
public class ReportService {

    private final ReportRepository reportRepository;
    private final SchemaRepository schemaRepository;

    private static final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    public ReportService(ReportRepository reportRepository, SchemaRepository schemaRepository) {
        this.reportRepository = reportRepository;
        this.schemaRepository = schemaRepository;
    }

    public Mono<ResponseEntity<Object>> getReportById(Integer id) {
        return Mono.fromCallable(() -> {
            if (id != null) {
                Optional<Report> optionalReport = reportRepository.findById(id);
                if (optionalReport.isEmpty()) {
                    return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
                }
                Report report = optionalReport.get();
                ReportDto reportDto = convertReportToDto(report);
                return new ResponseEntity<Object>(reportDto, HttpStatus.CREATED);
            } else return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }).publishOn(Schedulers.boundedElastic());
    }

    private ReportDto convertReportToDto(Report report) throws JsonProcessingException {
        ReportDto reportDto = new ReportDto();
        reportDto.setReportId(report.getReportId());
        reportDto.setTableAmount(reportDto.getTableAmount());
        reportDto.setTables(mapper.readValue(report.getTables(), new TypeReference<>() {
        }));
        return reportDto;
    }

    public Mono<ResponseEntity<Void>> createReport(ReportDto reportDto) {
        return Mono.fromCallable(() -> {
            if (reportDto != null) {
                if (reportDto.getTableAmount() != reportDto.getTables().size()) {
                    return new ResponseEntity<Void>(HttpStatus.NOT_ACCEPTABLE);
                }
                for (TableInfo tableInfo : reportDto.getTables()) {
                    List<TableStructureDto> tables = schemaRepository.getTableByName(tableInfo.getTableName());
                    if (tables.isEmpty()) {
                        return new ResponseEntity<Void>(HttpStatus.NOT_ACCEPTABLE);
                    }
                    for (TableColumn tableColumn : tableInfo.getColumns()) {
                        boolean isColumnExist = tables.stream().anyMatch(column -> column.getColumnname()
                                .equals(tableColumn.getTitle()));
                        if (!isColumnExist) {
                            return new ResponseEntity<Void>(HttpStatus.NOT_ACCEPTABLE);
                        }
                        boolean isColumnType = tables.stream().anyMatch(column -> column.getDatatype()
                                .equals(tableColumn.getType()));
                        if (!isColumnType) {
                            return new ResponseEntity<Void>(HttpStatus.NOT_ACCEPTABLE);
                        }
                    }
                }
                try {
                    Report report = convertReportDtoToReport(reportDto);
                    reportRepository.save(report);
                    return new ResponseEntity<Void>(HttpStatus.CREATED);
                } catch (Exception ex) {
                    return new ResponseEntity<Void>(HttpStatus.NOT_ACCEPTABLE);
                }
            } else return new ResponseEntity<Void>(HttpStatus.NOT_ACCEPTABLE);
        }).publishOn(Schedulers.boundedElastic());
    }

    private Report convertReportDtoToReport(ReportDto reportDto) throws JsonProcessingException {
        Report report = new Report();
        report.setReportId(reportDto.getReportId());
        report.setTableAmount(reportDto.getTableAmount());
        report.setTables(mapper.writeValueAsString(reportDto.getTables()));
        return report;
    }
}
