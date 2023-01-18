$(() => {
		
	$('#signup').on('click', () => {
		
		if(!emailValidate()){
			$('#email').addClass('is-invalid').focus();
			return false;
		}else if(!passwordValidate()){
			$('#passwordComform').addClass('is-invalid');
			$('#password').addClass('is-invalid').focus();
			return false;
		}
	});
});

var emailValidate = () => {
	const emailRegex = /^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/
	var email = $('#email').val();
	
	return emailRegex.test(email);
};

var passwordValidate = () => {
	var pw = $('#password').val();
	var pwComform = $('#passwordComform').val();
	
	console.log('pw', pw);
	console.log('pwComform', pwComform);
	
	return pw === pwComform;	
};