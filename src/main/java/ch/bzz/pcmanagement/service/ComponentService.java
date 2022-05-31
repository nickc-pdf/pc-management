package ch.bzz.pcmanagement.service;

import ch.bzz.pcmanagement.data.DataHandler;
import ch.bzz.pcmanagement.model.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
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
}
