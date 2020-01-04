import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Network {
    private int port;
    private OnRequestArrivesListener requestListener;

    public Network(int port, OnRequestArrivesListener l) {
        this.port = port;
        this.requestListener = l;
    }

    public void start() throws IOException {
        // Setup and get client
        ServerSocket serverSocket = new ServerSocket(port);

        while (true) {
            /*
            System.out.println("Continue? (1/0). 1 to continue.");
            if (new Scanner(System.in).nextInt() == 0) break;
             */
            System.out.println("Waiting for connection....");
            Socket clientSocket = serverSocket.accept();
            System.out.println("Got connection from: " + clientSocket.getRemoteSocketAddress());

            // Read data
            String requestString = readStream( clientSocket.getInputStream() );
            Request req = Request.from(requestString);

            // Send back data
            Response res = requestListener.on(req);
            writeString(
                    res.toHttpResponse(),
                    clientSocket.getOutputStream()
            );

            clientSocket.close();
        }
        //serverSocket.close();
    }

    private String readStream(InputStream stream) throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(stream));
        StringBuilder data = new StringBuilder();
        String line;
        while ((line = input.readLine()) != null) {
            System.out.println(line);
            if (line.isEmpty()) break;
            data.append(line).append("\r\n");
        }

        return data.toString();
    }

    private void writeString(String data, OutputStream stream) {
        PrintWriter output = new PrintWriter(stream, true );
        output.write( data );
        output.flush();
        output.close();
    }

    public interface OnRequestArrivesListener {
        Response on(Request r) throws IOException;
    }
}
