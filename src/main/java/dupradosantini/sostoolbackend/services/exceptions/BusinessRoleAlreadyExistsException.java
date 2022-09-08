package dupradosantini.sostoolbackend.services.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class BusinessRoleAlreadyExistsException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public BusinessRoleAlreadyExistsException(String message) {
        super(message);
    }
}
