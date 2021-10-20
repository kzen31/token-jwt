package backend.config.responseException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BadRequest extends RuntimeException {

    public BadRequest(String message) {
        super(message, null);
    }

    public List<Map<String, Object>> getErrors() {
        List<Map<String, Object>> errors = new ArrayList<>();
        Map<String, Object> detail = new HashMap<>();
        detail.put("codes", new ArrayList<>());
        detail.put("default_message", getMessage());
        detail.put("arguments", new ArrayList<>());
        detail.put("object_name", null);
        detail.put("field", null);
        detail.put("rejected_value", null);
        detail.put("binding_failure", null);
        detail.put("code", null);
        errors.add(detail);
        return errors;
    }

}

