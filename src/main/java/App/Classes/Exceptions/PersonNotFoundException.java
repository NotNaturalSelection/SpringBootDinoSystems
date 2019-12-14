package App.Classes.Exceptions;

public class PersonNotFoundException extends RuntimeException {
    public PersonNotFoundException(int id) {
        super("Could not find person " + id);
    }
}
