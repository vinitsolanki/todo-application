package com.app.todo.todoapp.common.bean;

import com.app.todo.todoapp.common.MessageKeys;

public class GenericResponseBean<T> {

    private boolean error;
    private String messageKey;
    private String localizedMessage;
    private T data;

    protected GenericResponseBean() {
    }

    protected GenericResponseBean(String messageKey) {
        this.messageKey = messageKey;
        if(messageKey != null) {
            this.localizedMessage = null; //TODO: set localized message from messageKey
        }
    }

    public static GenericResponseBean build(String messageKey) {
        GenericResponseBean responseBean = new GenericResponseBean(messageKey);
        return responseBean;
    }

    public static <T> GenericResponseBean build(T data) {
        GenericResponseBean responseBean = new GenericResponseBean(MessageKeys.RESULT_OK);
        responseBean.setData(data);
        return responseBean;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getMessageKey() {
        return messageKey;
    }

    public void setMessageKey(String messageKey) {
        this.messageKey = messageKey;
    }

    public String getLocalizedMessage() {
        return localizedMessage;
    }

    public void setLocalizedMessage(String localizedMessage) {
        this.localizedMessage = localizedMessage;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
