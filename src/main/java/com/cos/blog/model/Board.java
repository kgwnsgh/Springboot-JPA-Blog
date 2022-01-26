package com.cos.blog.model;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder // 빌더 패턴1
//ORM -> Java(다른언어) Object -> ㅁ\테이블로 매핑해주는 기술
@Entity // User 클래스가 MySQL에 테이블이 생성이 된다.
public class Board {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
	private int id;  //아이디
	
	@Column(nullable = false, length =100)
	private String title;  //제목
	
	@Lob // 대용량 데이터
	private String content;  // 섬머노트 라이브러리 <html> 태그가 섞여서 디자인 됨.
	
	//@ColumnDefault("0") // int값인 숫자에는 따로 ' ' 로 구분해주지  않아도 된다 
	private int count; // 조회수
	
	@ManyToOne(fetch = FetchType.EAGER) // Many = Board, User = One // 한명의 유저는 여러개의 게시글을 작성할 수 있다.
	@JoinColumn(name="userId")
	private User user; // DB는 오브젝트를 저장할 수 없다. FK,자바는 오브젝트를 저장할수 있다.
	
	@OneToMany(mappedBy = "board", fetch = FetchType.EAGER) // mappedBy 연관관계의 주인이 아닌(난 FK가 아니
	@JsonIgnoreProperties({"board"}) // 만약 board가 호출될때 reply 안에 board 호출이 한번더 일어나는것을 방지 / 왜안되지??
	@OrderBy("id desc")
	private List<Reply> replys;
	
	@CreationTimestamp // 데이터가 insert 혹은 update 될때 자동으로 시간이 추가됨
	private Timestamp createDate;
	
}
