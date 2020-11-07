package xyz.yougo.tags;

import xyz.yougo.Dude;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class UsernameTag extends TagSupport {

    @Override
    public int doStartTag() throws JspException {

        try {

            JspWriter out = pageContext.getOut();

            HttpServletRequest req = (HttpServletRequest) pageContext.getRequest();
            HttpSession session = req.getSession(false);

            if(session != null) {

                if(Dude.isAuthenticated()){
                    out.println(Dude.getUser());
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return TagSupport.SKIP_BODY;
    }
}
