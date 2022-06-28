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
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

/**
 * reads a list of all PCs
 *
 * @return Pcs as JSON
 */
@Path("pc")
public class PcService {
    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listPcs(
            @CookieParam("userRole") String userRole
    ) {
        List<PC> pcList = null;
        userRole = AESEncrypt.decrypt(userRole);
        int httpStatus;
        if (userRole == null || userRole.equals("guest")) {
            httpStatus = 403;
        } else {
            httpStatus = 200;
            pcList = DataHandler.readAllPc();
        }
        return Response
                .status(httpStatus)
                .entity(pcList)
                .build();
    }

    /**
     * reads a specific PC of all PCs
     *
     * @param pcID
     * @return PC as JSON
     */
    @GET
    @Path("read")
    @Produces(MediaType.APPLICATION_JSON)
    public Response readPc(
            @NotNull
            @QueryParam("id") int pcID,
            @CookieParam("userRole") String userRole
    ) {
        userRole = AESEncrypt.decrypt(userRole);
        int httpStatus = 200;
        PC pc = null;
        if (userRole == null || userRole.equals("guest")) {
            httpStatus = 403;
        } else {
            pc = DataHandler.readPCID(pcID);
            if (pc == null) {
                httpStatus = 404;
            }
        }
        return Response
                .status(httpStatus)
                .entity(pc)
                .build();
    }

    /**
     * deletes a pc by its id
     * @param pcID
     * @param userRole
     * @return
     */
    @DELETE
    @Path("delete")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deletePC(
            @NotNull
            @QueryParam("id") int pcID,
            @CookieParam("userRole") String userRole
    ) {
        userRole = AESEncrypt.decrypt(userRole);
        int httpStatus = 200;
        if (userRole == null || userRole.equals("guest") || userRole.equals("user")) {
            httpStatus = 403;
        } else if (!DataHandler.deletePC(pcID)) {
            httpStatus = 410;
        }
        return Response
                .status(httpStatus)
                .entity("")
                .build();
    }

    /**
     * creates a pc with the parameters given
     * @param pc
     * @param manufacturerID
     * @param userRole
     * @return
     */
    @POST
    @Path("create")
    @Produces(MediaType.TEXT_PLAIN)
    public Response insertPC(
            @Valid @BeanParam PC pc,
            @FormParam("manufacturerID")
            @NotEmpty String manufacturerID,
            @CookieParam("userRole") String userRole
    ) {
        userRole = AESEncrypt.decrypt(userRole);
        int httpStatus = 200;
        if (userRole == null || userRole.equals("guest") || userRole.equals("user")) {
            httpStatus = 403;
        } else {
            Manufacturer manufacturer = DataHandler.readManufacturerID(pc.getManufacturerID());
            if (manufacturer != null) {
                pc.setId(DataHandler.getPCId());
                pc.setManufacturerID(Integer.parseInt(manufacturerID));
                DataHandler.insertPC(pc);
            } else {
                httpStatus = 400;
            }
        }
        return Response
                .status(httpStatus)
                .entity("")
                .build();
    }

    /**
     * updates a pc by its id with parameters given
     * @param pc
     * @param manufacturerID
     * @param id
     * @param userRole
     * @return
     */
    @PUT
    @Path("update")
    @Produces(MediaType.TEXT_PLAIN)
    public Response updatePC(
            @Valid @BeanParam PC pc,
            @FormParam("manufacturerID")
            @NotNull int manufacturerID,
            @FormParam("id") int id,
            @CookieParam("userRole") String userRole
    ) {
        userRole = AESEncrypt.decrypt(userRole);
        int httpStatus = 200;
        if (userRole == null || userRole.equals("guest") || userRole.equals("user")) {
            httpStatus = 403;
        } else {
            PC oldPc = DataHandler.readPCID(id);
            Manufacturer manufacturer = DataHandler.readManufacturerID(pc.getManufacturerID());

            if (manufacturer == null) {
                httpStatus = 400;
            } else if (oldPc != null) {
                oldPc.setName(pc.getName());
                oldPc.setManufacturerID(pc.getManufacturerID());
                oldPc.setPrice(pc.getPrice());
                //pc.setComponents(components);

                DataHandler.updatePC();
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