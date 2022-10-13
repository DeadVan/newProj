package ge.ufc.webapps.exception;

import javax.xml.ws.WebFault;

@WebFault
public class AccessForbiddenException extends Exception {

    public AccessForbiddenException() {
        super("Access forbidden");
    }

    public AccessForbiddenException(String msg) {
        super(msg);
    }
}
