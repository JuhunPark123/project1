package com.sist.web.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sist.web.entity.MusicEntity;

@Repository
public interface MusicDAO extends JpaRepository<MusicEntity, Integer> { // 제네릭스는 무조건 클래스형 사용 (Wrapper)
	// 목록 출력 (페이징 기법)
	@Query(value = "SELECT no,cno,title,singer,album,state,idcrement,poster,mkey "
			+ "FROM music WHERE cno=:cno ORDER BY no ASC limit :start,20", nativeQuery = true)
	public List<MusicEntity> musicListData(@Param("cno") Integer cno, @Param("start") Integer start);

	// 총 페이지
	@Query(value = "SELECT CEIL(COUNT(*)/20.0) FROM music WHERE cno=:cno", nativeQuery = true)
	public int musicTotalPage(@Param("cno") Integer cno);

	@Query(value = "SELECT no,cno,idcrement,title,singer,album,state,poster,mkey "
			+ "FROM music WHERE no=:no", nativeQuery = true)
	public MusicEntity musicDetailData(@Param("no") Integer no);

	@Query(value = "SELECT COUNT(*) FROM music WHERE cno=:cno", nativeQuery = true)
	public int musicRowCount(@Param("cno") Integer cno);

	public MusicEntity findByNo(int no);
}
