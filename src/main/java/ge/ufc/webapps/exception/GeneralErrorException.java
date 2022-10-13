package ge.ufc.webapps.exception;

import javax.xml.ws.WebFault;

@WebFault
public class GeneralErrorException extends Exception {

    private static final long serialVersionUID = 1L;

    public GeneralErrorException() {
        super("General Error");
    }

    public GeneralErrorException(String msg) {
        super(msg);
    }
}
