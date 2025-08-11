package com.prodabel.atendimentos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NeighborhoodMetricDTO {
    private String neighborhood;
    private Long requestCount;
}
