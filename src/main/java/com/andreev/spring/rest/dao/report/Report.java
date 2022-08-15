package com.andreev.spring.rest.dao.report;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Builder
@Entity
@Table(name = "report")
@NoArgsConstructor
@AllArgsConstructor
public class Report {
    @Id
    @Column(name = "id")
    private Integer reportId;
    private Integer tableAmount;
    private String tables;

}
