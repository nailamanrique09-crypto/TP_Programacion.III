package com.bank.analysis.inicial;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TransactionGenerator {

    private final Random random = new Random(42);

    private static final String[] DESCS = {
            "Pago de servicios", "Transferencia recibida", "Depósito en cajero",
            "Retiro en sucursal", "Compra con tarjeta", "Pago de nómina",
            "Transferencia enviada", "Depósito en ventanilla", "Retiro en cajero",
            "Pago de factura", "Compra en línea", "Pago recurrente",
            "Depósito de cheque", "Transferencia entre cuentas", "Pago de préstamo",
            "Retiro sin tarjeta", "Depósito móvil", "Pago de impuestos",
            "Transferencia internacional", "Abono de intereses"
    };

    public List<Transaction> generarIniciales() {
        List<Transaction> lista = new ArrayList<>();

        lista.add(new Transaction(1001, 1500.00, TransactionType.DEPOSITO, LocalDate.of(2024, 1, 15), "Depósito en cajero"));
        lista.add(new Transaction(1002, 500.00, TransactionType.RETIRO, LocalDate.of(2024, 1, 16), "Retiro en sucursal"));
        lista.add(new Transaction(1003, 2500.00, TransactionType.TRANSFERENCIA, LocalDate.of(2024, 1, 17), "Transferencia recibida"));
        lista.add(new Transaction(1004, 350.00, TransactionType.PAGO, LocalDate.of(2024, 1, 18), "Pago de servicios"));
        lista.add(new Transaction(1005, 10000.00, TransactionType.DEPOSITO, LocalDate.of(2024, 1, 19), "Depósito en ventanilla"));
        lista.add(new Transaction(1006, 200.00, TransactionType.RETIRO, LocalDate.of(2024, 1, 20), "Retiro en cajero"));
        lista.add(new Transaction(1007, 5000.00, TransactionType.TRANSFERENCIA, LocalDate.of(2024, 1, 21), "Transferencia enviada"));
        lista.add(new Transaction(1008, 750.00, TransactionType.PAGO, LocalDate.of(2024, 1, 22), "Pago de factura"));
        lista.add(new Transaction(1009, 1000.00, TransactionType.PAGO, LocalDate.of(2024, 1, 23), "Compra en línea"));
        lista.add(new Transaction(1010, 200.00, TransactionType.PAGO, LocalDate.of(2024, 1, 24), "Pago recurrente"));

        return lista;
    }

    public List<Transaction> generarAleatorias(int cantidad) {
        List<Transaction> lista = new ArrayList<>();
        TransactionType[] tipos = TransactionType.values();

        for (int i = 0; i < cantidad; i++) {
            long id = 2000 + i;
            double monto = Math.round((100.0 + random.nextDouble() * 50000.0) * 100.0) / 100.0;
            TransactionType tipo = tipos[random.nextInt(tipos.length)];
            int anio = 2024;
            int mes = 1 + random.nextInt(12);
            int dia = 1 + random.nextInt(28);
            LocalDate fecha = LocalDate.of(anio, mes, dia);
            String descripcion = DESCS[random.nextInt(DESCS.length)];

            lista.add(new Transaction(id, monto, tipo, fecha, descripcion));
        }

        return lista;
    }
}