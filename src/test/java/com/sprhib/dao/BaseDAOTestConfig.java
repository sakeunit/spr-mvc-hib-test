package com.sprhib.dao;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.junit.After;
import org.junit.Before;
import org.springframework.core.io.ClassPathResource;

import com.sprhib.model.Member;
import com.sprhib.model.Organization;
import com.sprhib.model.Team;
import com.sprhib.model.TeamMemberLink;

public abstract class BaseDAOTestConfig {

	private static final String HIB_RESOURCE_XML = "com/sprhib/dao/hibernate.cfg.xml";
	protected static SessionFactory sessionFactory;

	@Before
	public void init() {
		Configuration conf = new Configuration();
		try {
			ClassPathResource cpr = new ClassPathResource(HIB_RESOURCE_XML);
			conf.configure(cpr.getFile());
		} catch (Exception e) {
			System.err.println("Failed reading hibernate.cfg.xml");
			throw new ExceptionInInitializerError(e);
		}

		conf.addAnnotatedClass(Organization.class);
		conf.addAnnotatedClass(Team.class);
		conf.addAnnotatedClass(Member.class);
		conf.addAnnotatedClass(TeamMemberLink.class);

		ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
				.applySettings(conf.getProperties()).build();
		try {
			sessionFactory = conf.buildSessionFactory(serviceRegistry);
		} catch (Exception e) {
			System.err.println("Failed creating SessionFactory");
			throw new ExceptionInInitializerError(e);
		}
	}

	@After
	public void after() {
		sessionFactory.close();
	}

}
