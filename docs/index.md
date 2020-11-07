# Dude! is a small Open Source security plugin that allows for quick securing of J2ee applications.

### Installation

Add dependency:

```
<dependency>
    <groupId>xyz.sheswayhot</groupId>
    <artifactId>dude</artifactId>
    <version>0.1</version>
</dependency>
```

Update `web.xml`, add DudeFilter declaration:

```
<filter>
    <filter-name>Dude</filter-name>
    <filter-class>xyz.sheswayhot.filters.DudeFilter</filter-class>
</filter>

<filter-mapping>
    <filter-name>Dude</filter-name>
    <url-pattern>/*</url-pattern>
</filter-mapping>
```

Create an Accessor, the class
that provides data to Dude.

Spring Example:

```
package xyz.ioc.accessor;

import xyz.sheswayhot.access.Accessor;
import org.springframework.beans.factory.annotation.Autowired;
import xyz.ioc.dao.AccountDao;
import xyz.ioc.model.Account;

import java.util.Set;

public class JdbcAccessor implements Accessor {

    @Autowired
    private AccountDao accountDao;

    public String getPassword(String user){
        String password = accountDao.getAccountPassword(user);
        return password;
    }

    public Set<String> getRoles(String user){
        Account account = accountDao.findByUsername(user);
        Set<String> roles = accountDao.getAccountRoles(account.getId());
        return roles;
    }

    public Set<String> getPermissions(String user){
        Account account = accountDao.findByUsername(user);
        Set<String> permissions = accountDao.getAccountPermissions(account.getId());
        return permissions;
    }

}
```

Add cookie xml declaration just in case the container 
doesn't pick up the cookie on authentication. Add to the **web.xml**

```
	<session-config>
		<tracking-mode>COOKIE</tracking-mode>
	</session-config>
```


Finally wire it up either by:

`Dude dude = new Dude(new JdcbAccessor());`

or if spring project define your beans as such:

```
<bean id="jdbcAccessor" class="com.project.accessor.JdbcAccessor"/>

<bean id="dude" class="xyz.sheswayhot.Dude" scope="singleton">
    <constructor-arg name="accessor" ref="jdbcAccessor"/>
</bean>
```

### Your project is configured. 

Now you can use can authenticate your user via :

`dude.login()`

And you'll have access to the following methods:

`dude.isAuthenticated()`

`dude.hasRole(role)`

`dude.hasPermission(permission)`

To sign out:

`dude.logout()`

### See, we told you it was cute!

Oh, we added something new. Taglibs:

You can now check authentication, and get user info 
within jsp without scriptlets.

**First include tag reference:**

`<%@ taglib prefix="dude" uri="/META-INF/tags/dude.tld"%>`

**3 available tags:**

Displays when user is anonymous & not authenticated

`<dude:anonymous></isAuthenticated>`


Displays content only when user is authenticated

`<dude:isAuthenticated></isAuthenticated>`


Displays current authenticated user

`<dude:username/>`


Sample web app can be viewed within the project under `src/sample-web`

If you want a more, we recommend Apache Shiro! It's a Bull Dog.