package br.com.luizalabs.testeTecnico.service;

import br.com.luizalabs.testeTecnico.models.Order;
import br.com.luizalabs.testeTecnico.models.Product;
import br.com.luizalabs.testeTecnico.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class FileService {
    public List<User> processFile(MultipartFile file) throws IOException {
        List<String> lines = new BufferedReader(new InputStreamReader(file.getInputStream())).lines().collect(Collectors.toList());

        Map<Integer, User> userMap = new HashMap<>();
        for (String line : lines) {
            int userId = Integer.parseInt(line.substring(0, 10).trim());
            String userName = line.substring(10, 55).trim();
            int orderId = Integer.parseInt(line.substring(55, 65).trim());
            int productId = Integer.parseInt(line.substring(65, 75).trim());
            String value = line.substring(75, 87).trim();
            String date = line.substring(87, 95).trim();
            date = date.substring(0, 4) + "-" + date.substring(4, 6) + "-" + date.substring(6);

            User user = userMap.getOrDefault(userId, new User(userId, userName, new ArrayList<>()));

            Order order = user.getOrders().stream().filter(o -> o.getOrderId() == orderId).findFirst().orElse(null);
            if (order == null) {
                order = new Order(orderId, "0.00", date, new ArrayList<>());
                user.getOrders().add(order);
            }

            Product product = new Product(productId, value);
            order.getProducts().add(product);

            double total = order.getProducts().stream().mapToDouble(p -> Double.parseDouble(p.getValue())).sum();
            order.setTotal(String.format("%.2f", total));

            userMap.put(userId, user);
        }

        return new ArrayList<>(userMap.values());
        }
}
