import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Pedido {

    private static final double IVA = 0.19;
    private static final SimpleDateFormat FORMATO_FECHA =
            new SimpleDateFormat("EEEE d 'de' MMMM 'de' yyyy, hh:mm:ss a", new Locale("es", "CO"));

    private final String identificadorPedido;
    private final Date fechaPedido;
    private final List<Producto> productos;
    private final double subtotal;
    private final double impuesto;
    private final double valorTotal;

    public Pedido(String identificadorPedido, List<Producto> productos) {
        this.identificadorPedido = identificadorPedido;
        this.fechaPedido = new Date();
        this.productos = productos;
        this.subtotal = calcularSubtotal();
        this.impuesto = subtotal * IVA;
        this.valorTotal = subtotal + impuesto;
    }

    public String getIdentificadorPedido() { return identificadorPedido; }
    public Date getFechaPedido() { return fechaPedido; }
    public List<Producto> getProductos() { return productos; }
    public double getSubtotal() { return subtotal; }
    public double getImpuesto() { return impuesto; }
    public double getValorTotal() { return valorTotal; }

    public Comprobante procesarPago(Pago pago) {
        if (valorTotal <= 0) {
            System.out.println("  [!] El monto del pedido no puede ser cero o negativo.");
            return new Comprobante("RECHAZADO-" + System.currentTimeMillis(), "RECHAZADO", valorTotal);
        }
        System.out.println("\n  Procesando pago del pedido " + identificadorPedido + "...");
        return pago.procesarPago(valorTotal);
    }

    private double calcularSubtotal() {
        return productos.stream().mapToDouble(Producto::getPrecioProducto).sum();
    }

    public void mostrarPedido() {
        System.out.println("\n  ========== RESUMEN DEL PEDIDO ==========");
        System.out.println("  ID     : " + identificadorPedido);
        System.out.println("  Fecha  : " + FORMATO_FECHA.format(fechaPedido));
        System.out.println("  Productos:");
        for (Producto p : productos) {
            System.out.printf("    - %-30s $%,.2f%n", p.getNombreProducto(), p.getPrecioProducto());
        }
        System.out.printf("%n  Subtotal : $%,.2f%n", subtotal);
        System.out.printf("  IVA 19%% : $%,.2f%n",  impuesto);
        System.out.printf("  TOTAL    : $%,.2f%n",   valorTotal);
        System.out.println("  ========================================");
    }
}
