package com.cos.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.cos.blog.config.auth.PrincipalDetailService;


@Configuration // 빈 등록(IoC관리) 빈 등록 : 스프링 컨테이너에서 객체를 관리할 수 있게 하는 것
@EnableWebSecurity // 시큐리티 필터가 등록된다 = 스프링 시큐리티가 활성화가 되어 있는데 어떤 설정을 해당 파일에서 하겠다
@EnableGlobalMethodSecurity(prePostEnabled = true) // 특정 주소로 접근을 하면 권한 및 인증을 미리 체크하겠다는 뜻
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private PrincipalDetailService principalDetailService;
	
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean // Ioc가 된다 / return 값을 스프링에서 관리한다
	public BCryptPasswordEncoder encodePWD() {
		return new BCryptPasswordEncoder();
	}
	
	// 시큐리티가 대신 로그인해주는데 password를 가로채기하는데
	// 해당 password가 뭘 해쉬가 되어 회원가입이 ㅇ되었는지 알아야
	// 같은 해쉬로 암호화해서 DB 에 있는 해쉬랑 비교할 수 있음.
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(principalDetailService).passwordEncoder(encodePWD());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable() // csrf 토큰 비활성화 (테스트시 걸어두는게 좋음)
				.authorizeRequests() // requests가 들어오면
				.antMatchers("/", "/auth/**", "/js/**", "/css/**", "/image/**", "dummy/**") // /aurt로 들어오면
				.permitAll() // 요청을 허용
				.anyRequest() // 이게 아닌 다른 요청은
				.authenticated() // 이증해야한다
				.and().formLogin().loginPage("/auth/loginForm")
				.loginProcessingUrl("/auth/loginProc")	//스프링 시큐리티가 해당 주소로 로그인을 가로 챈다.
				.defaultSuccessUrl("/");  //로그인이 완료되면 / 로 이동한다
	}
}
