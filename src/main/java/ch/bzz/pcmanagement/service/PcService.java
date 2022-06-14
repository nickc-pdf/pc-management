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
import java.util.UUID;

/**
 * reads a list of all PCs
 * @return Pcs as JSON
 */
@Path("pc")
public class PcService {
    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listPcs() {
        List<PC> pcList = DataHandler.readAllPc();
        return Response
                .status(200)
                .entity(pcList)
                .build();
    }

    /**
     * reads a specific PC of all PCs
     * @param pcID
     * @return PC as JSON
     */
    @GET
    @Path("read")
    @Produces(MediaType.APPLICATION_JSON)
    public Response readPc(
            @NotNull
            @QueryParam("id") int pcID
    ) {
        int httpStatus;
        PC pc = DataHandler.readPCID(pcID);
        if (pc == null){
            httpStatus= 404;
        } else {
            httpStatus = 200;
        }
        return Response
                .status(httpStatus)
                .entity(pc)
                .build();
    }

    @DELETE
    @Path("delete")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deletePC(
            @NotNull
            @QueryParam("id") int pcID
    ) {
        int httpStatus = 200;
        if(!DataHandler.deletePC(pcID)){
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
    public Response insertPC(
            @Valid @BeanParam PC pc,
            @FormParam("manufacturerID")
            @NotNull int manufacturerID
    ) {
        pc.setId(DataHandler.getPCId());
        pc.setManufacturerID(manufacturerID);
        DataHandler.insertPC(pc);
        int httpStatus = 200;
        return Response
                .status(httpStatus)
                .entity("")
                .build();
    }

    @PUT
    @Path("update")
    @Produces(MediaType.TEXT_PLAIN)
    public  Response updatePC(
            @Valid @BeanParam PC pc,
            @FormParam("manufacturerID")
            @NotNull int manufacturerID
    ) {
        int httpStatus = 200;
        PC oldPc = DataHandler.readPCID(pc.getId());
        if(oldPc != null     ){
            oldPc.setName(pc.getName());
            oldPc.setManufacturerID(pc.getManufacturerID());
            oldPc.setPrice(pc.getPrice());
            //pc.setComponents(components);
            pc.setId(DataHandler.getPCId());

            DataHandler.updatePC();
        } else {
            httpStatus = 410;
        }
        return Response
                .status(httpStatus)
                .entity("")
                .build();
    }
}