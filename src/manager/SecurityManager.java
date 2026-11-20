
package manager;

public class SecurityManager {

    public static boolean hasPermission(String role, String action) {

        if (role == null || action == null) {
            return false;
        }

        role = role.toUpperCase();
        action = action.toUpperCase();

        if (role.equals("ADMIN")) {
            return true;
        }

        if (role.equals("STAFF")) {
            return action.equals("VIEW") ||
                   action.equals("ADD");
        }

        if (role.equals("VIEWER")) {
            return action.equals("VIEW");
        }

        return false;
    }
}
