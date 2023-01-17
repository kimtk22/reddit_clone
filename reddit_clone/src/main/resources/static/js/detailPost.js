$(function(){
	$('.reply-button').on('click', function(){
		
		$('.comment-form').hide();
		$(this).closest('.comment').next().show();
	});
});