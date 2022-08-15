package com.andreev.spring.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class TableInfo {

    private String tableName;
    private List<TableColumn> columns;

}
