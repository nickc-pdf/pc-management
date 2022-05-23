package ch.bzz.pcmanagement.service;

import ch.bzz.pcmanagement.data.DataHandler;
import ch.bzz.pcmanagement.model.PC;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

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
        List<PC> pcList = DataHandler.getInstance().readAllPc();
        return Response
                .status(200)
                .entity(pcList)
                .build();
    }

    @GET
    @Path("read")
    @Produces(MediaType.APPLICATION_JSON)
    public Response readPc(
            @QueryParam("id") int pcID
    ) {
        PC pc = DataHandler.getInstance().readPCID(pcID);
        return Response
                .status(200)
                .entity(pc)
                .build();
    }
}
