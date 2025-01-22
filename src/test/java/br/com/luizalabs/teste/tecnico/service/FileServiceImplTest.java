package br.com.luizalabs.teste.tecnico.service;

import br.com.luizalabs.teste.tecnico.models.User;
import br.com.luizalabs.teste.tecnico.components.LineProcessor;
import br.com.luizalabs.teste.tecnico.components.UserFilter;
import br.com.luizalabs.teste.tecnico.models.FilterDTO;
import br.com.luizalabs.teste.tecnico.service.impl.FileServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class FileServiceImplTest {

    @InjectMocks
    private FileServiceImpl fileServiceImpl;

    @Mock
    private LineProcessor lineProcessor;

    @Mock
    private UserFilter userFilter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testProcessFile_withValidFile() throws IOException {
        MultipartFile file = new MockMultipartFile("file", "test.txt", "text/plain", "Mock content".getBytes());
        FilterDTO filterDTO = new FilterDTO(null, null, null);

        when(lineProcessor.processLine(anyString(), anyMap())).thenReturn(new User(70, "Mock User", List.of()));
        when(userFilter.filterUsers(any(FilterDTO.class), anyList())).thenReturn(List.of(new User(70, "Mock User", List.of())));

        List<User> users = fileServiceImpl.processFile(file, filterDTO);

        assertEquals(1, users.size(), "Deve retornar um usuário.");
        verify(lineProcessor, atLeastOnce()).processLine(anyString(), anyMap());
        verify(userFilter).filterUsers(any(FilterDTO.class), anyList());
    }
}
