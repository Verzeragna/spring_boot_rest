package com.andreev.spring.rest.service;

import com.andreev.spring.rest.dao.singlequery.SingleQuery;
import com.andreev.spring.rest.dao.singlequery.SingleQueryRepository;
import lombok.extern.jbosslog.JBossLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;
import java.util.Optional;

@Service
@JBossLog
public class SingleQueryService {

    private final SingleQueryRepository singleQueryRepository;
    private final SchemaService schemaService;

    @Autowired
    public SingleQueryService(SingleQueryRepository singleQueryRepository, SchemaService schemaService) {
        this.singleQueryRepository = singleQueryRepository;
        this.schemaService = schemaService;
    }

    public Mono<ResponseEntity<Void>> addNewQuery(SingleQuery singleQuery) {
        return Mono.fromCallable(() -> {
            if (singleQuery != null) {
                try {
                    singleQueryRepository.saveAndFlush(singleQuery);
                    return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
                } catch (Exception ex) {
                    return new ResponseEntity<Void>(HttpStatus.CREATED);
                }
            } else return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        }).publishOn(Schedulers.boundedElastic());
    }

    public Mono<ResponseEntity<Void>> modifySingleQuery(SingleQuery singleQuery) {
        return Mono.fromCallable(() -> {
            if (singleQuery != null) {
                try {
                    singleQueryRepository.saveAndFlush(singleQuery);
                    return new ResponseEntity<Void>(HttpStatus.NOT_ACCEPTABLE);
                } catch (Exception ex) {
                    return new ResponseEntity<Void>(HttpStatus.OK);
                }
            } else return new ResponseEntity<Void>(HttpStatus.NOT_ACCEPTABLE);
        }).publishOn(Schedulers.boundedElastic());
    }

    public Mono<ResponseEntity<Void>> deleteSingleQueryById(@PathVariable Long id) {
        return Mono.fromCallable(() -> {
            if (id != null) {
                try {
                    singleQueryRepository.deleteById(id);
                    return new ResponseEntity<Void>(HttpStatus.NOT_ACCEPTABLE);
                } catch (Exception ex) {
                    return new ResponseEntity<Void>(HttpStatus.ACCEPTED);
                }
            } else return new ResponseEntity<Void>(HttpStatus.NOT_ACCEPTABLE);
        }).publishOn(Schedulers.boundedElastic());
    }

    public Mono<ResponseEntity<Void>> executeSingleQueryById(Long id) {
        return Mono.fromCallable(() -> {
            if (id != null) {
                try {
                    Optional<SingleQuery> optionalSingleQuery = singleQueryRepository.findById(id);
                    if (optionalSingleQuery.isEmpty()) {
                        return new ResponseEntity<Void>(HttpStatus.NOT_ACCEPTABLE);
                    }
                    SingleQuery singleQuery = optionalSingleQuery.get();
                    schemaService.executeCustomQuery(singleQuery.getQuery());
                    return new ResponseEntity<Void>(HttpStatus.CREATED);
                } catch (Exception ex) {
                    return new ResponseEntity<Void>(HttpStatus.NOT_ACCEPTABLE);
                }
            } else return new ResponseEntity<Void>(HttpStatus.NOT_ACCEPTABLE);
        }).publishOn(Schedulers.boundedElastic());
    }

    public Mono<ResponseEntity<Object>> getSingleQueryById(@PathVariable Long id) {
        return Mono.fromCallable(() -> {
            if (id != null) {
                Optional<SingleQuery> optionalSingleQuery = singleQueryRepository.findById(id);
                if (optionalSingleQuery.isEmpty()) {
                    return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
                }
                SingleQuery singleQuery = optionalSingleQuery.get();
                return new ResponseEntity<Object>(singleQuery, HttpStatus.OK);
            }else return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }).publishOn(Schedulers.boundedElastic());
    }

    public Mono<ResponseEntity<List<SingleQuery>>> getAllSingleQueries() {
        return Mono.fromCallable(() -> {
            List<SingleQuery> singleQueries = singleQueryRepository.findAll();
            return new ResponseEntity<>(singleQueries, HttpStatus.OK);
        }).publishOn(Schedulers.boundedElastic());
    }
}
