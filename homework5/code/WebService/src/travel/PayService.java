package travel;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.jvnet.hk2.annotations.Service;

@Service
@Path("/PayService")
public class PayService {
	@POST
	@Path("/pay")
	@Produces(MediaType.APPLICATION_JSON)
	public double pay(@QueryParam("key")String key) {
		if (key.equals("by2221105")) {
			return 1;
		}
		else {
			return 0;
		}
	}
}