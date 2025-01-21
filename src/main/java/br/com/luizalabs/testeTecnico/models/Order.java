package br.com.luizalabs.testeTecnico.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Order implements Comparable<Order> {
    private int orderId;
    private String total;
    private String date;
    private List<Product> products;

    @Override
    public int compareTo(Order o) {
        return Integer.compare(this.getOrderId(), o.getOrderId());
    }
}
