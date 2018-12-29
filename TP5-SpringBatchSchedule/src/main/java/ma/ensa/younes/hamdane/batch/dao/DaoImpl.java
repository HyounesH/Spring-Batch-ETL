package ma.ensa.younes.hamdane.batch.dao;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ma.ensa.younes.hamdane.batch.entities.Personne;

@Repository
public class DaoImpl implements Doa {

	private SessionFactory sessionFactory;

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Transactional
	public void addPersonne(Personne personne) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(personne);
	}

}
