package xyz.sheswayhot.resources.access;

import java.util.Set;

public interface Accessor {

    public String getPassword(String user);

    public Set<String> getRoles(String user);

    public Set<String> getPermissions(String user);

}
