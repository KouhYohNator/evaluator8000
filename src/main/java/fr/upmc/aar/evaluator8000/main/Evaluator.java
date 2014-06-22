package fr.upmc.aar.evaluator8000.main;

import java.io.IOException;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import fr.upmc.aar.evaluator8000.business.Game;
import fr.upmc.aar.evaluator8000.business.Movie;
import fr.upmc.aar.evaluator8000.services.MetacriticAPIConnector;

public class Evaluator {
	
	public static void main(String[] args){
		MetacriticAPIConnector connector = MetacriticAPIConnector.getInstance();

		String title = "The Elder Scrolls V: Skyrim";
		String title2 = "Fast & Furious";
		int platform = 1;
		String result1 = null, result2 = "";

		try {
			result1 = connector.findGame(title, platform).toString();
			for(Game g : connector.searchGames(title))
			{
				result2 += g.toString() + "\n";
			}
			for(Movie m : connector.searchMovies(title2))
			{
				result2 += m.toString() + "\n";
			}
			Unirest.shutdown();
		} catch (UnirestException e) {
			System.err.println("Impossible d'obtenir les résultats auprès de l'API\n");
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("L'API de communication Unirest a rencontré le problem suivant : \n");
			e.printStackTrace();
		}

		System.out.println("\n\t-------------------- FIND --------------------\n");
		System.out.println(result1);
		System.out.println("\n\n\t------------------- SEARCH -------------------\n");
		System.out.println(result2);

	}
}
