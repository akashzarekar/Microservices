package com.empzuul.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.zuul.exception.ZuulException;

@RestController
public class ZuulErrorController implements ErrorController {
	private static final Logger LOG = LoggerFactory.getLogger(ZuulErrorController.class);

	@Value("${error.path:/error}")
	private String errorPath;

	@Override
	public String getErrorPath() {
		return errorPath;
	}

	@RequestMapping(value = "${error.path:/error}")
	public @ResponseBody ResponseEntity<ZuulException> getErrorResponse(HttpServletRequest request)
			throws ZuulException {
		LOG.info("Calling getErrorResponse() to handle Zuul Exception");
		Throwable throwable = (Throwable) request.getAttribute("javax.servlet.error.exception");
		if (throwable != null && throwable instanceof ZuulException) {
			ZuulException zuulException = (ZuulException) throwable;
			LOG.error("Throwing ZuulException to custom handler :: " + throwable);
			LOG.error("Status code ::" + zuulException.nStatusCode);
			throw zuulException;
		}
		return null;
	}

}
