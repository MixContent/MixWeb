package com.dugu.spring.mvc.blob.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.dugu.spring.mvc.blob.model.Document;

@Repository
public class DocumentDAO {

	@Autowired
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Transactional
	public void save(Document document) {
		Session session = sessionFactory.getCurrentSession();
		session.save(document);
	}

	@Transactional
	public Document getImage(int id) {
		return (Document) sessionFactory.getCurrentSession()
				.createCriteria(Document.class).add(Restrictions.eq("id", id))
				.uniqueResult();
	}
}
