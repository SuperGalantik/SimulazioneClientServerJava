import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Objects;

public class HttpRequestsHandler implements HttpHandler
{

    @Override
    public void handle(HttpExchange exchange) throws IOException
    {
        OutputStream outputStream = exchange.getResponseBody();
        //usa equals perchè è null-safe
        if(Objects.equals(exchange.getRequestMethod(), "GET"))
            getRequest(exchange);
        else
        {
            String not_found = "Method not found";
            exchange.sendResponseHeaders(405, not_found.length());
            exchange.getResponseHeaders().set("Content-Type", "text/plain; charset=UTF-8");
            outputStream.write(not_found.getBytes());
            outputStream.flush();
            outputStream.close();
            return;
        }
        handleRequest(exchange, outputStream);
    }

    public void getRequest(HttpExchange exchange)
    {
        System.out.println(ConsoleColors.ANSI_BLUE + "Get request received at uri: " + exchange.getRequestURI().toString() + ConsoleColors.ANSI_RESET);
    }

    public void handleRequest(HttpExchange exchange, OutputStream outputStream)  throws  IOException
    {
        String htmlResponse = getXMLText();
        if(htmlResponse.equals("Document not valid"))
        {
            String srv_err = "Internal server error";
            exchange.sendResponseHeaders(300, srv_err.length());
            exchange.getResponseHeaders().set("Content-Type", "text/plain; charset=UTF-8");
            outputStream.write(srv_err.getBytes());
            outputStream.flush();
            outputStream.close();
            return;
        }

        exchange.getResponseHeaders().set("Content-Type", "text/xml; charset=UTF-8");

        exchange.sendResponseHeaders(200, htmlResponse.length());
        outputStream.write(htmlResponse.getBytes());
        outputStream.flush();
        outputStream.close();
    }

    private String getXMLText()
    {
        if(!XMLValidator.validateXMLSchema(".\\src\\switch_laboratorio.xml", ".\\src\\switch_laboratorio.xsd"))
            return "Document not valid";

        String fileName = ".\\src\\switch_laboratorio.xml"; // Replace with your file path
        StringBuilder xml_code= new StringBuilder();

        try
        {
            String line;
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            while ((line = br.readLine()) != null)
                xml_code.append(line).append("\n");
        }
        catch (IOException e)
        {
            System.err.println("Error reading the file: " + e.getMessage());
            e.printStackTrace();
        }
        return xml_code.toString();
    }
}
