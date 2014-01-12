package org.wuspba.ctams.ui.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URISyntaxException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.http.client.utils.URIBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The server side implementation of the RPC service.
 */
public class BandListService extends HttpServlet {

    protected final static String PATH = ServerUtils.URI + "/band";

    private static final Logger LOG = LoggerFactory.getLogger(BandListService.class);

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            
            URI uri = new URIBuilder()
                    .setScheme(ServerUtils.PROTOCOL)
                    .setHost(ServerUtils.HOST)
                    .setPort(ServerUtils.PORT)
                    .setPath(PATH)
                    .build();
            
            LOG.info("Connecting to " + uri.toString());
            
            String ret = ServerUtils.query(uri);
            
            PrintWriter out = response.getWriter();
            out.println(ret);
        } catch (URISyntaxException ex) {
            LOG.error("URI Syntax Exception", ex);
            throw new IOException(ex);
        }
    }
}
