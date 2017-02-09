package br.org.ons.portal.gateway.responserewriting;

import static org.junit.Assert.*;
import static springfox.documentation.swagger2.web.Swagger2Controller.DEFAULT_URL;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import com.netflix.zuul.context.RequestContext;

/**
 * Tests SwaggerBasePathRewritingFilter class.
 */
public class SwaggerBasePathRewritingFilterTest {

    private SwaggerBasePathRewritingFilter filter = new SwaggerBasePathRewritingFilter();

    @Test
    public void filterOrder() {
        assertEquals(100, filter.filterOrder());
    }
    
    @Test
    public void shouldFilter_on_default_swagger_url() {

        MockHttpServletRequest request = new MockHttpServletRequest("GET", DEFAULT_URL);
        RequestContext.getCurrentContext().setRequest(request);

        assertTrue(filter.shouldFilter());
    }

    /**
     * Zuul DebugFilter can be triggered by "deug" parameter.
     */
    @Test
    public void shouldFilter_on_default_swagger_url_with_param() {

        MockHttpServletRequest request = new MockHttpServletRequest("GET", DEFAULT_URL);
        request.setParameter("debug", "true");
        RequestContext.getCurrentContext().setRequest(request);

        assertTrue(filter.shouldFilter());
    }

    @Test
    public void shouldNotFilter_on_wrong_url() {

        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/management/info");
        RequestContext.getCurrentContext().setRequest(request);

        assertFalse(filter.shouldFilter());
    }

    @Test
    public void run_on_valid_response() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/service1" + DEFAULT_URL);
        RequestContext context = RequestContext.getCurrentContext();
        context.setRequest(request);

        MockHttpServletResponse response = new MockHttpServletResponse();
        context.setResponse(response);

        InputStream in = IOUtils.toInputStream("{\"basePath\":\"/\"}");
        context.setResponseDataStream(in);

        filter.run();

        assertEquals("UTF-8", response.getCharacterEncoding());
        assertEquals("{\"basePath\":\"/service1\"}", context.getResponseBody());
    }
    
    @Test
    public void run_throws_exception() throws Exception {
    	MockHttpServletRequest request = new MockHttpServletRequest("GET", DEFAULT_URL);
    	RequestContext context = RequestContext.getCurrentContext();
    	context.setRequest(request);
    	
    	MockHttpServletResponse response = new MockHttpServletResponse();
    	context.setResponse(response);
    	
    	InputStream in = Mockito.mock(InputStream.class);
    	Mockito.when(in.read(Mockito.any())).thenThrow(new IOException());
    	context.setResponseDataStream(in);
    	
    	filter.run();
    	
    	assertEquals(null, context.getResponseBody());
    }
}
