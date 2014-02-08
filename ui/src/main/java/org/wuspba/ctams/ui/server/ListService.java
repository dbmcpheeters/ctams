package org.wuspba.ctams.ui.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Enumeration;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.http.client.utils.URIBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The server side implementation of the RPC service.
 */
public class ListService extends HttpServlet {

    protected String path;

    private static final Logger LOG = LoggerFactory.getLogger(ListService.class);

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        this.path = ServerUtils.URI + servletConfig.getInitParameter("uri");
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        try {

            URIBuilder builder = new URIBuilder()
                    .setScheme(ServerUtils.PROTOCOL)
                    .setHost(ServerUtils.HOST)
                    .setPort(ServerUtils.PORT)
                    .setPath(path);

            Enumeration attrs = request.getAttributeNames();
            while(attrs.hasMoreElements()) {
                Object attr = attrs.nextElement();
                builder.addParameter(attr.toString(), request.getAttribute(attr.toString()).toString());
                
                LOG.debug(attr.toString());
                LOG.debug(request.getAttribute(attr.toString()).toString());
            }

            Enumeration parameters = request.getParameterNames();
            while(parameters.hasMoreElements()) {
                Object param = parameters.nextElement();
                builder.addParameter(param.toString(), request.getParameter(param.toString()).toString());
                
                LOG.debug(param.toString());
                LOG.debug(request.getParameter(param.toString()).toString());
            }

            URI uri = builder.build();

            LOG.info("Connecting to " + uri.toString());

            String ret = ServerUtils.get(uri);

            PrintWriter out = response.getWriter();
            out.println(ret);
        } catch (URISyntaxException ex) {
            LOG.error("URI Syntax Exception", ex);
            throw new IOException(ex);
        }
    }
}
