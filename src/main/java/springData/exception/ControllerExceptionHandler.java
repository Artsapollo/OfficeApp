package springData.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import springData.dto.ErrorMessage;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ControllerExceptionHandler {

    private static final Logger LOG = LogManager.getLogger(ControllerExceptionHandler.class);
    private static final Logger LOGMAIL = LogManager.getLogger("error-logger");

    private Map<String, String> validationCodeDescription = new HashMap<>();

    @ExceptionHandler(value = {Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public @ResponseBody
    ErrorMessage unexpected(Exception e) {
        LOG.error("Unexpected exception {}", e.getMessage());
        return new ErrorMessage(e.getMessage());
    }

    @ExceptionHandler(value = {DeleteException.class, UpdateException.class})
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public @ResponseBody
    ErrorMessage entityExistingProblem(Exception e) {
        LOG.warn("Unprocessable entity {}", e.getMessage());
        return new ErrorMessage(e.getMessage());
    }
}