package com.andreev.spring.rest.service;

import com.andreev.spring.rest.dao.singlequery.SingleQuery;
import com.andreev.spring.rest.dao.tablequery.TableQuery;
import com.andreev.spring.rest.dao.tablequery.TableQueryRepository;
import lombok.extern.jbosslog.JBossLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;
import java.util.Optional;

@Service
@JBossLog
public class TableQueryService {

    private final TableQueryRepository tableQueryRepository;
    private final SchemaService schemaService;

    @Autowired
    public TableQueryService(TableQueryRepository tableQueryRepository, SchemaService schemaService) {
        this.tableQueryRepository = tableQueryRepository;
        this.schemaService = schemaService;
    }

    public Mono<ResponseEntity<Void>> addNewQueryToTable(TableQuery tableQuery) {
        return Mono.fromCallable(() -> {
            try {
                tableQueryRepository.saveAndFlush(tableQuery);
                return new ResponseEntity<Void>(HttpStatus.CREATED);
            } catch (Exception ex) {
                log.errorv("Can not add query: {0}", ex);
                return new ResponseEntity<Void>(HttpStatus.NOT_ACCEPTABLE);
            }
        }).publishOn(Schedulers.boundedElastic());
    }

    public Mono<ResponseEntity<Void>> modifyQueryInTable(TableQuery tableQuery) {
        return Mono.fromCallable(() -> {
            try {
                tableQueryRepository.saveAndFlush(tableQuery);
                return new ResponseEntity<Void>(HttpStatus.OK);
            } catch (Exception ex) {
                log.errorv("Can not update query: {0}", ex);
                return new ResponseEntity<Void>(HttpStatus.NOT_ACCEPTABLE);
            }
        }).publishOn(Schedulers.boundedElastic());
    }

    public Mono<ResponseEntity<Void>> deleteQueryById(Long id) {
        return Mono.fromCallable(() -> {
            try {
                tableQueryRepository.deleteById(id);
                return new ResponseEntity<Void>(HttpStatus.ACCEPTED);
            } catch (Exception ex) {
                log.errorv("Can not delete query: {0}", ex);
                return new ResponseEntity<Void>(HttpStatus.NOT_ACCEPTABLE);
            }
        }).publishOn(Schedulers.boundedElastic());
    }

    public Mono<ResponseEntity<Void>> executeTableQueryById(Long id) {
        return Mono.fromCallable(() -> {
            if (id != null) {
                try {
                    Optional<TableQuery> optionalSingleQuery = tableQueryRepository.findById(id);
                    if (optionalSingleQuery.isEmpty()) {
                        return new ResponseEntity<Void>(HttpStatus.NOT_ACCEPTABLE);
                    }
                    TableQuery tableQuery = optionalSingleQuery.get();
                    schemaService.executeCustomQuery(tableQuery.getQuery());
                    return new ResponseEntity<Void>(HttpStatus.ACCEPTED);
                } catch (Exception ex) {
                    return new ResponseEntity<Void>(HttpStatus.NOT_ACCEPTABLE);
                }
            } else return new ResponseEntity<Void>(HttpStatus.NOT_ACCEPTABLE);
        }).publishOn(Schedulers.boundedElastic());
    }

    public Mono<ResponseEntity<Object>> getAllQueriesByTableName(String name) {
        return Mono.fromCallable(() -> {
            if (name != null) {
                try {
                    List<SingleQuery> queries = tableQueryRepository.findAllByTableName(name);
                    return new ResponseEntity<Object>(queries, HttpStatus.OK);
                } catch (Exception ex) {
                    return new ResponseEntity<>(null, HttpStatus.OK);
                }
            } else return new ResponseEntity<>(null, HttpStatus.OK);
        }).publishOn(Schedulers.boundedElastic());
    }

    public Mono<ResponseEntity<Object>> getTableQueryById(Long id) {
        return Mono.fromCallable(() -> {
            if (id != null) {
                Optional<TableQuery> optionalSingleQuery = tableQueryRepository.findById(id);
                if (optionalSingleQuery.isEmpty()) {
                    return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
                }
                TableQuery tableQuery = optionalSingleQuery.get();
                return new ResponseEntity<Object>(tableQuery, HttpStatus.OK);
            } else return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }).publishOn(Schedulers.boundedElastic());
    }

    public Mono<ResponseEntity<List<TableQuery>>> getAllTableQueries() {
        return Mono.fromCallable(() -> {
            List<TableQuery> tableQueries = tableQueryRepository.findAll();
            return new ResponseEntity<>(tableQueries, HttpStatus.OK);
        }).publishOn(Schedulers.boundedElastic());
    }
}
