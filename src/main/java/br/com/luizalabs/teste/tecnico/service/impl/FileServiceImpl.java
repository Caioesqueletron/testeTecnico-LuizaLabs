package br.com.luizalabs.teste.tecnico.service.impl;

import br.com.luizalabs.teste.tecnico.models.FilterDTO;
import br.com.luizalabs.teste.tecnico.models.User;
import br.com.luizalabs.teste.tecnico.components.LineProcessor;
import br.com.luizalabs.teste.tecnico.components.UserFilter;
import br.com.luizalabs.teste.tecnico.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
@Slf4j
public class FileServiceImpl implements FileService {

    private final LineProcessor lineProcessor;
    private final UserFilter userFilter;

    public List<User> processFile(MultipartFile file, FilterDTO filterDTO) throws IOException {
        List<String> lines = new BufferedReader(new InputStreamReader(file.getInputStream())).lines().toList();
        log.info("Start processing file");

        Map<Integer, User> userMap = new HashMap<>();
        for (String line : lines) {
            try {
                User user = lineProcessor.processLine(line, userMap);
                if (user != null) {
                    userMap.put(user.getUserId(), user);
                }
            } catch (Exception e) {
                log.error("Error processing line. Skipping: {}", line);
            }
        }

        log.info("Users successfully extracted from the file");
        return userFilter.filterUsers(filterDTO, new ArrayList<>(userMap.values()));
    }


}
