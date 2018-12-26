Se si vuole installare la webapp realizzata si devono:
•	Installare un tomcat6
•	Installare un mysql8
•	Effettuare il deploy del usercrud.war nella cartella webapp di tomcat, verra scompattata automaticamente dal tomcat. Per cambiare i puntamenti del datasource modificare i seguenti dati:

o	username
o	password
o	nome del database
o	eventualmente in caso di cambio tipologia di database, driverClassName e rilativa url di connessione al db.
presenti nel file context.xml che si trova nel path <path_installazione_tomcat6>/webapp/usercrud/META-INF/
