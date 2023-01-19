class AuthService{
	
	construct(){
		this.url = 'http://localhost:8080';
		this.emailRegex = /^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/g
	}
	
	emailValidate(email){
		return this.emailRegex.test(email);
	}
	
	userNameValidate(userName){
		return userName !== '';
	}
	
	
	passwordValidate(password, passwordComfirm){
		return password === passwordComfirm;	
	}
	
	async emailDuplicateValidate(email){
		let body = {"email" : email}
		let resInit ={
			method : "post",
			headers : {
				"Content-Type" : "application/json"
			},
			body : JSON.stringify(body)
		}
		
		let response = await fetch(this.url + '/api/auth/email-validate', resInit);
		let json = await response.json();
		 
		return json.result; 
	}
}