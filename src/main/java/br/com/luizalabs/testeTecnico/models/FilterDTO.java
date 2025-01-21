package br.com.luizalabs.testeTecnico.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FilterDTO {
    private Long orderId;
    private String startDate;
    private String endDate;
}