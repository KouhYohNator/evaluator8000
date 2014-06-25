package fr.upmc.aar.evaluator8000.servlets;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.upmc.aar.evaluator8000.business.User;
import fr.upmc.aar.evaluator8000.persistence.PersistenceManager;

public class UserRegisterServlet extends HttpServlet {

	private static final long serialVersionUID = -4027232697216894456L;

	private PersistenceManager persistence;

	@Override
	public void init() throws ServletException {
		this.persistence = PersistenceManager.getInstance();
	}
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		if(req.getMethod().equals("POST"))
			doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		if(req.getParameterMap().containsKey("login") && req.getParameterMap().containsKey("passwd"))
		{

			if(persistence.findUser(req.getParameter("login")) == null)
			{
				User newUser = new User();
				newUser.setLogin(req.getParameter("login"));

				byte[] passToBytes = req.getParameter("passwd").getBytes();
				String digestPassword = null;
				try {
					digestPassword = 
							new String(MessageDigest.getInstance("MD5").digest(passToBytes));
				} catch (NoSuchAlgorithmException e) { e.printStackTrace();	}

				if(digestPassword != null && !digestPassword.isEmpty())
				{
					newUser.setPassword(digestPassword);
					newUser.setActive(true);
					persistence.saveUser(newUser);
				}
				
				resp.getWriter().print("<html><body><h3>Utilisateur enregistré</h3><br />");
				resp.getWriter().print("<p>" + newUser.toString() + "</p>");
				resp.getWriter().print("</body></html>");
			}
			else
			{
				resp.getWriter().print("<html><body><h3>Utilisateur déjà enregistré</h3><br />");
				resp.getWriter().print("<p>" + req.getParameter("login") + "</p>");
				resp.getWriter().print("</body></html>");	
			}
		}
	}
}
