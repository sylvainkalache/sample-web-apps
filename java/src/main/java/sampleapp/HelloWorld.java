package sampleapp;

import java.io.IOException;
import java.io.OutputStream;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

/**
 * This class implements a simple HTTP server that listens on port 8080
 * and responds with "Hello world!" to incoming requests.
 */
public final class HelloWorld {

    private static final int PORT_NUMBER = 8080;

    /**
     * Private constructor to prevent instantiation as this is a utility class.
     */
    private HelloWorld() {
        // Empty private constructor to prevent instantiation.
    }

    /**
     * The main method to start the HTTP server and listen on port 8080.
     *
     * @param args The command line arguments (not used).
     * @throws IOException If an I/O error occurs.
     */
    public static void main(final String[] args) throws IOException {
        HttpServer server = HttpServer.create(new java.net.InetSocketAddress(PORT_NUMBER), 0);
        server.createContext("/", new MyHandler());
        server.setExecutor(null); // creates a default executor
        server.start();
    }

    /**
     * Custom HTTP handler that sends "Hello world!" as the response.
     */
    static class MyHandler implements HttpHandler {
        @Override
        public void handle(final HttpExchange t) throws IOException {
	    String response = "Hello, World!\n";
	    String javaVersion = System.getProperty("java.version");
            response += "Java Version: " + javaVersion + "\n";

            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }
}
