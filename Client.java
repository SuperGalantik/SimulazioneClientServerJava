import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import static java.time.temporal.ChronoUnit.SECONDS;

public class Client
{
    private HttpClient client;
    public Client()
    {
        client = HttpClient.newHttpClient();
    }

    public String[] sendGetRequest()
    {
        String[] str_resp = new String[3];

        try
        {
            HttpRequest req = HttpRequest.newBuilder().uri(new URI("http://localhost:8080/get_labs")).timeout(Duration.of(2, SECONDS)).GET().build();
            getResponse(str_resp, req);
        }
        catch (URISyntaxException e)
        {
            e.printStackTrace();
        }

        return str_resp;
    }

    private void getResponse(String[] str_resp, HttpRequest req)
    {
        try
        {
            HttpResponse<String> response = client.send(req, HttpResponse.BodyHandlers.ofString());
            str_resp[0] = String.valueOf(response.statusCode());
            if (response.statusCode() == 200)
            {
                str_resp[1] = response.headers().toString();
                str_resp[2] = response.body();
            } else if (response.statusCode() == 300)
                str_resp[2] = "Internal server error";
            else if (response.statusCode() == 405)
                str_resp[2] = "Method not valid";
        }
        catch (IOException | InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    public String[] sendPostRequest()
    {
        String[] str_resp = new String[3];

        try
        {
            HttpRequest req = HttpRequest.newBuilder().uri(new URI("http://localhost:8080/get_labs")).timeout(Duration.of(2, SECONDS)).POST(HttpRequest.BodyPublishers.noBody()).build();
            getResponse(str_resp, req);
        }
        catch (URISyntaxException e)
        {
            e.printStackTrace();
        }

        return str_resp;
    }

}
