package com.jwt.rest;
 
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
  
@Path("/CCEngine")
public class CreditCardService {
	/**
	 * REST service 
	 * @param cardType
	 * @param cardCount
	 * @return
	 */
    @GET
    @Path("{cardType}/{cardCount}")
    public Response generateVISACards(@PathParam("cardType") String cardType, @PathParam("cardCount") String cardCount) {
  
        String output = "Welcome   : " + name;
        CreditCardEngine engine = new CreditCardEngine();
        List<CreditCardDTO> cards = engine.startCreditCardEngine(cardType, cardCount);
        return Response.status(200).entity(cards).build();
  
    }
}