package xyz.yougo.web;

import xyz.yougo.Dude;
import xyz.yougo.DudeFactory;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthServlet extends HttpServlet {

    Dude parakeet;
    DudeFactory parakeetFactory;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext context = config.getServletContext();
        DudeFactory parakeetFactory = (DudeFactory) context.getAttribute("parakeetFactory");
        if(parakeetFactory == null) {
            parakeetFactory = new DudeFactory();
            context.setAttribute("parakeetFactory", parakeetFactory);
        }
        parakeet = parakeetFactory.getDude();
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        if(parakeet.login(username, password)){
            req.getRequestDispatcher("/jsp/secured.jsp").forward(req, resp);
        }else{
            resp.sendRedirect("/b/");
        }

    }
}
