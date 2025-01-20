package br.com.luizalabs.testeTecnico.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class User {
    private int userId;
    private String name;
    private List<Order> orders;
}
