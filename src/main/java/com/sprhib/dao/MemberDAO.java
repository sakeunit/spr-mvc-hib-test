package com.sprhib.dao;

import java.util.List;

import com.sprhib.model.Member;

public interface MemberDAO {
	
	public void addMember(Member member);
	public void updateMember(Member member);
	public Member getMember(int id);
	public void deleteMember(int id);
	public List<Member> getMembers();
	public Member getMemberByNames(String lastName, String firstName);
	
}
