package br.edu.atitus.apisample.services;

import java.util.List;

import org.springframework.stereotype.Service;

import br.edu.atitus.apisample.entities.UserEntity;
import br.edu.atitus.apisample.repositories.UserRepository;

@Service
public class UserService {
	
	// essa classe possui uma dependencia de um objeto UserRepository
	private final UserRepository repository;
	// no método construtor existe a injeção de dependencia
	public UserService(UserRepository repository) {
		super();
		this.repository = repository;
	}

	public UserEntity save(UserEntity newUser) throws Exception {
		
		// validar regras de negócio
		if(newUser == null)
			throw new Exception("Objeto nulo!");
		
		if(newUser.getName() == null || newUser.getName().isEmpty())
			throw new Exception("Nome inválido!");
		newUser.setName(newUser.getName().trim()); // trim corta espaços da frente e do final
		
		if(newUser.getEmail() == null || newUser.getEmail().isEmpty())
			throw new Exception("Nome inválido!");
		// desafio: fazer validação do email com regex (opcional)
		newUser.setEmail(newUser.getEmail().trim());
		
		if (repository.existsByEmail(newUser.getEmail()))
			throw new Exception("Já existe um usuário com este e-mail");
		
		// invocar método camada repository
		this.repository.save(newUser);
		
		return newUser;
	}
	
	public List<UserEntity> findAll() throws Exception {
		return repository.findAll();
	}
}
