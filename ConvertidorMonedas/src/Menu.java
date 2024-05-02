import javax.swing.plaf.synth.SynthOptionPaneUI;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu {
    public static void menuInicio(){
        int opcion = 0;

        while (opcion != 7){
            Scanner teclado = new Scanner(System.in);
            System.out.println("*****************************");
            System.out.println("Bienvenido, digite una opción");
            System.out.println("""
                    ************************
                    1. Convertir PEN a ARS
                    2. Convertir USD a PEN
                    3. Convertir USD a ARS
                    4. Convertir USD a BRL
                    5. Convertir ARS a BRL
                    6. Convertir PEN a BRL
                    7. SALIR
                    ************************
                    """);
            try{
                opcion = teclado.nextInt();
                if(opcion < 1 || opcion > 8){
                    System.out.println("Seleccione una opción válida");
                    menuInicio();
                }
            }catch(Exception e){
                System.out.println("Seleccione una opción válida");
                menuInicio();
            }
            if (opcion == 7){break;}
            double value = cantidadDigitadaUsuario();
            switch (opcion){
                case 1:
                    ConeccionApi.creandoPedido("PEN","ARS",value);
                    break;
                case 2:
                    ConeccionApi.creandoPedido("USD","PEN",value);
                    break;
                case 3:
                    ConeccionApi.creandoPedido("USD","ARS",value);
                    break;
                case 4:
                    ConeccionApi.creandoPedido("USD","BRL",value);
                    break;
                case 5:
                    ConeccionApi.creandoPedido("ARS","BRL",value);
                    break;
                case 6:
                    ConeccionApi.creandoPedido("PEN","BRL",value);
                    break;
            }
        }
    }

    private static double cantidadDigitadaUsuario() {

        Scanner teclado2 = new Scanner(System.in);
        double cantidad = 0;
        System.out.println("Ingrese la cantidad a convertir");
        try{
            cantidad = teclado2.nextDouble();

       }catch (InputMismatchException e){
            System.out.println("Digite un número");
            teclado2.nextLine();
        }
        return cantidad;
    }
}
