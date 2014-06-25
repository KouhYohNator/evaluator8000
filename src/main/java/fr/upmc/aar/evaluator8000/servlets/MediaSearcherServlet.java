package fr.upmc.aar.evaluator8000.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mashape.unirest.http.exceptions.UnirestException;

import fr.upmc.aar.evaluator8000.business.Game;
import fr.upmc.aar.evaluator8000.business.Movie;
import fr.upmc.aar.evaluator8000.persistence.PersistenceManager;
import fr.upmc.aar.evaluator8000.services.MetacriticAPIConnector;

public class MediaSearcherServlet extends HttpServlet {

	private static final long serialVersionUID = 6993935908330992917L;

	private MetacriticAPIConnector connector;
	private PersistenceManager persistence;

	private HashMap<String, Long> searchCache;

	@Override
	public void init() throws ServletException {
		this.connector = MetacriticAPIConnector.getInstance();
		this.persistence = PersistenceManager.getInstance();
		this.searchCache = new HashMap<String, Long>();
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
			if(req.getParameterMap().containsKey("title"))
			{
				String title = req.getParameter("title");
				long currentTime = System.currentTimeMillis();

				ArrayList<Game> foundGames = null;

				if(searchCache.containsKey(title) && 
						(currentTime - searchCache.get(title) < 86400000))
				{
					foundGames = persistence.searchGames(title);
				}
				else
				{
					try {
						foundGames = connector.searchGames(title);
						persistence.saveGames(foundGames);
						searchCache.put(title, currentTime);
					} catch (UnirestException e) { e.printStackTrace(); }
				}

				resp.getWriter().print("<html><body><ul>");
				for(Game game : foundGames)
				{
					resp.getWriter().print("<li>" + game.toString() + "</li><br />");
				}
				resp.getWriter().print("</ul></body></html>");
				//				req.setAttribute("gameList", object);
				//				req.getRequestDispatcher("/showGameList.jsp").forward(req, resp);
			}
			break;
		case 2:
			if(req.getParameterMap().containsKey("title"))
			{
				String title = req.getParameter("title");
				long currentTime = System.currentTimeMillis();

				ArrayList<Movie> foundMovies = null;

				if(searchCache.containsKey(title) && 
						(currentTime - searchCache.get(title) < 86400000))
				{
					foundMovies = persistence.searchMovies(title);
				}
				else
				{
					try {
						foundMovies = connector.searchMovies(title);
						persistence.saveMovies(foundMovies);
						searchCache.put(title, currentTime);
					} catch (UnirestException e) { e.printStackTrace(); }
				}

				resp.getWriter().print("<html><body><ul>");
				for(Movie movie : foundMovies)
				{
					resp.getWriter().print("<li>" + movie.toString() + "</li><br />");
				}
				resp.getWriter().print("</ul></body></html>");
				//				req.setAttribute("gameList", object);
				//				req.getRequestDispatcher("/showGameList.jsp").forward(req, resp);
			}
			break;
			//TODO: autres medias
		}
	}
}
