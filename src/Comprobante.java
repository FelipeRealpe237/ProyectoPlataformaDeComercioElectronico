import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Comprobante {

    private final String numeroUnico;
    private final Date fecha;
    private final String estado;
    private final double monto;

    private static final SimpleDateFormat FORMATO_FECHA =
            new SimpleDateFormat("EEEE d 'de' MMMM 'de' yyyy, hh:mm:ss a", new Locale("es", "CO"));

    public Comprobante(String numeroUnico, String estado, double monto) {
        this.numeroUnico = numeroUnico;
        this.fecha = new Date();
        this.estado = estado;
        this.monto = monto;
    }

    public String getNumeroUnico() { return numeroUnico; }
    public Date getFecha(){ return fecha; }
    public String getEstado() { return estado; }
    public double getMonto() { return monto; }

    public void mostrarComprobante() {
        System.out.println("\n  ======= COMPROBANTE DE PAGO =======");
        System.out.println("  Numero : " + numeroUnico);
        System.out.println("  Fecha  : " + FORMATO_FECHA.format(fecha));
        System.out.println("  Estado : " + estado);
        System.out.printf ("  Monto  : $%,.2f%n", monto);
        System.out.println("  ===================================");
    }
}
