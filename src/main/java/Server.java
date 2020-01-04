import java.io.IOException;
import java.util.HashMap;

public class Server {

    /*
    An hash map to hold all the get paths and the functions to handle them.
     */
    private HashMap<String, OnGetArrivesListener> getPaths;

    public Server() {
        getPaths = new HashMap<>();
    }

    void start() {
        try {
            Network network = new Network(3910, request -> {
                if (request.getRequestType() == Request.RequestType.GET) {
                    System.out.println("PATH: " + request.getPath());
                    OnGetArrivesListener listener = getPaths.get( request.getPath() );
                    if (listener == null) {
                        return new Response( // Return 404 not found
                              404,
                              "404 NOT FOUND",
                              "text/html",
                              null
                        );
                    } else {
                        return listener.get( request );
                    }
                }
                else {
                    return new Response(
                            200,
                            "POST",
                            "text/html",
                            null
                    );
                }
            });

            network.start();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    /**
     * An handler for simple get request
     * @param path the path to handele
     * @param listener a listener that get Request object and returns Response object
     */
    public void get(String path, OnGetArrivesListener listener) {
        getPaths.put(path, listener);
    }

    public void getHTML(String path, OnGetArrivesListener listener, HashMap<String, String> resources) throws IOException {
        getPaths.put(path, listener);
        for (String resourcePath : resources.keySet()) {
            String responseType = resources.get(resourcePath);
            String resourceText = new FileReader(resourcePath).read();
            getPaths.put(resourcePath, request -> new Response(
                    200,
                    resourceText,
                    responseType,
                    null
            ));
        }
    }

    public interface OnGetArrivesListener {
        Response get(Request request);
    }
}
