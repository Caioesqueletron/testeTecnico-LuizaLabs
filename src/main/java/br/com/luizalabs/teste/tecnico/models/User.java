package br.com.luizalabs.teste.tecnico.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class User {
    private int userId;
    private String name;
    private List<Order> orders;
}
