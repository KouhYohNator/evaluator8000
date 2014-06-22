package fr.upmc.aar.evaluator8000.services;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import fr.upmc.aar.evaluator8000.business.Game;
import fr.upmc.aar.evaluator8000.business.Movie;

public class MetacriticAPIConnector {

	// Association des identifiants de plateforme avec leur nom (Jeux)
	private final static Map<String, Integer> platformIdToName;
	static{
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("PlayStation 3",1);
		map.put("PS3",1);
		map.put("Xbox 360", 2);
		map.put("X360", 2);
		map.put("PC", 3);
		map.put("DS", 4);
		map.put("PlayStation 2", 6);
		map.put("PSP", 7);
		map.put("Wii", 8);
		map.put("iPhone/iPad", 9);
		map.put("PlayStation", 10);
		map.put("Game Boy Advance", 11);
		map.put("Xbox", 12);
		map.put("GameCube", 13);
		map.put("Nintendo 64", 14);
		map.put("Dreamcast", 15);
		map.put("3DS", 16);
		map.put("PlayStation Vita", 67365);
		map.put("Wii U", 68410);
		platformIdToName = Collections.unmodifiableMap(map);
	}

	private static MetacriticAPIConnector _instance = null;

	private final String ApiURL;
	private final String ApiKey;

	private MetacriticAPIConnector(){
		this.ApiURL = "https://byroredux-metacritic.p.mashape.com";
		this.ApiKey = "uoEANscJrkQYgn1cJ5qFWnl16ax2TCNg";
	}

	/**
	 * Permet d'obtenir l'unique instance du MetacriticAPIConnector
	 */
	public static MetacriticAPIConnector getInstance()
	{
		if(_instance == null)
			_instance = new MetacriticAPIConnector();
		return _instance;
	}

	/**
	 * Envoie une requête HTTP à l'API pour trouver un jeu à partir de son titre
	 * et de l'identifiant de sa plateforme
	 * @param title Le titre du jeu à trouver
	 * @param platform La plateforme du jeu
	 * @return Le jeu renvoyé par l'API
	 * @throws UnirestException En cas d'erreur de communication avec le serveur
	 */
	public Game findGame(String title, int platform) throws UnirestException
	{
		HttpResponse<JsonNode> mediaJson = Unirest.post(ApiURL + "/find/game")
				.header("X-Mashape-Authorization", ApiKey)
				.field("title", title)
				.field("platform", String.valueOf(platform))
				.asJson();

		JSONObject gameJson = mediaJson.getBody().getObject().getJSONObject("result");

		Game askedGame = new Game();
		askedGame.setTitle(gameJson.getString("name"));
		askedGame.setGenre(gameJson.getString("genre"));
		askedGame.setImage(gameJson.getString("thumbnail"));
		askedGame.setRelease(Date.valueOf(gameJson.getString("rlsdate")));
		askedGame.setDeveloper(gameJson.getString("developer"));
		askedGame.setPublisher(gameJson.getString("publisher"));
		askedGame.setPlatform(platform);
		askedGame.setCompleted(true);

		return askedGame;
	}

	/**
	 * Envoie une requête HTTP à l'API pour trouver un film à partir de son titre
	 * @param title Le titre du film à trouver
	 * @return Le film renvoyé par l'API
	 * @throws UnirestException En cas d'erreur de communication avec le serveur
	 */
	public Movie findMovie(String title) throws UnirestException
	{
		HttpResponse<JsonNode> mediaJson = Unirest.post(ApiURL + "/find/movie")
				.header("X-Mashape-Authorization", ApiKey)
				.field("title", title)
				.asJson();

		JSONObject movieJson = mediaJson.getBody().getObject().getJSONObject("result");

		Movie askedMovie = new Movie();
		askedMovie.setTitle(movieJson.getString("name"));
		askedMovie.setRelease(Date.valueOf(movieJson.getString("rlsdate")));
		askedMovie.setRating(movieJson.getString("rating"));
		askedMovie.setCast(movieJson.getString("cast"));
		askedMovie.setGenre(movieJson.getString("genre"));
		askedMovie.setRuntime(movieJson.getString("runtime"));
		askedMovie.setImage(movieJson.getString("thumbnail"));
		askedMovie.setDirector(movieJson.getString("director"));
		askedMovie.setCompleted(true);
		
		return askedMovie;
	}

	/**
	 * Envoie une requête HTTP à l'API pour trouver une liste de jeu ayant
	 * comme titre celui passé en paramètre
	 * @param title Le titre que l'on souhaite rechercher
	 * @return L'ensemble des jeux ayant ce titre
	 * @throws UnirestException En cas d'erreur de communication avec le serveur
	 */
	public ArrayList<Game> searchGames(String title) throws UnirestException
	{
		HttpResponse<JsonNode> mediaJson = Unirest.post(ApiURL + "/search/game")
				.header("X-Mashape-Authorization", ApiKey)
				.field("title", title)
				.asJson();

		ArrayList<Game> gameList = new ArrayList<Game>();
		JSONArray gameListJson = mediaJson.getBody().getObject().getJSONArray("results");

		for(int i=0; i<gameListJson.length(); i++)
		{
			JSONObject gameJson = gameListJson.getJSONObject(i);

			Game askedGame = new Game();
			askedGame.setTitle(gameJson.getString("name"));
			askedGame.setRelease(Date.valueOf(gameJson.getString("rlsdate")));
			askedGame.setPublisher(gameJson.getString("publisher"));
			int platformID = 0;
			try{
				platformID= platformIdToName.get(gameJson.getString("platform"));
			} catch (NullPointerException e) {System.err.println("Aucun ID trouvé pour la platforme: " + gameJson.getString("platform"));}
			askedGame.setPlatform(platformID);
			askedGame.setCompleted(false);

			gameList.add(askedGame);
		}

		return gameList;
	}

	/**
	 * Envoie une requête HTTP à l'API pour trouver une liste de films ayant
	 * un titre similaire à celui passé en paramètre
	 * @param title Le titre que l'on souhaite rechercher
	 * @return L'ensemble des films ayant un titre similaire
	 * @throws UnirestException En cas d'erreur de communication avec le serveur
	 */
	public ArrayList<Movie> searchMovies(String title) throws UnirestException
	{
		HttpResponse<JsonNode> mediaJson = Unirest.post(ApiURL + "/search/movie")
				.header("X-Mashape-Authorization", ApiKey)
				.field("title", title)
				.field("max_pages", "2")
				.asJson();

		ArrayList<Movie> movieList = new ArrayList<Movie>();
		JSONArray movieListJson = mediaJson.getBody().getObject().getJSONArray("results");

		for(int i=0; i<movieListJson.length(); i++)
		{
			JSONObject movieJson = movieListJson.getJSONObject(i);

			Movie askedMovie = new Movie();
			askedMovie.setTitle(movieJson.getString("name"));
			askedMovie.setRelease(Date.valueOf(movieJson.getString("rlsdate")));
			askedMovie.setRating(movieJson.getString("rating"));
			askedMovie.setCast(movieJson.getString("cast"));
			askedMovie.setGenre(movieJson.getString("genre"));
			askedMovie.setRuntime(movieJson.getString("runtime"));
			askedMovie.setCompleted(false);

			movieList.add(askedMovie);
		}

		return movieList;
	}
}
