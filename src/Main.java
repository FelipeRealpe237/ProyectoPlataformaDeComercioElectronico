import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final Carrito carrito = new Carrito();
    private static final List<Producto> catalogo = cargarCatalogo();

    public static void main(String[] args) {
        mostrarMenuPrincipal();
        scanner.close();
    }

    private static void mostrarMenuPrincipal() {
        while (true) {
            System.out.println("\n╔══════════════════════════════════════╗");
            System.out.println("║   PLATAFORMA DE COMERCIO ELECTRONICO ║");
            System.out.println("╠══════════════════════════════════════╣");
            System.out.println("║  1. Ver catalogo de productos        ║");
            System.out.println("║  2. Agregar producto al carrito      ║");
            System.out.println("║  3. Quitar producto del carrito      ║");
            System.out.println("║  4. Ver carrito                      ║");
            System.out.println("║  5. Vaciar carrito                   ║");
            System.out.println("║  6. Generar pedido y pagar           ║");
            System.out.println("║  0. Salir                            ║");
            System.out.println("╚══════════════════════════════════════╝");
            System.out.print("  Opcion: ");

            switch (leerEntero()) {
                case 1: Producto.mostrarCatalogo(catalogo); break;
                case 2: agregarAlCarrito(); break;
                case 3: quitarDelCarrito(); break;
                case 4: carrito.mostrarCarrito(); break;
                case 5: carrito.vaciarCarrito(); break;
                case 6: generarPedidoYPagar(); break;
                case 0:
                    System.out.println("\n  Hasta luego!\n");
                    return;
                default:
                    System.out.println("  [!] Opcion no valida.");
            }
        }
    }

    private static void agregarAlCarrito() {
        Producto.mostrarCatalogo(catalogo);
        System.out.print("  Numero del producto (0 para cancelar): ");
        int opcion = leerEntero();
        if (opcion == 0) return;
        if (opcion < 1 || opcion > catalogo.size()) {
            System.out.println("  [!] Numero invalido.");
            return;
        }
        carrito.agregarProducto(catalogo.get(opcion - 1));
    }

    private static void quitarDelCarrito() {
        carrito.mostrarCarrito();
        if (carrito.estaVacio()) return;
        System.out.print("  Numero del producto a quitar (0 para cancelar): ");
        int opcion = leerEntero();
        if (opcion == 0) return;
        if (opcion < 1 || opcion > carrito.getArticulos().size()) {
            System.out.println("  [!] Numero invalido.");
            return;
        }
        carrito.quitarProducto(carrito.getArticulos().get(opcion - 1));
    }

    private static void generarPedidoYPagar() {
        carrito.mostrarCarrito();
        Pedido pedido = carrito.generarPedido();
        if (pedido == null) return;
        pedido.mostrarPedido();

        Pago pago = seleccionarMetodoPago();
        if (pago == null) return;

        Comprobante comprobante = pedido.procesarPago(pago);
        comprobante.mostrarComprobante();

        if (comprobante.getEstado().equals("APROBADO")) {
            pedido.getProductos().forEach(p -> p.reducirCantidadProducto(1));
            carrito.vaciarCarrito();
            System.out.println("  >> Compra realizada con exito.");
        } else {
            System.out.println("  >> Pago rechazado. Revise los datos e intente de nuevo.");
        }
    }

    private static Pago seleccionarMetodoPago() {
        System.out.println("\n  Seleccione metodo de pago:");
        System.out.println("  1. Tarjeta de Credito");
        System.out.println("  2. Cuenta Digital");
        System.out.println("  0. Cancelar");
        System.out.print("  Opcion: ");

        switch (leerEntero()) {
            case 1: return TarjetaCredito.solicitarDatos(scanner);
            case 2: return CuentaDigital.solicitarDatos(scanner);
            default:
                System.out.println("  >> Pago cancelado.");
                return null;
        }
    }

    public static int leerEntero() {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException excepcion) {
            return -1;
        }
    }

    private static List<Producto> cargarCatalogo() {
        List<Producto> lista = new ArrayList<>();
        lista.add(new Producto("P001", "Laptop Lenovo IdeaPad", 2800000.0, 10));
        lista.add(new Producto("P002", "Mouse Inalambrico", 85000.0, 15));
        lista.add(new Producto("P003", "Teclado Mecanico", 220000.0,  0));
        lista.add(new Producto("P004", "Monitor Samsung 24 pulgadas", 950000.0,  5));
        lista.add(new Producto("P005", "Audifonos Sony", 750000.0,  8));
        return lista;
    }
}


