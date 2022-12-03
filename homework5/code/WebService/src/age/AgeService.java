package age;

import org.jvnet.hk2.annotations.Service;
import cardinfo.IDCardUtils;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Service
@Path("/AgeService")
public class AgeService {
	@GET
	@Path("/get")
	@Produces(MediaType.TEXT_PLAIN)
	public int getAge(@QueryParam("card_id")String cardID) {
		System.out.print(cardID); 
		return IDCardUtils.getAgeByIdCard(cardID);
	}
}
