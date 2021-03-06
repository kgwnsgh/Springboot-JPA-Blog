package com.cos.blog.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cos.blog.model.User;

// DAO
// 자동으로 bean 등록이 된다
// @Repository // 생략이 가능하다
public interface UserRepository extends JpaRepository<User, Integer>{ //JpaRepository는 User를 관리하고 User는 int 숫자이다를 extends로 선언
	// SELECT * FROM user WHERE username = 1?
	Optional<User> findByUsername(String username);
	
}

//JPA Naming 전략
//Select * From user WHERE username = ?1 AND Password = ?2;
//	User findByUsernameAndPassword(String username, String password); // 전통적인 로그인방식

//	@Query(value="Select * From user WHERE username = ?1 AND Password = ?2", nativeQuery = true)
//	User login(String username, String password);