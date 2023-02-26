package com.example.storeforsocks.controllers;

import com.example.storeforsocks.impl.FilesServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@RestController
    @RequestMapping("/files")
    @Tag(name="Файловый контроллер", description = "Для скачивания и загрузки файлов.")
    public class FilesController {

        private final FilesServiceImpl filesService;

        public FilesController(FilesServiceImpl filesService) {
            this.filesService = filesService;
        }

        @GetMapping(value = "/export")
        @Operation(summary = "Скачивание файла со списком товаров в формате JSON.")
        public ResponseEntity<InputStreamResource> downloadFile() throws java.io.FileNotFoundException {
            File file = filesService.getDataFile();
            if(file.exists()){
                InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
                return ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_OCTET_STREAM)
                        .contentLength(file.length())
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"sockShop.json\"")
                        .body(resource);
            } else {
                return ResponseEntity.noContent().build();
            }
        }

        @PostMapping(value = "/import", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
        @Operation(summary = "Импорт файла со списком товаров в формате JSON.")
        @ApiResponse(responseCode = "200", description = "Файл загружен")
        public ResponseEntity<Void> uploadFile(@RequestParam MultipartFile file) throws FileNotFoundException {
            filesService.cleanDataFile();
            File dataFile = filesService.getDataFile();
            try(FileOutputStream fos = new FileOutputStream(dataFile)){
                IOUtils.copy(file.getInputStream(), fos);
                return ResponseEntity.ok().build();
            } catch (IOException e) {
                throw new FileNotFoundException("Не удалось загрузить файл.");
            }
        }

        @GetMapping(value = "/exportOperation")
        @Operation(summary = "Скачивание файла операций по товарам в формате JSON.")
        public ResponseEntity<InputStreamResource> downloadOperationFile() throws java.io.FileNotFoundException {
            File file = filesService.getOperationFile();
            if(file.exists()){
                InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
                return ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_OCTET_STREAM)
                        .contentLength(file.length())
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"socksOperation.json\"")
                        .body(resource);
            } else {
                return ResponseEntity.noContent().build();
            }
        }

        @PostMapping(value = "/importOperation", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
        @Operation(summary = "Импорт файла операций по товарам в формате JSON.")
        @ApiResponse(responseCode = "200", description = "Файл загружен")
        public ResponseEntity<Void> uploadOperationFile(@RequestParam MultipartFile file) throws FileNotFoundException {
            filesService.cleanOperationFile();
            File dataFile = filesService.getOperationFile();
            try(FileOutputStream fos = new FileOutputStream(dataFile)){
                IOUtils.copy(file.getInputStream(), fos);
                return ResponseEntity.ok().build();
            } catch (IOException e) {
                throw new FileNotFoundException("Не удалось загрузить файл.");
            }
        }

    }

