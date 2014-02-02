	$(document).ready(function(){
			$('#sign-in').submit(function(e){
				var hasError = false;
				if($("#sign-in-handle").val().length <= 0){$($("#sign-in-handle").parent()).addClass("has-error"); hasError=true;}
				if($("#sign-in-password").val().length <= 0){$($("#sign-in-password").parent()).addClass("has-error"); hasError=true;}
				if(hasError) e.preventDefault();
				else return;
			});
			$('#sign-up').submit(function(e){
				var hasError = false;
				if($("#sign-up-handle").val().length <= 0){$($("#sign-up-handle").parent()).addClass("has-error"); hasError=true;}
				if($("#sign-up-name").val().length <= 0){$($("#sign-up-name").parent()).addClass("has-error"); hasError=true;}
				if($("#sign-up-email").val().length <= 0){$($("#sign-up-email").parent()).addClass("has-error"); hasError=true;}
				if($("#sign-up-password").val().length <= 0){$($("#sign-up-password").parent()).addClass("has-error"); hasError=true;}
				if($("#sign-up-password-confirm").val().length <= 0){$($("#sign-up-password-confirm").parent()).addClass("has-error"); hasError=true;}
				if($("#sign-up-password-confirm").val() != $("#sign-up-password").val()){$($("#sign-up-password-confirm").parent()).addClass("has-error"); hasError=true;}
				if(hasError) e.preventDefault();
				else return;
			});
			$('.form-control').focus(function(){
				$($(this).parent()).removeClass('has-error');
			});
		});