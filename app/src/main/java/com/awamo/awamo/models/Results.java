package com.awamo.awamo.models;

import java.io.Serializable;

public class Results implements Serializable {
    private String number_one;
    private String number_two;
    private int response;
    private int expected;
    private String passed;


    public Results(String number_one, String number_two, int response,int expected,String passed) {
        this.number_one = number_one;
        this.number_two = number_two;
        this.response = response;
        this.expected = expected;
        this.passed = passed;

    }

    public String getNumber_one() {
        return number_one;
    }

    public void setNumber_one(String number_one) {
        this.number_one = number_one;
    }

    public String getNumber_two() {
        return number_two;
    }

    public void setNumber_two(String number_two) {
        this.number_two = number_two;
    }

    public int getResponse() {
        return response;
    }

    public void setResponse(int response) {
        this.response = response;
    }

    public int getExpected() {
        return expected;
    }

    public void setExpected(int expected) {
        this.expected = expected;
    }

    public String getPassed() {
        return passed;
    }

    public void setPassed(String passed) {
        this.passed = passed;
    }
}
