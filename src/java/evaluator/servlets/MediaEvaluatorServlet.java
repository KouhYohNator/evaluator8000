package evaluator.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.mashape.unirest.http.exceptions.UnirestException;

import evaluator.business.Game;
import evaluator.persistence.PersistenceManager;
import evaluator.services.MetacriticAPIConnector;

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

		switch(media)
		{
		case "game":
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
