package demo.nasnav.task.payload.request;

import java.util.Set;
import javax.validation.constraints.*;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpRequest {
    @NotBlank
    @Size(min = 2, max = 50)
    private String username;
 
    @NotBlank
    @Size(min= 12,max = 50)
    @Email
    private String email;
    
    
    private Set<String> role;
    
    @NotBlank
    @Size(min = 6, max = 40)
    private String password;
  
 
}
