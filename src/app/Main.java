package app;

import java.util.Scanner;

import modelo.Nodo;
import negocio.ArbolBinario;

public class Main {

    // Numero de la opcion para salir del programa
    private static final int OPCION_SALIR = 11;

    // Scanner unico para todo el programa (leer del teclado)
    private static final Scanner sc = new Scanner(System.in);

    // El arbol con el que trabaja el menu
    private static final ArbolBinario<String> arbol = new ArbolBinario<>();

    // Contadores que usa la opcion "Ejecutar casos de prueba"
    private static int aprobados;
    private static int totales;

    /**
     * Arranca el programa y mantiene el menu funcionando. Muestra el menu
     * lee la opcion con leerEntero y con un switch llama al metodo que le
     * corresponde a cada opcion. Todo esto se repite hasta que el usuario
     * elige salir, y al final se cierra el Scanner.
     */
    public static void main(String[] args) {
        int opcion;
        do {
            mostrarMenu();
            opcion = leerEntero("Seleccione una opcion: ");
            System.out.println();
            switch (opcion) {
                case 1: crearArbolInicial(); break;
                case 2: visualizar(); break;
                case 3: System.out.println("Numero de nodos: " + arbol.contarNodos()); break;
                case 4: System.out.println("Numero de hojas: " + arbol.contarHojas()); break;
                case 5: System.out.println("Altura del arbol (aristas): " + arbol.altura()); break;
                case 6: consultarGrado(); break;
                case 7: verificarHoja(); break;
                case 8: insertarDependencia(); break;
                case 9: vaciarArbol(); break;
                case 10: ejecutarCasosDePrueba(); break;
                case OPCION_SALIR: System.out.println("Fin del programa."); break;
                default: System.out.println("Opcion no valida. Intente de nuevo.");
            }
            System.out.println();
        } while (opcion != OPCION_SALIR);
        sc.close();
    }

    /**
     * Imprime las opciones del menu en consola. Las opciones 1 a 7 y Salir
     * son las que pide el enunciado, la 8, 9 y 10 se agregaron para poder
     * demostrar en vivo los casos de prueba un solo nodo, arbol vacio y
     * posicion ocupada.
     */
    private static void mostrarMenu() {
        System.out.println("==============================================");
        System.out.println("  ORGANIZACION JERARQUICA - ARBOL BINARIO");
        System.out.println("==============================================");
        System.out.println("  1. Crear el arbol inicial");
        System.out.println("  2. Visualizar el arbol");
        System.out.println("  3. Consultar el numero de nodos");
        System.out.println("  4. Consultar el numero de hojas");
        System.out.println("  5. Consultar la altura");
        System.out.println("  6. Consultar el grado de un nodo");
        System.out.println("  7. Verificar si un nodo es hoja");
        System.out.println("  8. Insertar una dependencia");
        System.out.println("  9. Vaciar el arbol");
        System.out.println(" 10. Ejecutar casos de prueba");
        System.out.println(" 11. Salir");
        System.out.println("----------------------------------------------");
    }

    /**
     * Opcion 1. Arma el arbol que pide el enunciado y lo muestra en pantalla.
     * Si ya habia un arbol creado, primero lo vacia para no intentar crear
     * una segunda raiz, y despues lo construye de nuevo con
     * construirArbolInicial y lo dibuja con mostrarArbol.
     */
    private static void crearArbolInicial() {
        if (!arbol.esVacio()) {
            System.out.println("El arbol ya existia: se vuelve a construir desde cero.");
            arbol.vaciar();
        }
        construirArbolInicial(arbol);
        System.out.println("Arbol inicial creado correctamente.");
        System.out.println(arbol.mostrarArbol());
    }

    /**
     * Primero crea la raiz y luego va colgando cada hijo en su lado con
     * agregarIzquierdo y agregarDerecho. Los nodos que despues van a ser
     * padres (universidad, fisei, dide y software se guardan en variables
     * para poder colgarles sus hijos. El arbol se recibe por parametro para
     * poder reutilizar este mismo metodo en los casos de prueba con un arbol
     * distinto al del menu.
     */
    private static void construirArbolInicial(ArbolBinario<String> a) {
        Nodo<String> universidad = a.crearRaiz("UNIVERSIDAD");

        Nodo<String> fisei = a.agregarIzquierdo(universidad, "FISEI");
        Nodo<String> dide = a.agregarDerecho(universidad, "DIDE");

        Nodo<String> software = a.agregarIzquierdo(fisei, "SOFTWARE");
        a.agregarDerecho(fisei, "TI");

        a.agregarIzquierdo(dide, "INVESTIGACION");
        a.agregarDerecho(dide, "VINCULACION");

        a.agregarIzquierdo(software, "NIVEL1");
        a.agregarDerecho(software, "NIVEL3");
    }

    /**
     * Opcion 2. Imprime el dibujo del arbol. Le pide el texto a mostrarArbol
     * de ArbolBinario y aqui solo se imprime, porque la que dibuja es la capa
     * de negocio y la que muestra en consola es Main.
     */
    private static void visualizar() {
        System.out.println(arbol.mostrarArbol());
    }

    /**
     * Opcion 6. Muestra cuantos hijos tiene una dependencia. Pide el nombre
     * con pedirNodo, que lo busca en el arbol, y si el nodo existe imprime
     * su grado usando el metodo grado de Nodo.
     */
    private static void consultarGrado() {
        Nodo<String> nodo = pedirNodo("Nombre de la dependencia: ");
        if (nodo != null) {
            System.out.println("Grado de " + nodo.getDato() + ": " + nodo.grado() + " hijo(s)");
        }
    }

    /**
     * Opcion 7. Dice si una dependencia es hoja, es decir si no tiene hijos.
     * Busca el nodo con pedirNodo y le pregunta esHoja; si no es hoja tambien
     * indica cuantos hijos tiene usando grado.
     */
    private static void verificarHoja() {
        Nodo<String> nodo = pedirNodo("Nombre de la dependencia: ");
        if (nodo != null) {
            if (nodo.esHoja()) {
                System.out.println(nodo.getDato() + " SI es hoja (no tiene hijos).");
            } else {
                System.out.println(nodo.getDato() + " NO es hoja (tiene "
                        + nodo.grado() + " hijo(s)).");
            }
        }
    }

    /**
     * Opcion 8. Agrega una dependencia nueva escrita desde el teclado. Si el
     * arbol esta vacio, lo que se escriba pasa a ser la raiz, y asi se puede
     * armar el caso de un arbol con un solo nodo. Si el arbol ya tiene nodos,
     * pide el padre y el lado I o D, si ese lado ya tiene un hijo avisa de
     * una vez que la posicion esta ocupada y si esta libre pide el nombre
     * nuevo revisa que no este vacio ni repetido y lo cuelga con
     * agregarIzquierdo o agregarDerecho. Todo va dentro de un try catch para
     * que, si ArbolBinario rechaza la insercion, se muestre el mensaje del
     * error y el programa siga funcionando sin caerse.
     */
    private static void insertarDependencia() {
        try {
            if (arbol.esVacio()) {
                String nombre = leerNombre("El arbol esta vacio. Nombre de la raiz: ");
                if (nombre.isEmpty()) {
                    System.out.println("El nombre no puede estar vacio.");
                    return;
                }
                arbol.crearRaiz(nombre);
                System.out.println("Raiz " + nombre + " creada.");
            } else {
                Nodo<String> padre = pedirNodo("Nombre del padre: ");
                if (padre == null) {
                    return;
                }
                String lado = leerNombre("Lado (I = izquierdo, D = derecho): ");
                if (!lado.equals("I") && !lado.equals("D")) {
                    System.out.println("Lado no valido. Use I o D.");
                    return;
                }
                // Se revisa aqui si el lugar ya esta ocupado, antes de pedir el nombre
                Nodo<String> ocupante = lado.equals("I") ? padre.getIzquierdo() : padre.getDerecho();
                if (ocupante != null) {
                    System.out.println("No se pudo insertar: la posicion "
                            + (lado.equals("I") ? "izquierda" : "derecha") + " de " + padre.getDato()
                            + " ya esta ocupada por " + ocupante.getDato());
                    return;
                }
                String nombre = leerNombre("Nombre de la nueva dependencia: ");
                if (nombre.isEmpty()) {
                    System.out.println("El nombre no puede estar vacio.");
                    return;
                }
                if (arbol.buscar(nombre) != null) {
                    System.out.println("Ya existe una dependencia llamada " + nombre + ".");
                    return;
                }
                if (lado.equals("I")) {
                    arbol.agregarIzquierdo(padre, nombre);
                } else {
                    arbol.agregarDerecho(padre, nombre);
                }
                System.out.println(nombre + " agregado como hijo "
                        + (lado.equals("I") ? "izquierdo" : "derecho") + " de " + padre.getDato() + ".");
            }
            System.out.println(arbol.mostrarArbol());
        } catch (IllegalStateException | IllegalArgumentException e) {
            System.out.println("No se pudo insertar: " + e.getMessage());
        }
    }

    /**
     * Opcion 9. Deja el arbol sin nodos llamando a vaciar. Sirve para
     * demostrar el caso del arbol vacio (0 nodos y altura -1) y, junto con la
     * opcion 8, el caso de un arbol con un solo nodo.
     */
    private static void vaciarArbol() {
        arbol.vaciar();
        System.out.println("El arbol ahora esta vacio.");
    }

    /**
     * Pide un nombre al usuario y devuelve el nodo que tiene ese nombre. Si el
     * arbol esta vacio avisa y devuelve null sin pedir nada. Si no, lee el
     * nombre con leerNombre y lo busca con buscar de ArbolBinario; si no
     * existe, avisa y tambien devuelve null. Lo usan las opciones 6, 7 y 8.
     */
    private static Nodo<String> pedirNodo(String mensaje) {
        if (arbol.esVacio()) {
            System.out.println("El arbol esta vacio. Use primero la opcion 1 u 8.");
            return null;
        }
        String nombre = leerNombre(mensaje);
        Nodo<String> nodo = arbol.buscar(nombre);
        if (nodo == null) {
            System.out.println("No existe la dependencia '" + nombre + "' en el arbol.");
        }
        return nodo;
    }

    /**
     * Opcion 10. Prueba de una sola vez los cinco casos
     * arbol vacio 0 nodos, 0 hojas, altura -1, un solo nodo 1 nodo, 1 hoja,
     * altura 0, arbol inicial 9 nodos, 5 hojas, altura 3, insertar en una
     * posicion ocupada debe rechazarse y el grado de FISEI y SOFTWARE 2 y 2.
     * Cada caso trabaja con su propio arbol para no tocar el arbol del menu, y
     * cada resultado se compara con verificar, que marca OK o FALLA. Al final
     * muestra cuantas verificaciones salieron correctas.
     */
    private static void ejecutarCasosDePrueba() {
        aprobados = 0;
        totales = 0;

        System.out.println("CASO 1: Arbol vacio");
        ArbolBinario<String> vacio = new ArbolBinario<>();
        System.out.println(vacio.mostrarArbol());
        verificar("esVacio()", true, vacio.esVacio());
        verificar("contarNodos()", 0, vacio.contarNodos());
        verificar("contarHojas()", 0, vacio.contarHojas());
        verificar("altura()", -1, vacio.altura());
        System.out.println();

        System.out.println("CASO 2: Arbol con un solo nodo");
        ArbolBinario<String> uno = new ArbolBinario<>();
        Nodo<String> solo = uno.crearRaiz("UNIVERSIDAD");
        System.out.print(uno.mostrarArbol());
        verificar("contarNodos()", 1, uno.contarNodos());
        verificar("contarHojas()", 1, uno.contarHojas());
        verificar("altura()", 0, uno.altura());
        verificar("raiz.esHoja()", true, solo.esHoja());
        System.out.println();

        System.out.println("CASO 3: Arbol inicial");
        ArbolBinario<String> inicial = new ArbolBinario<>();
        construirArbolInicial(inicial);
        System.out.print(inicial.mostrarArbol());
        verificar("contarNodos()", 9, inicial.contarNodos());
        verificar("contarHojas()", 5, inicial.contarHojas());
        verificar("altura()", 3, inicial.altura());
        System.out.println();

        // En el caso 4 se espera que salte el error: si salta, la prueba pasa
        System.out.println("CASO 4: Insertar en una posicion ocupada");
        Nodo<String> fisei = inicial.buscar("FISEI");
        try {
            inicial.agregarIzquierdo(fisei, "CIVIL");
            verificar("rechaza insercion en FISEI.izquierdo", true, false);
        } catch (IllegalStateException e) {
            System.out.println("  Mensaje: " + e.getMessage());
            verificar("rechaza insercion en FISEI.izquierdo", true, true);
        }
        try {
            inicial.crearRaiz("OTRA_RAIZ");
            verificar("rechaza una segunda raiz", true, false);
        } catch (IllegalStateException e) {
            System.out.println("  Mensaje: " + e.getMessage());
            verificar("rechaza una segunda raiz", true, true);
        }
        verificar("contarNodos() sigue igual", 9, inicial.contarNodos());
        System.out.println();

        System.out.println("CASO 5: Grado de FISEI y SOFTWARE");
        verificar("FISEI.grado()", 2, inicial.buscar("FISEI").grado());
        verificar("SOFTWARE.grado()", 2, inicial.buscar("SOFTWARE").grado());
        System.out.println();

        System.out.println("RESULTADO: " + aprobados + " de " + totales + " verificaciones correctas.");
    }

    /**
     * Compara un resultado con el valor que deberia dar. Cada vez que se llama
     * suma uno al total, y si lo esperado y lo obtenido son iguales (equals)
     * suma uno a los aprobados. Imprime OK o FALLA junto con los dos valores
     * para que se vea claramente que se esperaba y que salio.
     */
    private static void verificar(String descripcion, Object esperado, Object obtenido) {
        totales++;
        boolean ok = esperado.equals(obtenido);
        if (ok) {
            aprobados++;
        }
        System.out.println("  [" + (ok ? "OK" : "FALLA") + "] " + descripcion
                + " -> esperado: " + esperado + ", obtenido: " + obtenido);
    }

    /**
     * Lee un numero del teclado, que es la opcion del menu. Si el usuario
     * escribe letras el programa no se cae, atrapa el error de
     * Integer.parseInt y vuelve a pedir el numero. Si la entrada se termina
     * por ejemplo al cerrar la consola devuelve la opcion de salir.
     */
    private static int leerEntero(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            if (!sc.hasNextLine()) {
                return OPCION_SALIR;
            }
            String linea = sc.nextLine().trim();
            try {
                return Integer.parseInt(linea);
            } catch (NumberFormatException e) {
                System.out.println("Ingrese un numero valido.");
            }
        }
    }

    /**
     * Lee un texto del teclado, le quita los espacios de los lados y lo pasa
     * a mayusculas, asi "fisei" y "FISEI" se toman como lo mismo al buscar.
     * Si ya no hay entrada devuelve un texto vacio.
     */
    private static String leerNombre(String mensaje) {
        System.out.print(mensaje);
        if (!sc.hasNextLine()) {
            return "";
        }
        return sc.nextLine().trim().toUpperCase();
    }
}
