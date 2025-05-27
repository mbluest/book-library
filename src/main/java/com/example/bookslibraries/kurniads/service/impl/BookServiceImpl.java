package com.example.bookslibraries.kurniads.service.impl;

import com.example.bookslibraries.kurniads.dto.BookDto;
import com.example.bookslibraries.kurniads.dto.CategoryDto;
import com.example.bookslibraries.kurniads.entity.BookModel;
import com.example.bookslibraries.kurniads.entity.CategoryModel;
import com.example.bookslibraries.kurniads.repository.BookRepository;
import com.example.bookslibraries.kurniads.service.BookService;
import com.example.bookslibraries.kurniads.service.RoleManagementService;
import com.example.bookslibraries.kurniads.service.ValidateService;
import com.example.bookslibraries.kurniads.util.constant.ActiveConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class BookServiceImpl extends BaseImpl implements BookService {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    ValidateService validateService;

    @Autowired
    RoleManagementService roleManagementService;

    private List<BookModel> getAllBook(){
        return bookRepository.findAll();
    }

    private BookModel getBookById(int id){
        return bookRepository.findById(id).orElse(null);
    }

    private BookModel saveBook(BookModel bookModel){
        return bookRepository.save(bookModel);
    }

    private BookModel buildAddBook(BookDto bookDto){
        BookModel result = new BookModel();
        result.setTitle(bookDto.getTitle());
        result.setImageUrl(bookDto.getImageUrl());
        result.setCategory(bookDto.getCategory());
        result.setPublishingYear(bookDto.getPublishingYear());
        result.setAuthor(bookDto.getAuthor());
        result.setAvailable(bookDto.getAvailable());
        result.setTotal(bookDto.getTotal());
        result.setIsActive(ActiveConstants.IS_ACTIVE);
        result.setDescription(bookDto.getDescription());
        return result;
    }

    private Boolean doUpdateBook(BookDto bookDto) {
        BookModel dataExisting = getBookById(bookDto.getId());
        if(dataExisting == null){
            return false;
        }else{
            if (bookDto.getTitle() != null) dataExisting.setTitle(bookDto.getTitle());
            if (bookDto.getImageUrl() != null) dataExisting.setImageUrl(bookDto.getImageUrl());
            if (bookDto.getCategory() != null) dataExisting.setCategory(bookDto.getCategory());
            if (bookDto.getPublishingYear() != 0) dataExisting.setPublishingYear(bookDto.getPublishingYear());
            if (bookDto.getAuthor() != null) dataExisting.setAuthor(bookDto.getAuthor());
            if (bookDto.getDescription() != null) dataExisting.setDescription(bookDto.getDescription());
            dataExisting.setAvailable(bookDto.getAvailable());
            dataExisting.setTotal(bookDto.getTotal());
            try{
                saveBook(dataExisting);
                return true;
            }catch (Exception e){
                return false;
            }
        }
    }

    @Override
    public Map<String, Object> addBook(String auth, BookDto bookDto) {
        if(validateService.validateMemberAccess(auth, roleManagementService.getRoleAdminOnly())){
            try{
                saveBook(buildAddBook(bookDto));
                return responseSuccess(bookDto);
            }catch (Exception e){
                e.printStackTrace();
                return responseError(e.getMessage());
            }
        }else{
            return memberNotHaveAccess(null);
        }
    }

    @Override
    public Map<String, Object> updateBook(String auth, BookDto bookDto) {
        if(validateService.validateMemberAccess(auth, roleManagementService.getRoleAdminOnly())){
            try{
                Boolean doUpdateBook = doUpdateBook(bookDto);
                if(doUpdateBook){
                    return responseSuccess(bookDto);
                }else{
                    return responseError(null);
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
    public Map<String, Object> readBook(String auth) {
        if(validateService.validateMemberAccess(auth, roleManagementService.getRolePublic())){
            List<BookModel> result = getAllBook();
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
    public Map<String, Object> readDetailBook(String auth, int idBook) {
        if(validateService.validateMemberAccess(auth, roleManagementService.getRolePublic())){
            BookModel result = getBookById(idBook);
            if(result == null){
                return responseDataNotFound(null);
            }else{
                return responseSuccess(result);
            }
        }else{
            return memberNotHaveAccess(null);
        }
    }

    @Override
    public BookModel getBookByIdLock(int id){
        return bookRepository.findByIdForUpdate(id).orElse(null);
    }

    @Override
    public BookModel getDataBookById(int id) {
        return getBookById(id);
    }

    @Override
    public void doBorrow(BookModel data) {
        data.setAvailable(data.getAvailable() - 1);
        saveBook(data);
    }

    @Override
    public void doReturn(BookModel data) {
        data.setAvailable(data.getAvailable() + 1);
        saveBook(data);
    }
}
