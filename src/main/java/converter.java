import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URLEncoder;
import java.util.Scanner;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;

public class converter {
    public static void main( String[] args ) throws Exception {

        String host = "https://currency-converter-by-api-ninjas.p.rapidapi.com/";
        String charset = "UTF-8";

        String x_rapidapi_host = "currency-converter-by-api-ninjas.p.rapidapi.com";
        String x_rapidapi_key = "af6dee4f19msha4125e9f8840531p1df127jsnc70f48efd7e7";

        String yourCurrency = inputString("What currency do you have that you want to convert?");
        String currencyYouWant = inputString("What currency do you want to convert to?");
        int amount = inputInt("How much are you converting?");

        HttpResponse<String> response = Unirest.get(host+ "v1/convertcurrency?have=" +yourCurrency+"&want="+currencyYouWant+"&amount="+amount )
                .header("X-RapidAPI-Key", x_rapidapi_key)
                .header("X-RapidAPI-Host", x_rapidapi_host)
                .asString();



        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(response.getBody());

        // Extract and print the "link" value
        String new_amount = jsonNode.get("new_amount").asText();
        String old = jsonNode.get("old_currency").asText();
        String New = jsonNode.get("new_currency").asText();
        String old_amount = jsonNode.get("old_amount").asText();

        System.out.println("You converted "+ old_amount +" "+  old+ " to " + New +" which converts to "+ new_amount );

    }

    public static String inputString(String message){
        System.out.println(message);
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();

    }

    public static int inputInt(String message){
        System.out.println(message);
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();

    }
}
