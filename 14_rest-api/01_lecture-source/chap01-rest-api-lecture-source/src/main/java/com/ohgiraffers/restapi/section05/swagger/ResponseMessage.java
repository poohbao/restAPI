package com.ohgiraffers.restapi.section05.swagger;

import java.util.Map;

public class ResponseMessage {

    private int httpStatusCode;
    private String message;
    private Map<String, Object> results;


    public ResponseMessage() {
    }

    public ResponseMessage(int httpStatusCode, String message, Map<String, Object> results) {
        this.httpStatusCode = httpStatusCode;
        this.message = message;
        this.results = results;
    }

    public int getHttpStatusCode() {
        return httpStatusCode;
    }

    public void setHttpStatusCode(int httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, Object> getResults() {
        return results;
    }

    public void setResults(Map<String, Object> results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return "ResponseMessage{" +
                "httpStatusCode=" + httpStatusCode +
                ", message='" + message + '\'' +
                ", results=" + results +
                '}';
    }
}

