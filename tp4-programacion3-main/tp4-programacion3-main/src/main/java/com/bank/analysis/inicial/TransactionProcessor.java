package com.bank.analysis.inicial;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

//! Correcciones hechas: dejamos acá solo los métodos, separamos generación y almacenamiento de datos

/**
 * <b>⚠ GOD CLASS - CÓDIGO SUCIO INTENCIONAL:</b>
 * Esta clase concentra demasiadas responsabilidades: generación de datos,
 * almacenamiento, búsqueda, ordenamiento, filtrado e impresión.
 * <p>
 * Violaciones de SOLID y buenas prácticas presentes:
 * <ul>
 * <li><b>Single Responsibility:</b> La clase hace búsqueda, ordenamiento y
 * filtrado</li>
 * <li><b>Open/Closed:</b> Los métodos {@code buscarPorTipo} y
 * {@code filtrarAvanzado} usan cadenas de if-else que requieren modificar la
 * clase para agregar nuevos tipos</li>
 * <li><b>Alto acoplamiento:</b> Todo el código depende de la implementación
 * concreta</li>
 * <li><b>Baja cohesión:</b> Métodos que no están relacionados conviven en la
 * misma clase</li>
 * </ul>
 */

public class TransactionProcessor {

    private final TransactionStore store;
    private final TransactionGenerator generator;

    public TransactionProcessor(TransactionStore store, TransactionGenerator generator) {
        this.store = store;
        this.generator = generator;

        // Carga inicial de datos fijos
        this.store.agregarTodas(generator.generarIniciales());
    }

    public void generarTransacciones(int cantidad) {
        store.agregarTodas(generator.generarAleatorias(cantidad));
    }

    public List<Transaction> getTransacciones() {
        return store.getTransacciones();
    }

    public Transaction buscarPorId(long id) {
        for (Transaction transaction : store.getTransacciones()) {
            if (transaction.getId() == id) {
                return transaction;
            }
        }
        return null;
    }

    public List<Transaction> buscarPorMonto(double montoMin, double montoMax) {
        List<Transaction> resultado = new ArrayList<>();

        for (Transaction transaccion : store.getTransacciones()) {
            if (transaccion.getMonto() >= montoMin && transaccion.getMonto() <= montoMax) {
                resultado.add(transaccion);
            }
        }

        return resultado;
    }

    public List<Transaction> buscarPorTipo(String tipo) {
        List<Transaction> resultado = new ArrayList<>();

        for (Transaction transaccion : store.getTransacciones()) {
            if (transaccion.getTipo() != null && transaccion.getTipo().name().equalsIgnoreCase(tipo)) {
                resultado.add(transaccion);
            }
        }

        return resultado;
    }

    public List<Transaction> ordenarManual(boolean ascendente) {
        List<Transaction> transaccionesOrdenadas = new ArrayList<>(store.getTransacciones());

        for (int i = 0; i < transaccionesOrdenadas.size() - 1; i++) {
            for (int j = 0; j < transaccionesOrdenadas.size() - 1 - i; j++) {
                double actual = transaccionesOrdenadas.get(j).getMonto();
                double siguiente = transaccionesOrdenadas.get(j + 1).getMonto();

                boolean debeIntercambiar = ascendente
                        ? actual > siguiente
                        : actual < siguiente;

                if (debeIntercambiar) {
                    Transaction temp = transaccionesOrdenadas.get(j);
                    transaccionesOrdenadas.set(j, transaccionesOrdenadas.get(j + 1));
                    transaccionesOrdenadas.set(j + 1, temp);
                }
            }
        }

        return transaccionesOrdenadas;
    }

    public List<Transaction> ordenarBuiltIn(boolean ascendente) {
        List<Transaction> transaccionesOrdenadas = new ArrayList<>(store.getTransacciones());

        Comparator<Transaction> comparator = Comparator.comparing(Transaction::getMonto);

        if (!ascendente) {
            comparator = comparator.reversed();
        }

        transaccionesOrdenadas.sort(comparator);
        return transaccionesOrdenadas;
    }

    public List<Transaction> filtrarAvanzado(String tipo, Double montoMin, Double montoMax,
                                             LocalDate fechaDesde, LocalDate fechaHasta) {
        return store.getTransacciones().stream()
                .filter(t -> {
                    if (tipo != null && !tipo.isEmpty()) {
                        if (t.getTipo() == null || !t.getTipo().name().equalsIgnoreCase(tipo)) {
                            return false;
                        }
                    }

                    if (montoMin != null && t.getMonto() < montoMin) {
                        return false;
                    }

                    if (montoMax != null && t.getMonto() > montoMax) {
                        return false;
                    }

                    if (fechaDesde != null && t.getFecha().isBefore(fechaDesde)) {
                        return false;
                    }

                    if (fechaHasta != null && t.getFecha().isAfter(fechaHasta)) {
                        return false;
                    }

                    return true;
                })
                .collect(Collectors.toList());
    }

    public void imprimir(List<Transaction> lista, int maxMostrar) {
        int limite = maxMostrar > 0 ? Math.min(maxMostrar, lista.size()) : lista.size();

        for (int i = 0; i < limite; i++) {
            System.out.println(lista.get(i));
        }

        if (maxMostrar > 0 && limite < lista.size()) {
            System.out.println("... y " + (lista.size() - limite) + " más");
        }
    }

    public long contarPorTipo(String tipo) {
        long contador = 0;

        for (Transaction transaccion : store.getTransacciones()) {
            if (transaccion.getTipo() != null && transaccion.getTipo().name().equalsIgnoreCase(tipo)) {
                contador++;
            }
        }

        return contador;
    }

    public void mostrarConteoPorTipo() {
        Map<TransactionType, Long> conteo = store.getTransacciones().stream()
                .collect(Collectors.groupingBy(Transaction::getTipo, Collectors.counting()));

        System.out.println("=== Conteo por tipo ===");
        System.out.println("DEPOSITOS: " + conteo.getOrDefault(TransactionType.DEPOSITO, 0L));
        System.out.println("RETIROS: " + conteo.getOrDefault(TransactionType.RETIRO, 0L));
        System.out.println("TRANSFERENCIAS: " + conteo.getOrDefault(TransactionType.TRANSFERENCIA, 0L));
        System.out.println("PAGOS: " + conteo.getOrDefault(TransactionType.PAGO, 0L));
    }

    public double calcularBalance() {
        return store.getTransacciones().stream()
                .mapToDouble(transaction -> {
                    switch (transaction.getTipo()) {
                        case DEPOSITO:
                        case TRANSFERENCIA:
                            return transaction.getMonto();
                        case RETIRO:
                        case PAGO:
                            return -transaction.getMonto();
                        default:
                            return 0;
                    }
                })
                .sum();
    }
}