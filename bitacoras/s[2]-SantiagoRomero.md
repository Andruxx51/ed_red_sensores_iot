# Bitácora individual - Semana II

## 1. Datos de la actividad
*   **Estudiante:** Santiago Romero Oviedo
*   **Equipo:** Grupo 3 (creo)
*   **Semana:** 2
*   **Fecha del laboratorio:** 2026/09/14
*   **Fecha del taller:** 2026-09-17
*   **Tema principal:** El tema principal es la construcción de una plataforma de monitoreo ambiental urbano para analizar datos de sensores IoT aplicando Programación Orientada a Objetos y manejo de estructuras de datos (arreglos y matrices).

**Preguntas de la semana:**
1.  **Definición de TAD:** Un TAD es un contrato que agrupa un conjunto de datos y define las operaciones exactas que se pueden realizar sobre ellos. Su propósito es ocultar los detalles internos de cómo está construido el código, permitiendo que otros programadores usen la estructura como si fuera una caja negra, enfocándose solo en qué hace y no en cómo lo hace.
2.  **Comparación de redimensionamiento:** Considerando que de las 211 filas del archivo se almacenan 201 lecturas válidas, partiendo de una capacidad inicial de 10:
    *   *Crecimiento de uno en uno:* Realizaría 20,055 copias. Para guardar el elemento 11 copia 10 previos, para el 12 copia 11, y así sucesivamente hasta sumar desde 10 hasta 200.
    *   *Crecimiento por duplicación:* Realizó exactamente 310 copias. El arreglo creció en los límites de 10, 20, 40, 80 y 160, copiando esa misma cantidad de elementos en cada salto.
    *   *Conclusión:* Duplicar la capacidad es inmensamente más eficiente. Transforma un costo computacional destructivo (cuadrático) en un costo manejable, ahorrando más del 98% de las operaciones de memoria al reducir drásticamente las veces que el arreglo debe ser reconstruido.
3.  **Estrategia de eliminación:** Elegí la estrategia de Compactar.
    *   *Justificación:* Al eliminar una lectura, el código desplaza los elementos de la derecha hacia la izquierda y limpia la última referencia (`lecturas[cantidad - 1] = null`). Aunque esto requiere un ciclo para mover los datos, garantiza que no queden "huecos" nulos en el centro del arreglo. Esto mantiene la variable `cantidad` como un reflejo exacto del total de datos, permitiendo que cálculos mucho más frecuentes (como el promedio o la búsqueda) se ejecuten rápido y sin tener que validar nulos celda por celda.
4.  **El problema del cero fantasma:** Lo resolví utilizando `-1.0` como valor centinela. Rellené la matriz completa con `-1.0` al inicio y luego condicioné los ciclos para que ignoraran ese valor al sumar o contar las celdas válidas.
    *   *¿Por qué descarté otras alternativas?* Usar `Double[][]` (objetos) en lugar de `double[][]` (primitivos) para tener `null`: Consumiría muchísima más memoria RAM y ralentizaría los cálculos matemáticos debido al proceso de unboxing de Java. Crear una matriz booleana paralela (true/false para datos válidos): Duplicaría el uso de memoria innecesariamente y haría que el código de lectura fuera más complejo de mantener.
5.  **Escalabilidad de la búsqueda:** Sí, seguiría siendo una solución completamente adecuada.
    *   En el peor de los casos (la estación no existe o es la última), el código realizará 8,000 comparaciones. Un procesador moderno ejecuta miles de millones de instrucciones por segundo; recorrer 8,000 posiciones de un arreglo toma menos de 1 milisegundo. Para este volumen de datos tan pequeño, implementar y mantener estructuras más complejas (como un mapa hash o un árbol) añadiría un peso innecesario sin aportar una mejora de velocidad perceptible para el usuario.

---

## 2. Predicción antes de ejecutar
*   **¿Qué creo que va a ocurrir?** Yo creo que lo que va a ocurrir es un avance acerca del análisis de las lecturas y lo que hace falta acerca de la manera cómo va a entregar los datos ya que en la primera semana se lograron cosas mucho más superficiales que no alcanzaban a tocar temas como estos y pienso que es el siguiente paso a seguir.
*   **¿Qué parte del programa o del algoritmo puede fallar?** Yo creo que pueden fallar los análisis de lecturas.
*   **¿Cómo comprobaré mi predicción?** Ejecutando el código, viendo cuál es el error que manda en el terminal y comprobándolo desde el código.

---

## 3. Evidencia del laboratorio
*   **Resultado observado:** No funcionó en un principio pero al probar una solución logró funcionar tomando como datos de entrada las lecturas del sensor y dando datos como el total de lecturas almacenadas o promedio de pm2.5 que necesitan de un análisis.
*   **Diferencia entre la predicción y el resultado:** Coincidieron casi en su totalidad; por culpa de que no se podían asimilar de forma correctas las lecturas ni las matrices creadas no daba resultados.

**Error o comportamiento inesperado:**
*   **¿Qué ocurrió?** El problema se vio en el código de `RepositorioLecturas` y `AnalizadorMatriz` en mayor medida, dando desde un principio error en la terminal, hasta que `RepositorioLecturas` pudo ser arreglado, a parte, sin el analizador de matrices no se podría dar a conocer los resultados esperados desde un principio y este poseía errores como en el double.
*   **¿Por qué ocurrió?** Por falta de redimensionamiento, en malos usos de abstracciones y constructores.
*   **¿Cómo lo corregimos o qué falta corregir?** El paso a seguir es corregir el código, principalmente con los constructores para dar con una solución.

---

## 4. Explicación en lenguaje llano
Imagina que tienes un cuaderno donde anotas cuánto llovió cada día. Si un día se te olvida anotar, el espacio queda en blanco. Pero en programación, la computadora a veces llena esos espacios vacíos con ceros automáticamente. Si alguien ve ese cero, pensará que no llovió nada, cuando en realidad simplemente no tienes el dato, lo que arruinaría tus promedios.

**Ejemplo o analogía:**
Es como hacer un examen de 10 preguntas. Si respondes 9 bien y dejas 1 en blanco porque no te dio tiempo, el profesor no debería promediarte esa última como un "0" en tu conocimiento, sino simplemente calificar las 9 que sí respondiste. La analogía deja de ser exacta en el mundo real porque en la escuela a veces sí te penalizan con un cero por no responder, pero en la ciencia de datos, la ausencia de información jamás debe inventar resultados artificiales.

---

## 5. El vacío que encontré
*   **Mi duda concreta es:** ¿Por qué la matriz de Java se inicializa sola con `0.0` y no simplemente se queda vacía o en estado `null`?
*   **Lo que ya puedo explicar es:** Entiendo perfectamente que sumar esos ceros automáticos arruina el promedio de contaminación de las horas en las que una estación se desconectó.
*   **Para resolver la duda consulté:** Gemini y videos de YT.
*   **Ahora lo entiendo así:** Los datos primitivos (como `double`) son tan básicos que no tienen el concepto de "vacío" (`null`) para ahorrar memoria. Por diseño de la máquina virtual, siempre deben contener un valor, y su valor predeterminado de fábrica es cero. Por eso nos toca inventar "valores centinela" como `-1.0` para avisarle a nuestro propio programa que ahí no hay datos reales.

---

## 6. Trazado de la solución
*   **Paso 1:** `cantidad = 10`, tamaño del arreglo `lecturas = 10`. El sistema detecta que el arreglo está lleno al evaluar `cantidad == lecturas.length` e invoca el método `redimensionar()`.
*   **Paso 2:** El nuevo arreglo aún no existe en memoria. Se declara y crea `LecturaSensor[] nuevo = new LecturaSensor[lecturas.length * 2]`, lo que reserva espacio para 20 elementos.
*   **Paso 3:** El arreglo original tiene 10 datos y el nuevo está completamente vacío. Un ciclo `for` copia uno por uno los 10 objetos existentes al nuevo arreglo, y la variable `copiasRealizadas` se incrementa en 10.
*   **Paso 4:** Ambos arreglos contienen la misma información temporalmente. El programa reemplaza la referencia descartando el arreglo viejo mediante la instrucción `lecturas = nuevo`, dándole al repositorio una nueva capacidad física de 20.
*   **Paso 5:** `cantidad = 10`, tamaño del arreglo `lecturas = 20`. Se ejecuta finalmente la inserción original con `lecturas[cantidad] = lectura`, y la variable `cantidad` se incrementa a 11 con la instrucción `cantidad++`.

---

## 7. Decisión de diseño
*   **Problema que debíamos resolver:** Las estaciones a veces no reportan datos a ciertas horas. Al guardar esto en una matriz `double[][]`, Java colocaba `0.0`, bajando artificialmente el promedio de contaminación.
*   **Estructura, algoritmo o estrategia elegida:** Llenar la matriz con un valor centinela (`-1.0`) en el constructor y usar condicionales `if` para ignorarlo al calcular promedios.
*   **Alternativa descartada:** Cambiar el tipo de dato de primitivo `double[][]` a objeto `Double[][]` para poder insertar valores `null`.
*   **Por qué elegimos la primera:** Usar objetos `Double[][]` consume muchísima más memoria y hace que las operaciones matemáticas sean más lentas por el proceso de conversión automática de Java (unboxing). Usar `-1.0` es rápido, ligero y efectivo.
*   **Qué evidencia respalda la decisión:** En la ejecución final de la Fase 4, observamos que las horas caídas de la EST-003 en la mañana arrojaron promedios estables (entre 11.18 y 12.15), confirmando que los "huecos" fueron ignorados con éxito.

---

## 8. Aporte al proyecto
*   **Archivo(s) o módulo(s) trabajado(s):** `RepositorioLecturas.java` y `AnalizadorMatriz.java`.
*   **Cambio realizado:** Se implementó el crecimiento dinámico de arreglos mediante duplicación (Fase 2) y se corrigió el cálculo de promedios descartando ausencias de datos con un valor centinela (Fase 4).
*   **Cómo se conecta con la capa anterior:** Estos módulos son alimentados directamente por `IngestaSensores.java`, que lee el archivo CSV, filtra los datos inválidos apoyándose en `LecturaSensor.java`, y envía solo los registros limpios al Repositorio y al Analizador.
*   **Qué queda pendiente para la siguiente semana:** Implementar una interfaz gráfica o exportar los perfiles horarios a un nuevo archivo CSV para que un analista de datos pueda visualizar la curva de contaminación de la ciudad.

---

## 9. Commit realizado
> `[c48a2c0] Se resuelve el problema del 0 mentiroso y se corrigen promedios ignorando celdas sin datos`
> Se implementó el valor centinela -1.0 en AnalizadorMatriz para descartar las horas sin datos en el cálculo de promedios, completando la Fase 4.

---

## 10. Respuestas técnicas precisas
1.  **Definición de TAD:** Un TAD es un modelo lógico que agrupa datos y define las operaciones permitidas sobre ellos, funcionando como un contrato estricto. Oculta la lógica interna del código, permitiendo usar la estructura como una caja negra. Así, el programador solo necesita saber qué hace el objeto para utilizarlo correctamente, sin preocuparse de cómo está construido por dentro.
2.  **Comparación de redimensionamiento:** Considerando que a partir de la capacidad inicial de 10 se insertan 191 lecturas válidas nuevas:
    *   *Crecimiento de uno en uno:* Realizó exactamente 20,055 copias. Esto corresponde a la sumatoria desde 10 hasta 200 (para insertar el elemento 11 copias 10, para el 12 copias 11, etc.).
    *   *Crecimiento por duplicación:* Realizó exactamente 310 copias. El arreglo se redimensionó en los umbrales de 10, 20, 40, 80 y 160, copiando esa cantidad de elementos en cada evento.
    *   *Conclusión:* El crecimiento de uno en uno tiene una complejidad temporal cuadrática $O(N^2)$ que destruye el rendimiento. Al duplicar, logramos un costo amortizado casi lineal $O(1)$, reduciendo el desperdicio de procesamiento en más del 98%.
3.  **Estrategia de eliminación:** Elegí Compactar.
    *   *Justificación:* Al desplazar los elementos a la izquierda y asignar `lecturas[cantidad - 1] = null`, aseguro la contigüidad de la memoria y evito los "huecos". Esto es vital porque permite que la variable cantidad refleje el divisor exacto de los datos reales, asegurando que métodos estadísticos como `promedioPm25()` se calculen en tiempo lineal $O(N)$ sin exigir costosas validaciones de nulos celda por celda.
4.  **Resolución del cero fantasma:** Lo resolví asignando `-1.0` (un valor centinela fuera del rango físico de PM2.5) a toda la matriz en el constructor, condicionando las sumas a ignorar este valor.
    *   *Alternativas descartadas:* Cambiar `double[][]` por `Double[][]` (objetos): Lo descarté porque usar wrappers para permitir nulos exige instanciar memoria por cada celda, triplicando el consumo de RAM e introduciendo latencia por unboxing matemático. Matriz paralela booleana (indicadores true/false): Lo descarté porque duplicaría la complejidad espacial al requerir mantener dos matrices sincronizadas para representar una sola entidad de dominio.
5.  **Escalabilidad de la búsqueda:** Sí, sigue siendo adecuada.
    *   En el peor caso de búsqueda lineal, el algoritmo ejecutará 8,000 comparaciones. Un procesador moderno que opera a ~3.0 GHz ejecuta alrededor de 3 mil millones de ciclos por segundo. Realizar 8,000 comparaciones en memoria RAM primaria le toma una fracción diminuta, típicamente < 0.05 milisegundos. Para un $N = 8000$, la sobrecarga de instanciar y mantener una estructura de datos más pesada (como una tabla Hash) superaría el supuesto beneficio de velocidad.

---

## 11. Reflexión individual y Lista de verificación
*   **Lo que ahora puedo hacer y antes no podía:** Puedo identificar más clases de errores, usar de mejor manera los constructores y diferentes clases de objetos y atributos.
*   **El error o supuesto que más me enseñó:** Las fallas en las lecturas.
*   **La pregunta que llevaría a la próxima clase:** ¿De qué sirven los símbolos `++` en el código tal como en `fila++` cuando se trata de matrices?
*   **Qué parte del trabajo fue realmente mía:** La identificación del problema y la solución creada para los arreglos de matrices.

**Lista de verificación antes de entregar:**
- [x] Escribí la predicción antes de consultar el resultado.
- [x] Incluí evidencia concreta del laboratorio.
- [x] Expliqué un concepto sin depender de jerga.
- [x] Registré un vacío, una duda o un error real.
- [x] Tracé al menos un caso paso a paso.
- [x] Justifiqué una decisión del proyecto y una alternativa descartada.
- [x] Registré mis commits y mi aporte individual.
- [x] Dejé claro qué queda pendiente.
- [x] Renombré el archivo con el formato.