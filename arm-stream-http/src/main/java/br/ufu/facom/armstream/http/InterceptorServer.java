package br.ufu.facom.armstream.http;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

public class InterceptorServer {

    private static Server server;

    public static String start(int port) {

        server = new Server(port);
        ServletContextHandler handler = buildUsingResourceConfig();
        server.setHandler(handler);

        try {
            server.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        String url = server.getURI().getScheme() + "://"
                + server.getURI().getHost() + ":"
                + port + "/";

        return url;

    }

    public static void stop() {
        try {
            server.stop();
            server.destroy();
            server = null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static ServletContextHandler buildUsingResourceConfig() {
        ServletContextHandler handler = new ServletContextHandler(ServletContextHandler.NO_SESSIONS);
        handler.setContextPath("/");

        ResourceConfig resourceConfig = new ResourceConfig();
        resourceConfig.register(InterceptorResource.class);
        handler.addServlet(new ServletHolder(new ServletContainer(resourceConfig)), "/*");
        return handler;
    }

}
