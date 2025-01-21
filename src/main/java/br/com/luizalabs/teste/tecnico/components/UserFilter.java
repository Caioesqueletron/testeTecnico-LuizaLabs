package br.com.luizalabs.teste.tecnico.components;

import br.com.luizalabs.teste.tecnico.models.FilterDTO;
import br.com.luizalabs.teste.tecnico.models.Order;
import br.com.luizalabs.teste.tecnico.models.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserFilter {
    public List<User> filterUsers(FilterDTO filterDTO, List<User> users) {
        return users.stream().peek(user -> {
            List<Order> filteredOrders = user.getOrders().stream()
                    .filter(order -> (filterDTO.getOrderId() == null || order.getOrderId() == filterDTO.getOrderId()) &&
                            (filterDTO.getStartDate() == null || order.getDate().compareTo(filterDTO.getStartDate()) >= 0) &&
                            (filterDTO.getEndDate() == null || order.getDate().compareTo(filterDTO.getEndDate()) <= 0))
                    .toList();

            user.setOrders(filteredOrders);
        }).filter(user -> !user.getOrders().isEmpty()).toList();
    }
}
