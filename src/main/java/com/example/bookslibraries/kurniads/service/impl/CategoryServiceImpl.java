package com.example.bookslibraries.kurniads.service.impl;

import com.example.bookslibraries.kurniads.dto.AuthorDto;
import com.example.bookslibraries.kurniads.dto.CategoryDto;
import com.example.bookslibraries.kurniads.entity.AuthorModel;
import com.example.bookslibraries.kurniads.entity.CategoryModel;
import com.example.bookslibraries.kurniads.repository.CategoryRepository;
import com.example.bookslibraries.kurniads.service.CategoryService;
import com.example.bookslibraries.kurniads.service.RoleManagementService;
import com.example.bookslibraries.kurniads.service.ValidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CategoryServiceImpl extends BaseImpl implements CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ValidateService validateService;

    @Autowired
    RoleManagementService roleManagementService;

    private List<CategoryModel> findAll(){
        return categoryRepository.findAll();
    }

    private CategoryModel findById(int id){
        return categoryRepository.findById(id).orElse(null);
    }

    private CategoryModel saveCategory(CategoryModel categoryModel){
        return categoryRepository.save(categoryModel);
    }

    private CategoryModel buildCreateCategory(CategoryDto categoryDto){
        CategoryModel result = new CategoryModel();
        result.setCategory(categoryDto.getCategory());
        result.setDescription(categoryDto.getDescription());
        return result;
    }

    private Boolean doUpdateCategory(CategoryDto categoryDto) {
        CategoryModel dataExisting = findById(categoryDto.getId());
        if(dataExisting == null){
            return false;
        }else{
            if (categoryDto.getCategory() != null) dataExisting.setCategory(categoryDto.getCategory());
            if (categoryDto.getDescription() != null) dataExisting.setDescription(categoryDto.getDescription());
            try{
                saveCategory(dataExisting);
                return true;
            }catch (Exception e){
                return false;
            }
        }
    }

    @Override
    public Map<String, Object> createCategory(String auth, CategoryDto categoryDto) {
        if(validateService.validateMemberAccess(auth, roleManagementService.getRoleAdminOnly())){
            try{
                return responseSuccess(saveCategory(buildCreateCategory(categoryDto)));
            }catch (DataIntegrityViolationException e) {
                e.printStackTrace();
                String message = e.getMostSpecificCause().getMessage().toLowerCase();
                if (message.contains("duplicate")) {
                    return responseBadParams(null, MESSAGE_FAILED_ADD_CATEGORY, MESSAGE_CATEGORY_ALREADY_EXISTS);
                } else {
                    return responseError(e.getMessage());
                }
            }

        }else{
            return memberNotHaveAccess(null);
        }
    }

    @Override
    public Map<String, Object> updateCategory(String auth, CategoryDto categoryDto) {
        if(validateService.validateMemberAccess(auth, roleManagementService.getRoleAdminOnly())){
            try{
                Boolean doUpdate = doUpdateCategory(categoryDto);
                if(doUpdate){
                    return responseSuccess(categoryDto);
                }else{
                    return responseError(null);
                }
            }catch (Exception e){
                return responseError(e.getMessage());
            }
        }else{
            return memberNotHaveAccess(null);
        }
    }

    @Override
    public Map<String, Object> readCategory(String auth) {
        if(validateService.validateMemberAccess(auth, roleManagementService.getRolePublic())){
            List<CategoryModel> result = findAll();
            if(result.isEmpty()){
                return responseDataNotFound(null);
            }else{
                return responseSuccess(result);
            }
        }else{
            return memberNotHaveAccess(null);
        }
    }

    @Override
    public Map<String, Object> readDetailCategory(String auth, int id) {
        if(validateService.validateMemberAccess(auth, roleManagementService.getRolePublic())){
            CategoryModel result = findById(id);
            if(result == null){
                return responseDataNotFound(null);
            }else{
                return responseSuccess(result);
            }
        }else{
            return memberNotHaveAccess(null);
        }
    }
}
