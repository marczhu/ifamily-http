package com.mark.ifamily;

import com.mark.ifamily.exception.HttpServiceException;
import org.jboss.resteasy.spi.HttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

import static com.mark.ifamily.exception.HttpServiceException.*;

/**
 * purge rest实现
 *
 * Date: 13-8-23 上午10:42
 */
@Path("/ifamily")
public class HttpServiceImpl implements HttpService {
    static final Logger logger = LoggerFactory.getLogger(HttpServiceImpl.class);
    private IpManagementService ipManagementService;

    @Context
    HttpServletRequest request;

    @GET
    @Path("/put")
    @Produces("application/json")
    @Override
    public Response put(@QueryParam("key") String key) throws HttpServiceException {
        if (key != null && logger.isDebugEnabled()) {
            logger.debug(key.toString());
        }
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            if (key == null || key.length()<1) {
                throw new HttpServiceException.BadRequestException();
            }
            String ip = getIpAddr(request);
            ipManagementService.put(key,ip);
            map.put("key",key);
            map.put("ip",ip);
            return Response.ok().entity(map).build();
        } catch (HttpServiceException e) {
            logger.error("put failed!",e);
            map.put("error_code", e.getErrorCode());
            map.put("error_msg", e.getMessage());
            return Response.status(e.getStatus()).entity(map).build();
        } catch (Exception e) {
            logger.error("put failed!",e);
            HttpServiceException exception = new HttpServiceException.InternalException(e);
            map.put("error_code", exception.getErrorCode());
            map.put("error_msg", exception.getMessage());
            return Response.status(exception.getStatus()).entity(map).build();
        }
    }
    @GET
    @Path("/get")
    @Produces("application/json")
    @Override
    public Response get(@QueryParam("key") String key) throws HttpServiceException {
        if (key != null && logger.isDebugEnabled()) {
            logger.debug(key.toString());
        }
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            if (key == null || key.length()<1) {
                throw new HttpServiceException.BadRequestException();
            }
            String ip = ipManagementService.get(key);
            map.put("ip",ip);
            return Response.ok().entity(map).build();
        } catch (HttpServiceException e) {
            logger.error("get failed!",e);
            map.put("error_code", e.getErrorCode());
            map.put("error_msg", e.getMessage());
            return Response.status(e.getStatus()).entity(map).build();
        } catch (Exception e) {
            logger.error("put failed!",e);
            HttpServiceException exception = new HttpServiceException.InternalException(e);
            map.put("error_code", exception.getErrorCode());
            map.put("error_msg", exception.getMessage());
            return Response.status(exception.getStatus()).entity(map).build();
        }
    }
    @GET
    @Path("/list")
    @Produces("application/json")
    @Override
    public Response list() throws HttpServiceException {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            String ipList = ipManagementService.list();
            map.put("list",ipList);
            return Response.ok().entity(map).build();
        } catch (Exception e) {
            logger.error("list failed!",e);
            HttpServiceException exception = new HttpServiceException.InternalException(e);
            map.put("error_code", exception.getErrorCode());
            map.put("error_msg", exception.getMessage());
            return Response.status(exception.getStatus()).entity(map).build();
        }
    }
    @GET
    @Path("/clear")
    @Produces("application/json")
    @Override
    public Response clear() throws HttpServiceException {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            ipManagementService.clear();
            return Response.ok().entity(map).build();
        } catch (Exception e) {
            logger.error("clear failed!",e);
            HttpServiceException exception = new HttpServiceException.InternalException(e);
            map.put("error_code", exception.getErrorCode());
            map.put("error_msg", exception.getMessage());
            return Response.status(exception.getStatus()).entity(map).build();
        }
    }
    public void setIpManagementService(IpManagementService ipManagementService) {
        this.ipManagementService = ipManagementService;
    }

    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (ip.equals("0:0:0:0:0:0:0:1")) {
            ip = "localhost";
        }
        if (ip.split(",").length > 1) {
            ip = ip.split(",")[0];
        }
        return ip;
    }
}
