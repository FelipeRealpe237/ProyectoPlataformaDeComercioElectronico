import java.util.List;

public class Producto {

    private final String identificadorProducto;
    private final String nombreProducto;
    private final double precioProducto;
    private int cantidadProducto;
    private String estadoProducto;

    public Producto(String identificadorProducto, String nombreProducto,
                    double precioProducto, int cantidadProducto) {
        this.identificadorProducto = identificadorProducto;
        this.nombreProducto = nombreProducto;
        this.precioProducto = precioProducto;
        this.cantidadProducto = cantidadProducto;
        this.estadoProducto = (cantidadProducto > 0) ? "DISPONIBLE" : "AGOTADO";
    }

    public String getIdentificadorProducto() { return identificadorProducto; }
    public String getNombreProducto() { return nombreProducto; }
    public double getPrecioProducto() { return precioProducto; }
    public int getCantidadProducto() { return cantidadProducto; }
    public String getEstadoProducto() { return estadoProducto; }

    public boolean verificarDisponibilidadProducto() {
        return cantidadProducto > 0 && estadoProducto.equals("DISPONIBLE");
    }

    public void reducirCantidadProducto(int cantidad) {
        if (cantidad > cantidadProducto) {
            System.out.println("  [!] Cantidad insuficiente para: " + nombreProducto);
            return;
        }
        cantidadProducto -= cantidad;
        if (cantidadProducto == 0) estadoProducto = "AGOTADO";
    }

    public static void mostrarCatalogo(List<Producto> listadoProducto) {
        System.out.println("\n  ===== CATALOGO DE PRODUCTOS =====");
        for (int i = 0; i < listadoProducto.size(); i++) {
            System.out.printf("  %d. %s%n", i + 1, listadoProducto.get(i));
        }
        System.out.println("  =================================");
    }

    @Override
    public String toString() {
        return String.format("[%-4s] %-30s | $%,-12.2f | Stock: %-3d | %s",
                identificadorProducto, nombreProducto,
                precioProducto, cantidadProducto, estadoProducto);
    }
}
