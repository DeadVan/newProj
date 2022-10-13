package ge.ufc.webapps;

import ge.ufc.webapps.exception.AccessForbiddenException;
import ge.ufc.webapps.exception.PersonAlreadyExistsException;
import ge.ufc.webapps.exception.PersonNotFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/homeWork")
public interface Service
{
    @GET
    @Path("/get")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    Response getPerson(@QueryParam("id") int id, @HeaderParam("username") String username, @HeaderParam("password") String password,@Context HttpServletRequest request) throws AccessForbiddenException;

    @POST
    @Path("/add")
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    Response addPerson(Person person,@HeaderParam("username") String username, @HeaderParam("password") String password,@Context HttpServletRequest request) throws AccessForbiddenException, PersonAlreadyExistsException, PersonNotFoundException;

    @PUT
    @Path("/update")
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    Response updatePerson(Person person,@HeaderParam("username") String username, @HeaderParam("password") String password,@Context HttpServletRequest request) throws AccessForbiddenException, PersonNotFoundException;

    @DELETE
    @Path("/delete/{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    Response deletePerson(@PathParam("id") int id,@HeaderParam("username") String username, @HeaderParam("password") String password,@Context HttpServletRequest request) throws AccessForbiddenException;

    @GET
    @Path("/list")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    Response listPersons(@HeaderParam("username") String username, @HeaderParam("password") String password,@Context HttpServletRequest request) throws AccessForbiddenException;
}