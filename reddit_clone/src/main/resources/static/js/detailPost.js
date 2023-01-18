$(function(){
	$('.reply-button').on('click', function(){
		$('.comment-form').hide();
		//$(this).closest('.comment').next().show();
		$(this).parent().next().show();
	});
	
	$('.likes').on('click', function(){
		var $this = $(this);
		var commentId = $this.data("comment-id");
		updateLikes(commentId, 1).then((likes) => {
			$this.find('span').text(likes);
		});
	});
	
});

var updateLikes = async (commentId=0, likes=0) => {
	
	var body = {
		"id" : commentId,
		"likes" : likes
	}
	
	console.log(body);
	
	var requestInit = {
		method : "post",
		headers:{
			"Content-Type" : "application/json"
		},
		body : JSON.stringify(body)
	}
	
	var response = await fetch("http://localhost:8080/api/comment/likes", requestInit)
	var comment = await response.json(); 
	
	return comment.likes; 
}