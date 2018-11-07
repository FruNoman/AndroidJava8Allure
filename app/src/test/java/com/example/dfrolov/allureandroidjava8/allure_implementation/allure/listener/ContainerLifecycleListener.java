package com.example.dfrolov.allureandroidjava8.allure_implementation.allure.listener;


import com.example.dfrolov.allureandroidjava8.allure_implementation.model_pojo.TestResultContainer;

/**
 * Notifies about Allure test container lifecycle.
 *
 * @since 2.0
 */
public interface ContainerLifecycleListener {

    default void beforeContainerStart(TestResultContainer container) {
        //do nothing
    }

    default void afterContainerStart(TestResultContainer container) {
        //do nothing
    }

    default void beforeContainerUpdate(TestResultContainer container) {
        //do nothing
    }

    default void afterContainerUpdate(TestResultContainer container) {
        //do nothing
    }

    default void beforeContainerStop(TestResultContainer container) {
        //do nothing
    }

    default void afterContainerStop(TestResultContainer container) {
        //do nothing
    }

    default void beforeContainerWrite(TestResultContainer container) {
        //do nothing
    }

    default void afterContainerWrite(TestResultContainer container) {
        //do nothing
    }

}
