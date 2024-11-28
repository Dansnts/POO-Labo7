/**
 * @author Aubry Antoine
 * @author Faria dos Santos Dani Tiago
 */

package calculator;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Classe représentant une pile générique.
 *
 * @param <T> Le type d'éléments stockés dans la pile.
 */
public class Stack<T> implements Iterable<T> {
    private T[] elements;
    private int size = 0;
    private static final int INITIAL_CAPACITY = 10;

    /**
     * Constructeur de la classe Stack.
     * Initialise la pile avec une capacité initiale définie.
     */
    @SuppressWarnings("unchecked")
    public Stack() {
        elements = (T[]) new Object[INITIAL_CAPACITY];
    }

    /**
     * Empile un élément sur la pile.
     *
     * @param item L'élément à empiler.
     */
    public void push(T item) {
        if (size == elements.length) {
            resize(2 * elements.length);  // double la capacité si nécessaire
        }
        elements[size++] = item;
    }

    /**
     * Désempile un élément de la pile.
     *
     * @return L'élément désemparé.
     * @throws NoSuchElementException si la pile est vide.
     */
    public T pop() {
        if (size == 0) {
            throw new NoSuchElementException("La pile est vide");
        }
        T item = elements[--size];
        elements[size] = null;  // pour éviter les fuites de mémoire
        return item;
    }

    /**
     * Retourne une représentation sous forme de chaîne de caractères du contenu de la pile.
     *
     * @return Une chaîne représentant les éléments de la pile.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (int i = size - 1; i >= 0; i--) {
            sb.append(elements[i]);
            if (i > 0) sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }

    /**
     * Retourne un tableau représentant l'état actuel de la pile.
     *
     * @return Un tableau contenant les éléments de la pile.
     */
    public T[] toArray() {
        return Arrays.copyOfRange(elements, 0, size);
    }

    /**
     * Retourne un itérateur pour parcourir les éléments de la pile.
     *
     * @return Un itérateur sur les éléments de la pile.
     */
    @Override
    public Iterator<T> iterator() {
        return new StackIterator();
    }

    /**
     * Vérifie si la pile est vide.
     *
     * @return true si la pile est vide, sinon false.
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Retourne la taille actuelle de la pile.
     *
     * @return Le nombre d'éléments dans la pile.
     */
    public int size() {
        return size;
    }

    /**
     * Classe interne représentant un itérateur pour la pile.
     */
    private class StackIterator implements Iterator<T> {
        private int current = size - 1;

        /**
         * Vérifie s'il reste des éléments à parcourir dans la pile.
         *
         * @return true s'il reste des éléments, sinon false.
         */
        @Override
        public boolean hasNext() {
            return current >= 0;
        }

        /**
         * Retourne l'élément suivant dans la pile.
         *
         * @return L'élément suivant.
         * @throws NoSuchElementException si aucun élément n'est disponible.
         */
        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return elements[current--];
        }
    }

    /**
     * Redimensionne la capacité du tableau d'éléments.
     *
     * @param newCapacity La nouvelle capacité du tableau.
     */
    @SuppressWarnings("unchecked")
    private void resize(int newCapacity) {
        elements = Arrays.copyOf(elements, newCapacity);
    }
}
