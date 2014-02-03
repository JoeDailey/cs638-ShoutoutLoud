$(document).ready(function(){
	$(".handle").click(function(){
		var handle = $(this).attr("data-handle");
		if($(window).width() <= 768){
			document.location.href = "/user/"+handle;
		}else{
			$.ajax({
				url:"/user/"+handle+"/json",
				type:"GET",
				dataType:"json",
				success:function(user,status){
					$(".content").append($('<div class="popup-profile"><div class="popup-boxer col-md-3"></div><div class="col-md-6"><div class="shout-container"><div class="shout"><div id="them"><img src="http://placehold.it/100x100"><h1>'+user.fullName+'</h1><h2>@'+user.handle+'</h2></div><div class="profile-stats-container"><div class="profile-stats"><div class="col-md-4 col-sm-12"><p>Tweets</p><h3>'+user.numTweets+'</h3></div><div class="col-md-4 col-sm-12"><p>Followers</p><h3>'+user.numFollowers+'</h3></div><div class="col-md-4 col-sm-12"><p>Following</p><h3>'+user.numFollowing+'</h3></div><div class="clearfix"></div></div></div></div></div></div><div class="popup-boxer col-md-3"></div></div>'));
					$('.popup-boxer ').click(function(){
						console.log('click');
						$($(this).parent()).remove();
					});
					$(document).keyup(function(e) {
						if (e.keyCode == 27) { $('.popup-profile').remove(); }
					});
				}
			});
		}
	});
	$('.compose').keyup(function(e){
		console.log(e);
	});
});

