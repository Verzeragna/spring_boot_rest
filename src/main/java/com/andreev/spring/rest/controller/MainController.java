package com.andreev.spring.rest.controller;

import com.andreev.spring.rest.dao.singlequery.SingleQuery;
import com.andreev.spring.rest.dao.tablequery.TableQuery;
import com.andreev.spring.rest.dto.ReportDto;
import com.andreev.spring.rest.dto.TableDto;
import com.andreev.spring.rest.dto.TableInfo;
import com.andreev.spring.rest.service.ReportService;
import com.andreev.spring.rest.service.SchemaService;
import com.andreev.spring.rest.service.SingleQueryService;
import com.andreev.spring.rest.service.TableQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/api")
public class MainController {

    private final SchemaService schemaService;
    private final TableQueryService tableQueryService;
    private final SingleQueryService singleQueryService;
    private final ReportService reportService;

    @Autowired
    public MainController(SchemaService schemaService, TableQueryService tableQueryService, SingleQueryService singleQueryService, ReportService reportService) {
        this.schemaService = schemaService;
        this.tableQueryService = tableQueryService;
        this.singleQueryService = singleQueryService;
        this.reportService = reportService;
    }

    @PostMapping("/table/create-table")
    public Mono<ResponseEntity<Void>> createTable(@RequestBody List<TableDto> tableDtoList) {
        return schemaService.createTable(tableDtoList);
    }

    @GetMapping("/table/get-table-by-name/{name}")
    public Mono<ResponseEntity<Object>> getTableByName(@PathVariable String name) {
        return schemaService.getTableByName(name);
    }

    @DeleteMapping("/table/drop-table-by-name/{name}")
    public Mono<ResponseEntity<Void>> deleteTableByName(@PathVariable String name) {
        return schemaService.deleteTableByName(name);
    }

    @PostMapping("/table-query/add-new-query-to-table")
    public Mono<ResponseEntity<Void>> addNewQueryToTable(@RequestBody TableQuery tableQuery) {
        return tableQueryService.addNewQueryToTable(tableQuery);
    }

    @PutMapping("/table-query/modify-query-in-table")
    public Mono<ResponseEntity<Void>> modifyQueryInTable(@RequestBody TableQuery tableQuery) {
        return tableQueryService.modifyQueryInTable(tableQuery);
    }

    @DeleteMapping("/table-query/delete-table-query-by-id/{id}")
    public Mono<ResponseEntity<Void>> deleteQueryById(@PathVariable Long id) {
        return tableQueryService.deleteQueryById(id);
    }

    @GetMapping("/table-query/execute-table-query-by-id/{id}")
    public Mono<ResponseEntity<Void>> executeTableQueryById(@PathVariable Long id) {
        return tableQueryService.executeTableQueryById(id);
    }

    @GetMapping("/table-query/get-all-queries-by-table-name/{name}")
    public Mono<ResponseEntity<Object>> getAllQueriesByTableName(@PathVariable String name) {
        return tableQueryService.getAllQueriesByTableName(name);
    }

    @GetMapping("/table-query/get-table-query-by-id/{id}")
    public Mono<ResponseEntity<Object>> getTableQueryById(@PathVariable Long id) {
        return tableQueryService.getTableQueryById(id);
    }

    @GetMapping("/table-query/get-all-table-queries")
    public Mono<ResponseEntity<List<TableQuery>>> getAllTableQueries() {
        return tableQueryService.getAllTableQueries();
    }

    @PostMapping("/single-query/add-new-query")
    public Mono<ResponseEntity<Void>> addNewQuery(@RequestBody SingleQuery singleQuery) {
        return singleQueryService.addNewQuery(singleQuery);
    }

    @PutMapping("/single-query/modify-single-query")
    public Mono<ResponseEntity<Void>> modifySingleQuery(@RequestBody SingleQuery singleQuery) {
        return singleQueryService.modifySingleQuery(singleQuery);
    }

    @DeleteMapping("/single-query/delete-single-query-by-id/{id}")
    public Mono<ResponseEntity<Void>> deleteSingleQueryById(@PathVariable Long id) {
        return singleQueryService.deleteSingleQueryById(id);
    }

    @GetMapping("/single-query/execute-single-query-by-id/{id}")
    public Mono<ResponseEntity<Void>> executeSingleQueryById(@PathVariable Long id) {
        return singleQueryService.executeSingleQueryById(id);
    }

    @GetMapping("/single-query/get-single-query-by-id/{id}")
    public Mono<ResponseEntity<Object>> getSingleQueryById(@PathVariable Long id) {
        return singleQueryService.getSingleQueryById(id);
    }

    @GetMapping("/single-query/get-all-single-queries")
    public Mono<ResponseEntity<List<SingleQuery>>> getAllSingleQueries() {
        return singleQueryService.getAllSingleQueries();
    }

    @GetMapping("/report/get-report-by-id/{id}")
    public Mono<ResponseEntity<Object>> getReportById(@PathVariable Integer id) {
        return reportService.getReportById(id);
    }

    @PostMapping("/report/create-report")
    public Mono<ResponseEntity<Void>> createReport(@RequestBody ReportDto reportDto) {
        return reportService.createReport(reportDto);
    }
}
