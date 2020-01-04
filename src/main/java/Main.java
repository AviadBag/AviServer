import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        Server server = new Server();

        String htmlData;
        try {
            htmlData = new FileReader("src\\main\\resources\\index.html").read();
        } catch (IOException e) {
            System.out.println("File is not exists...");
            return;
        }

        server.getHTML("/index", request -> new Response(
                200,
                htmlData,
                "text/html",
                null
        ));

        server.start();
    }
}
