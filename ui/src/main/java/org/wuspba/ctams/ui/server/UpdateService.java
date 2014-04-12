package org.wuspba.ctams.ui.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URISyntaxException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.http.client.utils.URIBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wuspba.ctams.model.CTAMSDocument;
import org.wuspba.ctams.util.XMLUtils;

/**
 * The server side implementation of the RPC service.
 */
public class UpdateService extends HttpServlet {

    protected String path;
    protected Class type;

    private static final Logger LOG = LoggerFactory.getLogger(UpdateService.class);

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        this.path = ServerUtils.URI + servletConfig.getInitParameter("uri");
        try {
            this.type = Class.forName(servletConfig.getInitParameter("type"));
        } catch (ClassNotFoundException ex) {
            LOG.error("Could not find class", ex);
        }
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        CTAMSDocument doc = DataUtils.getDocument(type, request);

        try {
            
            URI uri = new URIBuilder()
                    .setScheme(ServerUtils.PROTOCOL)
                    .setHost(ServerUtils.HOST)
                    .setPort(ServerUtils.PORT)
                    .setPath(path)
                    .build();
            
            LOG.info("Connecting to " + uri.toString());
            
            String ret = ServerUtils.post(uri, XMLUtils.marshal(doc));
            
            PrintWriter out = response.getWriter();
            out.println(ret);
        } catch (URISyntaxException ex) {
            LOG.error("URI Syntax Exception", ex);
            throw new IOException(ex);
        }
    }
}
