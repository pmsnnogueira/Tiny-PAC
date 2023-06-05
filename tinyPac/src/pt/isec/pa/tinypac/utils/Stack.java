package pt.isec.pa.tinypac.utils;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Stack<T> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private List<T> lista;

    public <T> Stack() {
        lista = new ArrayList<>();
    }

    public boolean empty(){
        return (lista.isEmpty());
    }

    //Obter o ultimo objeto da pilha
    public T peek(){
        return (lista.get(lista.size() - 1));
    }

    //Obter o ultimo objeto da pilha e apagar
    public T pop(){
        T valor = null;

        if(!empty()) {
            valor = peek();
            lista.remove(lista.size() - 1);
        }

        return (valor);

    }

    //Colocar um objeto na ultima posicao da pilha
    public void push(T valor){
        lista.add(valor);
    }

    public void clear() {
        lista.clear();
    }
}

