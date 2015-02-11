package com.sprhib.service;

import java.util.List;

import com.sprhib.model.Member;

public interface TeamMemberService {

	public void addMember(Member member);
	public void updateMember(Member member);
	public Member getMember(int id);
	public void deleteMember(int id);
	public List<Member> getMembers();
	
}
