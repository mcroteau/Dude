package xyz.sheswayhot.web;

import xyz.sheswayhot.Parakeet;
import xyz.sheswayhot.ParakeetFactory;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class SecuredServlet extends HttpServlet {

    Parakeet parakeet;
    ParakeetFactory parakeetFactory;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext context = config.getServletContext();
        ParakeetFactory parakeetFactory = (ParakeetFactory) context.getAttribute("parakeetFactory");
        if(parakeetFactory == null) {
            parakeetFactory = new ParakeetFactory();
            context.setAttribute("parakeetFactory", parakeetFactory);
        }
        parakeet = parakeetFactory.getParakeet();
    }



    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        PrintWriter out = resp.getWriter();

        if(parakeet.isAuthenticated()){
            req.getRequestDispatcher("/jsp/secured.jsp").forward(req, resp);
        }else{
            out.println("<h1>Unauthorized yo!</h1>");
        }
    }

}
