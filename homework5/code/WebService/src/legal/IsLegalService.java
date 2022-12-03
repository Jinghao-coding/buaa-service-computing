package legal;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.jvnet.hk2.annotations.Service;

import cardinfo.IDCardUtils;


@Service
@Path("/IsLegalService")
public class IsLegalService {
	@GET
	@Path("/get")
	@Produces(MediaType.APPLICATION_JSON)
	public int isLegal(@QueryParam("card_id")String id) {
		IDCardUtils utils = new IDCardUtils();
		if(utils.validateCard(id)) {
			return 1;
		}
		return -1;
	}
}
