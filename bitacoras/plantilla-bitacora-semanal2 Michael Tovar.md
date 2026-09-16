# Bitacora individual - Semana 02

## 1. Datos de la actividad

- **Estudiante:** Michael David Tovar Parada
- **Equipo:** Equipo Sensores IoT
- **Semana:** 2
- **Fecha del laboratorio:** 2026-09-16
- **Fecha del taller:** 2026-09-16
- **Tema principal:** Arreglos dinámicos, redimensionamiento geométrico y encapsulamiento en TAD.
- **Pregunta de la semana:** ¿Cómo gestionar una colección de datos de tamaño variable garantizando que no se pierdan lecturas sin degradar el rendimiento del sistema?

## 2. Prediccion antes de ejecutar

1. **Que creo que va a ocurrir?**
   El programa procesará el archivo CSV y mostrará en consola únicamente 10 lecturas almacenadas, descartando el resto porque el arreglo tiene un tamaño inicial fijo.

2. **Que parte del programa o del algoritmo puede fallar?**
   El método `agregar()` de `RepositorioLecturas`, ya que al alcanzar el límite físico de `lecturas.length`, si no redimensiona, retorna `false` o ignora nuevas inserciones.

3. **Como comprobare mi prediccion?**
   Revisando la salida por consola en `IngestaSensores`: verificaré el valor de "Lecturas almacenadas" frente al total de líneas del archivo `lecturas.csv`.

## 3. Evidencia del laboratorio

### Resultado observado

Al ejecutar inicialmente, la consola reportó solo 10 lecturas almacenadas. Tras implementar el redimensionamiento dinámico y corregir el enlace con `LecturaSensor`, la consola arrojó:
- Lecturas almacenadas: 201
- Descartadas por formato: 2
- Descartadas por rango: 8
- PM2.5 promedio (repositorio): 17.050746268656724

### Diferencia entre la prediccion y el resultado

Se confirmó la predicción: inicialmente se perdían 191 lecturas por el tope rígido de capacidad. Tras la solución, el arreglo creció y almacenó la totalidad de los datos válidos.

### Error o comportamiento inesperado

- **Que ocurrio?** Error de compilación `cannot find symbol: method getIdEstacion()` y advertencia de nombre de archivo no coincidente.
- **Por que ocurrio?** Se intentó invocar un método inexistente; en la clase `LecturaSensor` el getter correcto es `getIdSensor()`.
- **Como lo corregimos o que falta corregir?** Se ajustó la llamada en `buscarPorEstacion()` usando `getIdSensor()`. Queda pendiente para mis compañeros la Fase 4 en `AnalizadorMatriz` para filtrar valores `null` en lugar de ceros.

## 4. Explicacion en lenguaje llano

Imagina que tienes una caja donde solo caben 10 cuadernos. Cuando llega el cuaderno número 11, en vez de botarlo a la basura, compras una caja del doble de tamaño (para 20), pasas los 10 cuadernos viejos a la nueva y guardas el nuevo. Repites esto cada vez que la caja se llena, así nunca te quedas sin espacio.

### Ejemplo o analogia

Es como una libreta de notas con pocas hojas. Cuando se acaba, compras una del doble de hojas, pasas en limpio lo que tenías y sigues escribiendo. La limitación es que pasar en limpio toma tiempo, pero comprar el doble de espacio hace que tengas que cambiar de libreta cada vez con menos frecuencia.

## 5. El vacio que encontre

- **Mi duda concreta es:** ¿Por qué duplicar el tamaño ($2 \times N$) es mejor computacionalmente que sumarle un tamaño fijo (por ejemplo $+10$ o $+1$)?
- **Lo que ya puedo explicar es:** Que cada vez que se crea un nuevo arreglo hay que copiar todos los elementos uno por uno mediante un ciclo.
- **Para resolver la duda consulte:** Discusión técnica con el equipo y análisis de complejidad temporal.
- **Ahora lo entiendo asi:** Duplicar permite que el costo de copiar se distribuya (costo amortizado $O(1)$). Si crecemos de uno en uno, haríamos copias en cada inserción ($O(N^2)$), lo que bloquearía el procesador con archivos grandes.

## 6. Trazado de la solucion

Trazado del crecimiento del arreglo hasta almacenar las 201 lecturas (partiendo de capacidad 10):

| Paso | Estado de los datos o estructura | Decision o resultado |
|---|---|---|
| 1 | Capacidad: 10, Cantidad: 10 | Arreglo lleno. Se dispara `redimensionar()`. Copias: 10. Nueva capacidad: 20. |
| 2 | Capacidad: 20, Cantidad: 20 | Arreglo lleno. Se dispara `redimensionar()`. Copias: 20. Nueva capacidad: 40. |
| 3 | Capacidad: 40, Cantidad: 40 | Arreglo lleno. Se dispara `redimensionar()`. Copias: 40. Nueva capacidad: 80. |
| 4 | Capacidad: 80, Cantidad: 80 | Arreglo lleno. Se dispara `redimensionar()`. Copias: 80. Nueva capacidad: 160. |
| 5 | Capacidad: 160, Cantidad: 160 | Arreglo lleno. Se dispara `redimensionar()`. Copias: 160. Nueva capacidad: 320. |
| Final | Capacidad: 320, Cantidad: 201 | Se almacenaron las 201 lecturas. Total: 5 redimensionamientos y 310 copias. |

## 7. Decision de diseño

- **Problema que debiamos resolver:** Permitir almacenamiento ilimitado y habilitar eliminación de lecturas sin dejar inconsistencias en los cálculos.
- **Estructura, algoritmo o estrategia elegida:** Arreglo dinámico con factor de multiplicación $\times 2$ y eliminación por compactación hacia la izquierda.
- **Alternativa descartada:** Asignar `null` en la posición eliminada sin desplazar los elementos.
- **Por que elegimos la primera:** Si dejamos `null`, métodos como `promedioPm25()` fallan por `NullPointerException` o distorsionan el divisor `cantidad`. Compactar mantiene los datos contiguos.
- **Que evidencia respalda la decision:** Al compactar, el cálculo de `promedioPm25()` arrojó `17.05` de manera limpia y sin errores de acceso.

## 8. Aporte al proyecto

- **Archivo(s) o modulo(s) trabajado(s):** `src/RepositorioLecturas.java`
- **Cambio realizado:** Implementación de `redimensionar()`, `agregar()` dinámico, métricas de copias/redimensionamientos y operaciones del contrato (`obtener`, `tamano`, `eliminar`, `actualizar`, `buscarPorEstacion`).
- **Como se conecta con la capa anterior:** Provee el almacenamiento subyacente para que `IngestaSensores` cargue los datos validados de `LecturaSensor`.
- **Que queda pendiente para la siguiente semana:** Fase 4: manejar valores ausentes en `AnalizadorMatriz` para evitar que ceros afecten los promedios horarios.

## 9. Commits realizados

| Commit | Mensaje | Que demuestra |
|---|---|---|
| *(hash de tu commit)* | `Se modifica RepositorioLecturas para que IngestaSensores y LecturaSensor logren compilar de manera adecuada logrando recorrer las lecturas, comparar el identificador, retornar la primera coincidencia y retornar null en caso de que no exista` | Implementación del contrato del TAD, resolución de dependencias entre clases y redimensionamiento dinámico. |

## 10. Reexplicacion final

La gestión eficiente de colecciones de tamaño desconocido requiere desacoplar la capacidad física del arreglo de la cantidad lógica de elementos. La duplicación geométrica amortiza el costo de reubicación de memoria en $O(1)$, mientras que el encapsulamiento dentro de un TAD garantiza que la capa de análisis consuma los datos sin acoplarse a la estructura interna.

## 11. Reflexion individual

1. **Lo que ahora puedo hacer y antes no podia:**
   Diseñar una estructura que crezca dinámicamente y entender el impacto cuantitativo de las copias en memoria.
2. **El error o supuesto que mas me enseno:**
   Asumir los nombres de los métodos de otras clases sin revisar su contrato previo (`getIdEstacion` vs `getIdSensor`).
3. **La pregunta que llevaria a la proxima clase:**
   ¿Bajo qué umbral de memoria libre conviene reducir la capacidad del arreglo al eliminar elementos?
4. **Que parte del trabajo fue realmente mia:**
   La implementación de la lógica interna de `RepositorioLecturas.java`, la resolución de errores de compilación y la verificación de las métricas de redimensionamiento.

## Lista de verificacion antes de entregar

- [x] Escribi la prediccion antes de consultar el resultado.
- [x] Inclui evidencia concreta del laboratorio.
- [x] Explique un concepto sin depender de jerga.
- [x] Registre un vacio, una duda o un error real.
- [x] Trace al menos un caso paso a paso.
- [x] Justifique una decision del proyecto y una alternativa descartada.
- [x] Registre mis commits y mi aporte individual.
- [x] Deje claro que queda pendiente.
- [x] Renombre el archivo con el formato `sXX-nombre.md`.