package br.edu.atitus.apisample.controllers;

import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.atitus.apisample.dtos.SingupDTO;
import br.edu.atitus.apisample.entities.TypeUser;
import br.edu.atitus.apisample.entities.UserEntity;
import br.edu.atitus.apisample.services.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	// a classe AuthController possui uma dependencia de "UserService"
	private final UserService service;
	
	// Injeção de dependencia via método construtor
	public AuthController(UserService service) {
		super();
		this.service = service;
	}

	@PostMapping("/singup")
	public ResponseEntity<UserEntity> createNewUser(@RequestBody SingupDTO singup) throws Exception {
		
		// converter DTO2Entity
		UserEntity newUser = new UserEntity();
		BeanUtils.copyProperties(singup, newUser);
		newUser.setType(TypeUser.Common);
		
		// invocar método camada service
		service.save(newUser);
		
		// retornar entidade User
		return ResponseEntity.ok(newUser);
	}	
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handlerMethod(Exception ex) {
		String msg = ex.getMessage().replaceAll("\r\n", "");
		return ResponseEntity.badRequest().body(msg);
	}
}
