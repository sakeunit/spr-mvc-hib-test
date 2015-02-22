package com.sprhib.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sprhib.dao.MemberDAO;
import com.sprhib.model.Member;

@Repository
public class MemberDAOImpl implements MemberDAO {

	@Autowired
	private SessionFactory sessionFactory;

	public MemberDAOImpl() {
	}
	
	public MemberDAOImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	private Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public void addMember(Member member) {
		getCurrentSession().save(member);
	}

	@Override
	public void updateMember(Member member) {
		Member memberToUpdate = getMember(member.getId());
		memberToUpdate.setLastName(member.getLastName());
		memberToUpdate.setFirstName(member.getFirstName());
		getCurrentSession().update(memberToUpdate);
	}

	@Override
	public Member getMember(int id) {
		Member member = (Member) getCurrentSession().get(Member.class, id);
		return member;
	}

	@Override
	public void deleteMember(int id) {
		Member member = getMember(id);
		if (member != null) {
			getCurrentSession().delete(member);
		}
	}

	@Override
	public List<Member> getMembers() {
		return getCurrentSession().createQuery("from Member").list();
	}

	@Override
	public Member getMemberByNames(String lastName, String firstName) {
		return (Member) getCurrentSession()
				.createQuery("from Member where lastName=:ln and firstName=:fn")
				.setParameter("ln", lastName).setParameter("fn", firstName)
				.uniqueResult();
	}

}
