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

    public NeighborhoodMetricDTO(String neighborhood, long requestCount) {
        this.neighborhood = neighborhood;
        this.requestCount = requestCount;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public Long getRequestCount() {
        return requestCount;
    }

    public void setRequestCount(Long requestCount) {
        this.requestCount = requestCount;
    }
}
