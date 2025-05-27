package com.example.bookslibraries.kurniads.util.generator;

import com.example.bookslibraries.kurniads.util.constant.RoleConstants;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UtilGenerator {

    public static String generateAccountId(Long total){
        if(total == null){
            return null;
        }
        return String.format("%09d", total+1);
    }

    public static String generatePassword(String password){
        try{
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b)); // convert byte to hex
            }
            return sb.toString();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }



}
