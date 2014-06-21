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

public class PersistenceManager {
	private static PersistenceManager _instance = null;	
	
	private final SessionFactory sessionFactory;
	
	private PersistenceManager(){
        Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml");
        StandardServiceRegistryBuilder ssrb = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
        sessionFactory = configuration.buildSessionFactory(ssrb.build());
	}
	
	public static PersistenceManager getInstance()
	{
		if(_instance == null)
			_instance = new PersistenceManager();
		return _instance;
	}

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
	
	public void saveGame(Game game) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		
		session.saveOrUpdate(game);
		
		tx.commit();
		session.clear();
		session.close();
	}

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
}
