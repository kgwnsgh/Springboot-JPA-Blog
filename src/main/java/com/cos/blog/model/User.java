package com.cos.blog.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


// ORM -> Java(다른언어) Object -> ㅁ\테이블로 매핑해주는 기술
@Entity // User 클래스가 MySQL에 테이블이 생성이 된다.
@Data // geter seter가 없어도 되게 해준다
@NoArgsConstructor
@AllArgsConstructor
@Builder // 빌더 패턴1
// @DynamicInsert // insert 시의 null의 빌드를 제외 시켜준다
public class User {
	
	@Id //Primary key 
	@GeneratedValue(strategy = GenerationType.IDENTITY) //프로젝트에서 연결된 DB의 넘ㅁ버링 전략을 따라간다
	private int id; //시퀀스, auto_increment
	
	@Column(nullable = false, length = 30, unique = true) // nullable = false를 통해 아이디에 null 값이 들어가지 않게 하고 최대 글자수는 30글자로 한다
	private String username; // 아이디
	
	@Column(nullable = false, length = 100) // 해쉬를 통해 비밀번호 암호화
	private String password; // 비밀번호
	
	@Column(nullable = false, length = 50)
	private String email; // 이메일
	
	// @ColumnDefault("'user'") // ColumnDefault를 사용하려면 "" 안에 ' ' 로 문자라는걸 한번더 작성해야한다
	@Enumerated(EnumType.STRING) // 해당 Enum이 String임을 확인 시켜줌
	private RoleType role; // Enum을 스는게 좋다/ ADMIN, USER
	//private Enum role;
	
	@CreationTimestamp // 시간을 자동으로 입력
	private Timestamp createDate; // java.sql 타임스탬프 사용
}
