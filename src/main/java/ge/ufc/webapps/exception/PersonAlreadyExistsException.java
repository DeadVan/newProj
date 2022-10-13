package ge.ufc.webapps.exception;

import javax.xml.ws.WebFault;

@WebFault
public class PersonAlreadyExistsException extends Exception {

    private static final long serialVersionUID = 1L;

    public PersonAlreadyExistsException() {
        super("person already exists");
    }

    public PersonAlreadyExistsException(String str) {
        super(str);
    }
}

