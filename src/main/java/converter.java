import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Scanner;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;


public class converter {
    public static void main( String[] args ) throws Exception {

        String yourCurrency ="";
        String currencyYouWant="";
        String YorNoCode = inputString("You need the currency codes to be able to convert, do you want to get the currency codes? \n"  +"Say y or n.");
        if (YorNoCode.equals("y")){

            Yoption(yourCurrency,currencyYouWant);

        }
        else{
            Noption(yourCurrency,currencyYouWant);
        }

    }
    public static void Yoption(String yourCurrency, String currencyYouWant){
        try{
            System.out.println("Enter the first countries name you want to convert");
            yourCurrency = getCode();
            System.out.println("Enter the countries name you want to get the new converted amount");
            currencyYouWant=getCode();
            converting(yourCurrency, currencyYouWant);

        }
        catch(Exception e){
            System.out.println("Entered wrong details try again");
            Yoption(yourCurrency,currencyYouWant);
        }

    }

    public static void Noption(String yourCurrency, String currencyYouWant){
        try{
            yourCurrency = askingForInitial();
            currencyYouWant = askingForNew();
            converting(yourCurrency, currencyYouWant);
        }
        catch (Exception e){
            System.out.println("Entered wrong details try again");
            Noption(yourCurrency,currencyYouWant);
        }


    }

    public static void converting(String yourCurrency, String currencyYouWant) throws Exception {
        String host = "https://currency-converter-by-api-ninjas.p.rapidapi.com/";
        String x_rapidapi_host = "currency-converter-by-api-ninjas.p.rapidapi.com";
        String x_rapidapi_key = "af6dee4f19msha4125e9f8840531p1df127jsnc70f48efd7e7";


            int amount = inputInt("How much are you converting?");

            HttpResponse<String> response = Unirest.get(host + "v1/convertcurrency?have=" + yourCurrency + "&want=" + currencyYouWant + "&amount=" + amount)
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

            System.out.println("You converted " + old_amount + " " + old + " to " + New + " which converts to " + new_amount);



    }

    public static String askingForInitial(){
        return inputString("What currency do you have that you want to convert?");
    }

    public static String askingForNew(){
        return inputString("What currency do you want to convert to?");
    }

    public static String getCode() throws Exception {

        String countryName = inputString("Enter:");
        String url = "https://restcountries.com/v2/name/" + countryName;
        HttpResponse<String> response = Unirest.get(url).asString();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(response.getBody());
        String currencyCode = jsonNode.get(0).get("currencies").get(0).get("code").asText();
        System.out.println(currencyCode);
        System.out.println();
        return currencyCode;
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
