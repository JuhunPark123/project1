package com.sist.web.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sist.web.entity.MemberEntity;

@Repository
public interface MemberDAO extends JpaRepository<MemberEntity, String> {
	@Query(value = "SELECT password FROM member WHERE id=:id", nativeQuery = true)
	public String loginChecking(@Param("id") String id);

	// 로그인시 아이디 확인
	@Query(value = "SELECT COUNT(*) FROM member WHERE id=:id", nativeQuery = true)
	public int idchecking(@Param("id") String id);
	
	// 중복체크
	@Query(value = "SELECT COUNT(*) FROM member WHERE username=:username", nativeQuery = true)
	public int nicchecking(@Param("username") String username);
	
	@Query(value = "SELECT COUNT(*) FROM member WHERE tel=:tel", nativeQuery = true)
	public int telchecking(@Param("tel") String tel);

	// 로그인시 비밀번호 확인
	@Query(value = "SELECT COUNT(*) FROM member WHERE password=:password", nativeQuery = true)
	public int pwdchecking(@Param("password") String pwd);

	// id 찾기시 tel로 찾기
	@Query(value = "SELECT id FROM member WHERE tel=:tel", nativeQuery = true)
	public String idfind(@Param("tel") String tel);

	// pwd 찾기시 id로 찾기
	@Query(value = "SELECT password FROM member WHERE id=:id", nativeQuery = true)
	public String pwdfind(@Param("id") String id);

	
}
