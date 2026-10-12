package modelo;

/**
 * Nodo de un arbol binario.
 * Guarda un dato y dos enlaces: hijo izquierdo e hijo derecho.
 * Si un enlace vale null, ese hijo no existe.
 *
 * param <T> tipo del dato almacenado
 */
public class Nodo<T> {

    private T dato;
    private Nodo<T> izquierdo;
    private Nodo<T> derecho;

    /** Crea un nodo sin hijos (nace como hoja). */
    public Nodo(T dato) {
        this.dato = dato;
        this.izquierdo = null;
        this.derecho = null;
    }

    /**
     * Un nodo es hoja cuando no tiene hijos,
     * los dos enlaces valen null.
     */
    public boolean esHoja() {
        return izquierdo == null && derecho == null;
    }

    /**
     * Grado = cantidad de hijos del nodo.
     * En un arbol binario solo puede ser 0, 1 o 2.
     */
    public int grado() {
        int hijos = 0;
        if (izquierdo != null) {
            hijos++;
        }
        if (derecho != null) {
            hijos++;
        }
        return hijos;
    }

    // ----- Getters y setters -----

    public T getDato() {
        return dato;
    }

    public void setDato(T dato) {
        this.dato = dato;
    }

    public Nodo<T> getIzquierdo() {
        return izquierdo;
    }

    public void setIzquierdo(Nodo<T> izquierdo) {
        this.izquierdo = izquierdo;
    }

    public Nodo<T> getDerecho() {
        return derecho;
    }

    public void setDerecho(Nodo<T> derecho) {
        this.derecho = derecho;
    }

    @Override
    public String toString() {
        return String.valueOf(dato);
    }
}
