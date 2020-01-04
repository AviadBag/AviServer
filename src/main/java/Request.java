import java.util.Arrays;
import java.util.HashMap;

public class Request {
    private String path;
    private RequestType requestType;
    private HashMap<String, String> params;
    private HashMap<String, String> headers;

    private static HashMap<String, String> getHeaders(String[] requestLines) {
        HashMap<String, String> headers = new HashMap<>();

        for (String line : requestLines) {
            String[] keyValue = line.split(": ");
            if (keyValue.length != 2) {
                System.out.println("Invalid http request header. header is: " + line);
                System.exit(-1);
            }
            headers.put(keyValue[0], keyValue[1]);
        }

        return headers;
    }

    /**
     * This function is used for parsing raw request from the client
     * @param raw The request.
     * @return A new Request object, with the data form the raw request.
     */
    public static Request from(String raw) {
        String[] lines = raw.split("\r\n");

        String[] pathRequestWords = lines[0].split(" "); // GET / HTTP/1.1 => ["GET", "/", "HTTP/1.1"

        RequestType requestType = null;
        if (pathRequestWords[0].equals("GET")) requestType = RequestType.GET;
        else if (pathRequestWords[0].equals("POST")) requestType = RequestType.POST;
        else {
            System.out.println("Invalid request type - " + pathRequestWords[0]);
            System.exit(-1);
        }

        String[] pathSplitParams = pathRequestWords[1].split("\\?");

        String path = pathSplitParams[0];

        HashMap<String, String> params = new HashMap<String, String>();
        if (requestType == RequestType.GET) { // GET /index.php?aviad=bagno&matanya=bagno
            if (pathSplitParams.length == 2) { // We have parameters!
                String paramsStr = pathSplitParams[1];
                String[] paramsArr = paramsStr.split("&");
                for (String paramsPair : paramsArr) {
                    System.out.println(paramsPair);
                    String[] keyValue = paramsPair.split("=");
                    params.put(keyValue[0], keyValue[1]);
                }
            }
        }

        if (params.size() == 0) params = null;

        HashMap<String, String> headers = getHeaders(
                Arrays.copyOfRange(lines, 1, lines.length)
        );

        return new Request(path, requestType, params, headers);
    }

    public Request(String path, RequestType requestType, HashMap<String, String> params, HashMap<String, String> headers) {
        this.path = path;
        this.requestType = requestType;
        this.params = params;
        this.headers = headers;
    }

    public String getPath() {
        return this.path;
    }

    public RequestType getRequestType() {
        return requestType;
    }

    public HashMap<String, String> getParams() {
        return this.params;
    }

    public HashMap<String, String> getHeaders() {
        return this.headers;
    }

    public enum RequestType {
        GET, POST
    }
}
