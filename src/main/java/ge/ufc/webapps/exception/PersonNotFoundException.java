package ge.ufc.webapps.exception;

import javax.xml.ws.WebFault;

@WebFault
public class PersonNotFoundException extends Exception {
    private static final long serialVersionUID = 1L;

    public PersonNotFoundException() {
        super("this person does not exist");
    }

    public PersonNotFoundException(String str) {
        super(str);
    }
}
