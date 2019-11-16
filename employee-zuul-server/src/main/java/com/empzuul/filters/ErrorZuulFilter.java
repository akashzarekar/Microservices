package com.empzuul.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

public class ErrorZuulFilter extends ZuulFilter {

	private static final Logger LOG = LoggerFactory.getLogger(ErrorZuulFilter.class);
	private static final String FILTER_TYPE = "error";
	private static final Integer FILTER_ORDER = -1;
	protected static final String SEND_ERROR_FILTER_RAN = "sendErrorFilter.ran";

	@Override
	public boolean shouldFilter() {
		// only forward to errorPath if it hasn't been forwarded to already
		RequestContext context = RequestContext.getCurrentContext();
		return context.getThrowable() != null && !context.getBoolean(SEND_ERROR_FILTER_RAN, false);
	}

	@Override
	public Object run() throws ZuulException {
		LOG.info("Inside ErrorZuulFilter to handle Errors");

		try {
			RequestContext context = RequestContext.getCurrentContext();
			// ctx.set(SEND_ERROR_FILTER_RAN); will block the SendErrorFilter from running.
			context.set(SEND_ERROR_FILTER_RAN);
			// Object error = context.get(ERROR_EXCEPTION);
			Object error = context.getThrowable();
			if (error != null && error instanceof ZuulException) {
				ZuulException zuulException = (ZuulException) error;
				LOG.error(zuulException.getCause().getMessage());
				// Remove error code to prevent further error handling in follow up filters
				throw zuulException;
			}

		} catch (Exception e) {
			LOG.error("Exception filtering in custom error filter", e.getLocalizedMessage());
			throw e;
		}
		LOG.info("Exit from ErrorZuulFilter");
		return null;
	}

	@Override
	public String filterType() {
		return FILTER_TYPE;
	}

	@Override
	public int filterOrder() {
		// (-1) needs to run before SendErrorFilter which has filterOrder == 0
		return FILTER_ORDER;
	}

}
