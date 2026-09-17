/* ============================================================
   PLATAFORMA DE MONITOREO AMBIENTAL URBANO
   TAD RepositorioLecturas - FASES 1, 2 Y 3 COMPLETADAS
   ============================================================ */

public class RepositorioLecturas {

    // 1. Declaramos los atributos privados
    private LecturaSensor[] lecturas;
    private int cantidad;

    // --- FASE 2.1: MEDIR ---
    // Variables para instrumentar y registrar el costo de redimensionar el arreglo.
    private int copiasRealizadas = 0;
    private int redimensionamientos = 0;

    // FASE 1: Constructor que recibe la capacidad inicial fija
    public RepositorioLecturas(int capacidadInicial) {
        lecturas = new LecturaSensor[capacidadInicial];
        cantidad = 0;
    }

    // ---------- OPERACIONES DEL CONTRATO ----------

    /**
     * FASE 2: AGREGAR Y ROMPER EL TECHO
     * Agrega una lectura al final. Si se llena la capacidad, llama a redimensionar().
     */
    public boolean agregar(LecturaSensor lectura) {
        if (cantidad == lecturas.length) {
            redimensionar(); // Rompe el techo duplicando el tamaño
        }
        lecturas[cantidad] = lectura;
        cantidad++;
        return true;
    }

    /**
     * FASE 3:
     * Devuelve la lectura en la posición indicada, validando límites.
     */
    public LecturaSensor obtener(int posicion) {
        if (posicion < 0 || posicion >= cantidad) {
            return null;
        }
        return lecturas[posicion];
    }

    /**
     * FASE 3:
     * Devuelve la cantidad real de elementos almacenados (no la capacidad física).
     */
    public int tamano() {
        return cantidad;
    }

    /**
     * FASE 3:
     * Elimina moviendo los elementos a la izquierda para no dejar huecos
     * y limpia la última referencia duplicada.
     */
    public void eliminar(int posicion) {
        if (posicion >= 0 && posicion < cantidad) {
            for (int i = posicion; i < cantidad - 1; i++) {
                lecturas[i] = lecturas[i + 1];
            }
            lecturas[cantidad - 1] = null;
            cantidad--;
        }
    }

    /**
     * FASE 3:
     * Recorre secuencialmente el arreglo buscando la primera coincidencia por ID.
     */
    public LecturaSensor buscarPorEstacion(String idSensor) {
        for (int i = 0; i < cantidad; i++) {
            if (lecturas[i].getIdSensor().equals(idSensor)) {
                return lecturas[i];
            }
        }
        return null;
    }

    /**
     * FASE 3:
     * Reemplaza una lectura existente validando que la posición sea válida.
     */
    public void actualizar(int posicion, LecturaSensor nueva) {
        if (posicion >= 0 && posicion < cantidad) {
            lecturas[posicion] = nueva;
        }
    }

    /**
     * FASE 2 y 2.1:
     * Duplica la capacidad del arreglo al llenarse, copiando los datos
     * y sumando métricas para comparar la eficiencia en la bitácora.
     */
    private void redimensionar() {
        redimensionamientos++;
        LecturaSensor[] nuevo = new LecturaSensor[lecturas.length * 2];

        for (int i = 0; i < cantidad; i++) {
            copiasRealizadas++;
            nuevo[i] = lecturas[i];
        }

        lecturas = nuevo;
    }

    /**
     * Calcula el promedio general de PM2.5 de forma segura.
     */
    public double promedioPm25() {
        if (cantidad == 0) return 0;

        double suma = 0;
        for (int i = 0; i < cantidad; i++) {
            suma = suma + lecturas[i].getPm25();
        }
        return suma / cantidad;
    }

    // ------ MÉTODOS DE APOYO PARA LA BITÁCORA (Fase 2.1) ------
    public int getCopiasRealizadas() {
        return copiasRealizadas;
    }

    public int getRedimensionamientos() {
        return redimensionamientos;
    }
}
