package dev.yougo;

import dev.yougo.access.Accessor;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class Dude {

    public static final String COOKIE = "Dude.Session";

    static final String ALGORITHM = "SHA-256";

    public static final String MOO_KEY = "dude";
    public static final String USER_KEY = "user";


    static Accessor accessor;
    static Map<String, HttpSession> sessions = new ConcurrentHashMap<String, HttpSession>();


    public static String getUser(){
        HttpServletRequest req = Storage.getRequest();
        HttpSession session = req.getSession();

        if(session != null){
            return (String) session.getAttribute(USER_KEY);
        }
        return "";
    }


    public static String get(String key){
        HttpServletRequest req = Storage.getRequest();
        HttpSession session = req.getSession();

        if(session != null &&
            session.getAttribute(key) != null){
            return (String) session.getAttribute(key);
        }
        return "";
    }


    public static boolean login(String username, String password){
        String hashedPassword = Dude.hash(password);
        String storedPassword = accessor.getPassword(username);

        if(!isAuthenticated() &&
                storedPassword.equals(hashedPassword)){

            HttpServletRequest req = Storage.getRequest();

            HttpSession oldSession = req.getSession(false);
            if(oldSession != null){
                oldSession.invalidate();
            }

            HttpSession httpSession = req.getSession(true);
            httpSession.setAttribute(USER_KEY, username);

            sessions.put(httpSession.getId(), httpSession);

            Cookie cookie = new Cookie(COOKIE, httpSession.getId());
            cookie.setPath("/");

            HttpServletResponse resp = Storage.getResponse();
            resp.addCookie(cookie);
            
            return true;

        }

        return false;
    }



    public static boolean logout(){
        HttpServletRequest req = Storage.getRequest();
        HttpServletResponse resp = Storage.getResponse();
        HttpSession session = req.getSession();

        if(session != null){
            session.removeAttribute(USER_KEY);
            ServletContext context = req.getServletContext();
            context.removeAttribute(MOO_KEY);
            if(sessions.containsKey(session.getId())){
                sessions.remove(session.getId());
            }
        }

        expireCookie(req, resp);

        return true;
    }

    public static boolean isAuthenticated(){
        HttpServletRequest req = Storage.getRequest();
        if(req != null) {
            HttpSession session = req.getSession(false);

            if (session != null && sessions.containsKey(session.getId())) {
                return true;
            }
        }
        return false;
    }


    private static void expireCookie(HttpServletRequest req, HttpServletResponse resp){
        Cookie cookie = new Cookie(COOKIE, "");
        cookie.setMaxAge(0);
        resp.addCookie(cookie);
    }



    public static boolean containsCookie(HttpServletRequest req){
        Cookie[] cookies = req.getCookies();
        if(cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(COOKIE)) {
                    return true;
                }
            }
        }
        return false;
    }


    public static boolean hasRole(String role){
        String user = Dude.get(USER_KEY);
        if(user != null) {
            Set<String> roles = accessor.getRoles(user);
            if(roles.contains(role)){
                return true;
            }
        }
        return false;
    }


    public static boolean hasPermission(String permission){
        String user = Dude.get(USER_KEY);
        if(user != null) {
            Set<String> permissions = accessor.getPermissions(user);
            if(permissions.contains(permission)){
                return true;
            }
        }
        return false;
    }

    public static boolean setAccessor(Accessor accessor){
        Dude.accessor = accessor;
        return true;
    }

    public static String hash(String password){
        MessageDigest md = null;
        StringBuffer passwordHashed = new StringBuffer();

        try {
            md = MessageDigest.getInstance(Dude.ALGORITHM);
            md.update(password.getBytes());

            byte byteData[] = md.digest();

            for (int i = 0; i < byteData.length; i++) {
                passwordHashed.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
            }

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return passwordHashed.toString();
    }

    public boolean isConfigured(){
        return Dude.accessor != null ? true: false;
    }

}
