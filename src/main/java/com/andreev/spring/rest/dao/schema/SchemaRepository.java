package com.andreev.spring.rest.dao.schema;

import com.andreev.spring.rest.dto.TableStructureDto;
import lombok.extern.jbosslog.JBossLog;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Repository
@JBossLog
public class SchemaRepository {

    @PersistenceContext
    private EntityManager entityManager;

    private final String tableInfo = "SELECT CAST(column_name as TEXT) as columnname," +
            "CAST(data_type as TEXT) as datatype," +
            "CAST(is_identity as TEXT) isidentity FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = CAST(:tableName as TEXT)";

    @Transactional
    public void createTable(String query) {
        entityManager.createNativeQuery(query).executeUpdate();
    }

    @Transactional
    public List<TableStructureDto> getTableByName(String tableName) {
        log.infov("Table name for search: {0}", tableName);
        try {
            List<TableStructureDto> tableStructureDtoList = entityManager
                    .createNativeQuery(tableInfo, "TableStructureDto")
                    .setParameter("tableName", tableName)
                    .getResultList();
            return tableStructureDtoList;
        } catch (NoResultException nre) {
            log.error("Get table error: {0}", nre);
            return new ArrayList<>();
        }
    }

    @Transactional
    public void deleteTableByName(String tableName) {
        log.infov("Table name to delete: {0}", tableName);
        entityManager.createNativeQuery("DROP TABLE " + tableName).executeUpdate();
    }

    @Transactional
    public void executeCustomQuery(String query) {
        entityManager.createNativeQuery(query).executeUpdate();
    }
}
