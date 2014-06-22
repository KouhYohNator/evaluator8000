package fr.upmc.aar.evaluator8000.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.mashape.unirest.http.exceptions.UnirestException;

import fr.upmc.aar.evaluator8000.business.Game;
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

		if(req.getMethod().equals("PUT"))
			doPut(req, resp);
	}

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp)
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
				int platform = Integer.parseInt(req.getParameter("platform"));

				Game foundGame = persistence.findGame(title, platform);

				if(foundGame == null)
				{
					try {
						foundGame = connector.findGame(title, platform);
					} catch (UnirestException e) { e.printStackTrace(); }
				}

				JSONObject object = new JSONObject(foundGame);
				req.setAttribute("game", object);
				req.getRequestDispatcher("/showGame.jsp").forward(req, resp);

			}
			break;
			//TODO: autres medias
		}
	}
}
