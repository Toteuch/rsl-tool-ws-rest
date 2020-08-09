package com.toteuch.rsl.tool.webservice;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

@Component
@ApplicationPath("/api/v1")
public class JerseyConfiguration extends ResourceConfig {
	public JerseyConfiguration() {
		packages("com.toteuch.rsl.tool.webservice.controller");
	}
}
