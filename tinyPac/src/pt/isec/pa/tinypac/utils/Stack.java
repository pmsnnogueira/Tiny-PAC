package pt.isec.pa.tinypac.utils;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A generic stack implementation that stores objects of type T.
 * @param <T> The type of objects stored in the stack.
 *
 * @author Pedro Nogueira
 * @version 1.0
 * @since 06/2023
 */
public class Stack<T> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private List<T> lista;

    /**
     * Creates an empty stack.
     */
    public <T> Stack() {
        lista = new ArrayList<>();
    }

    /**
     * Checks if the stack is empty.
     * @return {@code true} if the stack is empty, {@code false} otherwise.
     */
    public boolean empty(){
        return (lista.isEmpty());
    }

    /**
     * Retrieves the top element of the stack without removing it.
     * @return The top element of the stack.
     */
    public T peek(){
        return (lista.get(lista.size() - 1));
    }

    /**
     * Retrieves and removes the top element of the stack.
     * @return The top element of the stack.
     */
    public T pop(){
        T valor = null;

        if(!empty()) {
            valor = peek();
            lista.remove(lista.size() - 1);
        }

        return (valor);
    }

    /**
     * Pushes an element onto the top of the stack.
     * @param valor The element to be pushed onto the stack.
     */
    public void push(T valor){
        lista.add(valor);
    }

    /**
     * Removes all elements from the stack, making it empty.
     */
    public void clear() {
        lista.clear();
    }
}

