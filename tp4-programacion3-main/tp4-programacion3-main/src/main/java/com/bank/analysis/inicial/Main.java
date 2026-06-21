package com.bank.analysis.inicial;

import java.util.List;
import java.time.LocalDate;

/**
 * Clase principal del paquete "inicial".
 * <p>
 * <b>⚠ CÓDIGO PROCEDURAL - VIOLACIONES INTENCIONALES:</b>
 * <ul>
 * <li>Usa {@code new} directamente para crear dependencias (sin DI)</li>
 * <li>Toda la lógica está en el {@code main} (sin separación de
 * responsabilidades)</li>
 * <li>Usa {@code System.out.println} directamente (sin logging)</li>
 * <li>Sin manejo de excepciones</li>
 * </ul>
 * <p>
 * Este código representa el "antes" de la refactorización: sin Spring,
 * sin inyección de dependencias, sin separación de capas.
 */

public class Main {

    public static void main(String[] args) {
        System.out.println("==========================================");
        System.out.println("  SISTEMA DE ANÁLISIS DE TRANSACCIONES");
        System.out.println("==========================================");
        System.out.println();

        // Genera y almacena transacciones
        TransactionStore store = new TransactionStore();
        TransactionGenerator generator = new TransactionGenerator();
        TransactionProcessor procesador = new TransactionProcessor(store, generator);

        // Agrega más transacciones aleatorias
        procesador.generarTransacciones(100);

        System.out.println("Total de transacciones en memoria: " + procesador.getTransacciones().size());
        System.out.println();

        System.out.println("=== BÚSQUEDA POR ID ===");
        Transaction encontrada = procesador.buscarPorId(1005);
        System.out.println("Resultado buscarPorId(1005): " + encontrada);

        Transaction noEncontrada = procesador.buscarPorId(9999);
        System.out.println("Resultado buscarPorId(9999): " + noEncontrada);
        System.out.println();

        System.out.println("=== BÚSQUEDA POR MONTO ===");
        List<Transaction> porMonto = procesador.buscarPorMonto(1000.0, 5000.0);
        System.out.println("Transacciones entre $1,000 y $5,000: " + porMonto.size());
        System.out.println();

        System.out.println("=== BÚSQUEDA POR TIPO ===");
        List<Transaction> depositos = procesador.buscarPorTipo("DEPOSITO");
        System.out.println("Total de DEPOSITOS: " + depositos.size());
        System.out.println();

        System.out.println("=== ORDENAMIENTO MANUAL ===");
        List<Transaction> ordenadasManual = procesador.ordenarManual(false);
        procesador.imprimir(ordenadasManual, 5);
        System.out.println();

        System.out.println("=== ORDENAMIENTO BUILT-IN ===");
        List<Transaction> ordenadasBuiltIn = procesador.ordenarBuiltIn(true);
        procesador.imprimir(ordenadasBuiltIn, 5);
        System.out.println();

        System.out.println("=== FILTRO AVANZADO ===");
        List<Transaction> filtradas = procesador.filtrarAvanzado(
                "DEPOSITO",
                1000.0,
                20000.0,
                LocalDate.of(2024, 1, 1),
                LocalDate.of(2024, 3, 31)
        );
        System.out.println("Depositos filtrados: " + filtradas.size());
        procesador.imprimir(filtradas, 5);
        System.out.println();

        System.out.println("=== BALANCE TOTAL ===");
        double balance = procesador.calcularBalance();
        System.out.printf("Balance total: $%,.2f%n", balance);
        System.out.println();

        System.out.println("=== CONTEO POR TIPO ===");
        procesador.mostrarConteoPorTipo();
    }
}
