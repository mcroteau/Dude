package xyz.yougo.access;

import xyz.yougo.Dude;

import java.util.HashSet;
import java.util.Set;

public class MockAccessor implements Accessor {

    private static String MOCK_PASS     = "birdyah";

    private static String MOCK_ADMIN    = "ADMIN_ROLE";
    private static String MOCK_CUSTOMER = "CUSTOMER_ROLE";

    private static String MOCK_ACCOUNT_PERMISSION = "account:maintenance:1";
    private static String MOCK_POST_PERMISSION = "post:maintenance:3";

    @Override
    public String getPassword(String user) {
        return Dude.hash(MOCK_PASS);
    }

    @Override
    public Set<String> getRoles(String user) {
        Set<String> roles = new HashSet<String>();
        roles.add(MOCK_ADMIN);
        roles.add(MOCK_CUSTOMER);
        return roles;
    }

    @Override
    public Set<String> getPermissions(String user) {
        Set<String> permissions = new HashSet<String>();
        permissions.add(MOCK_ACCOUNT_PERMISSION);
        permissions.add(MOCK_POST_PERMISSION);
        return permissions;
    }
}
