import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class Server
{
    private HttpServer server;
    private final ThreadPoolExecutor threadPoolExecutor;
    public Server()
    {
        try
        {
            this.server = HttpServer.create(new InetSocketAddress("localhost", 8080), 0);
        }
        catch (IOException e)
        {
            System.out.println("Unable to create the server\n");
            e.printStackTrace();
        }

        //crea un executor con un numero fisso di thread della cpu da poter usare
        threadPoolExecutor = (ThreadPoolExecutor)Executors.newFixedThreadPool(10);
        instantiateServer();
    }

    private void instantiateServer()
    {
        server.createContext("/get_labs", new HttpRequestsHandler());
        server.setExecutor(threadPoolExecutor);
    }

    public void startServer()
    {
        server.start();
        System.out.println(ConsoleColors.ANSI_BLUE + "Server started on port 8080 and it's listening for connections" + ConsoleColors.ANSI_RESET);
    }

}
