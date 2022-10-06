package ge.ufc.webapps;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.xml.bind.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HomeWork implements Service {
    private static final Logger log = LogManager.getLogger(HomeWork.class);
    private final Map<Integer, Person> hashMap = new HashMap<>();
    private Persons persons;


    public HomeWork() {
        try {
            FileConf.getFileConf();
            JAXBContext jaxbContext = JAXBContext.newInstance(Persons.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            persons = (Persons) unmarshaller.unmarshal(new File(FileConf.getDatabase()));
            persons.personList.forEach(p -> hashMap.put(p.id, p));
        } catch (IOException | JAXBException ignored) {

        }
    }


    @Override
    public Response getPerson(int id, String username, String password, @Context HttpServletRequest request) {
        if (FileConf.getDatabase().equals("") || FileConf.getPassword().equals("") || FileConf.getUsername().equals("")) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        if (!isValidIp(request.getRemoteAddr())) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        if (!FileConf.getPassword().equals(password) || !username.equals(FileConf.getUsername())) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        Person person = hashMap.get(id);
        if (person == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        log.trace(persons);
        return Response.status(Response.Status.OK).entity(person).build();
    }

    @Override
    public Response addPerson(Person person, String username, String password, @Context HttpServletRequest request) {
        if (FileConf.getDatabase().equals("") || FileConf.getPassword().equals("") || FileConf.getUsername().equals("")) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        if (!isValidIp(request.getRemoteAddr())) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        if (!FileConf.getPassword().equals(password) || !username.equals(FileConf.getUsername())) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        if (hashMap.get(person.id) != null) {
            return Response.status(Response.Status.CONFLICT).build();
        }

        if (person.firstname == null || person.lastname == null || person.age == 0 || person.id == 0) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        hashMap.put(person.id, person);
        persons.setPersonList(new ArrayList<>(hashMap.values()));
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Persons.class);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(persons, new File(FileConf.getDatabase()));
        } catch (JAXBException e) {
            log.error(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        log.trace(person);
        log.trace(persons);
        return Response.status(Response.Status.OK).build();
    }

    @Override
    public Response updatePerson(Person person, String username, String password, @Context HttpServletRequest request) {
        if (FileConf.getDatabase().equals("") || FileConf.getPassword().equals("") || FileConf.getUsername().equals("")) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        if (!isValidIp(request.getRemoteAddr())) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        if (!FileConf.getPassword().equals(password) || !username.equals(FileConf.getUsername())) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        if (hashMap.get(person.id) == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        if (person.firstname == null || person.lastname == null || person.age == 0 || person.id == 0) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }

        hashMap.put(person.id, person);
        persons.setPersonList(new ArrayList<>(hashMap.values()));
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Persons.class);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(persons, new File(FileConf.getDatabase()));
        } catch (JAXBException e) {
            log.error(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        log.trace(person);
        log.trace(persons);
        return Response.status(Response.Status.OK).build();
    }

    @Override
    public Response deletePerson(int id, String username, String password, @Context HttpServletRequest request) {
        if (FileConf.getDatabase().equals("") || FileConf.getPassword().equals("") || FileConf.getUsername().equals("")) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        if (!isValidIp(request.getRemoteAddr())) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        if (!FileConf.getPassword().equals(password) || !username.equals(FileConf.getUsername())) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        if (hashMap.get(id) == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        Person person = hashMap.remove(id);
        persons.setPersonList(new ArrayList<>(hashMap.values()));
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Persons.class);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(persons, new File(FileConf.getDatabase()));
        } catch (JAXBException e) {
            log.error(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }

        log.trace(person);
        log.trace(persons);
        return Response.status(Response.Status.OK).build();
    }

    @Override
    public Response listPersons(String username, String password, @Context HttpServletRequest request) {
        if (FileConf.getDatabase().equals("") || FileConf.getPassword().equals("") || FileConf.getUsername().equals("")) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        if (!isValidIp(request.getRemoteAddr())) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        if (!FileConf.getPassword().equals(password) || !username.equals(FileConf.getUsername())) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        log.trace(persons);
        return Response.status(Response.Status.OK).entity(persons).build();
    }


    private boolean isValidIp(String addr) {
        String[] api = FileConf.getApi();
        boolean isThere = false;
        for (int i = 0; i < api.length; i++) {
            if (addr.equals(api[i])) {
                isThere = true;
                break;
            }
        }
        return isThere;
    }
}