/* ============================================================
   PLATAFORMA DE MONITOREO AMBIENTAL URBANO
   AnalizadorMatriz - FASE 4 COMPLETADA

   Una matriz de 9 estaciones x 24 horas para responder
   preguntas como: a que hora del dia se contamina mas la ciudad,
   y cual estacion sostiene los peores niveles.

   filas    = estaciones (0..8  ->  EST-001..EST-009)
   columnas = horas      (0..23)
   ============================================================ */

public class AnalizadorMatriz {

    private static final int NUM_ESTACIONES = 9;
    private static final int NUM_HORAS = 24;

    private double[][] pm25PorEstacionHora;

    public AnalizadorMatriz() {
        this.pm25PorEstacionHora = new double[NUM_ESTACIONES][NUM_HORAS];

        // Llenamos la matriz con -1.0 para representar la "ausencia de datos"
        // y evitar el problema del "cero mentiroso".
        for (int f = 0; f < NUM_ESTACIONES; f++) {
            for (int h = 0; h < NUM_HORAS; h++) {
                pm25PorEstacionHora[f][h] = -1.0;
            }
        }
    }

    /**
     * Convierte "EST-004" en el indice de fila 3.
     */
    private int indiceDeEstacion(String idSensor) {
        String numero = idSensor.substring(4);
        return Integer.parseInt(numero) - 1;
    }

    /**
     * Ubica una lectura en su celda correspondiente.
     */
    public void registrar(LecturaSensor lectura) {
        int fila = indiceDeEstacion(lectura.getIdSensor());
        int columna = lectura.getHora();
        pm25PorEstacionHora[fila][columna] = lectura.getPm25();
    }

    /**
     * Promedio de PM2.5 de una hora del dia, sobre todas las estaciones.
     */
    public double promedioDeHora(int hora) {
        double suma = 0;
        int estacionesValidas = 0; // Contador de estaciones que sí reportaron

        for (int fila = 0; fila < NUM_ESTACIONES; fila++) {
            // Solo tomamos en cuenta los datos reales, ignorando los huecos (-1.0)
            if (pm25PorEstacionHora[fila][hora] != -1.0) {
                suma = suma + pm25PorEstacionHora[fila][hora];
                estacionesValidas++;
            }
        }

        // Si ninguna estación reportó en esta hora, retornamos -1.0 para evitar error matemático
        if (estacionesValidas == 0) return -1.0;

        return suma / estacionesValidas;
    }

    /**
     * Promedio de PM2.5 de una estacion a lo largo del dia.
     */
    public double promedioDeEstacion(int fila) {
        double suma = 0;
        int horasValidas = 0; // Contador de horas con datos reales

        for (int h = 0; h < NUM_HORAS; h++) {
            // Ignoramos el valor centinela -1.0
            if (pm25PorEstacionHora[fila][h] != -1.0) {
                suma = suma + pm25PorEstacionHora[fila][h];
                horasValidas++;
            }
        }

        // Evitamos división por cero si la estación estuvo caída todo el día
        if (horasValidas == 0) return -1.0;

        return suma / horasValidas;
    }

    /**
     * Hora del dia con mayor contaminacion promedio en la ciudad.
     */
    public int horaMasContaminada() {
        int peorHora = -1;
        double maxPromedio = -1.0; // Guardará el nivel de contaminación más alto encontrado

        for (int h = 0; h < NUM_HORAS; h++) {
            double promedioActual = promedioDeHora(h);

            // Si la hora actual supera el máximo registrado, actualizamos la peor hora
            if (promedioActual > maxPromedio) {
                maxPromedio = promedioActual;
                peorHora = h;
            }
        }

        return peorHora;
    }

    /**
     * Imprime la matriz completa. Util para ver los huecos con tus ojos.
     */
    public void imprimirMatriz() {
        System.out.print("EST\\HORA");
        for (int h = 0; h < NUM_HORAS; h++) {
            System.out.printf("%7s", String.format("%02d", h));
        }
        System.out.println();
        for (int f = 0; f < NUM_ESTACIONES; f++) {
            System.out.printf("EST-%03d ", f + 1);
            for (int h = 0; h < NUM_HORAS; h++) {
                System.out.printf("%7.1f", pm25PorEstacionHora[f][h]);
            }
            System.out.println();
        }
    }
}
