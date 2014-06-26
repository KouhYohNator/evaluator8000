package fr.upmc.aar.evaluator8000.servlets;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.upmc.aar.evaluator8000.business.User;
import fr.upmc.aar.evaluator8000.persistence.PersistenceManager;

public class UserConnexionServlet extends HttpServlet {

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
		
		HttpSession session = req.getSession(true);
		
		if(req.getParameterMap().containsKey("login") && req.getParameterMap().containsKey("passwd"))
		{
			User user = persistence.findUser(req.getParameter("login"));
			
			byte[] passToBytes = req.getParameter("passwd").getBytes();
			String digestPassword = null;
			try {
				digestPassword = 
						new String(MessageDigest.getInstance("MD5").digest(passToBytes));
			} catch (NoSuchAlgorithmException e) { e.printStackTrace();	}


			if(user != null && user.getPassword() == digestPassword)
			{
				user.setActive(true);
				session.setAttribute("user", req.getParameter("login"));
				resp.sendRedirect("index.jsp");
			}
			else
			{
				req.setAttribute("badConnexion", "Erreur : authentifiant ou mot de passe incorrect");
				resp.sendRedirect("connexion.jsp");	
			}
		}
	}
}
