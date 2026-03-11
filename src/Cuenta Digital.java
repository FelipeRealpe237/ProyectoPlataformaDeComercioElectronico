import java.util.Scanner;

public class CuentaDigital extends Pago {

    private static final String[] DOMINIOS_VALIDOS =
            {"@gmail.com", "@unimayor.edu.co"};

    private final String correo;
    private final String proveedor;

    public CuentaDigital(String correo, String proveedor) {
        this.correo = correo;
        this.proveedor = proveedor;
    }

    public String getCorreo() { return correo; }
    public String getProveedor() { return proveedor; }

    public static CuentaDigital solicitarDatos(Scanner scanner) {
        System.out.println("\n  -- Datos de Cuenta Digital --");

        String correo = "";
        while (!correoValido(correo)) {
            System.out.print("  Correo (ej: nombre@gmail.com o @unimayor.edu.co) : ");
            correo = scanner.nextLine().trim();
            if (!correoValido(correo))
                System.out.println("  [!] Use @gmail.com, @unimayor.edu.co");
        }

        System.out.print("  Proveedor (Nequi/Bancolombia/Daviplata) : ");
        String proveedor = scanner.nextLine().trim();

        return new CuentaDigital(correo, proveedor);
    }

    private static boolean correoValido(String correo) {
        if (correo == null || correo.isEmpty()) return false;
        for (String dominio : DOMINIOS_VALIDOS) {
            if (correo.contains(dominio)) return true;
        }
        return false;
    }

    @Override
    public Comprobante procesarPago(double monto) {
        System.out.println("  >> Procesando pago con Cuenta Digital (" + proveedor + ")...");
        return generarComprobante(correoValido(correo) ? "APROBADO" : "RECHAZADO", monto);
    }
}
