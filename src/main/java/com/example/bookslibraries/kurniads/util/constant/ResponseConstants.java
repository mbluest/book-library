package com.example.bookslibraries.kurniads.util.constant;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ResponseConstants {

    public static final String CODE_STATUS_SUCCESS = "200";
    public static final String MESSAGE_STATUS_SUCCESS = "SUCCESS";

    public static final String CODE_STATUS_BAD_PARAMS = "400";
    public static final String MESSAGE_STATUS_BAD_PARAMS = "PARAMS NOT RIGHT";

    public static final String CODE_STATUS_NOT_FOUND = "404";
    public static final String MESSAGE_STATUS_NOT_FOUND = "DATA NOT FOUND";

    public static final String CODE_STATUS_ERROR = "500";
    public static final String MESSAGE_STATUS_ERROR = "ERROR";

    //USER
    public static final String MESSAGE_CREATE_MEMBER_SUCCESS = "Creating Member Successfully";

    public static final String MESSAGE_CREATE_MEMBER_FAILED = "Failed Creating Member";
    public static final String MESSAGE_EMAIL_OR_NICKNAME_ALREADY_EXISTS = "Nickname or Email Already Exists";

    public static final String MESSAGE_USERNAME_OR_EMAIL_NOT_FOUND = "Username or Email and Password Not Match";

    public static final String MESSAGE_MEMBER_ACCESS_HAS_EXPIRED = "Token Expired, Please Re-Login";

    public static final String MESSAGE_MEMBER_NOT_HAVE_ACCESS = "Member Not Have Access For This Feature";

    public static final String MESSAGE_MEMBER_NOT_ACTIVE = "Member is Not Active";

    public static final String MESSAGE_MEMBER_SUCCESS_UPDATE_DATA = "Success Updating Data Member";
    public static final String MESSAGE_MEMBER_FAILED_UPDATE_DATA = "Failed Updating Data Member";

    public static final String MESSAGE_MEMBER_SUCCESS_UPDATE_STATUS = "Success Updating Status Member";
    public static final String MESSAGE_MEMBER_FAILED_UPDATE_STATUS = "Failed Updating Status Member";


    //CATEGORY
    public static final String MESSAGE_CATEGORY_ALREADY_EXISTS = "Category Already Exists";

    public static final String MESSAGE_FAILED_ADD_CATEGORY = "Failed Creating Category";

    //BOOK
    public static final String MESSAGE_BOOK_NOT_AVAILABLE = "Sorry, Book Out of Stock";


}
