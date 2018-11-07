package com.example.dfrolov.allureandroidjava8.allure_implementation.model_api;

import com.example.dfrolov.allureandroidjava8.allure_implementation.model.Allure2ModelJackson;
import com.example.dfrolov.allureandroidjava8.allure_implementation.model_pojo.TestResult;
import com.example.dfrolov.allureandroidjava8.allure_implementation.model_pojo.TestResultContainer;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;



import static com.example.dfrolov.allureandroidjava8.allure_implementation.model_api.AllureUtils.generateTestResultContainerName;
import static com.example.dfrolov.allureandroidjava8.allure_implementation.model_api.AllureUtils.generateTestResultName;


/**
 * @author charlie (Dmitry Baev).
 */
public class FileSystemResultsWriter implements AllureResultsWriter {

    private final Path outputDirectory;

    private final ObjectMapper mapper;

    public FileSystemResultsWriter(Path outputDirectory) {
        this.outputDirectory = outputDirectory;
        this.mapper = Allure2ModelJackson.createMapper();
    }

    @Override
    public void write(TestResult testResult) {
        final String testResultName = Objects.isNull(testResult.getUuid())
                ? generateTestResultName()
                : generateTestResultName(testResult.getUuid());
        createDirectories(outputDirectory);
        Path file = outputDirectory.resolve(testResultName);
        try (OutputStream os = new BufferedOutputStream(new FileOutputStream(file.toFile()))) {
            mapper.writeValue(os, testResult);
        } catch (IOException e) {
            throw new AllureResultsWriteException("Could not write Allure test result", e);
        }
    }

    @Override
    public void write(TestResultContainer testResultContainer) {
        final String testResultContainerName = Objects.isNull(testResultContainer.getUuid())
                ? generateTestResultContainerName()
                : generateTestResultContainerName(testResultContainer.getUuid());
        createDirectories(outputDirectory);
        Path file = outputDirectory.resolve(testResultContainerName);
        try (OutputStream os = new BufferedOutputStream(new FileOutputStream(file.toFile()))) {
            mapper.writeValue(os, testResultContainer);
        } catch (IOException e) {
            throw new AllureResultsWriteException("Could not write Allure test result container", e);
        }
    }

    @Override
    public void write(String source, InputStream attachment) {
        createDirectories(outputDirectory);
        Path file = outputDirectory.resolve(source);
        try (InputStream is = attachment) {
            Files.copy(is, file);
        } catch (IOException e) {
            throw new AllureResultsWriteException("Could not write Allure attachment", e);
        }
    }

    private void createDirectories(Path directory) {
        try {
            Files.createDirectories(directory);
        } catch (IOException e) {
            throw new AllureResultsWriteException("Could not create Allure results directory", e);
        }
    }
}
