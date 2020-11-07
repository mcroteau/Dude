<%@ taglib prefix="dude" uri="/META-INF/tags/dude.tld"%>

<dude:isAuthenticated>
    Welcome back! <dude:username></dude:username>
    <h1>Secured Jsp</h1>
    <a href="/b/signout">Signout</a>
</dude:isAuthenticated>
