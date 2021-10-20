package backend.controller;

import backend.config.responseException.Forbidden;
import backend.model.RegisterModel;
import backend.service.RegisterService;
import backend.service.oauth.Oauth2UserDetailsService;
import backend.service.oauth.RolePathChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@RestController
public class MainController {

    private final Oauth2UserDetailsService oauth2UserDetailsService;

    private final RolePathChecker rolePathChecker;

    private final RegisterService registerService;

    @Autowired
    public MainController(Oauth2UserDetailsService oauth2UserDetailsService,
                          RolePathChecker rolePathChecker,
                          RegisterService registerService) {
        this.oauth2UserDetailsService = oauth2UserDetailsService;
        this.rolePathChecker = rolePathChecker;
        this.registerService = registerService;
    }

    @RequestMapping("/")
    public Map<String, String> indexAction() {
        Map<String, String> response = new HashMap<>();
        response.put("success", "true");
        response.put("message", "latihan-khuzain-cloud-heroku");
        return response;
    }

    @RequestMapping("/oauth/authenticate")
    public Map<String, Object> authenticateAction(
            @RequestParam Map<String, String> query,
            HttpServletResponse response,
            HttpServletRequest request,
            Principal principal) throws RuntimeException {
        String username = principal.getName();
        UserDetails user = null;

        String xUri = request.getHeader("X-Uri");
        if (StringUtils.isEmpty(xUri) && query.containsKey("uri")) {
            xUri = query.get("uri");
        }
        if (!StringUtils.isEmpty(username)) {
            user = oauth2UserDetailsService.loadUserByUsername(username);
        }
        if (null == user) {
            throw new UsernameNotFoundException("User not found");
        }
//        if (!rolePathChecker.isAllow(user, xUri, request.getMethod())) {
//            throw new Forbidden("Not enough access to this endpoint");
//        }
        response.addHeader("X-User", user.getUsername());
        Map<String, Object> userFound = new HashMap<>();
        userFound.put("data", user);
        return userFound;
    }

    @PostMapping("/register")
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map> save(@Valid @RequestBody RegisterModel registerModel) throws RuntimeException {
        Map map = new HashMap();
        map = registerService.registerManual(registerModel);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

}

