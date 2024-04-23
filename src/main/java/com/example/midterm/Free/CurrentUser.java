package com.example.midterm.Free;

public class CurrentUser {
    private static String currentUser;

    public static void setCurrentUser(String username) {
        currentUser = username;
    }

    public static void clearCurrentUser() {
        currentUser = null;
    }

    public static String getCurrentUser() {
        return currentUser;
    }
}
