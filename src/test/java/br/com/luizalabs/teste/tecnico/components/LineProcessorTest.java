package br.com.luizalabs.teste.tecnico.components;

import br.com.luizalabs.teste.tecnico.models.User;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class LineProcessorTest {
    private final LineProcessor lineProcessor = new LineProcessor();

    @Test
    void testProcessLine_withValidLine() {
        String line = "0000000070                              Palmer Prosacco00000007530000000003     1836.7420210308";
        Map<Integer, User> userMap = new HashMap<>();

        User user = lineProcessor.processLine(line, userMap);

        assertNotNull(user, "Usuário deve ser criado.");
        assertEquals(70, user.getUserId(), "O ID do usuário deve ser 70.");
        assertEquals("Palmer Prosacco", user.getName(), "O nome do usuário deve ser Palmer Prosacco.");
        assertEquals(1, user.getOrders().size(), "Deve haver uma ordem.");
    }

    @Test
    void testProcessLine_withInvalidLine() {
        String invalidLine = "InvalidDataHere";
        Map<Integer, User> userMap = new HashMap<>();

        assertThrows(IllegalArgumentException.class, () -> lineProcessor.processLine(invalidLine, userMap),
                "Deve lançar exceção para linha inválida.");
    }
}
