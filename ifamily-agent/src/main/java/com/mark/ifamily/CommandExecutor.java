package com.mark.ifamily;

import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.Charset;

/**
 * Created by mark.zhu on 2016/10/14.
 */
public class CommandExecutor {
    private static final Logger LOGGER = LoggerFactory.getLogger(CommandExecutor.class);
    private static final String CHAR_SET_NAME = "UTF-8";
    private static final String SERVER_URI = "http://27.223.70.53:7074/ifamily";
    private static final String KEY = "ifamily";

    public void start(String key) {
        if (key == null || key.length() < 1) {
            key = KEY;
        }
        while (true) {
            String result = executeGet(SERVER_URI + "/put/?key=" + key);
            LOGGER.info("result:" + result);
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                LOGGER.error("",e);
            }
        }
    }

    public String put(String key) {
        if (key == null || key.length() < 1) {
            key = KEY;
        }
        return executeGet(SERVER_URI + "/put/?key=" + key);

    }

    public String get(String key) {
        if (key == null || key.length() < 1) {
            key = KEY;
        }
        return executeGet(SERVER_URI + "/get/?key=" + key);
    }

    public String list() {
        return executeGet(SERVER_URI + "/list");
    }

    public void clear() {
        executeGet(SERVER_URI + "/clear");
    }

    protected String executeGet(String uri) {
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(uri);
//            HttpHost httpHost = new HttpHost(host,port);
        try {
//                httpClient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, httpHost);
            HttpResponse httpResponse = httpClient.execute(httpGet);
            httpResponse.getEntity().getContent();
            return getResult(httpResponse);
        } catch (ClientProtocolException e) {
            LOGGER.error("", e);
        } catch (IOException e) {
        } catch (HttpException e) {
            LOGGER.error("", e);
        } finally {
            httpGet.abort();
        }
        return null;
    }

    protected String getResult(HttpResponse response) throws HttpException, IOException {
        if (response.getStatusLine() == null) {
            throw new HttpException("execute failed! cause: statusLine is null.");
        }
        if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK && response.getStatusLine().getStatusCode() != HttpStatus.SC_NOT_FOUND) {
            throw new HttpException(String.format("execute failed! StatusLine:%s", response.getStatusLine().toString()));
        }
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(response.getStatusLine().toString());
        }
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(EntityUtils.toString(entity));
            }
        }
        return EntityUtils.toString(entity, Charset.forName(CHAR_SET_NAME));
    }
}
