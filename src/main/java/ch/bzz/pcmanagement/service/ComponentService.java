package ch.bzz.pcmanagement.service;

import ch.bzz.pcmanagement.data.DataHandler;
import ch.bzz.pcmanagement.model.Component;
import ch.bzz.pcmanagement.util.AESEncrypt;
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
    public Response listComponents(
            @CookieParam("userRole") String userRole
    ) {
        userRole = AESEncrypt.decrypt(userRole);
        int httpStatus = 200;
        List<Component> componentList = null;
        if(userRole == null || userRole.equals("guest")){
            httpStatus = 403;
        } else {
            componentList = DataHandler.readAllComponents();
        }
        return Response
                .status(httpStatus)
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
            @QueryParam("id") int componentID,
            @CookieParam("userRole") String userRole
    ) {
        userRole = AESEncrypt.decrypt(userRole);
        int httpStatus = 200;
        Component component = null;
        if(userRole == null || userRole.equals("guest")){
            httpStatus = 403;
        } else {
            component = DataHandler.readComponentID(componentID);
            if (component == null){
                httpStatus= 404;
            }
        }

        return Response
                .status(httpStatus)
                .entity(component)
                .build();
    }


    /**
     * deletes a component by its id
     * @param componentID
     * @return
     */
    @DELETE
    @Path("delete")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteComponent(
            @NotNull
            @QueryParam("id") int componentID,
            @CookieParam("userRole") String userRole
    ) {
        userRole = AESEncrypt.decrypt(userRole);
        int httpStatus = 200;
        if(userRole == null || userRole.equals("guest") || userRole.equals("user")){
            httpStatus = 403;
        } else if(!DataHandler.deleteComponent(componentID)){
            httpStatus = 410;
        }
        return Response
                .status(httpStatus)
                .entity("")
                .build();
    }

    /**
     * creates a component with the parameters given
     * @param component
     * @param releaseDate
     * @return
     */
    @POST
    @Path("create")
    @Produces(MediaType.TEXT_PLAIN)
    public Response insertComponent(
            @Valid @BeanParam Component component,
            @FormParam("releaseDate")@ReleaseDate(value = 1980) String releaseDate,
            @CookieParam("userRole") String userRole
    ) {
        userRole = AESEncrypt.decrypt(userRole);
        int httpStatus = 200;
        if(userRole == null || userRole.equals("guest") || userRole.equals("user")){
            httpStatus = 403;
        } else {
            component.setId(DataHandler.getComponentId());
            component.setReleaseDate(releaseDate);
            DataHandler.insertComponent(component);
        }
        return Response
                .status(httpStatus)
                .entity("")
                .build();
    }

    /**
     * updates a component by its id with the parameters given
     * @param component
     * @param id
     * @param releaseDate
     * @return
     */
    @PUT
    @Path("update")
    @Produces(MediaType.TEXT_PLAIN)
    public  Response updateComponent(
           @Valid @BeanParam Component component,
           @FormParam("id") int id,
           @FormParam("releaseDate")@ReleaseDate(value = 1980) String releaseDate,
           @CookieParam("userRole") String userRole
    ) {
        userRole = AESEncrypt.decrypt(userRole);
        int httpStatus = 200;
        if(userRole == null || userRole.equals("guest") || userRole.equals("user")){
            httpStatus = 403;
        } else {
            Component oldComponent = DataHandler.readComponentID(id);

            if(oldComponent != null){
                oldComponent.setName(component.getName());
                oldComponent.setDescription(component.getDescription());
                oldComponent.setGeneration(component.getGeneration());
                oldComponent.setReleaseDate(releaseDate);

                DataHandler.updateComponent();
            } else {
                httpStatus = 410;
            }
        }
        return Response
                .status(httpStatus)
                .entity("")
                .build();
    }
}
