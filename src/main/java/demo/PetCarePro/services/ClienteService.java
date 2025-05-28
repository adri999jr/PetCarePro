package demo.PetCarePro.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import demo.PetCarePro.persistence.entities.Cliente;
import demo.PetCarePro.persistence.entities.Veterinario;
import demo.PetCarePro.persistence.repositories.ClienteRepository;
import demo.PetCarePro.persistence.security.JwtUtils;
import demo.PetCarePro.services.dto.ClienteDTO;
import demo.PetCarePro.services.dto.LoginRequestDTO;
import demo.PetCarePro.services.dto.LoginResponseDTO;
import demo.PetCarePro.services.mapper.ClienteMapper;
@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private JwtUtils jwtUtils;
    @Autowired
    private PasswordEncoder passwordEncoder;
	//CRUDs
	public List<Cliente> findAll(){
		return this.clienteRepository.findAll();
	}
	
	public boolean existsCliente(int idCliente){
		return this.clienteRepository.existsById(idCliente);
	}
	
	public Optional<Cliente> findEntityById(int idCliente){
		return this.clienteRepository.findById(idCliente);
	}
	
	public ClienteDTO findById(int idCliente){
		return ClienteMapper.toDTO(this.clienteRepository.findById(idCliente).get());
	}
	
	public Cliente registerCliente(Cliente cliente) {
	    cliente.setPassword(passwordEncoder.encode(cliente.getPassword()));
	    cliente.setRole("CLIENTE");
	    return clienteRepository.save(cliente);
	}
	
	public Cliente save(Cliente cliente) {
		return this.clienteRepository.save(cliente);
	}
	
	public boolean delete(int idCliente) {
		boolean result = false;
		
		if(this.clienteRepository.existsById(idCliente)) {
			this.clienteRepository.deleteById(idCliente);
			result = true;
		}
		
		return result;
	}
	public LoginResponseDTO login(LoginRequestDTO request) {
	    Cliente cliente = clienteRepository.findByUsername(request.getUsername());
	    if (cliente == null) {
	        throw new RuntimeException("Cliente no encontrado");
	    }

	    if (!passwordEncoder.matches(request.getPassword(), cliente.getPassword())) {
	        throw new RuntimeException("Contraseña incorrecta");
	    }

	    String token = jwtUtils.generateJwtTokenFromCliente(cliente);

	    return new LoginResponseDTO(cliente.getId(), cliente.getUsername(), cliente.getRole(), token);
	}
}


	
	
