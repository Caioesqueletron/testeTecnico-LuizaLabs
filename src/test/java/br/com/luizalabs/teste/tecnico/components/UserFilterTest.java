package br.com.luizalabs.teste.tecnico.components;

import br.com.luizalabs.teste.tecnico.models.FilterDTO;
import br.com.luizalabs.teste.tecnico.models.Order;
import br.com.luizalabs.teste.tecnico.models.User;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserFilterTest {

    private final UserFilter userFilter = new UserFilter();

    @Test
    void testFilterUsers_withNoFilters() {
        List<User> users = createSampleUsers();
        FilterDTO filterDTO = new FilterDTO(null, null, null);

        List<User> filteredUsers = userFilter.filterUsers(filterDTO, users);

        assertEquals(2, filteredUsers.size(), "Sem filtros, todos os usuários devem ser retornados.");
    }

    @Test
    void testFilterUsers_withOrderId() {
        List<User> users = createSampleUsers();
        FilterDTO filterDTO = new FilterDTO(753L, null, null);

        List<User> filteredUsers = userFilter.filterUsers(filterDTO, users);

        assertEquals(1, filteredUsers.size(), "Deve retornar apenas o usuário com a ordem 753.");
    }

    @Test
    void testFilterUsers_withDateRange() {
        List<User> users = createSampleUsers();
        FilterDTO filterDTO = new FilterDTO(null, "2021-01-01", "2021-12-31");

        List<User> filteredUsers = userFilter.filterUsers(filterDTO, users);

        assertEquals(1, filteredUsers.size(), "Deve retornar apenas usuários com ordens dentro do intervalo.");
    }

    private List<User> createSampleUsers() {
        User user1 = new User(70, "Palmer Prosacco", new ArrayList<>());
        Order order1 = new Order(753, "1836.74", "2021-03-08", new ArrayList<>());
        user1.getOrders().add(order1);

        User user2 = new User(71, "John Doe", new ArrayList<>());
        Order order2 = new Order(754, "2000.00", "2020-01-01", new ArrayList<>());
        user2.getOrders().add(order2);

        return List.of(user1, user2);
    }
}
