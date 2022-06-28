package ch.bzz.pcmanagement.service;

import ch.bzz.pcmanagement.data.DataHandler;
import ch.bzz.pcmanagement.model.Component;
import ch.bzz.pcmanagement.model.Manufacturer;
import ch.bzz.pcmanagement.model.PC;
import ch.bzz.pcmanagement.util.AESEncrypt;

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
    public Response listManufacturers(
            @CookieParam("userRole") String userRole
    ) {
        List<Manufacturer> manufacturerList = null;
        userRole = AESEncrypt.decrypt(userRole);
        int httpStatus = 200;
        if(userRole == null || userRole.equals("guest")){
            httpStatus = 403;
        } else {
            manufacturerList = DataHandler.readAllManufacturer();
        }
        return Response
                .status(httpStatus)
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
            @QueryParam("id") int manufacturerID,
            @CookieParam("userRole") String userRole
    ) {
        userRole = AESEncrypt.decrypt(userRole);
        int httpStatus = 200;
        Manufacturer manufacturer = null;
        if(userRole == null || userRole.equals("guest")){
            httpStatus = 403;
        } else {
            manufacturer = DataHandler.readManufacturerID(manufacturerID);
            if (manufacturer == null){
                httpStatus= 404;
            }
        }
        return Response
                .status(httpStatus)
                .entity(manufacturer)
                .build();
    }

    /**
     * deletes a manufacturer by its id
     * @param manufacturerID
     * @param userRole
     * @return
     */
    @DELETE
    @Path("delete")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteManufacturer(
            @NotNull
            @QueryParam("id") int manufacturerID,
            @CookieParam("userRole") String userRole
    ) {
        userRole = AESEncrypt.decrypt(userRole);
        int httpStatus = 200;
        if(userRole == null || userRole.equals("guest") || userRole.equals("user")){
            httpStatus = 403;
        } else if(!DataHandler.deleteManufacturer(manufacturerID)){
            httpStatus = 410;
        }
        return Response
                .status(httpStatus)
                .entity("")
                .build();
    }

    /**
     * creates a manufacturer with the parameters given
     * @param manufacturer
     * @param userRole
     * @return
     */
    @POST
    @Path("create")
    @Produces(MediaType.TEXT_PLAIN)
    public Response insertManufacturer(
            @Valid @BeanParam Manufacturer manufacturer,
            @CookieParam("userRole") String userRole
    ) {
        userRole = AESEncrypt.decrypt(userRole);
        int httpStatus = 200;
        if(userRole == null || userRole.equals("guest") || userRole.equals("user")){
            httpStatus = 403;
        } else {
            manufacturer.setId(DataHandler.getManufacturerId());
            DataHandler.insertManufacturer(manufacturer);
        }
        return Response
                .status(httpStatus)
                .entity("")
                .build();
    }

    /**
     * updates a manufacturer by its id with parameters given
     * @param manufacturer
     * @param id
     * @param userRole
     * @return
     */
    @PUT
    @Path("update")
    @Produces(MediaType.TEXT_PLAIN)
    public  Response updateManufacturer(
            @Valid @BeanParam Manufacturer manufacturer,
            @FormParam("id") int id,
            @CookieParam("userRole") String userRole
    ) {
        userRole = AESEncrypt.decrypt(userRole);
        int httpStatus = 200;
        if(userRole == null || userRole.equals("guest") || userRole.equals("user")){
            httpStatus = 403;
        } else {
            Manufacturer oldManufacturer = DataHandler.readManufacturerID(id);
            if(oldManufacturer != null     ){
                oldManufacturer.setName(manufacturer.getName());
                oldManufacturer.setOrigin(manufacturer.getOrigin());
                oldManufacturer.setTel(manufacturer.getTel());
                oldManufacturer.setEmail(manufacturer.getEmail());

                DataHandler.updateManufacturer();
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
