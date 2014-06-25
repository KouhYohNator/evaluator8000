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
import fr.upmc.aar.evaluator8000.persistence.PersistenceManager;
import fr.upmc.aar.evaluator8000.services.MetacriticAPIConnector;

public class MediaFinderServlet extends HttpServlet {

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
			if(req.getParameterMap().containsKey("title") && req.getParameterMap().containsKey("platform"))
			{
				String title = req.getParameter("title");
				int platform = 1;
				if(!req.getParameter("platform").isEmpty())
					platform = Integer.parseInt(req.getParameter("platform"));

				Game foundGame = persistence.findGame(title, platform);

				if(foundGame == null)
				{
					try {
						foundGame = connector.findGame(title, platform);
					} catch (UnirestException e) { e.printStackTrace(); }

					persistence.saveGame(foundGame);
				}
				else if(!foundGame.isCompleted())
				{
					try {
						foundGame.update(connector.findGame(title, platform));
					} catch (UnirestException e) { e.printStackTrace(); }
					
					persistence.saveGame(foundGame);
				}
				
				persistence.refreshMedia(foundGame);

				resp.getWriter().print("<html><body><p>");
				resp.getWriter().print(foundGame.toString());
				resp.getWriter().print("</p><br />");
				if(foundGame.getComments() != null){
					resp.getWriter().print("<ul>");
					for(Comment com: foundGame.getComments())
					{
						resp.getWriter().print("<li> From: " + com.getUser().getLogin() +
								" - Score: " + com.getScore() + "<br />");
						resp.getWriter().print("<p>"+ com.getContent() + "</p></li><br />");
					}
					resp.getWriter().print("</ul>");
				}
				resp.getWriter().print("</body></html>");
				//				req.setAttribute("game", object);
				//				req.getRequestDispatcher("/showGame.jsp").forward(req, resp);
			}
			break;
		case 2:
			if(req.getParameterMap().containsKey("title"))
			{
				String title = req.getParameter("title");

				Movie foundMovie = persistence.findMovie(title);

				if(foundMovie == null)
				{
					try {
						foundMovie = connector.findMovie(title);
					} catch (UnirestException e) { e.printStackTrace(); }

					persistence.saveMovie(foundMovie);
					persistence.refreshMedia(foundMovie);
				}

				resp.getWriter().print("<html><body><p>");
				resp.getWriter().print(foundMovie.toString());
				resp.getWriter().print("</p></body></html>");
				//				req.setAttribute("game", object);
				//				req.getRequestDispatcher("/showGame.jsp").forward(req, resp);
			}
			break;
			//TODO: autres medias
		}
	}

}
