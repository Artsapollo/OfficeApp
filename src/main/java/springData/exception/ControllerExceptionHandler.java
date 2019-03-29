package springData.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;

import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import springData.dto.ErrorMessage;

import javax.annotation.PostConstruct;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ControllerExceptionHandler {

    private static final Logger LOG = LogManager.getLogger(ExceptionHandler.class);
    private static final Logger LOGMAIL = LogManager.getLogger("error-logger");

    private Map<String, String> validationCodeDescription = new HashMap<>();

    @ExceptionHandler(value = {Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public @ResponseBody
    ErrorMessage unexpected(Exception e) {
        LOGMAIL.error("Unexpected exception {}", e.getMessage());
        return new ErrorMessage(e.getMessage());
    }

    @ExceptionHandler(value = {DeleteException.class, UpdateException.class})
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public @ResponseBody
    ErrorMessage entityExistingProblem(Exception e) {
        LOG.warn("Unprocessable entity {}", e.getMessage());
        return new ErrorMessage(e.getMessage());
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody ErrorMessage validationProblem(MethodArgumentNotValidException e){
        LOGMAIL.warn("Request validation problem {}", e.getMessage());
        FieldError fe = e.getBindingResult().getFieldError();
        return new ErrorMessage(validationCodeDescription.get(fe.getDefaultMessage()));
    }

    @PostConstruct
    private void intValidationCodeDescription(){
        validationCodeDescription.put("1", "Office number must be between 100 and 200");
        validationCodeDescription.put("2", "We work only in 10 letters cities");
        validationCodeDescription.put("3", "Only 'S' letter regions required");
        validationCodeDescription.put("4", "Monthly sales min 10000 max 50000");
        validationCodeDescription.put("5", "Monthly target min 10000 max 50000");
        validationCodeDescription.put("6", "Can not be null!");

    }

}