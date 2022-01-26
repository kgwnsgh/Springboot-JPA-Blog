let index = {
		init: function() {
			$("#btn-save").on("click", ()=>{ // function() {}, ()=>{}
												// this를바인딩 하기 위해서
				this.save();
			});
			$("#btn-delete").on("click", ()=>{ 
				this.deleteById();
			});
			$("#btn-update").on("click", ()=>{ 
				this.update();
			});
			$("#btn-reply-save").on("click", ()=>{ 
				this.replySave();
			});
		},
			
			save: function() {
				// alert("user의 save함수 호출됨");
				let data = { // id값을 찾아서 호출
						title: $("#title").val(),
						content: $("#content").val(),
				};

				$.ajax({	// 회원가입 수행 요청
					type:"POST",
					url:"/api/board",
					data: JSON.stringify(data), // JSON으로 데이터 값을 변경 //http body
												// 데이터
					contentType: "application/json; charset=utf-8",
				dataType: "json" 
				}).done(function(resp){
					console.log(data);
					alert("글쓰기가 완료되었습니다.");
					location.href="/";
				}).fail(function(error){
					alert(JSON.stringify(error));
				}); // ajax 통신을 이용해서 3개의 데이터를 json으로 변경하여 insert 요청
			},
			
			deleteById: function(){
				let id = $("#id").text();
				
				$.ajax({ 
					type: "DELETE",
					url: "/api/board/"+id,
					dataType: "json"
				}).done(function(resp){
					alert("삭제가 완료되었습니다.");
					location.href = "/";
				}).fail(function(error){
					alert(JSON.stringify(error));
				}); 
			},
			
			update: function() {
				let id = $("#id").val();
				
				let data = { 
						title: $("#title").val(),
						content: $("#content").val(),
				};

				$.ajax({	
					type:"PUT",
					url:"/api/board/"+id,
					data: JSON.stringify(data),
					contentType: "application/json; charset=utf-8",
				dataType: "json" 
				}).done(function(resp){
					console.log(data);
					alert("수정이 완료되었습니다.");
					location.href="/";
				}).fail(function(error){
					alert(JSON.stringify(error));
				}); 
			},
			
			replySave: function() {
				// alert("user의 save함수 호출됨");
				let data = { 
						content: $("#reply-content").val(),
				};
				let boardId = $("#boardId").val();
//				console.log(data);

				$.ajax({	
					type:"POST",
					url:`/api/board/${boardId}/reply`,
					data: JSON.stringify(data), 
					contentType: "application/json; charset=utf-8",
				dataType: "json" 
				}).done(function(resp){
					console.log(data);
					alert("댓글 작성이 완료되었습니다.");
					location.href=`/board/${boardId}`;
				}).fail(function(error){
					alert(JSON.stringify(error));
				}); 
			},
		}

index.init();