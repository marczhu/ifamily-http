package com.mark.ifamily;

import com.mark.ifamily.exception.HttpServiceException;
import org.jboss.resteasy.annotations.Form;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * rest服务接口
 *
 * Date: 13-8-23 上午10:40
 */
@Path("/ifamily")
public interface HttpService {
    /**
     * @param key 请求参数
     * @return  响应
     * @throws HttpServiceException
     */
    @GET
    @Path("/put")
    @Produces("application/json")
    public Response put(@QueryParam("key") String key) throws HttpServiceException;

    @GET
    @Path("/put")
    @Produces("application/json")
    public Response get(@QueryParam("key") String key) throws HttpServiceException;

    @GET
    @Path("/put")
    @Produces("application/json")
    public Response list() throws HttpServiceException;

    @GET
    @Path("/put")
    @Produces("application/json")
    public Response clear() throws HttpServiceException;

}
