//! crear enum TransactionType con los tipos de transacciones posibles
//! DEPOSITO, RETIRO, TRANSFERENCIA, PAGO

package com.bank.analysis.inicial;

public enum TransactionType {
    DEPOSITO, RETIRO, TRANSFERENCIA, PAGO;

    public TransactionType getTipo() {
    return this;
}
}
