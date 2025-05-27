package com.example.bookslibraries.kurniads.service.impl;

import com.example.bookslibraries.kurniads.dto.AuthorDto;
import com.example.bookslibraries.kurniads.entity.AuthorModel;
import com.example.bookslibraries.kurniads.repository.AuthorRepository;
import com.example.bookslibraries.kurniads.service.AuthorService;
import com.example.bookslibraries.kurniads.service.RoleManagementService;
import com.example.bookslibraries.kurniads.service.ValidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AuthorServiceImpl extends BaseImpl implements AuthorService {

    @Autowired
    AuthorRepository authorRepository;

    @Autowired
    ValidateService validateService;

    @Autowired
    RoleManagementService roleManagementService;

    private AuthorModel getAuthorById(int id){
        return authorRepository.findById(id).orElse(null);
    }

    private List<AuthorModel> getAllAuthor(){
        return authorRepository.findAll();
    }

    private AuthorModel saveAuthor(AuthorModel authorModel){
        return authorRepository.save(authorModel);
    }

    private AuthorModel buildCreateAuthor(AuthorDto authorDto){
        AuthorModel authorModel = new AuthorModel();
        authorModel.setName(authorDto.getName());
        authorModel.setBio(authorDto.getBio());
        return authorModel;
    }

    private Boolean doUpdateAuthor(AuthorDto authorDto) {
        AuthorModel dataExisting = getAuthorById(authorDto.getId());
        if(dataExisting == null){
            return false;
        }else{
            if (authorDto.getName() != null) dataExisting.setName(authorDto.getName());
            if (authorDto.getBio() != null) dataExisting.setBio(authorDto.getBio());
            try{
                saveAuthor(dataExisting);
                return true;
            }catch (Exception e){
                return false;
            }
        }
    }

    @Override
    public Map<String, Object> createAuthor(String auth, AuthorDto authorDto) {
        if(validateService.validateMemberAccess(auth, roleManagementService.getRoleAdminOnly())){
            try{
                saveAuthor(buildCreateAuthor(authorDto));
                return responseSuccess(authorDto);
            }catch (Exception e){
                e.printStackTrace();
                return responseError(null);
            }
        }else{
            return memberNotHaveAccess(null);
        }
    }

    @Override
    public Map<String, Object> updateAuthor(String auth, AuthorDto authorDto) {
        if(validateService.validateMemberAccess(auth, roleManagementService.getRoleAdminOnly())){
            Boolean doUpdate = doUpdateAuthor(authorDto);
            if(doUpdate){
                return responseSuccess(authorDto);
            }else{
                return responseError(null);
            }
        }else{
            return memberNotHaveAccess(null);
        }
    }

    @Override
    public Map<String, Object> readAuthor(String auth) {
        if(validateService.validateMemberAccess(auth, roleManagementService.getRolePublic())){
            List<AuthorModel> result = getAllAuthor();
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
    public Map<String, Object> readAuthorById(String auth, int id) {
        if(validateService.validateMemberAccess(auth, roleManagementService.getRolePublic())){
            AuthorModel result = getAuthorById(id);
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
