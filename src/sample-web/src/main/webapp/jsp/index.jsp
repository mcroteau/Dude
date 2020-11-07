<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="dude" uri="/META-INF/tags/dude.tld"%>

<h1>Signin</h1>

<dude:anonymous>

    <c:if test="${not empty requestScope.message}">
       <p><c:out value="${message}"/></p>
    </c:if>

    <form action="/b/auth" method="post">

        <label>Username</label>
        <input type="text" name="username" placeholder="Username" value="mockyah"/>

        <br/>

        <label>Password</label>
        <input type="text" name="password" placeholder="Password ***" value="birdyah"/>

        <br/>
        <br/>

        <input type="submit" value="Signin"/>
    </form>
</dude:anonymous>
<dude:isAuthenticated>
    <p>You're already signed in... <a href="/b/signout">Signout</a></p>
</dude:isAuthenticated>