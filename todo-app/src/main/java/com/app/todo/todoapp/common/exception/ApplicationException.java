package com.app.todo.todoapp.common.exception;

public class ApplicationException extends Exception {

    private String messageKey;
    private String localizedMessage;

    public ApplicationException() {
        super();
    }

    public ApplicationException(String message) {
        super(message);
        this.messageKey = message;
        if(this.messageKey != null) {
            this.localizedMessage = null; //TODO: set localized message from properties
        }
    }

    public String getMessageKey() {
        return messageKey;
    }

    public void setMessageKey(String messageKey) {
        this.messageKey = messageKey;
    }

    @Override
    public String getLocalizedMessage() {
        return localizedMessage;
    }

    public void setLocalizedMessage(String localizedMessage) {
        this.localizedMessage = localizedMessage;
    }
}
