package ch.bzz.pcmanagement.service;

import ch.bzz.pcmanagement.data.DataHandler;
import ch.bzz.pcmanagement.model.Component;
import ch.bzz.pcmanagement.model.Manufacturer;
import ch.bzz.pcmanagement.model.PC;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
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
            @NotNull
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

    @DELETE
    @Path("delete")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteManufacturer(
            @NotNull
            @QueryParam("id") int manufacturerID
    ) {
        int httpStatus = 200;
        if(!DataHandler.deleteManufacturer(manufacturerID)){
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
    public Response insertManufacturer(
            @Valid @BeanParam Manufacturer manufacturer
    ) {
        manufacturer.setId(DataHandler.getManufacturerId());
        DataHandler.insertManufacturer(manufacturer);
        int httpStatus = 200;
        return Response
                .status(httpStatus)
                .entity("")
                .build();
    }

    @PUT
    @Path("update")
    @Produces(MediaType.TEXT_PLAIN)
    public  Response updateManufacturer(
            @Valid @BeanParam Manufacturer manufacturer
    ) {
        int httpStatus = 200;
        Manufacturer oldManufacturer = DataHandler.readManufacturerID(manufacturer.getId());
        if(oldManufacturer != null     ){
            oldManufacturer.setName(manufacturer.getName());
            oldManufacturer.setOrigin(manufacturer.getOrigin());
            oldManufacturer.setTel(manufacturer.getTel());
            oldManufacturer.setEmail(manufacturer.getEmail());

            DataHandler.updateManufacturer();
        } else {
            httpStatus = 410;
        }
        return Response
                .status(httpStatus)
                .entity("")
                .build();
    }
}
