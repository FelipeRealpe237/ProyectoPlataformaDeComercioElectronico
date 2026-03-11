import java.util.ArrayList;
import java.util.List;

public class Carrito {

    private static final double IVA = 0.19;

    private final List<Producto> articulos;

    public Carrito() {
        this.articulos = new ArrayList<>();
    }

    public List<Producto> getArticulos() { return articulos; }

    public boolean agregarProducto(Producto producto) {
        if (!producto.verificarDisponibilidadProducto()) {
            System.out.println("  [!] '" + producto.getNombreProducto() + "' esta AGOTADO.");
            return false;
        }
        long unidadesEnCarrito = articulos.stream()
                .filter(p -> p.getIdentificadorProducto()
                              .equals(producto.getIdentificadorProducto()))
                .count();
        if (unidadesEnCarrito >= producto.getCantidadProducto()) {
            System.out.println("  [!] Solo hay " + producto.getCantidadProducto()
                    + " unidades disponibles de '" + producto.getNombreProducto() + "'.");
            return false;
        }
        articulos.add(producto);
        System.out.println("  >> '" + producto.getNombreProducto()
                + "' agregado. (Unidades en carrito: " + (unidadesEnCarrito + 1) + ")");
        return true;
    }

    public boolean quitarProducto(Producto producto) {
        boolean eliminado = articulos.remove(producto);
        if (eliminado) {
            System.out.println("  >> '" + producto.getNombreProducto() + "' quitado.");
        } else {
            System.out.println("  [!] Producto no encontrado en el carrito.");
        }
        return eliminado;
    }

    public void vaciarCarrito() {
        articulos.clear();
        System.out.println("  >> Carrito vaciado.");
    }

    public boolean estaVacio() {
        return articulos.isEmpty();
    }

    public double calcularSubtotal() {
        return articulos.stream().mapToDouble(Producto::getPrecioProducto).sum();
    }

    public double calcularTotal() {
        double subtotal = calcularSubtotal();
        return subtotal + (subtotal * IVA);
    }

    public Pedido generarPedido() {
        if (estaVacio()) {
            System.out.println("  [!] El carrito esta vacio. Agregue productos primero.");
            return null;
        }
        String idPedido = "PED-" + System.currentTimeMillis();
        Pedido pedido = new Pedido(idPedido, new ArrayList<>(articulos));
        System.out.println("  >> Pedido generado: " + idPedido);
        return pedido;
    }

    public void mostrarCarrito() {
        System.out.println("\n  ======= CARRITO DE COMPRAS =======");
        if (estaVacio()) {
            System.out.println("  (vacio)");
        } else {
            for (int i = 0; i < articulos.size(); i++) {
                Producto p = articulos.get(i);
                System.out.printf("  %d. %-30s $%,.2f%n",
                        i + 1, p.getNombreProducto(), p.getPrecioProducto());
            }
            double subtotal = calcularSubtotal();
            System.out.printf("%n  Subtotal  : $%,.2f%n", subtotal);
            System.out.printf("  IVA 19%%   : $%,.2f%n", subtotal * IVA);
            System.out.printf("  TOTAL     : $%,.2f%n", calcularTotal());
        }
        System.out.println("  ==================================");
    }
}
