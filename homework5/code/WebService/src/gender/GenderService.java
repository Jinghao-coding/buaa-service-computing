package gender;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.jvnet.hk2.annotations.Service;

import cardinfo.IDCardUtils;


@Service
@Path("/GenderService")
public class GenderService {
	@GET
	@Path("/get")
	@Produces(MediaType.APPLICATION_ATOM_XML)
	public String getGender(@QueryParam("card_id")String id) {
		return IDCardUtils.getGenderByIdCard(id);
	}
}
