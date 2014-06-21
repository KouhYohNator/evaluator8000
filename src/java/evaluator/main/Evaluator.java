package evaluator.main;

import java.io.IOException;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import evaluator.business.Game;
import evaluator.services.MetacriticAPIConnector;

public class Evaluator {
	
	public static void main(String[] args){
		MetacriticAPIConnector connector = MetacriticAPIConnector.getInstance();

		String title = "The Elder Scrolls V: Skyrim";
		int platform = 1;
		String result1 = null, result2 = "";

		try {
			result1 = connector.findGame(title, platform).toString();
			for(Game g : connector.searchGame(title))
			{
				result2 += g.toString() + "\n";
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
