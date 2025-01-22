package br.com.luizalabs.teste.tecnico.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.core.annotation.Order;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product implements Comparable<Product> {
    @JsonProperty(value = "product_id", index = 1)
    @Order(value = 1)
    private int productId;
    @JsonProperty(index = 2)
    private String value;

    @Override
    public int compareTo(Product o) {
        return Integer.compare(this.getProductId(), o.getProductId());
    }
}