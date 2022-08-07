package com.example.api1.enumeration;

public enum UserMessageStatus {
    DONE("Done"),
    PENDING("Pending"),
    IN_PROGRESS("In Progress");

    String displayName;

    UserMessageStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
