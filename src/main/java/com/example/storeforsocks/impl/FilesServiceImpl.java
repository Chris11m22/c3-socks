package com.example.storeforsocks.impl;

import com.example.storeforsocks.service.FilesService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class FilesServiceImpl implements FilesService {

    @Value("${path.to.data.file}")
    private String dataFilePath;

    @Value("${name.of.data.file}")
    private String dataFileName;

    @Value("${name.of.operation.file}")
    private String operationFileName;

    @Override
    public boolean saveToFile(String json){
        Path path = Path.of(dataFilePath, dataFileName);
        try {
            cleanDataFile();
            Files.writeString(path, json);
            return true;
        } catch (IOException e){
            return false;
        }
    }

    @Override
    public String readFromFile(){
        Path path = Path.of(dataFilePath, dataFileName);
        try{
            return Files.readString(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean cleanDataFile(){
        Path path = Path.of(dataFilePath, dataFileName);
        try{
            Files.deleteIfExists(path);
            Files.createFile(path);
            return true;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean saveToOperationFile(String json){
        Path path = Path.of(dataFilePath, operationFileName);
        try {
            cleanOperationFile();
            Files.writeString(path, json);
            return true;
        } catch (IOException e){
            return false;
        }
    }

    @Override
    public String readFromOperationFile(){
        Path path = Path.of(dataFilePath, operationFileName);
        try{
            return Files.readString(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean cleanOperationFile(){
        Path path1 = Path.of(dataFilePath,operationFileName);
        try{
            Files.deleteIfExists(path1);
            Files.createFile(path1);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public File getDataFile(){
        return new File(dataFilePath + "/" + dataFileName);
    }

    @Override
    public File getOperationFile(){
        return new File(dataFilePath + "/" + operationFileName);
    }
}
