let index = {
		init: function() {
			$("#btn-save").on("click", ()=>{ // function() {}, ()=>{} this를바인딩 하기 위해서
				this.save();
			});
			$("#btn-login").on("click", ()=>{ // function() {}, ()=>{} this를바인딩 하기 위해서
				this.login();
			});
		},
			
			save: function() {
				// alert("user의 save함수 호출됨");
				let data = { // id값을 찾아서 호출
						username: $("#username").val(),
						password: $("#password").val(),
						email: $("#email").val(),
				};
				// console.log(data); // 데이터를 잘 가지고 오는지 확인
				
				// 회원가입시 ajax를 사용하는 이유는 서버가 html로 응답을 하면 앱에서는 안 돌아가기 때문에 data로
				// 응답 해주는 ajax를 사용
				// 비동기 통신을 위해서 // 비동기는 일처리 순서가 주도적으로 바꿔어서 일을 한다
				// ajax가 통신을 성공하고 서버가 json을 리턴해주면 자동으로 자바 오브젝트로 변환
				$.ajax({	// 회원가입 수행 요청
					type:"POST",
					url:"/api/user",
					data: JSON.stringify(data), // JSON으로 데이터 값을 변경 //http body
												// 데이터
					contentType: "application/json; charset=utf-8",
				dataType: "json" // 요청을 서버로해서 응답이 왔을 때 기본적으로 모든 것이 문자열(생긴게
									// json이라면)=> javascript 오브젝트로 변경
				}).done(function(resp){
					alert("회원가입이 완료되었습니다.");
					// console.log(resp);
					location.href="/";
				}).fail(function(error){
					alert(JSON.stringify(error));
				}); // ajax 통신을 이용해서 3개의 데이터를 json으로 변경하여 insert 요청
			},
			
			login: function() {
				//alert("user의 login함수 호출됨");
				let data = { // id값을 찾아서 호출
						username: $("#username").val(),
						password: $("#password").val(),
				};
	
				$.ajax({	
					type:"POST",
					url:"/api/user/login",
					data: JSON.stringify(data), // JSON으로 데이터 값을 변경 //http body
					contentType: "application/json; charset=utf-8",
				dataType: "json" // 요청을 서버로해서 응답이 왔을 때 기본적으로 모든 것이
									// 문자열(생긴게json이라면)=> javascript 오브젝트로 변경
				}).done(function(resp){
					alert("로그인이 완료되었습니다.");
					// console.log(resp);
					location.href="/";
				}).fail(function(error){
					alert(JSON.stringify(error));
				}); // ajax 통신을 이용해서 3개의 데이터를 json으로 변경하여 insert 요청
			},
		}

index.init();