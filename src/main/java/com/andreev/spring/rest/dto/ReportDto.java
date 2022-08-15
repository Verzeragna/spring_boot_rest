package com.andreev.spring.rest.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ReportDto {

    private Integer reportId;
    private Integer tableAmount;
    private List<TableInfo> tables;
}
