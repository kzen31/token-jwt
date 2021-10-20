package backend.config.responseException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "Forbidden")
public class Forbidden extends RuntimeException {

    public Forbidden(String message) {
        super(message, null);
    }

}

