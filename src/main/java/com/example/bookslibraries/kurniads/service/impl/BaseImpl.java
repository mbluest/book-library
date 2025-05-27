package com.example.bookslibraries.kurniads.service.impl;

import com.example.bookslibraries.kurniads.util.constant.ResponseConstants;

import java.util.HashMap;
import java.util.Map;

public class BaseImpl extends ResponseConstants{

    private String messageName = "message";
    private String codeName = "code";
    private String dataName = "data";

    public Map<String, Object> responseSuccess(Object data){
        Map<String, Object> response = new HashMap<>();
        response.put(codeName, CODE_STATUS_SUCCESS);
        response.put(messageName, MESSAGE_STATUS_SUCCESS);
        response.put(dataName, data);
        return response;
    }

    public Map<String, Object> responseSuccess(Object data, String message){
        Map<String, Object> response = new HashMap<>();
        response.put(codeName, CODE_STATUS_SUCCESS);
        response.put(messageName, message);
        response.put(dataName, data);
        return response;
    }

    public Map<String, Object> responseBadParams(Object data){
        Map<String, Object> response = new HashMap<>();
        response.put(codeName, CODE_STATUS_BAD_PARAMS);
        response.put(messageName, MESSAGE_STATUS_BAD_PARAMS);
        response.put(dataName, data);
        return response;
    }

    public Map<String, Object> responseBadParams(Object data, String message,String detail){
        Map<String, Object> response = new HashMap<>();
        response.put(codeName, CODE_STATUS_BAD_PARAMS);
        response.put(messageName, message + " - " +detail);
        response.put(dataName, data);
        return response;
    }

    public Map<String, Object> responseDataNotFound(Object data){
        Map<String, Object> response = new HashMap<>();
        response.put(codeName, CODE_STATUS_NOT_FOUND);
        response.put(messageName, MESSAGE_STATUS_NOT_FOUND);
        response.put(dataName, data);
        return response;
    }

    public Map<String, Object> responseDataNotFound(Object data, String message){
        Map<String, Object> response = new HashMap<>();
        response.put(codeName, CODE_STATUS_NOT_FOUND);
        response.put(messageName, message);
        response.put(dataName, data);
        return response;
    }

    public Map<String, Object> responseError(Object data){
        Map<String, Object> response = new HashMap<>();
        response.put(codeName, CODE_STATUS_ERROR);
        response.put(messageName, MESSAGE_STATUS_ERROR);
        response.put(dataName, data);
        return response;
    }

    public Map<String, Object> responseError(Object data, String message){
        Map<String, Object> response = new HashMap<>();
        response.put(codeName, CODE_STATUS_ERROR);
        response.put(messageName, message);
        response.put(dataName, data);
        return response;
    }

    public Map<String, Object> tokenAlreadyExpired(Object data){
        Map<String, Object> response = new HashMap<>();
        response.put(codeName, CODE_STATUS_ERROR);
        response.put(messageName, MESSAGE_MEMBER_ACCESS_HAS_EXPIRED);
        response.put(dataName, data);
        return response;
    }

    public Map<String, Object> memberNotHaveAccess(Object data){
        Map<String, Object> response = new HashMap<>();
        response.put(codeName, CODE_STATUS_ERROR);
        response.put(messageName, MESSAGE_MEMBER_NOT_HAVE_ACCESS);
        response.put(dataName, data);
        return response;
    }

}
