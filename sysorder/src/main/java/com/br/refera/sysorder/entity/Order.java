package com.br.refera.sysorder.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_order")
public class Order {

    @Id
    @Column(name = "ID_ORDER")
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @Column(name = "CONTACT_NAME")
    private String contactName;

    @Column(name = "CONTACT_PHONE")
    private String contactPhone;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "COMPANY")
    private String company;

    @Column(name = "REAL_ESTATE_AGENCY")
    private String realEstateAgency;

    @Column(name = "DEADLINE")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date deadline;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_CATEGORY")
    private Category category;
}
