import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.DecimalFormat;

public class ConeccionApi {
    public static void creandoPedido(String monedaBase,
                                     String monedaCambio,
                                     double valor ){
        DecimalFormat formatoDecimal = new DecimalFormat("####.####");
        String direccion = "https://v6.exchangerate-api.com/v6/efdf48fcc4eacb4eef7d87b5/pair/" +
                monedaBase + "/" + monedaCambio + "/" + formatoDecimal.format(valor);
        try{
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(direccion))
                    .build();


            HttpResponse<String> response = client
                        .send(request, HttpResponse.BodyHandlers.ofString());

            String miJson = response.body();

            Gson gson = new GsonBuilder()
                    .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create();

            CapturaConversion miCaptura = gson
                    .fromJson(miJson,CapturaConversion.class);

            System.out.println(miCaptura);

            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }




    }
}
