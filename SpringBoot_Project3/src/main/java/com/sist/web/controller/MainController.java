package com.sist.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sist.web.dao.MemberDAO;
import com.sist.web.dao.MusicDAO;
import com.sist.web.entity.MemberEntity;
import com.sist.web.entity.MusicEntity;
import com.sist.web.service.MainServiceImpl;

@Controller
public class MainController {
	@Autowired
	private MainServiceImpl service;

	@Autowired
	private MemberDAO dao;
	@Autowired
	private MusicDAO mdao;

	@GetMapping("/main")
	public String main_page(String cno, String page, Model model, HttpServletRequest request) {
		if (page == null)
			page = "1";
		if (cno == null)
			cno = "1";
		int curpage = Integer.parseInt(page);
		int rowSize = 20;
		int start = (curpage * rowSize) - rowSize;

		List<MusicEntity> list = service.musicListData(Integer.parseInt(cno), start);
		for (MusicEntity vo : list) {
			String t = vo.getTitle();
			String s = vo.getSinger();
			if (t.length() > 35) {
				t = t.substring(0, 35) + "...";
			}
			vo.setTitle(t);

			if (s.length() > 20) {
				s = s.substring(0, 20) + "...";
			}
			vo.setSinger(s);
		}
		int count = service.musicRowCount(Integer.parseInt(cno));
		count = ((curpage * rowSize) - rowSize) + 1;
		int lastcount = (curpage * rowSize);
		int totalpage = service.musicTotalPage(Integer.parseInt(cno));

		// 쿠키
		Cookie[] cookies = request.getCookies();
		List<MusicEntity> mlist = new ArrayList<MusicEntity>();
		if (cookies != null) {
			for (int i = cookies.length - 1; i >= 0; i--) {
				if (cookies[i].getName().startsWith("m")) {
					cookies[i].setPath("/");
					String mno = cookies[i].getValue();
					MusicEntity me = mdao.findByNo(Integer.parseInt(mno));
					mlist.add(me);
				}
			}
		}
		model.addAttribute("mlist", mlist);

		model.addAttribute("curpage", curpage);
		model.addAttribute("list", list);
		model.addAttribute("totalpage", totalpage);
		model.addAttribute("cno", cno);
		model.addAttribute("count", count);
		model.addAttribute("lastcount", lastcount);
		model.addAttribute("main_content", "music/list");

		return "main";
	}

	@PostMapping("/music/detail")
	@ResponseBody
	// 리턴형을 설정하면 자동으로 JSON으로 전달함
	public MusicEntity music_detail_before(int no, HttpServletResponse response) {

		Cookie cookie = new Cookie("m" + no, String.valueOf(no));
		cookie.setPath("/");
		cookie.setMaxAge(60 * 60 * 24);
		response.addCookie(cookie);

		MusicEntity vo = service.musicDetailData(no);

		return vo;
	}

	@GetMapping("/member/login")
	public String member_login(Model model) {

		model.addAttribute("main_content", "member/login");
		return "main";
	}

	@PostMapping("/member/log_in")
	@ResponseBody
	public String loginDoing(String id, String pwd, HttpSession session) {
		String result = service.loginChecking(id, pwd);
		System.out.println("id=" + id);
		System.out.println("pwd=" + pwd);
		if (!(result.equals("NOID") && result.equals("NOPWD"))) {
			session.setAttribute("id", id);
		}
		System.out.println(result);
		return result;
	}

	@GetMapping("/member/loout")
	public String member_logout(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
		session.invalidate();
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				if (cookies[i].getName().startsWith("m")) {
					cookies[i].setPath("/");
					cookies[i].setMaxAge(0);
					response.addCookie(cookies[i]);
				}
			}
		}
		return "redirect:../main";
	}

	@GetMapping("/member/idfind")
	public String member_idfind(Model model) {

		model.addAttribute("main_content", "member/idfind");
		return "main";
	}

	@GetMapping("/member/pwdfind")
	public String member_pwdfind(Model model) {

		model.addAttribute("main_content", "member/pwdfind");
		return "main";
	}

	@PostMapping("/member/pwdfind_ok")
	@ResponseBody
	public String member_pwdfind_ok(String id, Model model) {
		String findpwd = service.pwdfind(id);
		String result = "";

		result = findpwd;

		return result;
	}

	@PostMapping("/member/idfind_ok")
	@ResponseBody
	public String member_idfind_ok(String tel, Model model) {
		String findid = service.idfind(tel);
		String result = "";

		result = findid;

		return result;
	}

	@GetMapping("/member/join")
	public String member_join(Model model) {

		model.addAttribute("main_content", "member/join");
		return "main";
	}

	@PostMapping("/member/idcheck")
	@ResponseBody
	public String member_idcheck(String id) {
		String result = "";

		int count = service.idchecking(id);
		if (count == 0) {
			result = "YES"; // 사용가능
		} else {
			result = "NO"; // 사용 불가능
		}

		return result;
	}

	@PostMapping("/member/niccheck")
	@ResponseBody
	public String member_niccheck(String nic) {
		String result = "";

		int count = service.nicchecking(nic);
		if (count == 0) {
			result = "YES"; // 사용가능
		} else {
			result = "NO"; // 사용 불가능
		}

		return result;
	}

	@PostMapping("/member/telcheck")
	@ResponseBody
	public String member_telcheck(String tel) {
		String result = "";

		int count = service.telchecking(tel);
		if (count == 0) {
			result = "YES"; // 사용가능
		} else {
			result = "NO"; // 사용 불가능
		}

		return result;
	}

	@PostMapping("/member/join_ok")
	@ResponseBody
	public String member_join_ok(String id, String pwd, String nic, String tel) {
		String result = "";

		MemberEntity vo = new MemberEntity();
		vo.setId(id);
		vo.setPassword(pwd);
		vo.setUsername(nic);
		vo.setTel(tel);

		dao.save(vo);

		return result = "";
	}
}
