package com.example.dfrolov.allureandroidjava8.allure_implementation.model_api;

import com.example.dfrolov.allureandroidjava8.allure_implementation.model_pojo.TestResult;
import com.example.dfrolov.allureandroidjava8.allure_implementation.model_pojo.TestResultContainer;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Stream;



/**
 * @author charlie (Dmitry Baev).
 */
public interface AllureResultsReader {

    Stream<TestResult> readTestResults();

    Stream<TestResultContainer> readTestResultsContainers();

    Stream<String> findAllAttachments();

    InputStream readAttachment(String source) throws IOException;

    List<ReadError> getErrors();

}
