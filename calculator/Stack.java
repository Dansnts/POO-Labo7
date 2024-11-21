package calculator;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Stack<T> implements Iterable<T> {
    private T[] elements;
    private int size = 0;
    private static final int INITIAL_CAPACITY = 10;

    @SuppressWarnings("unchecked")
    public Stack() {
        elements = (T[]) new Object[INITIAL_CAPACITY];
    }

    // Méthode pour empiler un élément
    public void push(T item) {
        if (size == elements.length) {
            resize(2 * elements.length);  // double la capacité si nécessaire
        }
        elements[size++] = item;
    }

    // Méthode pour désempiler un élément
    public T pop() {
        if (size == 0) {
            throw new NoSuchElementException("La pile est vide");
        }
        T item = elements[--size];
        elements[size] = null;  // pour éviter les fuites de mémoire
        return item;
    }

    // Obtenir une représentation en chaîne de caractères du contenu de la pile
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Pile : [");
        for (int i = size - 1; i >= 0; i--) {
            sb.append(elements[i]);
            if (i > 0) sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }

    // Obtenir un tableau représentant l’état actuel de la pile
    public T[] toArray() {
        return Arrays.copyOfRange(elements, 0, size);
    }

    // Itérateur pour parcourir les éléments de la pile
    @Override
    public Iterator<T> iterator() {
        return new StackIterator();
    }

    // Classe interne pour l'itérateur
    private class StackIterator implements Iterator<T> {
        private int current = size - 1;

        @Override
        public boolean hasNext() {
            return current >= 0;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return elements[current--];
        }
    }

    // Méthode interne pour redimensionner le tableau
    @SuppressWarnings("unchecked")
    private void resize(int newCapacity) {
        elements = Arrays.copyOf(elements, newCapacity);
    }
}

