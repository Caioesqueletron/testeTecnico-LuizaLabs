package br.com.luizalabs.testeTecnico.controller;

import br.com.luizalabs.testeTecnico.models.FilterDTO;
import br.com.luizalabs.testeTecnico.models.User;
import br.com.luizalabs.testeTecnico.service.FileService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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
    @PostMapping(path = "/process", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    //@ApiOperation(value = "Process a file to get users based on filters")
    public ResponseEntity<?> processFile(@RequestPart("file") @Valid MultipartFile file,
                                         @RequestParam(required = false) Long orderId,
                                         @RequestParam(required = false) @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}") String startDate,
                                         @RequestParam(required = false) @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}") String endDate) {
        try {
            FilterDTO filterDTO = new FilterDTO(orderId, startDate, endDate);
            List<User> users = fileService.processFile(file, filterDTO);
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Internal Server Error", "An unexpected error occurred"));
        }
    }

    /**
     * Error response object.
     */
    public static class ErrorResponse {
        private String error;
        private String message;

        public ErrorResponse(String error, String message) {
            this.error = error;
            this.message = message;
        }

    }
}