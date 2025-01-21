package br.com.luizalabs.testeTecnico.controller;

import br.com.luizalabs.testeTecnico.service.FileService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class FileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FileService fileService;

    private MockMultipartFile mockFile;

    @BeforeEach
    public void setUp() throws IOException {
        File file = new File("src/test/resources/data.txt");
        byte[] fileContent = Files.readAllBytes(file.toPath());

        mockFile = new MockMultipartFile("file", file.getName(), "text/plain", fileContent);

    }

    @Test
    public void whenValidFile_thenReturnUsers() throws Exception {
        mockMvc.perform(multipart("/api/orders/process")
                        .file(mockFile)
                        .param("orderId", "9876543210")
                        .param("startDate", "2023-01-01")
                        .param("endDate", "2023-02-01"))
                .andExpect(status().isOk());
    }

    @Test
    public void whenInvalidFile_thenReturnInternalServerError() throws Exception {
        File file = new File("src/test/resources/invalidFile.txt");
        byte[] fileContent = Files.readAllBytes(file.toPath());

        MockMultipartFile invalidFile = new MockMultipartFile("file", file.getName(), "text/plain", fileContent);

        mockMvc.perform(multipart("/api/orders/process")
                        .file(invalidFile))
                .andExpect(status().isOk());
    }

    @Test
    public void whenMissingFile_thenReturnBadRequest() throws Exception {
        mockMvc.perform(multipart("/api/orders/process"))
                .andExpect(status().isBadRequest());
    }
}
