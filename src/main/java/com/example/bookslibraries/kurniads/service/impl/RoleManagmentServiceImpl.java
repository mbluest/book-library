package com.example.bookslibraries.kurniads.service.impl;

import com.example.bookslibraries.kurniads.service.RoleManagementService;
import com.example.bookslibraries.kurniads.util.constant.RoleConstants;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RoleManagmentServiceImpl implements RoleManagementService {

    private static Map<String, Integer> setRoleAdmin(String name, Integer code){
        Map<String, Integer> data = new HashMap<>();
        data.put(name, code);
        return data;
    }

    @Override
    public List<Integer> getRoleAdminOnly() {
        List<Integer> result = new ArrayList<>();
        result.add(RoleConstants.ROLE_ADMIN_MEMBER);
        return result;
    }

    @Override
    public List<Integer> getRolePublic() {
        List<Integer> result = new ArrayList<>();
        result.add(RoleConstants.ROLE_ADMIN_MEMBER);
        result.add(RoleConstants.ROLE_PUBLIC_MEMBER);
        return result;
    }

    @Override
    public List<Map<String, Integer>> getRole() {
        List<Map<String, Integer>> result = new ArrayList<>();
        try{
            result.add(setRoleAdmin("admin", RoleConstants.ROLE_ADMIN_MEMBER));
            result.add(setRoleAdmin("public", RoleConstants.ROLE_PUBLIC_MEMBER));
            return result;
        }catch (Exception e){
            e.printStackTrace();
            return result;
        }
    }
}
