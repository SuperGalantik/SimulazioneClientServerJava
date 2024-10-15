public class Main
{
    public static void main(String[] args)
    {
        Server srv = new Server();
        srv.startServer();

        Client cln;
        String[] response;

        System.out.println(ConsoleColors.ANSI_GREEN + "Client is doing a get request to the server" + ConsoleColors.ANSI_RESET);
        cln = new Client();
        response = cln.sendGetRequest();
        checkResponseCode(response);
        cln = null;

        System.out.println("\n\n"+ConsoleColors.ANSI_GREEN + "Client is doing a POST request to the server" + ConsoleColors.ANSI_RESET);
        cln = new Client();
        cln.sendPostRequest();
        response = cln.sendPostRequest();
        checkResponseCode(response);
    }

    private static void checkResponseCode(String[] response)
    {
        if(Integer.parseInt(response[0])==200)
            System.out.println(ConsoleColors.ANSI_GREEN + "Server response code: " + response[0] + "\nHeaders: "+ response[1] +"\nResponse: \n" + ConsoleColors.ANSI_RESET + response[2]);
        else if(Integer.parseInt(response[0])==300)
        {
            System.err.println("Server responded: " + response[0] + " = " + response[2]);
            return;
        }
        else if(Integer.parseInt(response[0])==405)
        {
            System.err.println("Server responded: " + response[0] + " = " + response[2]);
            return;
        }

        System.out.println(ConsoleColors.ANSI_GREEN + "Parsed xml document: " + ConsoleColors.ANSI_RESET);
        for(String tag : XMLReader.ReadXMLString(response[2])) //sending xml string to XMLReader
        {
            System.out.println(tag);
        }
    }
}