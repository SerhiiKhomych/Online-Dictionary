package com.study.utils;

public enum ServiceErrorCode {

    GENERAL_ERROR("generalError", true),
    NOT_AUTHORIZED("notAuthorized", false),
    PERMISSION_DENIED("permissionDenied", false),
    INVALID_ARGUMENTS("invalidArguments", false),
    ITEM_NOT_FOUND("itemNotFound", false);

    String resKey;
    boolean showErrorMessage;

    ServiceErrorCode(String resKey, boolean showErrorMessage) {
        this.resKey = resKey;
        this.showErrorMessage = showErrorMessage;
    }

    public String getResKey() {
        return resKey;
    }

    public boolean showErrorMessage() {
        return showErrorMessage;
    }
}