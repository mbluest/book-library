package com.example.bookslibraries.kurniads.service.impl;

import com.example.bookslibraries.kurniads.dto.BookDto;
import com.example.bookslibraries.kurniads.dto.BorrowedBookDto;
import com.example.bookslibraries.kurniads.dto.MemberDto;
import com.example.bookslibraries.kurniads.entity.BookModel;
import com.example.bookslibraries.kurniads.entity.BorrowedBookModel;
import com.example.bookslibraries.kurniads.entity.MemberModel;
import com.example.bookslibraries.kurniads.repository.BorrowedBookRepository;
import com.example.bookslibraries.kurniads.service.*;
import com.example.bookslibraries.kurniads.util.enums.TransactionStatus;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class BorrowedBookServiceImpl extends BaseImpl implements BorrowedBookService {

    @Autowired
    ValidateService validateService;

    @Autowired
    RoleManagementService roleManagementService;

    @Autowired
    BookService bookService;

    @Autowired
    BorrowedBookRepository borrowedBookRepository;

    @Autowired
    MemberService memberService;

    private Boolean createBorrowBook(BorrowedBookModel data){
        try{
            borrowedBookRepository.save(data);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    private BorrowedBookModel buildBorrowedBook(int book, String member){
        BorrowedBookModel data = new BorrowedBookModel();
        data.setBook(bookService.getDataBookById(book));
        data.setMember(memberService.getMemberById(member));
        data.setStatus(TransactionStatus.PENDING);
        data.setCreatedDate(LocalDateTime.now());
        return data;
    }

    private Boolean doUpdateStatus(BorrowedBookModel borrowedBookModel, TransactionStatus status, String notes, String admin){
        borrowedBookModel.setStatus(status);
        borrowedBookModel.setApprovedDate(LocalDateTime.now());
        borrowedBookModel.setNotes(notes);
        borrowedBookModel.setIdAdmin(admin);
        try{
            createBorrowBook(borrowedBookModel);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

    }

    private List<BorrowedBookModel> getAllTransactionByMember(String idMember){
        return borrowedBookRepository.findByMember_AccountId(idMember);
    }

    private List<BorrowedBookModel> getAllTransactionByMemberAndStatus(String idMember, TransactionStatus status){
        return borrowedBookRepository.findByMember_AccountIdAndStatus(idMember, status);
    }

    private List<BorrowedBookModel> getAllTransactionByStatus(TransactionStatus status){
        return borrowedBookRepository.findByStatus(status);
    }

    private List<BorrowedBookModel> getAllTransaction(){
        return borrowedBookRepository.findAll();
    }

    private List<BorrowedBookModel> getByFilter(String title, String name, LocalDateTime startDate, LocalDateTime endDate){
        return borrowedBookRepository.searchBorrowedBooks(title, name, startDate, endDate != null ? endDate.plusDays(1):null);
    }

    private BorrowedBookModel getById(int id){
        return borrowedBookRepository.findById(id);
    }

    private BorrowedBookModel lastOfBookingBookByUser(String accountId, int id){
        return borrowedBookRepository.findTopByMember_AccountIdAndBook_IdOrderByCreatedDateDesc(accountId, id);
    }

    private List<BorrowedBookDto> buildResponseBorrowedBook(List<BorrowedBookModel> borrowedBookModel){
        List<BorrowedBookDto> result = new ArrayList<>();
        for(BorrowedBookModel data:borrowedBookModel){
            BorrowedBookDto newData = new BorrowedBookDto();
            newData.setId(data.getId());
            newData.setBook(buildResponseBookDto(data.getBook()));
            newData.setMember(buildResponseMemberDto(data.getMember()));
            if(data.getIdAdmin() != null){
                newData.setAdmin(buildResponseMemberDto(data.getIdAdmin()));
            }
            newData.setStatus(data.getStatus().toString());
            newData.setCreatedDate(String.valueOf(data.getCreatedDate()));
            newData.setNotes(data.getNotes());
            newData.setApprovedDate(String.valueOf(data.getApprovedDate()));
            result.add(newData);
        }

        return result;
    }

    private BookDto buildResponseBookDto(BookModel bookModel){
        BookDto result = new BookDto();
        result.setId(bookModel.getId());
        result.setTitle(bookModel.getTitle());
        result.setImageUrl(bookModel.getImageUrl());
        result.setCategory(bookModel.getCategory());
        result.setPublishingYear(bookModel.getPublishingYear());
        result.setAuthor(bookModel.getAuthor());
        result.setAvailable(bookModel.getAvailable());
        result.setTotal(bookModel.getTotal());
        return result;
    }

    private MemberDto buildResponseMemberDto(MemberModel memberModel){
        MemberDto result = new MemberDto();

        result.setAccountId(memberModel.getAccountId());
        result.setRole(memberModel.getRole());
        result.setName(memberModel.getName());
        result.setNickname(memberModel.getNickname());
        result.setEmail(memberModel.getEmail());
        result.setPhone(memberModel.getPhone());
        result.setBirthDay(memberModel.getBirthDay());
        result.setAddress(memberModel.getAddress());

        return result;
    }

    private MemberDto buildResponseMemberDto(String accountId){
        return buildResponseMemberDto(memberService.getMemberById(accountId));
    }

    @Override
    public Map<String, Object> readAll(String auth, TransactionStatus status) {
        if(validateService.validateMemberAccess(auth, roleManagementService.getRoleAdminOnly())){
            List<BorrowedBookModel> data;
            if(status == null){
                data = getAllTransaction();
            }else{
                data = getAllTransactionByStatus(status);
            }
            if(data.isEmpty()){
                return responseDataNotFound(null);
            }
            return responseSuccess(data);
        }else{
            return memberNotHaveAccess(null);
        }
    }

    @Override
    public Map<String, Object> readByUser(String auth, String accountId, TransactionStatus status) {
        if(validateService.validateMemberAccess(auth, roleManagementService.getRolePublic())){
            List<BorrowedBookModel> data;
            if(status == null){
                data = getAllTransactionByMember(accountId);
            }else{
                data = getAllTransactionByMemberAndStatus(accountId, status);
            }
            if(data.isEmpty()){
                return responseDataNotFound(null);
            }
            return responseSuccess(data);
        }else{
            return memberNotHaveAccess(null);
        }
    }

    @Override
    public Map<String, Object> readByFilter(String auth, String title, String memberName, LocalDateTime startDate, LocalDateTime endDate) {
        if(validateService.validateMemberAccess(auth, roleManagementService.getRolePublic())){
            List<BorrowedBookModel> data = getByFilter(title, memberName, startDate, endDate);
            if(data.isEmpty()){
                return responseDataNotFound(null);
            }
            return responseSuccess(buildResponseBorrowedBook(data));
        }else{
            return memberNotHaveAccess(null);
        }
    }

    @Override
    @Transactional
    public Map<String, Object> doBorrowBook(String auth, int idBook, String idMember) {
        if(validateService.validateMemberAccess(auth, roleManagementService.getRolePublic())){
            try{
                BookModel result = bookService.getBookByIdLock(idBook);
                if(result != null){
                    if(result.getAvailable() <=0 ){
                        return responseDataNotFound(result, MESSAGE_BOOK_NOT_AVAILABLE);
                    }
                    Boolean submit = createBorrowBook(buildBorrowedBook(idBook, idMember));
                    if(submit){
                        bookService.doBorrow(result);
                        return responseSuccess(result);
                    }else{
                        return responseError(null);
                    }
                }else{
                    return responseDataNotFound(idBook);
                }
            }catch (Exception e){
                e.printStackTrace();
                return responseError(e.getMessage());
            }

        }else{
            return memberNotHaveAccess(null);
        }
    }

    @Override
    public Map<String, Object> doApproveBorrowBook(String auth, BorrowedBookDto borrowedBookDto) {
        if(validateService.validateMemberAccess(auth, roleManagementService.getRoleAdminOnly())){
            try {
                BorrowedBookModel data = getById(borrowedBookDto.getId());
                if(data == null){
                    return responseDataNotFound(borrowedBookDto.getId());
                }else{
                    Boolean approved = doUpdateStatus(data, TransactionStatus.APPROVED, borrowedBookDto.getNotes(), validateService.extractAccountId(auth));
                    if(approved){
                        return responseSuccess(borrowedBookDto.getId());
                    }else{
                        return responseError(borrowedBookDto.getId());
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
                return responseError(e.getMessage());
            }
        }else{
            return memberNotHaveAccess(null);
        }
    }

    @Override
    @Transactional
    public Map<String, Object> doRejectBorrowBook(String auth, BorrowedBookDto borrowedBookDto) {
        if(validateService.validateMemberAccess(auth, roleManagementService.getRolePublic())){
            try {
                BorrowedBookModel data = getById(borrowedBookDto.getId());
                if(data == null){
                    return responseDataNotFound(borrowedBookDto.getId());
                }else{
                    Boolean rejected = doUpdateStatus(data, TransactionStatus.CANCELED, borrowedBookDto.getNotes(), validateService.extractAccountId(auth));
                    if(rejected){
                        bookService.doReturn(data.getBook());
                        return responseSuccess(borrowedBookDto.getId());
                    }else{
                        return responseError(borrowedBookDto.getId());
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
                return responseError(e.getMessage());
            }
        }else{
            return memberNotHaveAccess(null);
        }
    }

    @Override
    @Transactional
    public Map<String, Object> doReturnBook(String auth, BorrowedBookDto borrowedBookDto) {
        if(validateService.validateMemberAccess(auth, roleManagementService.getRoleAdminOnly())){
            try {
                BorrowedBookModel data = getById(borrowedBookDto.getId());
                if(data == null){
                    return responseDataNotFound(borrowedBookDto.getId());
                }else{
                    Boolean completed = doUpdateStatus(data, TransactionStatus.COMPLETED, borrowedBookDto.getNotes(), validateService.extractAccountId(auth));
                    if(completed){
                        bookService.doReturn(data.getBook());
                        return responseSuccess(borrowedBookDto.getId());
                    }else{
                        return responseError(borrowedBookDto.getId());
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
                return responseError(e.getMessage());
            }
        }else{
            return memberNotHaveAccess(null);
        }
    }

    @Override
    public Map<String, Object> findBookByUser(String auth, String accountId, int bookId) {
        if(validateService.validateMemberAccess(auth, roleManagementService.getRolePublic())){
            try {
                BorrowedBookModel data = lastOfBookingBookByUser(accountId, bookId);
                if(data == null){
                    return responseSuccess(null);
                }else{
                    return responseSuccess(data.getStatus());
                }
            }catch (Exception e){
                e.printStackTrace();
                return responseError(e.getMessage());
            }
        }else{
            return memberNotHaveAccess(null);
        }
    }


}
