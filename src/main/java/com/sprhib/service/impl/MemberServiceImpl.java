package com.sprhib.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sprhib.dao.MemberDAO;
import com.sprhib.model.Member;
import com.sprhib.service.TeamMemberService;

@Service
@Transactional
public class MemberServiceImpl implements TeamMemberService {

	@Autowired
	private MemberDAO memberDAO;
	
	@Override
	public void addMember(Member member) {
		memberDAO.addMember(member);
	}

	@Override
	public void updateMember(Member member) {
		memberDAO.updateMember(member);
	}

	@Override
	public Member getMember(int id) {
		return memberDAO.getMember(id);
	}

	@Override
	public void deleteMember(int id) {
		memberDAO.deleteMember(id);
	}

	@Override
	public List<Member> getMembers() {
		return memberDAO.getMembers();
	}

}
