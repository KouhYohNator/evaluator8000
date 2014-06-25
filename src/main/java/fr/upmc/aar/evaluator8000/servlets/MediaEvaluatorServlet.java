package fr.upmc.aar.evaluator8000.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mashape.unirest.http.exceptions.UnirestException;

import fr.upmc.aar.evaluator8000.business.Comment;
import fr.upmc.aar.evaluator8000.business.Game;
import fr.upmc.aar.evaluator8000.business.Movie;
import fr.upmc.aar.evaluator8000.business.User;
import fr.upmc.aar.evaluator8000.persistence.PersistenceManager;
import fr.upmc.aar.evaluator8000.services.MetacriticAPIConnector;

public class MediaEvaluatorServlet extends HttpServlet {

	private static final long serialVersionUID = 6993935908330992917L;

	private MetacriticAPIConnector connector;
	private PersistenceManager persistence;

	@Override
	public void init() throws ServletException {
		this.connector = MetacriticAPIConnector.getInstance();
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

		String media = req.getParameter("media");

		// Compatibilité java 1.6 pour tomcat
		int mediaType = 0;
		if(media.equals("game")) mediaType = 1;
		else if(media.equals("movie")) mediaType = 2;

		switch(mediaType)
		{
		case 1:
			if(req.getParameterMap().containsKey("title") 
					&& req.getParameterMap().containsKey("platform")
					&& req.getParameterMap().containsKey("login"));
			{
				String title = req.getParameter("title");
				String login = req.getParameter("login");
				String content = req.getParameter("content");
				double score = Double.parseDouble(req.getParameter("score"));

				int platform = 1;
				if(!req.getParameter("platform").isEmpty())
					platform = Integer.parseInt(req.getParameter("platform"));

				// Recherche du jeu
				Game foundGame = persistence.findGame(title, platform);
				if(foundGame == null)
				{
					try {
						foundGame = connector.findGame(title, platform);
					} catch (UnirestException e) { e.printStackTrace(); }
				}

				// Recherche de l'utilisateur
				User foundUser = persistence.findUser(login);

				// Media et utilisateur trouvé
				if(foundUser != null && foundGame != null)
				{
					Comment foundComment = persistence.findComment(foundUser, foundGame);

					// Si pas de commentaire alors on peut le poster
					if(foundComment == null)
					{
						foundComment = new Comment();
						foundComment.setUser(foundUser);
						foundComment.setMedia(foundGame);
						foundComment.setContent(content);
						foundComment.setScore(score);

						persistence.saveComment(foundComment);

						resp.getWriter().print("<html><body><h3>Commentaire enregistré</h3><br />");
						resp.getWriter().print("<p>" + foundComment.toString() + "</p>");
						resp.getWriter().print("</body></html>");
					}
					else
					{
						resp.getWriter().print("<html><body><h3>Commentaire déjà existant</h3><br />");
						resp.getWriter().print("<p>" + foundComment.toString() + "</p>");
						resp.getWriter().print("</body></html>");
					}
				}
				else
				{
					resp.getWriter().print("<html><body><h3>Media ou utilisateur inconnu</h3><br />");
					resp.getWriter().print("</body></html>");
				}
			}
			break;
		case 2:
			if(req.getParameterMap().containsKey("title") 
					&& req.getParameterMap().containsKey("login"));
			{
				String title = req.getParameter("title");
				String login = req.getParameter("login");
				String content = req.getParameter("content");
				double score = Double.parseDouble(req.getParameter("score"));

				// Recherche du jeu
				Movie foundMovie = persistence.findMovie(title);
				if(foundMovie == null)
				{
					try {
						foundMovie = connector.findMovie(title);
					} catch (UnirestException e) { e.printStackTrace(); }
				}

				// Recherche de l'utilisateur
				User foundUser = persistence.findUser(login);

				// Media et utilisateur trouvé
				if(foundUser != null && foundMovie != null)
				{
					Comment foundComment = persistence.findComment(foundUser, foundMovie);

					// Si pas de commentaire alors on peut le poster
					if(foundComment == null)
					{
						foundComment = new Comment();
						foundComment.setUser(foundUser);
						foundComment.setMedia(foundMovie);
						foundComment.setContent(content);
						foundComment.setScore(score);

						persistence.saveComment(foundComment);

						resp.getWriter().print("<html><body><h3>Commentaire enregistré</h3><br />");
						resp.getWriter().print("<p>" + foundComment.toString() + "</p>");
						resp.getWriter().print("</body></html>");
					}
					else
					{
						resp.getWriter().print("<html><body><h3>Commentaire déjà existant</h3><br />");
						resp.getWriter().print("<p>" + foundComment.toString() + "</p>");
						resp.getWriter().print("</body></html>");
					}
				}
				else
				{
					resp.getWriter().print("<html><body><h3>Media ou utilisateur inconnu</h3><br />");
					resp.getWriter().print("</body></html>");
				}
			}
			break;
			//TODO: autres medias
		}
	}
}
