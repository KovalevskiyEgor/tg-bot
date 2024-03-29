package com.example.repository;

import com.example.models.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<AppUser, Long> {
    AppUser findById(int id);
    Optional<AppUser> findAppUserByTelegramUserId(Long id);
}
