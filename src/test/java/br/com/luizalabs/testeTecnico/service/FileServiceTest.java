package br.com.luizalabs.testeTecnico.service;

import br.com.luizalabs.testeTecnico.models.FilterDTO;
import br.com.luizalabs.testeTecnico.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.mock.web.MockMultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FileServiceTest {

    @InjectMocks
    private FileService fileService;

    @Mock
    private MultipartFile mockFile;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testProcessFile_withValidFile() throws IOException {
        File file = new File("src/test/resources/data.txt");
        byte[] fileContent = Files.readAllBytes(file.toPath());

        MultipartFile multipartFile = new MockMultipartFile("file", file.getName(), "text/plain", fileContent);

        FilterDTO filterDTO = new FilterDTO(null, null, null);

        List<User> users = fileService.processFile(multipartFile, filterDTO);

        assertEquals(2, users.size(), "Deve retornar 2 usuários.");
        assertEquals("Palmer Prosacco", users.get(0).getName(), "O primeiro usuário deve ser Palmer Prosacco.");
        assertEquals("John Doe", users.get(1).getName(), "O segundo usuário deve ser John Doe.");
    }

    @Test
    void testProcessFile_withInvalidFile() throws IOException {
        File file = new File("src/test/resources/invalidFile.txt"); // Outro arquivo com dados inválidos
        byte[] fileContent = Files.readAllBytes(file.toPath());

        MultipartFile multipartFile = new MockMultipartFile("file", file.getName(), "text/plain", fileContent);

        FilterDTO filterDTO = new FilterDTO(null, null, null);

        List<User> users = fileService.processFile(multipartFile, filterDTO);

        assertEquals(0, users.size(), "Não deve retornar nenhum usuário.");
    }
}