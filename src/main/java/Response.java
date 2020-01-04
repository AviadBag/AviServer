import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

public class Response {
    private int code;
    private String codeText;
    private String resBody;
    private String returnType;
    private HashMap<String, String> headers;

    public Response(int code, String resBody, String returnType, HashMap<String, String> headers) {
        this.code = code;
        this.resBody = resBody;
        this.returnType = returnType;
        this.headers = headers;

        switch (code) {
            case 200:
                codeText = "OK";
                break;
            case 404:
                codeText = "NOT FOUND";
                break;
            default:
                System.out.println("CODE IS NOT SUPPORTED");
                System.exit(-1);
        }
    }
    /*
    public String getResBody() {
        return this.resBody;
    }

    public String getReturnType() {
        return this.returnType;
    }

    public HashMap<String, String> getHeaders() {
        return this.headers;
    }*/

    private int getResBodyLength() {
        byte[] utf8Bytes = resBody.getBytes(StandardCharsets.UTF_8);
        return utf8Bytes.length;
    }

    public String toHttpResponse() {
        StringBuilder responseStrB = new StringBuilder((
                "HTTP/1.1 " + code + " " + codeText + "\r\n" +
                        "Content-Length: " + getResBodyLength() + "\r\n" +
                        "Content-Type: " + returnType + "\r\n"
        ));

        if (headers != null) {
            for (String key : headers.keySet()) {
                responseStrB.append(String.format("%s: %s\r\n", key, headers.get(key)));
            }
        }

        responseStrB.append("\r\n");
        responseStrB.append(resBody);

        return responseStrB.toString();
    }
}
