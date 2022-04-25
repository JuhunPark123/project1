package com.sist.web.service;

import java.util.List;
import java.util.StringTokenizer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sist.web.dao.MemberDAO;
import com.sist.web.dao.MusicDAO;
import com.sist.web.entity.MemberEntity;
import com.sist.web.entity.MusicEntity;

@Service
public class MainServiceImpl implements MainService {
	@Autowired
	private MusicDAO dao;

	@Autowired
	private MemberDAO mdao;

	@Override
	public List<MusicEntity> musicListData(Integer cno, Integer start) {
		// TODO Auto-generated method stub
		return dao.musicListData(cno, start);
	}

	@Override
	public int musicTotalPage(int cno) {
		// TODO Auto-generated method stub
		return dao.musicTotalPage(cno);
	}

	@Override
	public MusicEntity musicDetailData(Integer no) {
		// TODO Auto-generated method stub
		return dao.musicDetailData(no);
	}

	@Override
	public int musicRowCount(Integer cno) {
		// TODO Auto-generated method stub
		return dao.musicRowCount(cno);
	}

	@Override
	public String loginChecking(String id, String pwd) {
		String result = "";
		int count = mdao.idchecking(id);

		if (count == 0) {
			result = "NOID";
		} else {
			String db_pwd = mdao.loginChecking(id);
			if (db_pwd.equals(pwd)) {
				result = "환영합니다.";
			} else {
				result = "NOPWD";
			}

		}
		return result;
	}

	@Override
	public int idchecking(String id) {
		// TODO Auto-generated method stub
		return mdao.idchecking(id);
	}

	@Override
	public int pwdchecking(String pwd) {
		// TODO Auto-generated method stub
		return mdao.pwdchecking(pwd);
	}

	@Override
	public String idfind(String tel) {
		// TODO Auto-generated method stub
		return mdao.idfind(tel);
	}

	@Override
	public String pwdfind(String id) {
		// TODO Auto-generated method stub
		return mdao.pwdfind(id);
	}

	@Override
	public int nicchecking(String username) {
		// TODO Auto-generated method stub
		return mdao.nicchecking(username);
	}

	@Override
	public int telchecking(String tel) {
		// TODO Auto-generated method stub
		return mdao.telchecking(tel);
	}

	

}
