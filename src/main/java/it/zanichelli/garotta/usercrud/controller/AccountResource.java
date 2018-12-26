package it.zanichelli.garotta.usercrud.controller;

import java.sql.SQLException;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.apache.log4j.Logger;

import it.zanichelli.garotta.usercrud.dao.AccountDao;
import it.zanichelli.garotta.usercrud.exception.AccountException;
import it.zanichelli.garotta.usercrud.model.Response;
import it.zanichelli.garotta.usercrud.reponse.MessageResponse;

@Path("/account")
public class AccountResource {

	static final Logger logger = Logger.getLogger(AccountResource.class);
	private AccountDao accountDao = null;
	 
	//match username
	private static final String usernameRegex = "^[a-zA-Z0-9_-]{3,16}$"; 
	//match password
	private static final String passwordRegex = "^[a-zA-Z0-9_-]{6,18}$";
	//match ruolo
	private static final String ruoloRegex = "^[a-zA-Z0-9_-]{4,10}$";


	public AccountDao getAccountDao() {
		if (accountDao == null)
			accountDao = new AccountDao();
		return accountDao;
	}

	@GET
	@Path("listAll")
	@Produces("application/json")
	public Response getAll() {
		Response response = new Response();
		try {
			accountDao = getAccountDao();
			response.setMessage(MessageResponse.OK.getMessage());
			response.setCode(MessageResponse.OK.getCode());

			response.setListAccount(accountDao.listAll());

		} catch (SQLException e) {
			response.setMessage(MessageResponse.KO_GET_LISTA_ACCOUNT.getMessage());
			response.setCode(MessageResponse.KO_GET_LISTA_ACCOUNT.getCode());
			response.setErrorType(e.getMessage());
		}
		return response;
	}
	
	@GET
	@Path("allUsername")
	@Produces("application/json")
	public Response getAllUsername() {
		Response response = new Response();
		try {
			accountDao = getAccountDao();
			response.setMessage(MessageResponse.OK.getMessage());
			response.setCode(MessageResponse.OK.getCode());

			response.setListUsername(accountDao.getAllUsername());

		} catch (SQLException e) {
			response.setMessage(MessageResponse.KO_GET_LISTA_USERNAME.getMessage());
			response.setCode(MessageResponse.KO_GET_LISTA_USERNAME.getCode());
			response.setErrorType(e.getMessage());
		}
		return response;
	}

	@GET
	@Path("getByUsername/{username}")
	@Produces("application/json")
	public Response getByUsername(@PathParam("username") String username) {
		Response response = new Response();
		try {
			accountDao = getAccountDao();
			response.setMessage(MessageResponse.OK.getMessage());
			response.setCode(MessageResponse.OK.getCode());
			response.setAccount(accountDao.getByUsername(username));
		} catch (SQLException e) {
			response.setMessage(MessageResponse.KO_GET_ACCOUNT.getMessage());
			response.setCode(MessageResponse.KO_GET_ACCOUNT.getCode());
			response.setErrorType(e.getMessage());
		}
		return response;
	}

	@POST
	@Path("delete")
	@Produces("application/json")

	public Response deleteByUsername(@FormParam("username") String username) throws AccountException {
		Response response = new Response();
		try {
			accountDao = getAccountDao();
			if (accountDao.delete(username)) {
				response.setMessage(MessageResponse.OK.getMessage());
				response.setCode(MessageResponse.OK.getCode());
			} else {
				response.setMessage(MessageResponse.KO_DELETE.getMessage());
				response.setCode(MessageResponse.KO_DELETE.getCode());
				response.setErrorType("non esiste una tupla con username="+username);
			}
		} catch (SQLException e) {
			response.setMessage(MessageResponse.KO_DELETE.getMessage());
			response.setCode(MessageResponse.KO_DELETE.getCode());
			response.setErrorType(e.getMessage());
		}
		return response;
	}
	
	private boolean validateString(String objToValidate, String regex){
		    return objToValidate.matches(regex);
	    
	}

	@POST
	@Path("insert")
	@Produces("application/json")

	public Response insert(@FormParam("username") String username, 
			@FormParam("password") String password,
			@FormParam("ruolo") String ruolo) throws AccountException {
		Response response = new Response();
		try {
			accountDao = getAccountDao();
			if (validateString(username,usernameRegex) &&
					validateString(password,passwordRegex) &&
					validateString(ruolo,ruoloRegex))
			{
				if (accountDao.insert(username, password, ruolo)) {
					response.setMessage(MessageResponse.OK.getMessage());
					response.setCode(MessageResponse.OK.getCode());
				} else {
					response.setMessage(MessageResponse.KO_INSERT.getMessage());
					response.setCode(MessageResponse.KO_INSERT.getCode());
				}
			}	
			else{
				response.setMessage(MessageResponse.KO_INSERT.getMessage());
				response.setCode(MessageResponse.KO_INSERT.getCode());
				response.setErrorType("i parametri in ingresso non sono corretti");
			}
				

		} catch (SQLException e) {
			response.setMessage(MessageResponse.KO_INSERT.getMessage());
			response.setCode(MessageResponse.KO_INSERT.getCode());
			response.setErrorType(e.getMessage());
		}
		return response;
	}

	@POST
	@Path("update")
	@Produces("application/json")

	public Response update(@FormParam("username") String username, 
			@FormParam("password") String password,
			@FormParam("ruolo") String ruolo) throws AccountException {
		Response response = new Response();
		try {
			accountDao = getAccountDao();
			if (validateString(username,usernameRegex) &&
					validateString(password,passwordRegex) &&
					validateString(ruolo,ruoloRegex))
			{
				if (accountDao.update(username, password, ruolo)) {
				
					response.setMessage(MessageResponse.OK.getMessage());
					response.setCode(MessageResponse.OK.getCode());
				} else {
					response.setMessage(MessageResponse.KO_UPDATE.getMessage());
					response.setCode(MessageResponse.KO_UPDATE.getCode());
					response.setErrorType("non esiste una tupla con username="+username);
				}
			}
			else
			{
				response.setMessage(MessageResponse.KO_UPDATE.getMessage());
				response.setCode(MessageResponse.KO_UPDATE.getCode());
				response.setErrorType("i parametri in ingresso non sono corretti");
				
			}
		} catch (SQLException e) {
			response.setMessage(MessageResponse.KO_UPDATE.getMessage());
			response.setCode(MessageResponse.KO_UPDATE.getCode());
		}
		return response;
	}
}