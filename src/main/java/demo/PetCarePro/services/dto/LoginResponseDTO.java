package demo.PetCarePro.services.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter

public class LoginResponseDTO {

	private int id;
    private String username;
    private String role;
    private String token;
    
    public LoginResponseDTO(int id, String username, String role, String token) {
        this.id = id;
        this.username = username;
        this.role = role;
        this.token = token;
    }
}
