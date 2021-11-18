package com.br.refera.sysorder.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderModel {
    private Long id;
    private String contactName;
    private String contactPhone;
    private String description;
    private String company;
    private String realEstateAgency;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date deadline;
    private Long categoryId;
}
