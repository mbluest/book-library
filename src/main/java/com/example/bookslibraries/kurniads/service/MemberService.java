package com.example.bookslibraries.kurniads.service;

import com.example.bookslibraries.kurniads.dto.LoginDto;
import com.example.bookslibraries.kurniads.dto.MemberDto;
import com.example.bookslibraries.kurniads.entity.MemberModel;

import java.util.Map;

public interface MemberService {

    public Map<String, Object> login(LoginDto loginDto);

    public Map<String, Object> createMember(MemberDto memberData);

    public Map<String, Object> updateMember(String auth, MemberDto data);

    public Map<String, Object> removeMember(String auth, String accountId);

    public Map<String, Object> readAllMember(String auth);

    public Map<String, Object> readMemberById(String auth, String accountId);

    public Map<String, Object> getRole();

    public MemberModel getMemberById(String id);
}
