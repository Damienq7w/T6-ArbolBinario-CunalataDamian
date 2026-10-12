package negocio;

import modelo.Nodo;

/**
 * Arbol binario construido nodo por nodo.
 * Capa de negocio: aplica las reglas (una sola raiz, no pisar un hijo
 * que ya existe) y calcula nodos, hojas y altura con recursividad.
 *
 * param <T> tipo del dato que guarda cada nodo
 */
public class ArbolBinario<T> {

    private Nodo<T> raiz;

    /** Crea un arbol vacio sin raiz. */
    public ArbolBinario() {
        this.raiz = null;
    }

    // Consultas basicas

    /** true si el arbol no tiene raiz. */
    public boolean esVacio() {
        return raiz == null;
    }

    public Nodo<T> getRaiz() {
        return raiz;
    }

    /** Deja el arbol vacio otra vez. */
    public void vaciar() {
        raiz = null;
    }

   
    // Insercion

    /**
     * Crea la raiz del arbol.
     * Regla: un arbol solo tiene una raiz.
     */
    public Nodo<T> crearRaiz(T dato) {
        validarDato(dato);
        if (raiz != null) {
            throw new IllegalStateException("El arbol ya tiene raiz: " + raiz.getDato());
        }
        raiz = new Nodo<>(dato);
        return raiz;
    }

    /**
     * Cuelga un hijo nuevo a la izquierda de 'padre'.
     * Regla: no se puede pisar un hijo izquierdo que ya existe.
     */
    public Nodo<T> agregarIzquierdo(Nodo<T> padre, T dato) {
        validarDato(dato);
        validarPadre(padre);
        if (padre.getIzquierdo() != null) {
            throw new IllegalStateException("La posicion izquierda de " + padre.getDato()
                    + " ya esta ocupada por " + padre.getIzquierdo().getDato());
        }
        Nodo<T> nuevo = new Nodo<>(dato);
        padre.setIzquierdo(nuevo);
        return nuevo;
    }

    /**
     * Cuelga un hijo nuevo a la derecha de 'padre'.
     * Regla: no se puede pisar un hijo derecho que ya existe.
     */
    public Nodo<T> agregarDerecho(Nodo<T> padre, T dato) {
        validarDato(dato);
        validarPadre(padre);
        if (padre.getDerecho() != null) {
            throw new IllegalStateException("La posicion derecha de " + padre.getDato()
                    + " ya esta ocupada por " + padre.getDerecho().getDato());
        }
        Nodo<T> nuevo = new Nodo<>(dato);
        padre.setDerecho(nuevo);
        return nuevo;
    }

    /**
     * Reglas para colgar un hijo:
     * el padre no puede ser null, el arbol no puede estar vacio
     * y el padre debe pertenecer a ESTE arbol.
     */
    private void validarPadre(Nodo<T> padre) {
        if (padre == null) {
            throw new IllegalArgumentException("El padre no puede ser null");
        }
        if (esVacio()) {
            throw new IllegalStateException("El arbol esta vacio: primero cree la raiz");
        }
        if (!contiene(raiz, padre)) {
            throw new IllegalArgumentException("El nodo " + padre.getDato()
                    + " no pertenece a este arbol");
        }
    }

    private void validarDato(T dato) {
        if (dato == null) {
            throw new IllegalArgumentException("El dato no puede ser null");
        }
    }

    /** Recorre el arbol buscando ESE nodo (por referencia, no por dato). */
    private boolean contiene(Nodo<T> actual, Nodo<T> buscado) {
        if (actual == null) {
            return false;
        }
        if (actual == buscado) {
            return true;
        }
        return contiene(actual.getIzquierdo(), buscado)
                || contiene(actual.getDerecho(), buscado);
    }

    // Operaciones recursivas
    // Patron: el metodo publico arranca en la raiz y el privado
    // resuelve un subarbol cada vez mas pequeno hasta llegar a null.

    /** Numero total de nodos del arbol. */
    public int contarNodos() {
        return contarNodos(raiz);
    }

    private int contarNodos(Nodo<T> nodo) {
        if (nodo == null) {                 // caso base: subarbol vacio
            return 0;
        }
        return 1 + contarNodos(nodo.getIzquierdo()) + contarNodos(nodo.getDerecho());
    }

    /** Numero de hojas (nodos sin hijos). */
    public int contarHojas() {
        return contarHojas(raiz);
    }

    private int contarHojas(Nodo<T> nodo) {
        if (nodo == null) {                 // caso base 1: no hay nodo
            return 0;
        }
        if (nodo.esHoja()) {                // caso base 2: es hoja, cuenta 1
            return 1;
        }
        return contarHojas(nodo.getIzquierdo()) + contarHojas(nodo.getDerecho());
    }

    /**
     * Altura del arbol medida en aristas:
     * camino mas largo desde la raiz hasta una hoja.
     * Arbol vacio = -1, un solo nodo = 0.
     */
    public int altura() {
        return altura(raiz);
    }

    private int altura(Nodo<T> nodo) {
        if (nodo == null) {                 // caso base: vacio mide -1
            return -1;                      // asi una hoja mide 1 + max(-1, -1) = 0
        }
        int alturaIzq = altura(nodo.getIzquierdo());
        int alturaDer = altura(nodo.getDerecho());
        return 1 + Math.max(alturaIzq, alturaDer);
    }

    /**
     * Busca el primer nodo cuyo dato sea igual a 'dato' (recorrido preorden).
     * Devuelve null si no existe. Si hubiera datos repetidos, devuelve el
     * primero que encuentra en preorden (raiz, izquierdo, derecho).
     * Lo usa el menu para consultar grado y hoja.
     */
    public Nodo<T> buscar(T dato) {
        return buscar(raiz, dato);
    }

    private Nodo<T> buscar(Nodo<T> nodo, T dato) {
        if (nodo == null) {                 // caso base: no se encontro
            return null;
        }
        if (nodo.getDato().equals(dato)) {  // dato nunca es null (validarDato)
            return nodo;
        }
        Nodo<T> encontrado = buscar(nodo.getIzquierdo(), dato);
        if (encontrado != null) {
            return encontrado;
        }
        return buscar(nodo.getDerecho(), dato);
    }

    // =====================================================
    // Visualizacion
    // =====================================================

    /**
     * Devuelve el dibujo del arbol en texto para mostrarlo en consola.
     * I = hijo izquierdo, D = hijo derecho.
     * Devuelve texto (no imprime) para que la capa de negocio
     * no dependa de la consola; Main se encarga de imprimirlo.
     */
    public String mostrarArbol() {
        if (esVacio()) {
            return "(arbol vacio)";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(raiz.getDato()).append("\n");
        dibujarHijos(raiz, "", sb);
        return sb.toString();
    }

    private void dibujarHijos(Nodo<T> nodo, String prefijo, StringBuilder sb) {
        Nodo<T> izq = nodo.getIzquierdo();
        Nodo<T> der = nodo.getDerecho();
        if (izq != null) {
            boolean esUltimo = (der == null);
            dibujarRama(izq, "I", prefijo, esUltimo, sb);
        }
        if (der != null) {
            dibujarRama(der, "D", prefijo, true, sb);
        }
    }

    private void dibujarRama(Nodo<T> hijo, String lado, String prefijo,
                             boolean esUltimo, StringBuilder sb) {
        sb.append(prefijo)
          .append(esUltimo ? "`-- " : "+-- ")
          .append(lado).append(": ")
          .append(hijo.getDato())
          .append("\n");
        String prefijoHijos = prefijo + (esUltimo ? "    " : "|   ");
        dibujarHijos(hijo, prefijoHijos, sb);
    }
}
