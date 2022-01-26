
package com.cos.blog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.blog.model.Board;
import com.cos.blog.model.Reply;
import com.cos.blog.model.User;
import com.cos.blog.repository.BoardRepository;
import com.cos.blog.repository.ReplyRepository;

import lombok.RequiredArgsConstructor;


//서비스는 여러개의 트렌젝션을 하나로 묶어주는 역활을 한다
//스프링이 컴포넌트 스캔을 통해서 Bean에 등로을 해줌. ioC를 해준다.
@Service
public class BoardService {

	@Autowired
	private BoardRepository boardRepository;
	
	@Autowired
	private ReplyRepository replyRepository;
	
	@Transactional
	public void 글쓰기(Board board, User user) { // title, content
		board.setCount(0);
		board.setUser(user);
		boardRepository.save(board);
	}
	
	@Transactional(readOnly = true)
	public Page<Board> 글목록(Pageable pageable){
		return boardRepository.findAll(pageable);
	}

	@Transactional(readOnly = true)
	public Board 글상세보기(int id) {
		return boardRepository.findById(id)
				.orElseThrow(()->{
					return new IllegalArgumentException("글 상세보기 실패 : 아이디를 찾을 수 없습니다.");
				});
	}
	
	@Transactional
	public void 글삭제하기(int id) {
		boardRepository.deleteById(id);
	}
	
	@Transactional 
	public void 글수정하기(int id, Board requesBoard) {
		Board board = boardRepository.findById(id)
				.orElseThrow(()->{
					return new IllegalArgumentException("글 찾기 실패 : 아이디를 찾을 수 없습니다.");
				}); //영속화 완료
		board.setTitle(requesBoard.getTitle());
		board.setContent(requesBoard.getContent());
		// 해당 함수 종료시 (Service가 종료될 때) 트렌잭션이 종료 됩니다.
	}
	
	@Transactional
	public void 댓글쓰기(User user, int boardId, Reply requestReply) {
		
		Board board = boardRepository.findById(boardId).orElseThrow(()->{
			return new IllegalArgumentException("댓글 쓰기 실패: 게시글 id를 찾을 수 없습니다.");
		}); // 영속화 완료;
		
		requestReply.setUser(user); // user에 대한 정보를 reply에 담는다 /오브젝트 
		requestReply.setBoard(board); // board에 대한 정보를 reply에 담는다 /오브젝트
		
		replyRepository.save(requestReply); // 담긴정보를 저장
	}
}
