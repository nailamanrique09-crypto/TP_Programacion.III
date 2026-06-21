package com.bank.analysis.inicial;

import java.util.Objects;
import java.time.LocalDate;


//! correcciones: campos privados, enum de tipo de transacción, campos no mutables, nombres descriptivos, fecha como LocalDate, getters para los campos final

/**
 * Representa una transacción bancaria.
 * <p>
 * <b>⚠ CÓDIGO SUCIO - CON MALAS PRÁCTICAS INTENCIONALES:</b>
 * Esta clase viola intencionalmente principios de encapsulamiento y buenas
 * prácticas
 * para que los estudiantes identifiquen los problemas y los corrijan en la
 * versión refactorizada.
 * <ul>
 * <li>Todos los campos son públicos (sin encapsulamiento)</li>
 * <li>Usa {@code String} para el tipo en lugar de un {@code enum}</li>
 * <li>Campos no finales (mutables sin control)</li>
 * <li>Nombres poco descriptivos (mnt, fec, desc)</li>
 * <li>La fecha se almacena como String en lugar de {@code LocalDate}</li>
 * </ul>
 */
public class Transaction {

    /** Identificador único de la transacción */
    private final long id;

    /** Monto de la transacción */
    private final double monto;

    /**
     * Tipo de transacción como {@code TransactionType}.
     * Valores posibles: "DEPOSITO", "RETIRO", "TRANSFERENCIA", "PAGO"
     */
    private final TransactionType tipo;

    /**
     * Fecha de la transacción en {@code LocalDate}.
     */
    private final LocalDate fecha;

    /** Descripción de la transacción (nomenclatura poco clara) */
    private final String descripcion;

    /**
     * Constructor con todos los campos.
     *
     * @param id         identificador de la transacción
     * @param monto      monto de la transacción
     * @param tipo       tipo de transacción como TransactionType
     * @param fecha      fecha como LocalDate
     * @param descripcion descripción de la transacción
     */

    //! constructor y atributos final
    public Transaction(long id, double monto, TransactionType tipo, LocalDate fecha, String descripcion) {
        this.id = id;
        this.monto = monto;
        this.tipo = Objects.requireNonNull(tipo, "tipo de transacción no puede ser nulo");
        this.fecha = Objects.requireNonNull(fecha, "no puede ser nula la fecha");
        this.descripcion = Objects.requireNonNull(descripcion, "no puede ser nula la descripcion");
    }

    //! getters para los campos final
    public long getId() {
        return id;
    }

    public double getMonto() {
        return monto;
    }

    public TransactionType getTipo() {
        return tipo;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    //! toString para los campos final
    @Override
    public String toString() {
        return "Transaction{id=" + getId() + ", monto=" + getMonto() + ", tipo=" + getTipo() + ", fecha=" + getFecha() + ", descripcion='" + getDescripcion()  
                + "'}";
    }

    //! equals para los campos final sirve para la comparación de igualdad en collection
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Transaction that = (Transaction) o;
        return id == that.getId() && Double.compare(monto, that.getMonto()) == 0 && getTipo().equals(that.getTipo()) && Objects.equals(fecha, that.getFecha())
                && Objects.equals(descripcion, that.getDescripcion());
    }

    //! hashCode para los campos final sirve para la comparación de igualdad en collection
    @Override
    public int hashCode() {
        return Objects.hash(id, getMonto());
    }
}
