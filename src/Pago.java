public abstract class Pago {

    public abstract Comprobante procesarPago(double monto);

    public Comprobante generarComprobante(String estado, double monto) {
        String numeroUnico = "COMP-" + System.currentTimeMillis();
        return new Comprobante(numeroUnico, estado, monto);
    }
}
