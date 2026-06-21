package com.bank.analysis.inicial;

import java.util.ArrayList;
import java.util.List;

public class TransactionStore {

    private final List<Transaction> transacciones = new ArrayList<>();

    public void agregar(Transaction transaction) {
        transacciones.add(transaction);
    }

    public void agregarTodas(List<Transaction> lista) {
        transacciones.addAll(lista);
    }

    public List<Transaction> getTransacciones() {
        return new ArrayList<>(transacciones);
    }

    public int size() {
        return transacciones.size();
    }
}
