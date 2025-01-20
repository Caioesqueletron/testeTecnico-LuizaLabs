package br.com.luizalabs.testeTecnico.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
public class Order {
    private int orderId;
    private String total;
    private String date;
    private List<Product> products;
}
