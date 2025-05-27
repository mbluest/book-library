package com.example.bookslibraries.kurniads.repository;

import com.example.bookslibraries.kurniads.entity.MemberModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<MemberModel, Long> {

    Optional<MemberModel> findByAccountId(String accountId);

    Optional<MemberModel> findByNickname(String nickname);

    Optional<MemberModel> findByEmail(String email);

}
