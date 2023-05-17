package br.ufu.facom.armstream.http;

import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.LinkedHashMap;

@Path("/")
public class InterceptorResource {

    private final InterceptorService interceptorService = InterceptorService.getInstance();

    @POST
    @Path("/intercept")
    @Produces(MediaType.APPLICATION_JSON)
    public Response intercept(ArmInterceptionContextDTO context) {
        return Response.ok(this.interceptorService.intercept(context)).build();
    }

    @POST
    @Path("/peek")
    public Response peek(LinkedHashMap<String, String> properties) {
        this.interceptorService.peek(properties);
        return Response.ok().build();
    }

    @POST
    @Path("/finish")
    public Response finish() {
        this.interceptorService.finish();
        return Response.ok().build();
    }

}
