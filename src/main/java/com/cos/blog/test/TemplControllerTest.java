package com.cos.blog.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller // 이 어노테이션은 파일을 리턴해 준다
public class TemplControllerTest {
	
	// http://localhost:8000/blog/temp/home
	@GetMapping("/temp/home")
	public String tempHome() {
		// 파일 리턴 기본경로 : src/main/resources/static < 기본경로 파일을 찾아봄
		// 리턴명 : /home.html이 되야한다
		return "/home.html";
	}
	
	@GetMapping("/temp/img")	
	public String tempImg() {
		return "/a.png";
	}
	
	@GetMapping("/temp/jsp")
	public String tempJsp() {
		// prefix : /WEB-INF/views/
		// suffix :.jsp
		// 풀네임 : /WEB-INF/views/
		return "test";
	}
}
