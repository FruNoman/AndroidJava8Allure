package com.example.dfrolov.allureandroidjava8;

import com.example.dfrolov.allureandroidjava8.allure_implementation.allure.Allure;


public class Some {

    public void doSome(){
        Allure.addAttachment("SOME","FUCK");
        System.out.println("FUCK !");
    }

    public void enable(String doSome){
        System.out.println(doSome);
    }
}
