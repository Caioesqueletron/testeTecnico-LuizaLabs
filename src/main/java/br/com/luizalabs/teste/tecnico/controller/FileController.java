package br.com.luizalabs.teste.tecnico.controller;

import br.com.luizalabs.teste.tecnico.models.FilterDTO;
import br.com.luizalabs.teste.tecnico.models.User;
import br.com.luizalabs.teste.tecnico.service.FileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/orders")
@Validated
@AllArgsConstructor
public class FileController {

    private final FileService fileService;

    /**
     * Endpoint for processing a file and returning a list of users.
     *
     * @param file      The uploaded file to process.
     * @param orderId   Optional order ID for filtering.
     * @param startDate Optional start date for filtering.
     * @param endDate   Optional end date for filtering.
     * @return A list of users after processing the file.
     */

    @Operation(
            summary = "Processa um arquivo para obter usuários com base nos filtros",
            description = "Este endpoint processa um arquivo enviado pelo cliente e retorna uma lista de usuários filtrados com base no `orderId`, `startDate` e `endDate` fornecidos."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Arquivo processado com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "400", description = "Requisição inválida",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor",
                    content = @Content(mediaType = "application/json"))
    })
    @PostMapping(path = "/process", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<List<User>> processFile(@RequestPart("file") @Valid MultipartFile file,
                                                  @RequestParam(required = false) Long orderId,
                                                  @RequestParam(required = false) @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "Formato da data deve ser yyyy-MM-dd") String startDate,
                                                  @RequestParam(required = false) @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "Formato da data deve ser yyyy-MM-dd") String endDate) throws IOException {
        try {
            if (!Objects.requireNonNull(file.getOriginalFilename()).toLowerCase().endsWith(".txt")) {
                throw new IllegalArgumentException("Only .txt files are allowed.");
            }
            FilterDTO filterDTO = new FilterDTO(orderId, startDate, endDate);
            List<User> users = fileService.processFile(file, filterDTO);
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            throw new RuntimeException("An unexpected error occurred", e);
        }
    }


}