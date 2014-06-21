package evaluator.servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.mashape.unirest.http.exceptions.UnirestException;

import evaluator.business.Game;
import evaluator.persistence.PersistenceManager;
import evaluator.services.MetacriticAPIConnector;

public class MediaSearcherServlet extends HttpServlet {

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

		switch(media)
		{
		case "game":
			if(req.getParameterMap().containsKey("title"))
			{
				String title = req.getParameter("title");

				ArrayList<Game> foundGames = persistence.searchGames(title);

				if(foundGames == null)
				{
					try {
						foundGames = connector.searchGame(title);
						JSONObject object = new JSONObject(foundGames.toArray());
						req.setAttribute("gameList", object);
						req.getRequestDispatcher("/showGameList.jsp").forward(req, resp);
					} catch (UnirestException e) { e.printStackTrace(); }
				}
			}
			break;
			//TODO: autres medias
		}
	}
}
