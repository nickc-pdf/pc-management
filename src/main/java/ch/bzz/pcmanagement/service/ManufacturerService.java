package ch.bzz.pcmanagement.service;

import ch.bzz.pcmanagement.data.DataHandler;
import ch.bzz.pcmanagement.model.Manufacturer;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * reads a list of all Manufacturers
 * @return Manufacturers as JSON
 */
@Path("manufacturer")
public class ManufacturerService {
    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listManufacturers() {
        List<Manufacturer> manufacturerList = DataHandler.readAllManufacturer();
        return Response
                .status(200)
                .entity(manufacturerList)
                .build();
    }

    /**
     * reads a specific Manufacturer of all Manufacturers
     * @param manufacturerID
     * @return Manufacturer as JSON
     */
    @GET
    @Path("read")
    @Produces(MediaType.APPLICATION_JSON)
    public Response readManufacturer(
            @QueryParam("id") int manufacturerID
    ) {
        int httpStatus;
        Manufacturer manufacturer = DataHandler.readManufacturerID(manufacturerID);
        if (manufacturer == null){
            httpStatus= 404;
        } else {
            httpStatus = 200;
        }
        return Response
                .status(httpStatus)
                .entity(manufacturer)
                .build();
    }
}
