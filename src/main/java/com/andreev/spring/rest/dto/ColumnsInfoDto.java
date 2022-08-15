package com.andreev.spring.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ColumnsInfoDto {

    private String title;
    private String type;
}
