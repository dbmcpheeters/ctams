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

/**
 * The server side implementation of the RPC service.
 */
public class DeleteService extends HttpServlet {

    protected String path;

    private static final Logger LOG = LoggerFactory.getLogger(DeleteService.class);

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        this.path = ServerUtils.URI + servletConfig.getInitParameter("uri");
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String id = request.getParameter("id");

        try {
            
            URI uri = new URIBuilder()
                    .setScheme(ServerUtils.PROTOCOL)
                    .setHost(ServerUtils.HOST)
                    .setPort(ServerUtils.PORT)
                    .setPath(path)
                    .setParameter("id", id)
                    .build();
            
            LOG.info("Connecting to " + uri.toString());
            
            String ret = ServerUtils.delete(uri);
            
            PrintWriter out = response.getWriter();
            out.println(ret);
        } catch (URISyntaxException ex) {
            LOG.error("URI Syntax Exception", ex);
            throw new IOException(ex);
        }
    }
}
