package com.andreev.spring.rest.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TableStructureDto {

    private String columnname;
    private String datatype;
    private String isidentity;

    public TableStructureDto(String columnname, String datatype, String isidentity) {
        this.columnname = columnname;
        this.datatype = datatype;
        this.isidentity = isidentity;
    }
}
