package com.empzuul.filters;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

import io.micrometer.core.instrument.util.IOUtils;

public class PostZuulFilter extends ZuulFilter {
	private static final Logger LOG = LoggerFactory.getLogger(PostZuulFilter.class);
	private static final String FILTER_TYPE = "post";
	private static final Integer FILTER_ORDER = 0;
	private static final String IN_REQ_TIME = "IN_REQ_TIME";
	private static final String SERVICE_URL = "SERVICE_URL";

	@Override
	public boolean shouldFilter() {
		return RequestContext.getCurrentContext().getThrowable() == null;
	}

	@Override
	public Object run() throws ZuulException {
		LOG.info("Inside PostZuulFilter to handle Response");
		LocalTime responseTime = LocalTime.now();
		RequestContext context = RequestContext.getCurrentContext();
		LocalTime requestTime = (LocalTime) context.get(IN_REQ_TIME);
		LOG.debug("Total Response Time in Millis :" + requestTime.until(responseTime, ChronoUnit.MILLIS) + "ms");
		String response_Data = null;
		try (InputStream inputStream = context.getResponseDataStream()) {
			response_Data = IOUtils.toString(inputStream);
			LOG.debug("Response Generated :" + response_Data);
		} catch (IOException e) {
			LOG.error("Exception Occured while Reading response data ::" + e);
		}
		context.setResponseBody(response_Data);
		LOG.info("Exit from PostZuulFilter");
		LOG.info("\n REQUEST_EXIT : " + context.get(SERVICE_URL));
		return null;
	}

	@Override
	public String filterType() {
		return FILTER_TYPE;
	}

	@Override
	public int filterOrder() {
		return FILTER_ORDER;
	}

}
