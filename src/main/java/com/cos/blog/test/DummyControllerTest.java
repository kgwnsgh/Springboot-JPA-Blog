package com.cos.blog.test;

import java.util.List;
import java.util.function.Supplier;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;

// html 파일이 아니라 data를 리턴해주는 controller =RestController
@RestController
public class DummyControllerTest {

	@Autowired // 의존성 주입(DI) 메모리에 뜨면 자동으로 데이터 삽입
	private UserRepository userRepository;
	
	@DeleteMapping("/dummy/user/{id}")
	public String delete(@PathVariable int id) {
		try {
			userRepository.deleteById(id);
		} catch(EmptyResultDataAccessException e) {  //일반 Exception을 걸어도 오류없이 가능
			return "삭제에 실패하였습니다. 해당 id는 DB에 없습니다";
		}
		
		return "삭제 되었습니다. id : " +id;
	}
	
	// save 함수는 id 전달하면 해당 id에 대한 데이터가 있으면 update하고 없으면 insert를 한다
	//email. password를 입력 받는다
	@Transactional //더티 체킹 - 세이브가 없어도 업데이트를 할수있음
	//함수 종료시에 자동으로 commit
	@PutMapping("/dummy/user/{id}") // 주소는 같아도 요청 방식으로 구분되어서 같은 주소도 괜찮음
	public User updateUser(@PathVariable int id, @RequestBody User requestUser) { // json 데이터를 요청 => java Object(MessageConverter의 Jackson라이브러리가 받아줌
		System.out.println("id : " + id);
		System.out.println("password : " + requestUser.getPassword());
		System.out.println("email : " + requestUser.getEmail());
		
		User user = userRepository.findById(id).orElseThrow(()->{
			return new IllegalArgumentException("수정에 실패하였습니다.");
		});
		user.setPassword(requestUser.getPassword());
		user.setEmail(requestUser.getEmail());
		
//		userRepository.save(user); // 세이브를 사용하면 데이터 베이스가 널값이 들어갈수 있다
		return user;
	}
	
	//http:/localhost:8000/blog/dummy/user
	@GetMapping("/dummy/users")
	public List<User> list() {
		return userRepository.findAll(); // DB의 유저 전체를 리턴
	}
	
	// 한페이지당 2건의 데이터를 리턴받아 볼 예정
	// 아이디를 2건씩 최근 순서로 가지고 온다
	@GetMapping("/dummy/user")
	public Page<User> pegeList(@PageableDefault(size=2, sort="id",direction = Sort.Direction.DESC) Pageable pageable) {
		Page<User> pagingUsers = userRepository.findAll(pageable);
		
		List<User> users = pagingUsers.getContent();
		return pagingUsers;
	}
	
	//{id} 주소로 파라메터를 전달 받을수 있음
	// http:/localhost:8000/blog/dummy/user/3
	@GetMapping("/dummy/user/{id}")
	public User detail(@PathVariable int id) {
		// userRepository를 통해 id값을 가지고 왔는데 그 값이 null 경우를 대비해서 
		// 데이터 값을 옵셔널의 상태로 전달해준다 / 그래서 옵셔널 값을 null인지 확인 후에 넣으면 된다
		// .get()  옵셔널 정보 그대로 사용/ .orElseGet()
		
		//User user = userRepository.findById(id).get();
		
//		User user = userRepository.findById(id).orElseGet(new Supplier<User>() {
//			@Override
//			public User get() {
//				// TODO Auto-generated method stub
//				return new User(); //만약 id값이 데이터베이스에 없는 경우 빈 객체를 새로 만들어서 null값이 되지 않게 방지해준다
//			}
//		});
		
		
		//람다식 - 스플라이어 타입과 같은 내용이 생략되어 조금더 간단하게 작성이 가능하다
//		User user = userRepository.findById(id).orElseThrow(() -> {
//			return new IllegalArgumentException("해당 사용자는 없습니다.");
//		});
//		return user;
//	}
		
		
		
		User user = userRepository.findById(id).orElseThrow(new Supplier<IllegalArgumentException>() {
		@Override
		public IllegalArgumentException get() {
			// TODO Auto-generated method stub
			return new IllegalArgumentException("해당 유저는 없습니다.id : " + id); // 예외 상황이 발생하면 텍스트와 함께 아이디출력
		}
		});
		// 요청 : 웹브라우저
		// user 객체 = 자바 오브젝트
		// 변환 (웹 브라우저가 이해할 수 있는 데이터) -> json (gson 라이브러리)
		// 스프링부트 = MessageConverter라는 애가 응답시에 자동 작동
		// 만약 자바 오브젝트를 리턴하게 되면 MessageConverter가 Jackson 라이브러리를 호출해서
		// user 오브젝트를 json으로 변환해서 브라우저에 던져줌
		return user;
	}
	
	// http://localhost:8000/blog/dummy/join (요청)
	// http의 body에 username,password, email 데이터를 가지고 (요청)
	@PostMapping("/dummy/join")
//	public String join(String username, String password, String email ) {// key=value(약속된 규칙)
	public String join(User user) {
		System.out.println("id : " + user.getId());
		System.out.println("username : " + user.getUsername());
		System.out.println("password : " + user.getPassword());
		System.out.println("email : " + user.getEmail());
		System.out.println("role : " + user.getRole());
		System.out.println("createDate : " + user.getCreateDate());
		
		user.setRole(RoleType.USER);
		userRepository.save(user);
		return "회원가입이 완료되었습니다.";
	}
}