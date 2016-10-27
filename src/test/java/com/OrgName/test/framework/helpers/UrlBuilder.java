package com.salmon.test.framework.helpers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

public class UrlBuilder {
    private static final Logger LOG = LoggerFactory.getLogger(UrlBuilder.class);
    private static final String RUN_CONFIG_PROPERTIES = "/environment.properties";
    private static URL basePath;
    private static URL apiUrl;

    static {
        try {
            Props.loadRunConfigProps(RUN_CONFIG_PROPERTIES);
            basePath = new URL(Props.getProp("site.url"));
            apiUrl = new URL(Props.getProp("api.url"));

        } catch (MalformedURLException e) {
            LOG.error(e.getMessage());
        }

    }

    public static void startAtHomePage() {
        WebDriverHelper.getWebDriver().navigate().to((basePath));
    }

    public static URL getApiUrlForEndPoint(String endpoint) {
        return createApiUrl(endpoint);
    }

    public static URI getBasePathURI() {
        return URI.create(Props.getProp("api.url"));
    }

    public static URI getCoreMediaBasePathURI() {
        return URI.create(Props.getProp("cm.api.url"));
    }

    public static URI getContextRootURI() {
        return URI.create(Props.getProp("contextRoot.url"));
    }

    public static URI getStoreId() {
        return URI.create(Props.getProp("storeId"));
    }

    public static String getUrl(String applicationUrl) {
        return Props.getProp(applicationUrl);
    }

    public static URL createApiUrl(String endpoint) {
        try {
            return new URL(apiUrl.getProtocol(), apiUrl.getHost(), apiUrl.getPort(), endpoint);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }


    public static URL createUrl(String path) {
        try {
            return new URL(basePath.getProtocol(), basePath.getHost(), basePath.getPort(), path);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    public static URI getSystestIP() {
        return URI.create(Props.getProp("systest.ip"));
    }

    public static URI getSystestUser(){
        return URI.create(Props.getProp("systest.user"));
    }

    public static URI getSystestPassword(){
        return URI.create(Props.getProp("systest.password"));
    }

    public static String readValueFromConfig(String key){
        return Props.getProp(key);
    }
}
