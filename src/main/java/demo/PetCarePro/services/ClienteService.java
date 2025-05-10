package demo.PetCarePro.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.PetCarePro.persistence.entities.Cliente;
import demo.PetCarePro.persistence.repositories.ClienteRepository;
import demo.PetCarePro.services.dto.ClienteDTO;
import demo.PetCarePro.services.mapper.ClienteMapper;
@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
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
	
	public Cliente create(Cliente cliente) {
		return this.clienteRepository.save(cliente);
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
	
	

}