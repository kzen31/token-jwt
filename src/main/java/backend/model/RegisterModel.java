package backend.model;

import lombok.Data;
import org.hibernate.validator.constraints.Email;

@Data
public class RegisterModel {

    @Email(message = "Email not valid")
    public String username;

    public String fullname;

    public String password;

}