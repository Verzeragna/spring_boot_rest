package com.andreev.spring.rest.dao.tablequery;

import com.andreev.spring.rest.dao.singlequery.SingleQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TableQueryRepository extends JpaRepository<TableQuery, Long> {

    @Query("SELECT t FROM TableQuery t WHERE t.tableName = :tableName")
    public List<SingleQuery> findAllByTableName(@Param("tableName") String tableName);
}
