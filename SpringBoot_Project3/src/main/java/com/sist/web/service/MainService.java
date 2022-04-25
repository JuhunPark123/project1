package com.sist.web.service;

import java.util.List;

import org.springframework.data.repository.query.Param;

import com.sist.web.entity.MemberEntity;
import com.sist.web.entity.MusicEntity;

public interface MainService {
	public List<MusicEntity> musicListData(@Param("cno") Integer cno, @Param("start") Integer start);

	public int musicTotalPage(@Param("cno") int cno);

	public MusicEntity musicDetailData(@Param("no") Integer no);

	public int musicRowCount(@Param("cno") Integer cno);

	public String loginChecking(@Param("id") String id, @Param("password") String pwd);

	public int idchecking(@Param("id") String id);

	public int pwdchecking(@Param("password") String pwd);

	public String idfind(@Param("tel") String tel);

	public String pwdfind(@Param("id") String id);

	public int nicchecking(@Param("username") String username);

	public int telchecking(@Param("tel") String tel);
	
}
