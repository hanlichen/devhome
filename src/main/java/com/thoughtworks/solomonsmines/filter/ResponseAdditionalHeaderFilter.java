package com.thoughtworks.solomonsmines.filter;

import com.thoughtworks.solomonsmines.utils.ConfigUtil;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.Provider;

@Provider
public class ResponseAdditionalHeaderFilter implements ContainerResponseFilter {

	@Override
	public void filter(ContainerRequestContext requestContext, ContainerResponseContext cresp) throws IOException {
		String allowOrigin = ConfigUtil.getProperty("http.access_control_allow_origin");
		cresp.getHeaders().putSingle("Access-Control-Allow-Origin", "*");
		cresp.getHeaders().putSingle("Access-Control-Allow-Credentials", "true");
		cresp.getHeaders().putSingle("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT");
		cresp.getHeaders().putSingle("Access-Control-Allow-Headers",
				Joiner.on(",").join(Lists.newArrayList("Content-Type", "Accept")));

		cresp.getHeaders().putSingle("Cache-Control", "no-cache, must-revalidate");
		cresp.getHeaders().putSingle("Pragma", "no-cache");

		MediaType mediaType = cresp.getMediaType();
		if (mediaType != null) {
			String contentType = mediaType.toString();
			if (!contentType.toLowerCase().contains("charset")) {
				cresp.getHeaders().putSingle("Content-Type", mediaType.withCharset("utf-8").toString());
			}
		}
	}
}
