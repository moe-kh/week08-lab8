/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filters;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import services.AccountService;
import services.UserService;
import servlets.LoginServlet;

/**
 *
 * @author awarsyle
 */
public class AuthenticationFilter implements Filter {
    
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {
        
            // this code will execute before HomeServlet and UsersServlet
            HttpServletRequest r = (HttpServletRequest)request;
            HttpSession session = r.getSession();
               String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        
        AccountService ac = new AccountService();
            if (session.getAttribute("username") != null) {
                // if they are authenticated, i.e. have a username in the session,
                // then allow them to continue on to the servlet
                chain.doFilter(request, response);
            } else {
                // they do not have a username in the sesion
                // so, send them to login 
                 if (ac.login(username, password) != null) {
            
          
               
                session.setAttribute("username", username);
                
                UserService u=new UserService();
                int id;
                System.out.println("oihd[ou qhewpf  ibwcpi  gbpqicyg    pieqygc");
                try{
                id = u.get(username).getRole().getRoleid();
                System.out.println(id);
                session.setAttribute("id", id);
                     HttpServletResponse resp = (HttpServletResponse)response;
                 resp.sendRedirect("users");
            } catch (Exception ex) {
                Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
       
                       
            
                 }else {
                HttpServletResponse resp = (HttpServletResponse)response;
                resp.sendRedirect("login");
            }
            
            // this code will execute after HomeServlet and UsersServlet
            }
            
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        
    }

    @Override
    public void destroy() {
        
    }

    
}
