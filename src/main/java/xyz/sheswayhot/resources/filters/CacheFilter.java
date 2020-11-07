package xyz.sheswayhot.resources.filters;

import xyz.sheswayhot.Dude;
import xyz.sheswayhot.resources.Storage;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class CacheFilter implements Filter {

    @Override
    public void init(FilterConfig config) throws ServletException { }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        Storage.storeRequest(req);
        Storage.storeResponse(resp);

        if(request != null && response != null) {
            HttpSession httpSession = req.getSession(false);
            if(httpSession != null) {
                Cookie cookie = new Cookie(Dude.COOKIE, httpSession.getId());
                cookie.setPath("/");
                resp.addCookie(cookie);
            }
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() { }

}
