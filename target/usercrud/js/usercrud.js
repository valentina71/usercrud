$( function() {
    $( "#tabs" ).tabs();

	//match username
	var usernameRegex = new RegExp('^[a-zA-Z0-9_-]{3,16}$'); 
	//match password
	var passwordRegex = new RegExp('^[a-zA-Z0-9_-]{6,18}$');
	//match ruolo
	var ruoloRegex = new RegExp('^[a-zA-Z0-9_-]{4,10}$');

	function caricaListAll(){
		$.getJSON( "/usercrud/rest/account/listAll", function( data ) {
				$('#result-veditutti').html("");
				if (data.code=="OK")
				{
					if(data.listAccount!=null)
					{
						$('#result-veditutti').append("<tr><td><b><h3>USERNAME</h3></b></td><td><b><h3>PASSWORD</h3></b></td><td><b><h3>RUOLO</h3></b></td>");
						var listAccount = data.listAccount.accounts;
						if (listAccount.username!=undefined)
						{
							console.log(listAccount);$('#result-veditutti').append("<tr><td>"+listAccount.username+"</td><td>"+listAccount.password+"</td><td>"+listAccount.ruolo+"</td>");
						}
						else 
							$.each(listAccount, function (index, value) {
								$('#result-veditutti').append("<tr><td>"+value.username+"</td><td>"+value.password+"</td><td>"+value.ruolo+"</td>");
								console.log(value);
							});
					}	 
					else
						$('#result-veditutti').html("non ci sono utenti");
				}
				else
					$('#result-veditutti').html(data.errorType);
		});
	}	

	caricaListAll();
	
	
	$("#veditutti").click(function() {
		caricaListAll();
	});
  
  
	$("#ricercautente").click(function() {
		$('#result-ricercautente').html(" ");
		$.getJSON( "/usercrud/rest/account/allUsername", function( data ) {
			if (data.code=="OK")
			{
				$('#ricercautente_username').html("<option value='scegli'>Scegli uno username</option>");
				if (data.listUsername!=null){
					var listAccount = data.listUsername.usernames;
					try{
						$.each(listAccount, function (index, riga) {
							$('#ricercautente_username').append("<option value='"+riga+"'>"+riga+"</option>");
						});
					}catch(e){
						$('#ricercautente_username').append("<option value='"+listAccount+"'>"+listAccount+"</option>");
					}
				}
				else
					 $('#result-ricercautente').html("non ci sono utenti");
					
            }  
			else
				$('#result-ricercautente').html(data.errorType);
		});
	});
  
	
	$("#cancellautente").click(function() {
		$('#result-cancellautente').html(" ");
		$.getJSON( "/usercrud/rest/account/allUsername", function( data ) {
			if (data.code=="OK")
			{
				$('#cancellautente_username').html("<option value='scegli'>Scegli uno username</option>");
				if (data.listUsername!=null){
					var listAccount = data.listUsername.usernames;
					try{
						$.each(listAccount, function (index, riga) {
							$('#cancellautente_username').append("<option value='"+riga+"'>"+riga+"</option>");
						});
					}catch(e){
						$('#cancellautente_username').append("<option value='"+listAccount+"'>"+listAccount+"</option>");
					}
				}
				else
					$('#result-cancellautente').html("non ci sono utenti");
            }              
			else
				$('#result-cancellautente').html(data.errorType);
		});
	});
	
	
	$("#updateutente").click(function() {
		$('#result-updateutente').html(" ");
		$("#updateutente_username").val("");
		$("#updateutente_password").val("");
		$("#updateutente_ruolo").val("");		
		$("#updateutente_username").prop('disabled', false);
		$.getJSON( "/usercrud/rest/account/allUsername", function( data ) {
			if (data.code=="OK")
			{
				$('#updateutente_username').html("<option value='scegli'>Scegli uno username</option>");
				if (data.listUsername!=null){
					var listAccount = data.listUsername.usernames;
					try{
						$.each(listAccount, function (index, riga) {
							$('#updateutente_username').append("<option value='"+riga+"'>"+riga+"</option>");
						});
					}catch(e){
						$('#updateutente_username').append("<option value='"+listAccount+"'>"+listAccount+"</option>");
					}
				}
				else
					$('#result-cancellautente').html("non ci sono utenti");
            }              
			else
				$('#result-cancellautente').html(data.errorType);
		});
	});
  
	$("#ricercautente_but").click(function() {
		$('#result-ricercautente').html(" ");
		var username=$("#ricercautente_username").val();
		if (username!="scegli")
			$.getJSON( "/usercrud/rest/account/getByUsername/"+username, function( data ) {
				if (data.code=="OK")
				{
					var account = data.account;
					$('#result-ricercautente').html("<tr><td><b>Username:</b></td><td>"+account.username+"</td></tr><tr><td><b>Password</b>:</td><td>"+account.password+"</td></tr><tr><td><b>Ruolo</b>:</td><td>"+account.ruolo+"</td></tr>");
				}
				else
					$('#result-ricercautente').html(data.errorType);
			});
		else
			alert("devi scegliere un utente");
	});
	
	
	$("#annulla_updateutente_but").click(function() {
		$("#updateutente").trigger("click");
	});
	
	$("#insertutente").click(function(){
		$("#insertutente_username").val("");
		$("#insertutente_password").val("");
		$("#insertutente_ruolo").val("");
	});
	
	$("#annulla_insertutente_but").click(function() {
		$("#insertutente").trigger("click");
	});
	
	$("#selupdateutente_but").click(function() {
		var username=$("#updateutente_username").val();
		if (username!="scegli")
		{
			$("#updateutente_username").prop('disabled', true);
			$.getJSON( "/usercrud/rest/account/getByUsername/"+username, function( data ) {
				if (data.code=="OK")
				{
					var account = data.account;
					$("#updateutente_password").val(account.password);
					$("#updateutente_ruolo").val(account.ruolo);
				}
				else
					$('#result-ricercautente').html(data.errorType);
			});
		}
		else
			alert("devi scegliere un utente");
	});
	
	
	
	
	$("#cancellautente_but").click(function() {
		$('#result-cancellautente').html(" ");
		var username=$("#cancellautente_username").val();
		if (username!="scegli")
			$.post( "/usercrud/rest/account/delete/",{"username":username}, function( data ) {
				if (data.code=="OK")
					$('#result-cancellautente').html("Cancellazione avvenuta con successo");
				else
				    $('#result-cancellautente').html(data.errorType);
              
			},"json" );
		else
			alert("devi scegliere un utente");
	});
	
	
	$("#insertutente_but").click(function() {
		$('#result-insertutente').html(" ");
		var username=$("#insertutente_username").val();
		var password=$("#insertutente_password").val();
		var ruolo=$("#insertutente_ruolo").val();
		if (username!="" && password!="" && ruolo!=""
			&& usernameRegex.test(username) &&
			passwordRegex.test(password) &&
			ruoloRegex.test(ruolo))
		{
			$.post( "/usercrud/rest/account/insert/",{"username":username,"password":password,"ruolo":ruolo}, function( data ) {
				if (data.code=="OK")
					$('#result-insertutente').html("Inserimento utente avvenuta con successo");
				else
					$('#result-insertutente').html(data.errorType);
				$("#insertutente_username").val("");
				$("#insertutente_password").val("");
				$("#insertutente_ruolo").val("");				
			},"json" );
		}
		else
		{
			alert("Devi inserire username, password, ruolo. Segui i consigli.");
		}
	});
	
	$("#updateutente_but").click(function() {
		$('#result-updateutente').html(" ");
		var username=$("#updateutente_username").val();
		var password=$("#updateutente_password").val();
		var ruolo=$("#updateutente_ruolo").val();
		if (password!="" && ruolo!="" &&
			passwordRegex.test(password) &&
			ruoloRegex.test(ruolo))
		{
			$.post( "/usercrud/rest/account/update/",{"username":username,"password":password,"ruolo":ruolo}, function( data ) {
				if (data.code=="OK")
					$('#result-updateutente').html("Aggiornamento utente avvenuta con successo");
				else
					$('#result-updateutente').html(data.errorType);
				$("#updateutente_username").val("");
				$("#updateutente_password").val("");
				$("#updateutente_ruolo").val("");				
			},"json" );
			$("#updateutente_username").prop('disabled', false);
		}
		else
		{
			alert("puoi modificare password, ruolo. Segui i consigli");
		}
	});
 
  });
