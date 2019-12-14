package App.Classes.Exceptions;

import java.util.Arrays;

public class RequestHasNoRequiredParametersException extends RuntimeException {
    public RequestHasNoRequiredParametersException(String... parameters) {
        super("Could not find required parameters: " + String.join(", ", parameters));
    }
}

