package App.Classes.Advices;

import App.Classes.Exceptions.RequestHasNoRequiredParametersException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class RequestHasNoRequiredParametersAdvice {
    @ResponseBody
    @ExceptionHandler(RequestHasNoRequiredParametersException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String requestHasNoRequiredParametersHandler(RequestHasNoRequiredParametersException e) {
        return e.getMessage();
    }
}
