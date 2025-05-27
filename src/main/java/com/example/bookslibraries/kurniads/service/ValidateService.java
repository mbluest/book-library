package com.example.bookslibraries.kurniads.service;

import com.example.bookslibraries.kurniads.dto.MemberDto;

import java.util.List;
import java.util.Map;

public interface ValidateService {

    public Boolean validateLogin(String dbPassword, String appPassword, Boolean isHash);

    public String generateToken(String accountId);

    public String extractAccountId(String token);

    public Boolean isTokenValid(String token);

    public Boolean validateMemberAccess(String auth, List<Integer> role);

}
