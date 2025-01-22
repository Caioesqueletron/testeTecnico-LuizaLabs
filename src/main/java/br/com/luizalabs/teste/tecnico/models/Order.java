package br.com.luizalabs.teste.tecnico.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Order implements Comparable<Order> {
    @JsonProperty("order_id")
    private int orderId;
    private String total;
    private String date;
    private List<Product> products;

    @Override
    public int compareTo(Order o) {
        return Integer.compare(this.getOrderId(), o.getOrderId());
    }
}
