$(() => {
	const authService = new AuthService();
	// 회원가입 유효성 검사.
	$('#signup').on('click', () => {
		let email = $('#email').val();
		let userName = $('#userName').val();
		let password = $('#password').val();
		let passwordComfirm = $('#passwordComfirm').val();
		
		if(!authService.emailValidate(email)){
			$('#email').addClass('is-invalid').focus();
			return false;
		}else if(!authService.userNameValidate(userName)){
			$('#userName').addClass('is-invalid').focus();
			return false;
		}else if(!authService.passwordValidate(password, passwordComfirm)){
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
		'blur' : async (e) => {
			let $this = $(e.target);
			
			
			// 1. 이메일 유효성 검사
			if(authService.emailValidate($this.val()) === false){
				$('#emailFeedback').text('Email invalid.')
				$this.addClass('is-invalid');
				return;
			}
			
			// 2. 중복 체크 (Requerst)
			let result = await authService.emailDuplicateValidate($this.val());
			if(result === false){
				$('#emailFeedback').text('Duplicate email.')
				$this.addClass('is-invalid');
				return;
			}
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