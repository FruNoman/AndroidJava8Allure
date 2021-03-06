package com.example.dfrolov.allureandroidjava8.allure_implementation.allure.listener;


import com.example.dfrolov.allureandroidjava8.allure_implementation.model_pojo.StepResult;

/**
 * Notifies about Allure step lifecycle events.
 *
 * @since 2.0
 */
public interface StepLifecycleListener {

    default void beforeStepStart(StepResult result) {
        //do nothing
    }

    default void afterStepStart(StepResult result) {
        //do nothing
    }

    default void beforeStepUpdate(StepResult result) {
        //do nothing
    }

    default void afterStepUpdate(StepResult result) {
        //do nothing
    }

    default void beforeStepStop(StepResult result) {
        //do nothing
    }

    default void afterStepStop(StepResult result) {
        //do nothing
    }

}
