$(document).ready(function(){
	$(".handle").click(function(){
		console.log(screen.width);
		if($(window).width() <= 768){
			document.location.href = "/user/asdaf";
		}else{
			$(".content").append($('<div class="popup-profile"><div class="popup-boxer col-md-3"></div><div class="col-md-6"><div class="shout-container"><div class="shout"><div id="them"><img src="http://placehold.it/100x100"><h1>profile.fullname</h1><h2>profile.handle</h2></div><div class="profile-stats-container"><div class="profile-stats"><div><p>Tweets</p><h3>{tweets}</h3></div><div><p>Followers</p><h3>{Followers}</h3></div><div><p>Following</p><h3>{Following}</h3></div></div></div></div></div></div><div class="popup-boxer col-md-3"></div></div>'));
			$('.popup-boxer ').click(function(){
				console.log('click');
				$($(this).parent()).remove();
			});
		}
		$(document).keyup(function(e) {
		  	if (e.keyCode == 27) { $('.popup-profile').remove(); }
		});
	});
});

