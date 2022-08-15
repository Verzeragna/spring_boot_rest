package com.andreev.spring.rest.dao.tablequery;

import com.andreev.spring.rest.dto.TableStructureDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@Entity
@Table(name = "table_query")
@NoArgsConstructor
@AllArgsConstructor
@SqlResultSetMapping(name = "TableStructureDto", classes = {
        @ConstructorResult(targetClass = TableStructureDto.class,
                columns = {@ColumnResult(name = "columnname"),
                        @ColumnResult(name = "datatype"),
                        @ColumnResult(name = "isidentity")
                })
})
public class TableQuery {

    @Id
    @Column(name = "id")
    private Long id;
    private String tableName;
    private String query;
}
