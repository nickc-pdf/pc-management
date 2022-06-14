package ch.bzz.pcmanagement.service;

import ch.bzz.pcmanagement.data.DataHandler;
import ch.bzz.pcmanagement.model.Component;
import ch.bzz.pcmanagement.util.ReleaseDate;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * reads a list of all Components
 * @return Components as JSON
 */
@Path("component")
public class ComponentService {
    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listComponents() {
        List<Component> componentList = DataHandler.readAllComponents();
        return Response
                .status(200)
                .entity(componentList)
                .build();
    }

    /**
     * reads a specific Component of all Components
     * @param componentID
     * @return Component as JSON
     */
    @GET
    @Path("read")
    @Produces(MediaType.APPLICATION_JSON)
    public Response readComponent(
            @NotNull
            @QueryParam("id") int componentID
    ) {
        int httpStatus;
        Component component = DataHandler.readComponentID(componentID);
        if (component == null){
            httpStatus= 404;
        } else {
            httpStatus = 200;
        }
        return Response
                .status(httpStatus)
                .entity(component)
                .build();
    }


    @DELETE
    @Path("delete")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteComponent(
            @NotNull
            @QueryParam("id") int componentID
    ) {
        int httpStatus = 200;
        if(!DataHandler.deleteComponent(componentID)){
            httpStatus = 410;
        }
        return Response
                .status(httpStatus)
                .entity("")
                .build();
    }

    @POST
    @Path("create")
    @Produces(MediaType.TEXT_PLAIN)
    public Response insertComponent(
            @Valid @BeanParam Component component,
            @FormParam("releaseDate")@ReleaseDate(value = 1980) String releaseDate
    ) {
        component.setId(DataHandler.getComponentId());
        component.setReleaseDate(releaseDate);
        DataHandler.insertComponent(component);
        int httpStatus = 200;
        return Response
                .status(httpStatus)
                .entity("")
                .build();
    }

    @PUT
    @Path("update")
    @Produces(MediaType.TEXT_PLAIN)
    public  Response updateComponent(
           @Valid @BeanParam Component component,
           @FormParam("releaseDate")@ReleaseDate(value = 1980) String releaseDate
    ) {
        int httpStatus = 200;
        Component oldComponent = DataHandler.readComponentID(component.getId());
        if(oldComponent != null){
            oldComponent.setName(component.getName());
            oldComponent.setDescription(component.getDescription());
            oldComponent.setGeneration(component.getGeneration());
            oldComponent.setReleaseDate(releaseDate);

            DataHandler.updateComponent();
        } else {
            httpStatus = 410;
        }
        return Response
                .status(httpStatus)
                .entity("")
                .build();
    }
}
