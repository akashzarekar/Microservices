package com.empzuul.filters;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalTime;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

import io.micrometer.core.instrument.util.IOUtils;

public class PreZuulFilter extends ZuulFilter {
	private static final Logger LOG = LoggerFactory.getLogger(PreZuulFilter.class);
	private static final String FILTER_TYPE = "pre";
	private static final Integer FILTER_ORDER = 0;
	private static final String IN_REQ_TIME = "IN_REQ_TIME";
	private static final String SERVICE_URL = "SERVICE_URL";

	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public Object run() throws ZuulException {
		LOG.info("Inside PreZuulFilter to catch request");
		RequestContext context = RequestContext.getCurrentContext();
		HttpServletRequest httpServletRequest = context.getRequest();
		String methodName = httpServletRequest.getMethod();
		String serviceURL = httpServletRequest.getRequestURL().toString();
		LocalTime localTime = LocalTime.now();
		LOG.debug("Request Method :" + methodName + " \n REQUEST_ENTRY :" + serviceURL + " \n Request Incoming time : "
				+ localTime);
		context.set(IN_REQ_TIME, localTime);
		context.set(SERVICE_URL, serviceURL);
		printInputData(httpServletRequest);

		LOG.info("Exit from PreZuulFilter");
		return null;
	}

	private void printInputData(HttpServletRequest httpServletRequest) {
		String req_Input_Data = null;
		try (InputStream inputStream = httpServletRequest.getInputStream()) {
			req_Input_Data = IOUtils.toString(inputStream);
			LOG.debug("Input Request :" + req_Input_Data);
		} catch (IOException e) {
			LOG.error("Exception Occured while Reading request data ::" + e);
		}

//		ObjectMapper mapper = new ObjectMapper();
//		String formatedJsonData = null;
//		try {
//			if (!StringUtils.isBlank(req_Input_Data)) {
//				formatedJsonData = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(req_Input_Data);
//			}
//			LOG.debug("Input Request :" + formatedJsonData);
//		} catch (JsonProcessingException e) {
//			LOG.error("Error occured while parsing Request ::" + e);
//		}
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
