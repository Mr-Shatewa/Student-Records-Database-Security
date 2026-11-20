package manager;

public class RoleManager {

    public static String getRole(String username) {

        switch (username) {
            case "admin_user":
                return "ADMIN";

            case "staff_user":
                return "STAFF";

            case "viewer_user":
                return "VIEWER";

            default:
                return "UNKNOWN";
        }
    }
}
