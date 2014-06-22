package evaluator.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;

import com.mashape.unirest.http.exceptions.UnirestException;

import evaluator.business.Game;
import evaluator.business.Movie;
import evaluator.persistence.PersistenceManager;
import evaluator.services.MetacriticAPIConnector;

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

		switch(media)
		{
		case "game":
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
				
				JSONArray object = new JSONArray(foundGames.toArray());
				resp.getWriter().print("<html><body><p>");
				resp.getWriter().print(object.toString());
				resp.getWriter().print("</p></body></html>");
//				req.setAttribute("gameList", object);
//				req.getRequestDispatcher("/showGameList.jsp").forward(req, resp);
			}
			break;
		case "movie":
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
				
				JSONArray object = new JSONArray(foundMovies.toArray());
				resp.getWriter().print("<html><body><p>");
				resp.getWriter().print(object.toString());
				resp.getWriter().print("</p></body></html>");
//				req.setAttribute("gameList", object);
//				req.getRequestDispatcher("/showGameList.jsp").forward(req, resp);
			}
			break;
			//TODO: autres medias
		}
	}
}
