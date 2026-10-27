package com.projects.ruleEngine.models;

public class Violation {

    private String message;

    public Violation(String message){
        this.message = message;
    }

    public static Violation of(String message){
        return new Violation(message);
    }

    public String getMessage() {
        return message;
    }
}
