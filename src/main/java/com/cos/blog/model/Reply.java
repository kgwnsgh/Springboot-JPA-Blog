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

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity // User 클래스가 MySQL에 테이블이 생성이 된다.
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder // 빌더 패턴1
public class Reply {

	@Id //Primary key 
	@GeneratedValue(strategy = GenerationType.IDENTITY) //프로젝트에서 연결된 DB의 넘ㅁ버링 전략을 따라간다
	private int id; //시퀀스, auto_increment
	
	@Column(nullable = false,length = 200)
	private String content;  // 섬머노트 라이브러리 <html> 태그가 섞여서 디자인 됨.
	
	@ManyToOne //여러개의 답변은 하나의 게시글에 존재 가능
	@JoinColumn(name="boardId") // 보드와 연결
	private Board board;
	
	@ManyToOne(fetch = FetchType.EAGER) // 여러개의 답변을 하나의 유저가 작성할수 이싸
	@JoinColumn(name="userId") // 유저와 연결
	private User user;
	
	@OneToMany(mappedBy = "board",fetch = FetchType.EAGER) // 하나의 게시글은 여러개의 답변을 가질수 있다
	//mappedBy =연관관계의 주인이 아니다(FK가 아님을 선언) DB에 컬럼을 생성하지 않음 // FK = foring key
	// @JoinColumn(name="replyId") foring key에 받는 정보는 하나만 입력 받는데 여러개의 댓글을 받으면 불가능 하기 때문에 사용하지 않음 
	private List<Reply> reply; // List 자바 유틸을 통해 여러개의 답변을 받을수 있게 한다
	
	@CreationTimestamp // 시간을 자동으로 작성
	private Timestamp createDate;
}
	

//EGER 전략은 정보를 처음부터 전부 요청해서 다 가지고  오는 방법이고
// LEZY 전략은 정보를 요청하면 필요한 정보를 가지고 오는 방법이다
