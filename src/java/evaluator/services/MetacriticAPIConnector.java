package evaluator.services;

import java.io.IOException;
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

import evaluator.business.Game;

public class MetacriticAPIConnector {
	private final static Map<String, Integer> platformIdToName;
	static{
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("PlayStation 3",1);
		map.put("Xbox 360", 2);
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

	
	private MetacriticAPIConnector(){
		this.ApiURL = "https://byroredux-metacritic.p.mashape.com";
	}
	
	public static MetacriticAPIConnector getInstance()
	{
		if(_instance == null)
			_instance = new MetacriticAPIConnector();
		return _instance;
	}
	
	public Game findGame(String title, int platform) throws UnirestException, IOException
	{
		HttpResponse<JsonNode> mediaJson = Unirest.post(ApiURL + "/find/game")
				  .header("X-Mashape-Authorization", "uoEANscJrkQYgn1cJ5qFWnl16ax2TCNg")
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
				
		return askedGame;
	}
	
	public ArrayList<Game> searchGame(String title) throws UnirestException, IOException
	{
		HttpResponse<JsonNode> mediaJson = Unirest.post(ApiURL + "/search/game")
				  .header("X-Mashape-Authorization", "uoEANscJrkQYgn1cJ5qFWnl16ax2TCNg")
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
			int platformID = platformIdToName.get(gameJson.getString("platform"));
			askedGame.setPlatform(platformID);
			
			gameList.add(askedGame);
		}

		return gameList;
	}
	
}
