package com.example.dfrolov.allureandroidjava8;

import com.example.dfrolov.allureandroidjava8.allure_implementation.allure.Allure;
import com.example.dfrolov.allureandroidjava8.allure_implementation.allure.Step;

public class Some {
    @Step
    public void doSome(){
        Allure.addAttachment("SOME","FUCK");
        System.out.println("FUCK !");
    }
}
