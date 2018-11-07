package com.example.dfrolov.allureandroidjava8.helper;

import com.android.ddmlib.MultiLineReceiver;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Type;


public class Shell extends MultiLineReceiver {
    private String output = "";

    @Override
    public void processNewLines(String[] lines) {
        for (String line : lines) {
            output = output + line;
        }
    }

    @Override
    public boolean isCancelled() {
        return false;
    }

    public String getOutput() {
        return output;
    }

    public String getDataOutput() {
        String dataOutput = "";
        String[] data = output.split(",");
        if (data.length > 1) {
            dataOutput = data[data.length-1];
            dataOutput = dataOutput.substring(7, dataOutput.length() - 1);
        }
        output = "";
        return dataOutput;
    }

    public <T> T getDataOutput(Type returnType) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setLenient();
        gsonBuilder.excludeFieldsWithModifiers(java.lang.reflect.Modifier.TRANSIENT);
        Gson gson = gsonBuilder.create();
        T val = gson.fromJson(getDataOutput(), returnType);
        output = "";
        return val;
    }
}
