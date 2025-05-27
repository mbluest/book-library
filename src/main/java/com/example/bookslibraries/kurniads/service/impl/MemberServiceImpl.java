package com.example.bookslibraries.kurniads.service.impl;

import com.example.bookslibraries.kurniads.dto.LoginDto;
import com.example.bookslibraries.kurniads.dto.MemberDto;
import com.example.bookslibraries.kurniads.entity.MemberModel;
import com.example.bookslibraries.kurniads.repository.MemberRepository;
import com.example.bookslibraries.kurniads.service.MemberService;
import com.example.bookslibraries.kurniads.service.RoleManagementService;
import com.example.bookslibraries.kurniads.service.ValidateService;
import com.example.bookslibraries.kurniads.util.constant.ActiveConstants;
import com.example.bookslibraries.kurniads.util.generator.UtilGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class MemberServiceImpl extends BaseImpl implements MemberService {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    ValidateService validateService;

    @Autowired
    RoleManagementService roleManagementService;

    private String generateAccountId(){
        Long total = memberRepository.count();
        return UtilGenerator.generateAccountId(total);
    }

    private String generatePassword(String password){
        return UtilGenerator.generatePassword(password);
    }

    private MemberModel addNewMember(MemberModel memberData){
        return memberRepository.save(memberData);
    }

    private Boolean setStatusMember(String accountId, int status){
        MemberModel data = findByAccountId(accountId);
        if(data == null){
            return false;
        }else{
            data.setIsActive(status);
            try{
                addNewMember(data);
                return true;
            }catch (Exception e){
                e.printStackTrace();
                return false;
            }

        }
    }

    private Boolean doUpdateMember(MemberDto data) {
        MemberModel dataExisting = findByAccountId(data.getAccountId());
        if(dataExisting == null){
            return false;
        }else{
            if (data.getRole() != null) dataExisting.setRole(data.getRole());
            if (data.getName() != null) dataExisting.setName(data.getName());
            if (data.getNickname() != null) dataExisting.setNickname(data.getNickname());
            if (data.getEmail() != null) dataExisting.setEmail(data.getEmail());
            if(data.getPassword() != null && !data.getPassword().equalsIgnoreCase("")){
                System.out.println(data.getPassword());
                dataExisting.setPassword(generatePassword(data.getPassword()));
            }
            if (data.getPhone() != null) dataExisting.setPhone(data.getPhone());
            if (data.getBirthDay() != null) dataExisting.setBirthDay(data.getBirthDay());
            if (data.getAddress() != null) dataExisting.setAddress(data.getAddress());
            if (data.getStatus() != null) dataExisting.setIsActive(Integer.parseInt(data.getStatus()));
            try{
                addNewMember(dataExisting);
                return true;
            }catch (Exception e){
                return false;
            }
        }
    }

    private MemberModel findByAccountId(String accountId){
        return memberRepository.findByAccountId(accountId).orElse(null);
    }

    private List<MemberModel> findAllMember(){
        return memberRepository.findAll();
    }

    private MemberModel findByEmail(String email){
        return memberRepository.findByEmail(email).orElse(null);
    }

    private MemberModel findByNickname(String nickname){
        return memberRepository.findByNickname(nickname).orElse(null);
    }


    private MemberModel buildMemberForCreate(MemberDto memberDto){
        MemberModel data = new MemberModel();
        data.setAccountId(generateAccountId());
        data.setRole(memberDto.getRole());
        data.setNickname(memberDto.getNickname());
        data.setEmail(memberDto.getEmail());
        data.setPassword(generatePassword(memberDto.getPassword()));
        data.setName(memberDto.getName());
        data.setPhone(memberDto.getPhone());
        data.setBirthDay(memberDto.getBirthDay());
        data.setAddress(memberDto.getAddress());
        data.setIsActive(1);
        return data;
    }

    private LoginDto buildLoginData(MemberModel memberModel){
        LoginDto data = new LoginDto();
        data.setAccountId(memberModel.getAccountId());
        data.setAuth(validateService.generateToken(memberModel.getAccountId()));
        data.setPassword(null);
        data.setEmail(memberModel.getEmail());
        data.setNickname(memberModel.getNickname());
        data.setRole(memberModel.getRole());
        return data;
    }

    @Override
    public Map<String, Object> login(LoginDto loginDto) {
        MemberModel data = findByNickname(loginDto.getNickname());
        if(data == null){
            data = findByEmail(loginDto.getEmail());
        }
        if(data == null){
            return responseDataNotFound(null, MESSAGE_USERNAME_OR_EMAIL_NOT_FOUND);
        }

        if(data.getIsActive() == ActiveConstants.IS_NON_ACTIVE){
            return responseError(null, MESSAGE_MEMBER_NOT_ACTIVE);
        }

        Boolean isLoginValid = validateService.validateLogin(data.getPassword(), loginDto.getPassword(), loginDto.getIsHashPassword());

        if(isLoginValid){
            return responseSuccess(buildLoginData(data));
        }else{
            return responseDataNotFound(null, MESSAGE_USERNAME_OR_EMAIL_NOT_FOUND);
        }
    }

    @Override
    public Map<String, Object> createMember(MemberDto memberData) {
        try{
            addNewMember(buildMemberForCreate(memberData));
            return responseSuccess(memberData, MESSAGE_CREATE_MEMBER_SUCCESS);
        }catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            String message = e.getMostSpecificCause().getMessage().toLowerCase();
            if (message.contains("duplicate")) {
                return responseBadParams(null,MESSAGE_CREATE_MEMBER_FAILED, MESSAGE_EMAIL_OR_NICKNAME_ALREADY_EXISTS);
            } else {
                return responseError(e.getMessage());
            }
        }
    }

    @Override
    public Map<String, Object> updateMember(String auth, MemberDto data) {
        if(validateService.validateMemberAccess(auth, roleManagementService.getRoleAdminOnly())){
            Boolean updatingMember = doUpdateMember(data);
            if(updatingMember){
                return responseSuccess(data, MESSAGE_MEMBER_SUCCESS_UPDATE_DATA);
            }else{
                return responseError(null, MESSAGE_MEMBER_FAILED_UPDATE_DATA);
            }
        }else{
            return memberNotHaveAccess(null);
        }
    }

    @Override
    public Map<String, Object> removeMember(String auth, String accountId) {
        if(validateService.validateMemberAccess(auth, roleManagementService.getRoleAdminOnly())){
            Boolean doUpdateMember = setStatusMember(accountId, ActiveConstants.IS_NON_ACTIVE);
            if(doUpdateMember){
                return responseSuccess(accountId, MESSAGE_MEMBER_SUCCESS_UPDATE_STATUS);
            }else{
                return responseError(accountId, MESSAGE_MEMBER_FAILED_UPDATE_STATUS);
            }
        }else{
            return memberNotHaveAccess(null);
        }
    }

    @Override
    public Map<String, Object> readAllMember(String auth) {
        if(validateService.validateMemberAccess(auth, roleManagementService.getRoleAdminOnly())){
            List<MemberModel> data = findAllMember();
            if(data.isEmpty()){
                return responseDataNotFound(null);
            }else{
                return responseSuccess(data);
            }
        }else{
            return memberNotHaveAccess(null);
        }
    }

    @Override
    public Map<String, Object> readMemberById(String auth, String accountId) {
        if(validateService.validateMemberAccess(auth, roleManagementService.getRolePublic())){
            MemberModel data = findByAccountId(accountId);
            if(data == null){
                return responseDataNotFound(null);
            }else{
                return responseSuccess(data);
            }
        }else{
            return memberNotHaveAccess(null);
        }
    }

    @Override
    public Map<String, Object> getRole() {
        return responseSuccess(roleManagementService.getRole());
    }

    @Override
    public MemberModel getMemberById(String id) {
        return findByAccountId(id);
    }
}
