package br.com.luizalabs.teste.tecnico.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product implements Comparable<Product> {
    private int productId;
    private String value;

    @Override
    public int compareTo(Product o) {
        return Integer.compare(this.getProductId(), o.getProductId());
    }
}