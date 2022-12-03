package province;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.jvnet.hk2.annotations.Service;

import cardinfo.IDCardUtils;

@Service
@Path("/ProvinceService")
public class ProviceService {
	@GET
	@Path("/get")
	@Produces(MediaType.APPLICATION_JSON)
	public String getProvince(@QueryParam("card_id") String id) {
		return IDCardUtils.getProvinceByIdCard(id);
	}
}
