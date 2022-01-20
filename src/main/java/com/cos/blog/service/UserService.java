package com.cos.blog.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;

//서비스는 여러개의 트렌젝션을 하나로 묶어주는 역활을 한다
//스프링이 컴포넌트 스캔을 통해서 Bean에 등로을 해줌. ioC를 해준다.
@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Transactional
	public void join(User user) {
			userRepository.save(user);
	}
}
