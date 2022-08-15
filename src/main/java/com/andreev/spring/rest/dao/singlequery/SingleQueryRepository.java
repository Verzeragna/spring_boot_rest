package com.andreev.spring.rest.dao.singlequery;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SingleQueryRepository extends JpaRepository<SingleQuery, Long> {

}
