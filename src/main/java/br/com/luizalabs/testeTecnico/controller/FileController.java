package br.com.luizalabs.testeTecnico.controller;

import br.com.luizalabs.testeTecnico.models.User;
import br.com.luizalabs.testeTecnico.service.FileService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@Validated
@AllArgsConstructor
public class FileController {

    private final FileService fileService;

    @PostMapping(path = "/process",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<List<User>> processFile(@ModelAttribute("file") MultipartFile file) {
        try {
            List<User> users = fileService.processFile(file);
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
