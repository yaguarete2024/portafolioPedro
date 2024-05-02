import com.google.gson.annotations.SerializedName;

public class CapturaConversion implements Comparable<CapturaConversion> {

    @SerializedName("conversion_result")
    private double resultado;

    @SerializedName("target_code")
    private String divisaCambio;

    public CapturaConversion(double resultado){
        this.resultado = resultado;
    }

    public  CapturaConversion(){

    }

    public double getResultado() {
        return resultado;
    }

    public void setResultado(double resultado) {
        this.resultado = resultado;
    }

    @Override
    public String toString() {
        return "Su cantidad en la divisa es = " + resultado + "(" + divisaCambio + ")";
    }

    @Override
    public int compareTo(CapturaConversion o) {
        return 0;
    }
}
