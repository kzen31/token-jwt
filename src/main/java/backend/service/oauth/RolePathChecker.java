package backend.service.oauth;

import backend.entity.RolePath;
import backend.repository.RolePathRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class RolePathChecker {

    private final RolePathRepository rolePathRepository;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public RolePathChecker(RolePathRepository rolePathRepository) {
        this.rolePathRepository = rolePathRepository;
    }

    public <T extends UserDetails> boolean isAllow(T user, String xUri, String method) {
        if (StringUtils.isEmpty(xUri)) {
            return false;
        }
        List<RolePath> rolePaths = rolePathRepository.findByUser(user);
        if (rolePaths.size() < 1) {
            return false;
        }
        boolean allowed = false;
        try {
            for (RolePath path : rolePaths) {
                logger.info("Checking regex: {} {} with string: {} {}", path.getMethod(), path.getPattern(), method, xUri);
                if (xUri.matches(path.getPattern()) && path.getMethod().equalsIgnoreCase(method)) {
                    allowed = true;
                    break;
                }
            }
        } catch (Exception e) {
            logger.warn("Error: {}", e.getMessage());
        }
        return allowed;
    }

}
