package com.andreev.spring.rest.dao.singlequery;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@Entity
@Table(name = "single_query")
@NoArgsConstructor
@AllArgsConstructor
public class SingleQuery {

    @Id
    @Column(name = "id")
    private Long id;
    private String query;

}