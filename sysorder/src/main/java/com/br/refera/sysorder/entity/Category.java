package com.br.refera.sysorder.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Builder
@Table(name = "tb_category")
@AllArgsConstructor
@NoArgsConstructor
public class Category {

    @Id
    @Column(name = "ID_CATEGORY")
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Column(name = "DESC_CATEGORY")
    private String desc;
}
