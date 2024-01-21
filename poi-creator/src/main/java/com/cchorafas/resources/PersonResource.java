package com.cchorafas.resources;

import com.cchorafas.entities.Person;
import io.quarkus.hibernate.reactive.panache.Panache;
import io.quarkus.panache.common.Sort;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.jboss.resteasy.reactive.RestResponse;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.jboss.resteasy.reactive.RestResponse.Status.*;

@Path("/persons")
@ApplicationScoped
public class PersonResource {

    @Channel("add-person")
    Emitter<String> addRequestEmitter;

    @Channel("remove-person")
    Emitter<String> removeRequestEmitter;

    @Channel("google-calendar")
    Emitter<String> calendarEmitter;

    @GET
    public Uni<List<Person>> getPersons() {
        return Person.listAll(Sort.by("age"));
    }

    @GET
    @Path("/{id}")
    public Uni<Person> getPersonById(Long id) {
        return Person.findById(id);
    }

    @POST
    public Uni<RestResponse<Person>> create(@Valid Person person) {
        return Panache.withTransaction(person::persist)
                .replaceWith(RestResponse.status(CREATED, person))
                .invoke(personCreated -> {
                    System.out.println("Added person:\n" + person);
//                    addRequestEmitter.send(person.toString());
                    calendarEmitter.send(person.toString());
                });
    }

    @DELETE
    @Path("/{id}")
    public Uni<Response> delete(@PathParam("id") Long id) {
        return Panache.withTransaction(() -> Person.deleteById(id)
                        .map(deleted -> deleted
                                ? Response.ok().status(NO_CONTENT).build()
                                : Response.ok().status(NOT_FOUND).build()))
                .invoke(item -> {
                    System.out.println("Removed person with ID: " + id);
                    removeRequestEmitter.send(String.valueOf(id));
                });
    }
}
