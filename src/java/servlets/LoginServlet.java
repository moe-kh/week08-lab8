/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import services.AccountService;
import services.UserService;

/**
 *
 * @author awarsyle
 */
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // more secure, logout if seeing login page
        HttpSession session = request.getSession();
        session.invalidate();
      
           
        getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        
        AccountService ac = new AccountService();
     
          
        if (ac.login(username, password) != null) {
            
          
                HttpSession session = request.getSession();
                session.setAttribute("username", username);
                
                UserService u=new UserService();
                int id;
                System.out.println("oihd[ou qhewpf  ibwcpi  gbpqicyg    pieqygc");
                try{
                id = u.get(username).getRole().getRoleid();
                System.out.println(id);
                session.setAttribute("id", id);
                response.sendRedirect("users");
            } catch (Exception ex) {
                Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
       
                       
            
           
            
        } else {
            getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
        }
    }

}
