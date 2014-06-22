package evaluator.persistence;

import java.util.ArrayList;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;

import evaluator.business.Game;
import evaluator.business.Movie;

public class PersistenceManager {
	private static PersistenceManager _instance = null;	
	
	private final SessionFactory sessionFactory;
	
	private PersistenceManager(){
        Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml");
        StandardServiceRegistryBuilder ssrb = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
        sessionFactory = configuration.buildSessionFactory(ssrb.build());
	}
	
	/**
	 * Permet d'obtenir l'unique instance du PersistenceManager
	 */
	public static PersistenceManager getInstance()
	{
		if(_instance == null)
			_instance = new PersistenceManager();
		return _instance;
	}

	/**
	 * Trouver un jeu à partir de son titre et de sa plateforme
	 * @param title Le titre du jeu
	 * @param platform L'identifiant de la platforme
	 * @return Le jeu trouvé, null si aucun n'est trouvé
	 */
	public Game findGame(String title, int platform) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		
		Criteria crit = session.createCriteria(Game.class);
		
		@SuppressWarnings("unchecked")
		ArrayList<Game> games = (ArrayList<Game>) crit
				.add(Restrictions.like("title", title))
				.add(Restrictions.eq("platform", platform)).list();
		
		tx.commit();
		session.clear();
		session.close();
		
		if(games.size() == 0)
			return null;
		
		return games.get(0);
	}
	
	/**
	 * Trouver un film à partir de son titre
	 * @param title Le titre du film
	 * @return Le film trouvé, null si aucun n'est trouvé
	 */
	public Movie findMovie(String title) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		
		Criteria crit = session.createCriteria(Movie.class);
		
		@SuppressWarnings("unchecked")
		ArrayList<Movie> movies = (ArrayList<Movie>) crit
				.add(Restrictions.like("title", title)).list();
		
		tx.commit();
		session.clear();
		session.close();
		
		if(movies.size() == 0)
			return null;
		
		return movies.get(0);
	}
	
	/**
	 * Trouver tous les jeux comportant un titre (ex: The Elder Scrolls)
	 * @param title Le titre que l'on veut rechercher
	 * @return La liste des jeux trouvés, null si aucun résultat
	 */
	public ArrayList<Game> searchGames(String title) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		
		Criteria crit = session.createCriteria(Game.class);
		
		@SuppressWarnings("unchecked")
		ArrayList<Game> games = (ArrayList<Game>) crit.add(Restrictions.like("title", title)).list();
		
		tx.commit();
		session.clear();
		session.close();
		
		if(games.isEmpty())
			return null;
		
		return games;
	}
	
	/**
	 * Rechercher tous les film ayant le titre passé en paramètre
	 * @param title Le titre que l'on veut rechercher
	 * @return La liste des films comportant ce titre (peut être un singleton)
	 */
	public ArrayList<Movie> searchMovies(String title) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		
		Criteria crit = session.createCriteria(Movie.class);
		
		@SuppressWarnings("unchecked")
		ArrayList<Movie> movies = (ArrayList<Movie>) crit.add(Restrictions.like("title", title)).list();
		
		tx.commit();
		session.clear();
		session.close();
		
		if(movies.isEmpty())
			return null;
		
		return movies;
	}
	
	/**
	 * Enregistrer le jeu passé en paramètre dans la base de données
	 * @param game Le jeu à auvegarder
	 */
	public void saveGame(Game game) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		
		session.saveOrUpdate(game);
		
		tx.commit();
		session.clear();
		session.close();
	}
	
	/**
	 * Enregistrer le film passé en paramètre dans la base de données
	 * @param game Le film à auvegarder
	 */
	public void saveMovie(Movie movie) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		
		session.saveOrUpdate(movie);
		
		tx.commit();
		session.clear();
		session.close();
	}

	/**
	 * Sauvegarder une collection de jeux dans la base de données
	 * @param games L'ensemble des jeux à sauvegarder
	 */
	public void saveGames(ArrayList<Game> games) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		
		for(Game game: games)
		{
			session.saveOrUpdate(game);
		}
		
		tx.commit();
		session.clear();
		session.close();
	}
	
	/**
	 * Sauvegarder une collection de films dans la base de données
	 * @param movies L'ensemble des films à sauvegarder
	 */
	public void saveMovies(ArrayList<Movie> movies) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		
		for(Movie movie: movies)
		{
			session.saveOrUpdate(movie);
		}
		
		tx.commit();
		session.clear();
		session.close();
	}
}
