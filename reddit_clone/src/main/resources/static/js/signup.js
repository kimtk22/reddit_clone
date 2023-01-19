$(() => {
	
	const authService = new AuthService();
	
	console.log(authService);
	
	// 회원가입 유효성 검사.
	$('#signup').on('click', () => {
		if(!authService.emailValidate()){
			$('#email').addClass('is-invalid').focus();
			return false;
		}else if(!userNameValidate()){
			$('#userName').addClass('is-invalid').focus();
			return false;
		}else if(!passwordValidate()){
			$('#passwordComfirm').addClass('is-invalid');
			$('#password').addClass('is-invalid').focus();
			return false;
		}
	});
	
	// 이벤트
	$('#email').on({
		'keydown' : (e) => {
			let $this = $(e.target);
			if($this.hasClass('is-invalid') === true){
				$this.removeClass('is-invalid');
			}
		},
		'blur' : (e) => {
			// 1. 이메일 유효성 검사
			if(emailValidate() === false){
				$('#emailFeedback').text('Email invalid.')
				$(e.target).addClass('is-invalid');
				return;
			}
			
			// 2. 중복 체크 (Requerst)
			emailDuplicateValidate($(e.target).val())
				.then((result) => {
					if(result === false){
						$('#emailFeedback').text('Duplicate email.')
						$(e.target).addClass('is-invalid');
						return;
					}
				});
		}
	});
	
	$('#userName').on({
		'keydown' : (e) => {
			let $this = $(e.target);
			if($this.hasClass('is-invalid') === true){
				$this.removeClass('is-invalid');
			}
		},
		'blur' : (e) => {
			let userName = $(e.target).val();
			if(userName === '') $(e.target).addClass('is-invalid');
		}
	});
	$('#password').on('keydown', (e) => {
		let $this = $(e.target);
		if($this.hasClass('is-invalid') === true){
			$this.removeClass('is-invalid');
		}
	});
	$('#passwordComfirm').on('keydown', (e) => {
		let $this = $(e.target);
		if($this.hasClass('is-invalid') === true){
			$this.removeClass('is-invalid');
		}
	});
});

//let emailValidate = () => {
//	const emailRegex = /^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/
//	let email = $('#email').val();
//	
//	return emailRegex.test(email);
//};
//
//let userNameValidate = () => {
//	let userName = $('#userName').val();
//	return userName !== '';
//};
//
//let passwordValidate = () => {
//	let pw = $('#password').val();
//	let pwComfirm = $('#passwordComfirm').val();
//	
//	console.log('pw', pw);
//	console.log('pwComfirm', pwComfirm);
//	
//	return pw === pwComfirm;	
//};
//
//let emailDuplicateValidate = async (email) => {
//	let body = {"email" : email}
//	let resInit ={
//		method : "post",
//		headers : {
//			"Content-Type" : "application/json"
//		},
//		body : JSON.stringify(body)
//	}
//	
//	let response = await fetch('http://localhost:8080/api/auth/email-validate', resInit);
//	response = await response.json();
//	 
//	return response.result; 
//};