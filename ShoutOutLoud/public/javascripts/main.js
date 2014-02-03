var isShift = false;
$(document).ready(function(){
	$(window).resize(function(){
		if($(window).width() > 768){
			$("#panel-right").css("height", ($(window).height()-$(".navbar").height()-25)+"px");
		}else{
			console.log(($(window).height()-$(".navbar").height()-$("#mobile-compose").height()-30));
			$("#panel-right").css("height", ($(window).height()-$(".navbar").height()-$("#mobile-compose").height()-35)+"px");
		}
	});
	$(window).resize();
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
					console.log(user);
					$(".content").append($('<div class="popup-profile"><div class="popup-boxer col-md-3"></div><div id="popup-mid" class="col-md-6"><div class="shout-container"><div class="shout"><div id="them"><img src="/assets/images/icon.png"><h1>'+user.fullName+'</h1><h2>@'+user.handle+'<button class="btn btn-default follow-button" data-handle="'+user.handle+'">follow</button></h2></div><div class="profile-stats-container"><div class="profile-stats"><div class="col-md-4 col-sm-12"><p>Tweets</p><h3>'+user.numTweets+'</h3></div><div class="col-md-4 col-sm-12"><p>Followers</p><h3>'+user.numFollowers+'</h3></div><div class="col-md-4 col-sm-12"><p>Following</p><h3>'+user.numFollowing+'</h3></div><div class="clearfix"></div></div></div></div></div></div><div class="popup-boxer col-md-3"></div></div>'));
					if(user.handle==$('user').attr("handle")){
						$(".follow-button").remove();
					}else{
						if(followingList.indexOf(user.handle)>-1){
							$('.follow-button').toggleClass("btn-default");
							$('.follow-button').toggleClass("btn-success");
							$('.follow-button').text("following");
						}
					}
					$('.follow-button').click(function(){
						var button = $(this);
						button.toggleClass("btn-success");
						button.toggleClass("btn-default");
						if(button.hasClass("btn-success"))
							button.text("following");
						else
							button.text("follow");
						$.ajax({
							url:"/follow/"+button.attr("data-handle"),
							type:"GET",
							dataType:"json",
							success:function(){
								console.log('success');
							}
						});
					});
					for (var i = 0; i < user.tweets.length ; i++) {
					// for(var i = 0; i < user.tweets.length; i++) {
						$("#popup-mid").append(newShout(user, user.tweets[i]));
					};
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
	$('.compose').keydown(function(e){
		if(e.keyCode==16)
			isShift = true;
	});
	$(document).keyup(function(e){
		if(e.keyCode==16)
			isShift=false;
	});
	$('.compose').keyup(function(e){
		if(e.keyCode==16)
			isShift=false;
		if(e.keyCode==13 && $('.compose').text().length > 0){
			if(!isShift){
				e.preventDefault();
				shout();
			}
		}
	});
	$('.compose').click(function(){
		var box = $(this);
		if(box.attr("clicked")=="false"){
			box.removeClass("unclicked");
			box.attr("clicked", "true");
		}
	});
	$('.compose').bind("DOMSubtreeModified",function(){
		var numChars = $(this).text().length;
		var chars = $($($($(this).parent()).children('.compose-control')).children('.characters-left'));
		chars.html(140-numChars);
		chars.css("color", "rgb("+Math.floor(200*(numChars/140))+","+Math.floor(200*(1-numChars/140))+",0)");
	});
	$('.compose-send').click(function(){
		console.log('pressed');
		shout();
	})
	$('.follow-button').click(function(){
		var button = $(this);
		button.toggleClass("btn-success");
		button.toggleClass("btn-default");
		if(button.hasClass("btn-success"))
			button.text("following");
		else
			button.text("follow");
		$.ajax({
			url:"/follow/"+button.attr("data-handle"),
			type:"GET",
			dataType:"json",
			success:function(){
				console.log('success');
			}
		});
	});

	$("#all-shouts").scroll(function() {
	 	if($("#all-shouts").attr("tapped")=="false")
	 		console.log(($("#all-shouts").scrollTop()+$("#panel-right").height()) +",  "+$('#all-shouts').height());
		 	if($("#all-shouts").scrollTop() >= $('#panel-right').height()-$("#all-shouts").height()) {
				$.ajax({
					url:"/"+$('#all-shouts').attr('src')+"?maxId="+$('#all-shouts').attr("maxId"),
					type:"GET",
					dataType:"json",
					cache:"false",
					success:function(tweets, status){
						console.log(tweets)
						if(tweets.length == 0){
							$('#all-shouts').attr("tapped", "true");
							if($('#all-shouts').children(".none").length==0)
								$('#all-shouts').append($('<div class="shout-container none"><div class="shout"><h1 style="text-align:center">No more Shouts</h1></div></div>'));
						}else{
							for (var i = 0; i < tweets.length; i++) {
								$('#all-shouts').append(newShout(tweets[i].user, tweets[i]));
								$('#all-shouts').attr("maxId", tweets[i].id );
							};
						}
					}
				});
			}
	});
});

var newShout = function(user, tweet){
	var html = tweet.content+"";
	html = html.split(' ');
	var full = "";
	for (var i = 0; i < html.length; i++) {
		if(html[i].indexOf('#') == 0)
			html[i] = '<a href="/search/'+html[i].substring(1)+'">'+html[i]+'</a>';
		full += html[i] + " ";
	};

	var time = moment(tweet.timestamp).fromNow();
	
	return $('<div class="shout-container"><div class="shout"><div class="shout-avatar"><img src="/assets/images/icon.png"></div><div class="shout-data"><div class="shout-info"><a class="shout-names handle" data-handle="'+user.handle+'" href="#"><strong>'+user.fullName+'</strong><span>@'+user.handle+'</span></a><a class="shout-time" href="#">'+time+'</a><div class="clearfix"></div></div><div class="shout-content" tweet-id="'+tweet.id+'"></div>'+full+'</div><div class="clearfix"></div><div class="shout-options"><div class="btn">  <span class="glyphicon glyphicon-star"></span> Favorite</div><div class="btn">  <span class="glyphicon glyphicon-retweet"></span> Reshout</div><div class="btn">  <span class="glyphicon glyphicon-share-alt"></span> Reply</div></div><div class="clearfix"></div></div></div>');
}
var shout = function(){
	$.ajax({
		url:"/compose",
		type:"POST",
		data:{
			'content':$('.compose').text(),
			'handle':$('user').attr("handle")
		}, 
		success:function(session, status){
			console.log("weeee");
			$(".compose").attr("clicked", "false");
			$(".compose").addClass("unclicked");
			$(".compose").html("");
		}
	});
}