package br.com.luizalabs.teste.tecnico.service;

import br.com.luizalabs.teste.tecnico.models.FilterDTO;
import br.com.luizalabs.teste.tecnico.models.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FileService {
    List<User> processFile(MultipartFile file, FilterDTO filterDTO) throws IOException;
}
