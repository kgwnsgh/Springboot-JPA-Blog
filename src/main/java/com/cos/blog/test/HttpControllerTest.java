package com.cos.blog.test;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

// 사용자가 요청 -> 응답
@RestController
public class HttpControllerTest {
	
	private static final String TAG= "HttpControllerTest : ";
	
	// 롬복을 사용하면 @AllArgsConstructor와 같은 어노테이션 하나로 빌더를 쉽게 만들수 있다 and 생성자 순서를 안 맞추고 사용해도 오류가 없다
	// 
	@GetMapping("/http/lombok")
	public String lombokTest() {
		//Member m = new Member(1, "ssar", "1234", "email"); // AllArgsConstructor 이용
		Member m = Member.builder().username("ssar").password("1234").email("ssar@nate.com").build(); //builder를 통한 설정
		System.out.println(TAG+"getter : "+m.getUsername());
		m.setUsername("cos");
		System.out.println(TAG+"setter : " +m.getUsername());
		return "lombok test 완료";
	}
	
	// 인터넷 브라우저 요청은 무조건 get 요청밖에 할수 없다
	// http://localhost:8080/http/get (select)
	//데이터를 주소에 담아서 받는다 (?id=1&username=jun&password=1234)
	@GetMapping("/http/get")
	public String getTest(Member m) {
		return "get 요청 : "+ m.getId() +", " + m.getUsername() + ", " + m.getPassword() + ", " + m.getEmail();
	}

	//데이터를 바디에 담아서 받는다(form 태그를 통해서 받거나 
	// http://localhost:8080/http/post (insert)
	@PostMapping("/http/post")
//	public String postTest(Member m) {
//		return "post 요청 : "+ m.getId() +", " + m.getUsername() + ", " + m.getPassword() + ", " + m.getEmail();
		
	//Json 방식으로 받은 데이터를 자동으로 파싱해줄수 있다
	public String postTest(@RequestBody Member m) { // MessageConverter (스프링부트)
		return "post 요청 : " + m.getId() +", " + m.getUsername() + ", " + m.getPassword() + ", " + m.getEmail(); //test/plain, application/json
	}

	// http://localhost:8080/http/put (update)
	@PutMapping("/http/put")
	public String putTest(@RequestBody Member m) {
		return "put 요청 : " + m.getId() +", " + m.getUsername() + ", " + m.getPassword() + ", " + m.getEmail();
	}
	
	// http://localhost:8080/http/delete (delete)
	@DeleteMapping("/http/delete")
	public String deleteTest() {
		return "delete 요청";
	}
	
	
}
