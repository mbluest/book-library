package com.example.bookslibraries.kurniads.service;

import java.util.List;
import java.util.Map;

public interface RoleManagementService {

    public List<Integer> getRoleAdminOnly();

    public List<Integer> getRolePublic();

    public List<Map<String, Integer>> getRole();
}
