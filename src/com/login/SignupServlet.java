
package com.login;

import java.io.Console;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.*;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

@SuppressWarnings("serial")
public class SignupServlet extends HttpServlet {

	public static boolean checkUser(Key name) {
		boolean status = false;
		DatastoreService ds = DatastoreServiceFactory.getDatastoreService();

		try {
			ds.get(name);

			status = true;
		} catch (EntityNotFoundException e) {
			e.printStackTrace();
			status = false;
					}

		return status;
	}

	public void doPost(HttpServletRequest request, HttpServletResponse resp) throws IOException {
		com.google.appengine.api.datastore.DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
		PrintWriter out = resp.getWriter();
		resp.setContentType("text/html");
		String emailId = request.getParameter("emailId");
		String userName = request.getParameter("username");
		String password = request.getParameter("password");
		if (emailId != null && userName != null && password != null) {

			Entity user = new Entity("UserDetails", userName);
			Key key = KeyFactory.createKey("UserDetails", userName);
			System.out.println(checkUser(key));
			if (checkUser(key) == false) {
				user.setProperty("emailId", emailId);
				user.setProperty("userName", userName);
				user.setProperty("password", password);
				ds.put(user);
				out.print("<p>Your signup was successfull</p>");
				out.print("<a href=\"ProfileServlet\"> Profile</a>");
				out.print("<a href=\"LogoutServlet\"> Logout</a>");
				
			}

			else {
				out.print("<p>Please Enter Valid Details</p>");

			}
		} else {
			out.print("<p>Please Enter Valid Details</p>");
		}

	}

}
