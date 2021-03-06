package fr.upmc.aar.evaluator8000.persistence;

import java.util.ArrayList;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;

import fr.upmc.aar.evaluator8000.business.Comment;
import fr.upmc.aar.evaluator8000.business.Game;
import fr.upmc.aar.evaluator8000.business.Media;
import fr.upmc.aar.evaluator8000.business.Movie;
import fr.upmc.aar.evaluator8000.business.User;

public class PersistenceManager {
	private static PersistenceManager _instance = null;	
	
	private final SessionFactory sessionFactory;
	private Session session;
	
	private PersistenceManager(){
        Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml");
        StandardServiceRegistryBuilder ssrb = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
        sessionFactory = configuration.buildSessionFactory(ssrb.build());
        session = sessionFactory.openSession();
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
	 * Trouver un jeu Ã  partir de son titre et de sa plateforme
	 * @param title Le titre du jeu
	 * @param platform L'identifiant de la platforme
	 * @return Le jeu trouvÃ©, null si aucun n'est trouvÃ©
	 */
	public Game findGame(String title, int platform) {
		Transaction tx = session.beginTransaction();
		
		Criteria crit = session.createCriteria(Game.class);
		
		@SuppressWarnings("unchecked")
		ArrayList<Game> games = (ArrayList<Game>) crit
				.add(Restrictions.like("title", title))
				.add(Restrictions.eq("platform", platform)).list();
		
		tx.commit();
		session.flush();
		
		if(games.size() == 0)
			return null;
		
		return games.get(0);
	}
	
	/**
	 * Trouver un film Ã  partir de son titre
	 * @param title Le titre du film
	 * @return Le film trouvÃ©, null si aucun n'est trouvÃ©
	 */
	public Movie findMovie(String title) {
		Transaction tx = session.beginTransaction();
		
		Criteria crit = session.createCriteria(Movie.class);
		
		@SuppressWarnings("unchecked")
		ArrayList<Movie> movies = (ArrayList<Movie>) crit
				.add(Restrictions.like("title", title)).list();
		
		tx.commit();
		
		if(movies.size() == 0)
			return null;
		
		return movies.get(0);
	}
	
	/**
	 * Trouver un utilisateur Ã  partir de son identifiant
	 * @param login L'identifiant de l'utilisateur
	 * @return L'utilisateur trouvÃ©, null si aucun n'est trouvÃ©
	 */
	public User findUser(String login) {
		Transaction tx = session.beginTransaction();
		
		Criteria crit = session.createCriteria(User.class);
		
		@SuppressWarnings("unchecked")
		ArrayList<User> users = (ArrayList<User>) crit
				.add(Restrictions.like("login", login)).list();
		
		tx.commit();
		session.flush();
		
		if(users.size() == 0)
			return null;
		
		return users.get(0);
	}
	
	/**
	 * Trouver un commentaire Ã  partir de l'utilisateur et du media
	 * @param user L'utilisateur qui a commentÃ©
	 * @param media Le media auquel est attachÃ© le commentaire
	 * @return Le commentaire trouvÃ©, null si aucun n'est trouvÃ©
	 */
	public Comment findComment(User user, Media media) {
		Transaction tx = session.beginTransaction();
		
		Criteria crit = session.createCriteria(Comment.class);
		
		@SuppressWarnings("unchecked")
		ArrayList<Comment> comments = (ArrayList<Comment>) crit
				.add(Restrictions.like("user", user))
				.add(Restrictions.like("media", media)).list();
		
		tx.commit();
		session.flush();
		
		if(comments.size() == 0)
			return null;
		
		return comments.get(0);
	}
	
	/**
	 * Trouver tous les jeux comportant un titre (ex: The Elder Scrolls)
	 * @param title Le titre que l'on veut rechercher
	 * @return La liste des jeux trouvÃ©s, null si aucun rÃ©sultat
	 */
	public ArrayList<Game> searchGames(String title) {
		Transaction tx = session.beginTransaction();
		
		Criteria crit = session.createCriteria(Game.class);
		
		@SuppressWarnings("unchecked")
		ArrayList<Game> games = (ArrayList<Game>) crit.add(Restrictions.like("title", title)).list();
		
		tx.commit();
		session.flush();
		
		if(games.isEmpty())
			return null;
		
		return games;
	}
	
	/**
	 * Rechercher tous les film ayant le titre passÃ© en paramÃ¨tre
	 * @param title Le titre que l'on veut rechercher
	 * @return La liste des films comportant ce titre (peut Ãªtre un singleton)
	 */
	public ArrayList<Movie> searchMovies(String title) {
		Transaction tx = session.beginTransaction();
		
		Criteria crit = session.createCriteria(Movie.class);
		
		@SuppressWarnings("unchecked")
		ArrayList<Movie> movies = (ArrayList<Movie>) crit.add(Restrictions.like("title", title)).list();
		
		tx.commit();
		session.flush();
		
		if(movies.isEmpty())
			return null;
		
		return movies;
	}
	
	/**
	 * Enregistrer le jeu passÃ© en paramÃ¨tre dans la base de donnÃ©es
	 * @param game Le jeu Ã  auvegarder
	 */
	public void saveGame(Game game) {
		Transaction tx = session.beginTransaction();
		
		session.saveOrUpdate(game);
		
		tx.commit();
		session.flush();
	}
	
	/**
	 * Enregistrer le film passÃ© en paramÃ¨tre dans la base de donnÃ©es
	 * @param game Le film Ã  auvegarder
	 */
	public void saveMovie(Movie movie) {
		Transaction tx = session.beginTransaction();
		
		session.saveOrUpdate(movie);
		
		tx.commit();
		session.flush();
	}

	/**
	 * Sauvegarder une collection de jeux dans la base de donnÃ©es
	 * @param games L'ensemble des jeux Ã  sauvegarder
	 */
	public void saveGames(ArrayList<Game> games) {
		Transaction tx = session.beginTransaction();
		
		for(Game game: games)
		{
			session.saveOrUpdate(game);
		}
		
		tx.commit();
		session.flush();
	}
	
	/**
	 * Sauvegarder une collection de films dans la base de donnÃ©es
	 * @param movies L'ensemble des films Ã  sauvegarder
	 */
	public void saveMovies(ArrayList<Movie> movies) {
		Transaction tx = session.beginTransaction();
		
		for(Movie movie: movies)
		{
			session.saveOrUpdate(movie);
		}
		
		tx.commit();
		session.flush();
	}
	
	/**
	 * Sauvegarder un nouvel utilisateur dans la base de donnÃ©es
	 * @param user L'utilisateur Ã  enregistrer
	 */
	public void saveUser(User user) {
		Transaction tx = session.beginTransaction();
		
		session.saveOrUpdate(user);
		
		tx.commit();
		session.flush();
	}
	
	/**
	 * Sauvegarder un nouvel utilisateur dans la base de donnÃ©es
	 * @param user L'utilisateur Ã  enregistrer
	 */
	public void saveComment(Comment comment) {
		Transaction tx = session.beginTransaction();
		
		session.saveOrUpdate(comment);
		
		
		tx.commit();
		session.flush();
	}
	
	/**
	 * Rafraichit la session avec le media souhaitÃ©
	 * @param media Le media Ã  rafraichir dans la session
	 */
	public void refreshMedia (Media media)
	{
		session.refresh(media);
		session.flush();
	}
	
	@Override
	public void finalize()
	{
		session.clear();
		session.close();
	}
}
