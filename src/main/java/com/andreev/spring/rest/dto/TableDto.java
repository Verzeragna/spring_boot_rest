package com.andreev.spring.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TableDto {

    private String tableName;
    private Integer columnsAmount;
    private String primaryKey;
    private List<ColumnsInfoDto> columnsInfo;
}
