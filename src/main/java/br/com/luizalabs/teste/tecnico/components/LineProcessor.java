package br.com.luizalabs.teste.tecnico.components;

import br.com.luizalabs.teste.tecnico.models.Order;
import br.com.luizalabs.teste.tecnico.models.Product;
import br.com.luizalabs.teste.tecnico.models.User;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;

@Component
public class LineProcessor {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final Pattern NAME_PATTERN = Pattern.compile("^[a-zA-ZÀ-ÿ.'\\s]+$");

    public User processLine(String line, Map<Integer, User> userMap) {
        try {
            int userId = Integer.parseInt(line.substring(0, 10).trim());
            String userName = line.substring(10, 55).trim();
            int orderId = Integer.parseInt(line.substring(55, 65).trim());
            int productId = Integer.parseInt(line.substring(65, 75).trim());
            String value = line.substring(75, 87).trim();
            String date = line.substring(87, 95).trim();
            date = date.substring(0, 4) + "-" + date.substring(4, 6) + "-" + date.substring(6);

            if (!isValidName(userName)) {
                throw new IllegalArgumentException("Invalid user name: " + userName);
            }

            User user = userMap.getOrDefault(userId, new User(userId, userName, new ArrayList<>()));


            String finalDate = date;

            if (!isValidDate(finalDate)) {
                throw new IllegalArgumentException("Invalid date: " + date);
            }

            Order order = user.getOrders().stream()
                    .filter(o -> o.getOrderId() == orderId)
                    .findFirst()
                    .orElseGet(() -> createAndAddOrder(user, orderId, finalDate));

            Product product = new Product(productId, value);
            order.getProducts().add(product);

            double total = order.getProducts().stream().mapToDouble(p -> Double.parseDouble(p.getValue())).sum();
            order.setTotal(String.format(Locale.US, "%.2f", total));

            return user;
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Validation error: {}", e);
        } catch (StringIndexOutOfBoundsException e) {
            throw new IllegalArgumentException("Invalid line format", e);
        }

    }

    private Order createAndAddOrder(User user, int orderId, String date) {
        Order order = new Order(orderId, "0.00", date, new ArrayList<>());
        user.getOrders().add(order);
        return order;
    }

    private boolean isValidDate(String date) {
        try {
            LocalDate.parse(date, DATE_FORMATTER);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    private boolean isValidName(String name) {
        return NAME_PATTERN.matcher(name).matches();
    }
}
