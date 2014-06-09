package com.webapp2;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;
import java.util.Map.Entry;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;

	public void init( ){
		ServletContext ctx = getServletContext();
	}
	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException 
	{
		ServletContext ctx = request.getServletContext();
		Properties prop = (Properties)ctx.getAttribute("input");
		String user = request.getParameter("user");
		String pwd = request.getParameter("pwd");
		for(Entry<Object, Object> e:prop.entrySet())
		{
			if(user.equals(e.getKey())&&pwd.equals(e.getValue()))
			{
				HttpSession session = request.getSession();
				session.setAttribute("name", user);
				session.setMaxInactiveInterval(2*60);
				response.sendRedirect("loginsuccsess.html");
				break;
			}
		}
		RequestDispatcher rd = getServletContext().getRequestDispatcher("/login.html");
		PrintWriter out= response.getWriter();
		out.println("<font color=red>Either user name or password is wrong.</font>");
		rd.include(request, response);
	}

}
