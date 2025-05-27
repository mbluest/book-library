package com.example.bookslibraries.kurniads.controller;

import com.example.bookslibraries.kurniads.dto.LoginDto;
import com.example.bookslibraries.kurniads.dto.MemberDto;
import com.example.bookslibraries.kurniads.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/member")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @PostMapping("/login")
    @CrossOrigin("*")
    public ResponseEntity login(@RequestBody(required = false) LoginDto loginDto){
        return new ResponseEntity(memberService.login(loginDto), HttpStatus.OK);
    }

    @PostMapping("/create")
    @CrossOrigin("*")
    public ResponseEntity addNew(@RequestBody(required = false) MemberDto memberDto){
        return new ResponseEntity(memberService.createMember(memberDto),HttpStatus.OK);
    }

    @DeleteMapping("/remove")
    @CrossOrigin("*")
    public ResponseEntity remove(@RequestHeader("Authorization") String auth,
                                 @RequestParam("accountId") String accountId){
        return new ResponseEntity(memberService.removeMember(auth, accountId),HttpStatus.OK);
    }

    @PutMapping("/update")
    @CrossOrigin("*")
    public ResponseEntity update(@RequestHeader("Authorization") String auth,
                                 @RequestBody MemberDto data){
        return new ResponseEntity(memberService.updateMember(auth, data),HttpStatus.OK);
    }

    @GetMapping("/read")
    @CrossOrigin("*")
    public ResponseEntity findAll(@RequestHeader("Authorization") String auth){
        return new ResponseEntity(memberService.readAllMember(auth), HttpStatus.OK);
    }

    @GetMapping("/read-detail")
    @CrossOrigin("*")
    public ResponseEntity findById(@RequestHeader("Authorization") String auth,
                                   @RequestParam("accountId") String accountId){
        return new ResponseEntity(memberService.readMemberById(auth, accountId), HttpStatus.OK);
    }

    @GetMapping("/read-role")
    @CrossOrigin("*")
    public ResponseEntity getRole(){
        return new ResponseEntity(memberService.getRole(), HttpStatus.OK);
    }
}
