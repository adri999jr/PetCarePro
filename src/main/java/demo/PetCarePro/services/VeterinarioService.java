package demo.PetCarePro.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import demo.PetCarePro.persistence.entities.Veterinario;
import demo.PetCarePro.persistence.repositories.VeterinarioRepository;
import demo.PetCarePro.persistence.security.JwtUtils;
import demo.PetCarePro.services.dto.LoginRequestDTO;
import demo.PetCarePro.services.dto.LoginResponseDTO;
@Service
public class VeterinarioService {
	@Autowired
    private VeterinarioRepository veterinarioRepository;
	@Autowired
	private JwtUtils jwtUtils;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    // Devuelve la lista de todos los veterinarios
    public List<Veterinario> findAll() {
        return this.veterinarioRepository.findAll();
    }
    
    // Verifica si existe un veterinario con el id proporcionado
    public boolean existsVeterinario(int idVeterinario) {
        return this.veterinarioRepository.existsById(idVeterinario);
    }
    
   
    
    // Devuelve la entidad Veterinario (se asume que el id existe, de lo contrario se lanzará una excepción)
    public Veterinario findById(int idVeterinario) {
        return this.veterinarioRepository.findById(idVeterinario).get();
    }
    
    // Crea un nuevo Veterinario
    public Veterinario registerVeterinario(Veterinario veterinario) {
        veterinario.setPassword(passwordEncoder.encode(veterinario.getPassword()));
        veterinario.setRole("VETERINARIO");
        veterinario.setValidado(false);
        return veterinarioRepository.save(veterinario);
    }
    
    // Actualiza o guarda un Veterinario
    public Veterinario save(Veterinario veterinario) {
        return this.veterinarioRepository.save(veterinario);
    }
    
    // Elimina un Veterinario por su id
    public boolean delete(int idVeterinario) {
        boolean result = false;
        if (this.veterinarioRepository.existsById(idVeterinario)) {
            this.veterinarioRepository.deleteById(idVeterinario);
            result = true;
        }
        return result;
    }
    public LoginResponseDTO login(LoginRequestDTO request) {
        Optional<Veterinario> vetOpt = veterinarioRepository.findByUsername(request.getUsername());
        
        if (vetOpt.isEmpty()) {
            throw new RuntimeException("Veterinario no encontrado");
        }

        Veterinario vet = vetOpt.get();

        if (!passwordEncoder.matches(request.getPassword(), vet.getPassword())) {
            throw new RuntimeException("Contraseña incorrecta");
        }

        if (!vet.isValidado()) {
            throw new RuntimeException("Cuenta no validada");
        }

        String token = jwtUtils.generateJwtToken(vet);

        return new LoginResponseDTO(vet.getId(), vet.getUsername(), vet.getRole(), token);
    }

    public Optional<Veterinario> getVeterinarioByUsername(String username) {
        return veterinarioRepository.findByUsername(username);
    }
    
    public boolean validarVeterinario(String username) {
        Optional<Veterinario> optionalVeterinario = veterinarioRepository.findByUsername(username);
        if (optionalVeterinario.isPresent()) {
            Veterinario veterinario = optionalVeterinario.get();
            veterinario.setValidado(true);
            veterinarioRepository.save(veterinario);
            return true;
        }
        return false;
    }

    public List<Veterinario> findNoValidados() {
        return veterinarioRepository.findByValidadoFalse();
    }
    
    public List<Veterinario> obtenerTodosSinAdmins() {
        return veterinarioRepository.findByRoleNot("ADMIN");
    }



}
