package com.example.bookslibraries.kurniads.service.impl;

import com.example.bookslibraries.kurniads.entity.MemberModel;
import com.example.bookslibraries.kurniads.repository.MemberRepository;
import com.example.bookslibraries.kurniads.service.ValidateService;
import com.example.bookslibraries.kurniads.util.constant.ActiveConstants;
import com.example.bookslibraries.kurniads.util.generator.UtilGenerator;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.*;

@Service
public class ValidateServiceImpl extends BaseImpl implements ValidateService {

    @Autowired
    MemberRepository memberRepository;

    private final Key secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private final long EXPIRATION_TIME = 1000 * 60 * 60;

    private Boolean accessMember(String roleMember, int roleAccess){

        List<Integer> haveRole = new ArrayList<>();

        if(roleMember.contains(",")){
            String[] roleSplit = roleMember.split(",");
            for(String role: roleSplit){
                haveRole.add(Integer.valueOf(role));
            }
        }else{
            haveRole.add(Integer.valueOf(roleMember));
        }
        return haveRole.contains(roleAccess);
    }

    @Override
    public Boolean validateLogin(String dbPassword, String appPassword, Boolean isHash) {
        Boolean result;
        if(isHash){
            if(dbPassword.equals(appPassword)){
                result = true;
            }else{
                result = false;
            }
        }else{
            String genAppPassword = UtilGenerator.generatePassword(appPassword);
            if(dbPassword.equals(genAppPassword)){
                result = true;
            }else{
                result = false;
            }
        }
        return result;
    }

    @Override
    public String generateToken(String accountId) {
        return Jwts.builder()
                .setSubject(accountId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(secretKey)
                .compact();
    }

    @Override
    public String extractAccountId(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    @Override
    public Boolean isTokenValid(String token) {
        try{
            Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public Boolean validateMemberAccess(String auth, List<Integer> role) {
        if(!isTokenValid(auth)){
            return false;
        }
        String accountId = extractAccountId(auth);
        Optional<MemberModel> member =  memberRepository.findByAccountId(accountId);
        if(member.isEmpty()){
            return false;
        }
        if(member.get().getIsActive() == ActiveConstants.IS_NON_ACTIVE){
            return false;
        }
//        return member.map(memberModel -> accessMember(memberModel.getRole(), role)).orElse(false);
        return accessFeature(role, member.get().getRole());
    }

    private Boolean accessFeature(List<Integer> roleFeature, String roleMember){
        List<Integer> haveRole = new ArrayList<>();

        if(roleMember.contains(",")){
            String[] roleSplit = roleMember.split(",");
            for(String role: roleSplit){
                haveRole.add(Integer.valueOf(role));
            }
        }else{
            haveRole.add(Integer.valueOf(roleMember));
        }
        return roleFeature.parallelStream().anyMatch(haveRole::contains);
    }
}
