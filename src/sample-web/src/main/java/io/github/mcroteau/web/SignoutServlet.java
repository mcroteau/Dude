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

public class SignoutServlet extends HttpServlet {

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

    public void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {
        parakeet.logout();
        req.setAttribute("message", "Successfully signed out!");
        req.getRequestDispatcher("/jsp/index.jsp").forward(req, resp);
    }

}
