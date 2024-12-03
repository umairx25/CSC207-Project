package entity;

public class CurrentUser {
    private static String email;

    public CurrentUser() {
        CurrentUser.email = "";
    }

    public static void setEmail(String email) {
        CurrentUser.email = email;
    }

    public static String getemail() {
        return email;
    }

    public static void logout() {
        email = "";
    }
}