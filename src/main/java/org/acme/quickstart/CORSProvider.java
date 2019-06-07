package org.acme.quickstart;

import org.jboss.resteasy.plugins.interceptors.CorsFilter;

import javax.ws.rs.core.Feature;
import javax.ws.rs.core.FeatureContext;
import javax.ws.rs.ext.Provider;

@Provider
public class CORSProvider implements Feature {
    @Override
    public boolean configure(FeatureContext context) {
        CorsFilter filter = new CorsFilter();
        filter.getAllowedOrigins().add("*");
        filter.setAllowedMethods("GET, POST, OPTIONS, HEAD");
        filter.setAllowedHeaders("accept, content-type, origin, token");
        context.register(filter);

        System.out.println("通過");
        return true;
    }
}
