package backend.service.implement;

import backend.config.Config;
import backend.entity.Role;
import backend.entity.User;
import backend.model.RegisterModel;
import backend.repository.RoleRepository;
import backend.repository.UserRepository;
import backend.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class RegisterImplement implements RegisterService {

    private final RoleRepository roleRepository;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    Config config = new Config();

    @Autowired
    public RegisterImplement(RoleRepository roleRepository,
                             UserRepository userRepository,
                             PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;

    }

    @Override
    public Map registerManual(RegisterModel registerModel) {
        Map map = new HashMap();
        try {
            String[] roleNames = {"ROLE_USER", "ROLE_READ", "ROLE_WRITE"};
            String password = passwordEncoder.encode(registerModel.getPassword().replaceAll("\\s+", ""));
            List<Role> role = roleRepository.findByNameIn(roleNames);
            User valUser = userRepository.findOneByUsername(registerModel.getUsername());
            if (valUser != null) {
                map.put(config.getCode(), "403");
                map.put(config.getMessage(), "Username " + registerModel.getUsername() + " is exist");
                return map;
            }

            User user = new User();
            user.setUsername(registerModel.getUsername());
            user.setFullname(registerModel.getFullname());
            user.setPassword(registerModel.getPassword());
            user.setRoles(role);
            user.setPassword(password);

            User obj = userRepository.save(user);
            map.put("data", obj);
            map.put(config.getCode(), config.code_sukses);
            map.put(config.getMessage(), config.message_sukses);
            return map;
        } catch (Exception e) {
            map.put(config.getCode(), config.code_server);
            map.put(config.getMessage(), e.getLocalizedMessage());
            return map;
        }
    }

}
