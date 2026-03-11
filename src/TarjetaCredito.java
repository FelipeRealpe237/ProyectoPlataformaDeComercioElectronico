import java.util.Calendar;
import java.util.Scanner;

public class TarjetaCredito extends Pago {

    private final String numeroTarjeta;
    private final String fechaExpiracion;
    private final String codigoSeguridad;

    public TarjetaCredito(String numeroTarjeta, String fechaExpiracion, String codigoSeguridad) {
        this.numeroTarjeta = numeroTarjeta;
        this.fechaExpiracion = fechaExpiracion;
        this.codigoSeguridad = codigoSeguridad;
    }

    public String getNumeroTarjeta() { return numeroTarjeta; }
    public String getFechaExpiracion() { return fechaExpiracion; }
    public String getCodigoSeguridad() { return codigoSeguridad; }

    public static TarjetaCredito solicitarDatos(Scanner scanner) {
        System.out.println("\n  -- Datos de Tarjeta de Credito --");

        String numero = "";
        while (numero.length() != 8) {
            System.out.print("  Numero (8 digitos): ");
            numero = scanner.nextLine().trim();
            if (numero.length() != 8)
                System.out.println("  [!] Debe tener exactamente 8 digitos.");
        }

        String vencimiento = "";
        while (!vencimiento.matches("\\d{2}/\\d{2}") || !tarjetaVigente(vencimiento)) {
            System.out.print("  Vencimiento (MM/AA, ej 08/27): ");
            vencimiento = scanner.nextLine().trim();
            if (!vencimiento.matches("\\d{2}/\\d{2}"))
                System.out.println("  [!] Formato invalido. Use MM/AA.");
            else if (!tarjetaVigente(vencimiento))
                System.out.println("  [!] La tarjeta esta vencida.");
        }

        String codigo = "";
        while (codigo.length() != 3) {
            System.out.print("  Codigo de seguridad (3 dig)  : ");
            codigo = scanner.nextLine().trim();
            if (codigo.length() != 3)
                System.out.println("  [!] Debe tener exactamente 3 digitos.");
        }

        return new TarjetaCredito(numero, vencimiento, codigo);
    }

    private static boolean tarjetaVigente(String fechaExpiracion) {
        try {
            String[] partes = fechaExpiracion.split("/");
            int mes = Integer.parseInt(partes[0]);
            int año = 2000 + Integer.parseInt(partes[1]);
            Calendar hoy = Calendar.getInstance();
            int mesActual = hoy.get(Calendar.MONTH) + 1;
            int añoActual = hoy.get(Calendar.YEAR);
            return año > añoActual || (año == añoActual && mes >= mesActual);
        } catch (Exception excepcion) {
            return false;
        }
    }

    @Override
    public Comprobante procesarPago(double monto) {
        System.out.println("  >> Procesando pago con Tarjeta de Credito...");
        boolean valido = numeroTarjeta != null   && numeroTarjeta.length() == 8
                      && fechaExpiracion != null  && fechaExpiracion.matches("\\d{2}/\\d{2}")
                      && tarjetaVigente(fechaExpiracion)
                      && codigoSeguridad != null  && codigoSeguridad.length() == 3;
        if (!valido) System.out.println("  [!] Tarjeta invalida o vencida.");
        return generarComprobante(valido ? "APROBADO" : "RECHAZADO", monto);
    }
}
