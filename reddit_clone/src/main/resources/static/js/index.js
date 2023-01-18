$(function(){
	$(".upVote").on('click', function(){
		var postId = $(this).data("id");
		var voteCountTag = $(this).next();
		vote(postId, "UP", voteCountTag);
	});
	
	$(".downVote").on('click', function(){
		var postId = $(this).data("id");
		var voteCountTag = $(this).prev();
		vote(postId, "DOWN", voteCountTag);
	});
});


var vote = function(postId=0, voteType="UP", voteCountTag){
	fetch("http://localhost:8080/api/posts/vote", {
		method : "post",
		headers:{
			"Content-Type" : "application/json"
		},
		body : JSON.stringify(
			{
				"postId" : postId,
				"voteType" : voteType
			}
		)
	})
	.then((res) => res.json())
	.then((res) => {
		if(res.message){
			console.log(res);
			var mes = res.message;
			$("#liveToast strong").text(mes);
			const toast = new bootstrap.Toast(document.getElementById('liveToast'));
	    	toast.show();
		}else{
			console.log(res);
			voteCountTag.text(res.voteCount)
		}
	})
	.catch((error) => console.log(error))
}