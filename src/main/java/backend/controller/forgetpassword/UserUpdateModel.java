package backend.controller.forgetpassword;

import lombok.Data;


@Data
public class UserUpdateModel {

    public Long id;

    public String email;

    public String otp;

    public String newPassword;

}
