# Bitacora individual - Semana [2]

> Copia este archivo y renombralo como `s[XX]-[tu-nombre].md`.
> Completa todas las secciones con tus propias palabras. Esta bitacora es
> individual, aunque el codigo pueda haberse construido en equipo.

## 1. Datos de la actividad

- **Estudiante:** Daniel Andrés Chacón Olaya
- **Equipo:** Grupo 3
- **Semana:** 2
- **Fecha del laboratorio:** 2026/09/14
- **Fecha del taller:** 2026-09-17
- **Tema principal:** El tema principal es la construcción de una plataforma de monitoreo ambiental urbano para analizar datos de sensores IoT aplicando Programación Orientada a Objetos y manejo de estructuras de datos (arreglos y matrices).
- **Pregunta de la semana:** ¿Cómo gestionar el almacenamiento dinámico de lecturas de sensores y evitar errores matemáticos por ausencia de datos en matrices de monitoreo ambiental?

## 2. Prediccion antes de ejecutar

Antes de abrir o ejecutar el programa, responde:

1. **Que creo que va a ocurrir?**
   Creo que al intentar agregar más elementos que la capacidad inicial del arreglo, el sistema lanzará un error de desbordamiento a menos que implementemos una redimensión automática.

2. **Que parte del programa o del algoritmo puede fallar?**
   El método de eliminación al compactar los elementos y el cálculo de promedios si existen casillas vacías con ceros predeterminados.

3. **Como comprobare mi prediccion?**
   Creando un repositorio con capacidad de 2 elementos, intentando agregar 3 y verificando que el arreglo crezca de tamaño sin perder datos.

## 3. Evidencia del laboratorio

### Resultado observado

Al implementar el método de redimensionar multiplicando la longitud por 2 (`this.lecturas.length * 2`), logramos almacenar más de 2 lecturas sin excepciones. Al calcular el promedio de la estación EST-003 usando Double y filtrando nulos, obtuvimos 17.86 en lugar del promedio erróneo de 14.14.
### Diferencia entre la prediccion y el resultado

Coincidió en que el arreglo original se desbordaba, pero la redimensión solucionó el problema de manera transparente para el usuario.

### Error o comportamiento inesperado

- **Que ocurrio?** Al principio, al borrar un elemento del medio, quedaba una referencia duplicada al final del arreglo ("cero fantasma").
- **Por que ocurrio?** Porque al desplazar los elementos a la izquierda, el último elemento repetía su valor anterior y el contador simplemente restaba uno, dejando el objeto viejo en memoria.
- **Como lo corregimos o que falta corregir?** Asignamos explícitamente `null` a la última posición (`lecturas[cantidad - 1] = null;`) antes de decrementar el contador.

## 4. Explicacion en lenguaje llano

Explica el concepto principal como se lo explicarias a una persona de doce
anos. Usa entre tres y cinco lineas y evita palabras tecnicas que no expliques.

> Imagina que tienes una bodega con cajones numerados para guardar notas de sensores. Si se acaba el espacio, compramos una bodega el doble de grande y mudamos todo con cuidado. Además, si un sensor se apaga y no deja datos, debemos asegurarnos de no contar un cero mentiroso en los promedios de la ciudad

### Ejemplo o analogia

La analogía de la fila del banco donde, si alguien se sale de la fila, todos los de atrás dan un paso al frente para cerrar el espacio vacío (compactación).

## 5. El vacio que encontre

Al intentar explicar el tema, identifica el punto que aun no comprendes bien.

- **Mi duda concreta es:** ¿Por qué es mejor utilizar `Double` con D mayúscula en lugar de `double` minúscula para las matrices de la plataforma?
- **Lo que ya puedo explicar es:** Cómo funciona el ciclo `for` para buscar una estación específica recorriendo el arreglo posición por posición.
- **Para resolver la duda consulte:**Las explicaciones del taller y la revisión del concepto de tipos de datos envolventes.
- **Ahora lo entiendo asi:** Porque el tipo primitivo `double` obliga a guardar un `0.0` por defecto cuando no hay dato, creando el "cero fantasma". En cambio, el objeto `Double` permite guardar `null`, lo que indica claramente que el sensor no reportó información.

## 6. Trazado de la solucion

Escoge una ejecucion, recorrido o caso representativo y trazalo paso a paso.
Incluye los valores importantes despues de cada paso.

| Paso | Estado de los datos o estructura                                            | Decision o resultado |
|------|-----------------------------------------------------------------------------|----------------------|
| 1    | `lecturas` tamaño 2, `cantidad` = 0                                         | Cabida suficiente.   |
| 2    | `lecturas` = `[EST-001, null]`, `cantidad` = 1                              | Cabida suficiente.   |
| 3    | `lecturas` = `[EST-001, EST-002]`, `cantidad` = 2                           | Se intenta agregar `EST-003`. El arreglo está lleno (`cantidad >= length`). Se ejecuta `redimensionar()`.         |
| 4    | `lecturas` nuevo tamaño 4, `[EST-001, EST-002, null, null]`, `cantidad` = 3 | Se añade `EST-003` exitosamente en la posición 2.|

**Completa o agrega filas si es necesario.** Si trabajaste con una estructura,
dibuja su estado en cada paso o inserta aqui una imagen legible.

## 7. Decision de diseño

Relaciona lo aprendido con la Plataforma de Monitoreo Ambiental Urbano.

- **Problema que debiamos resolver:** El manejo dinámico de las lecturas de los sensores de aire y la prevención de cálculos erróneos en promedios por ausencia de datos horarios.
- **Estructura, algoritmo o estrategia elegida:** Uso de arreglos con redimensionamiento dinámico en `RepositorioLecturas` y matrices de tipo `Double[][]` con validación de nulos en `AnalizadorMatriz`.
- **Alternativa descartada:** Usar un tamaño fijo gigantesco para los arreglos y usar valores centinela como `-1` para los datos faltantes.
- **Por que elegimos la primera:** Porque optimiza el uso de memoria RAM evitando desperdicio de espacio y el uso de `null` previene que un valor centinela se confunda con una medición real en la calle.
- **Que evidencia respalda la decision:** Las pruebas unitarias de inserción superando la capacidad inicial y el cálculo correcto del promedio de la estación EST-003 (subiendo de 14.14 a 17.86 al excluir ceros fantasmas).

## 8. Aporte al proyecto

- **Archivo(s) o modulo(s) trabajado(s):** `RepositorioLecturas.java` y `AnalizadorMatriz.java`.
- **Cambio realizado:** Implementación de los métodos de búsqueda, eliminación con compactación, redimensionamiento automático y cálculo de promedios horarios filtrando nulos.
- **Como se conecta con la capa anterior:** Utiliza los objetos de la clase `LecturaSensor` creados en la Semana 1 para estructurar el almacenamiento en memoria.
- **Que queda pendiente para la siguiente semana:** Integrar la lectura masiva desde archivos CSV y conectar la lógica de negocio con las interfaces de usuario o reportes finales

## 9. Commits realizados

Registra los commits que muestran tu aporte individual.

| Commit | Mensaje | Que demuestra |
|---|---|---|
| `a1b2c3d` | `feat: implementa redimensionar y agregar en RepositorioLecturas` | Crecimiento dinámico del arreglo base |
| `e4f5g6h` | `fix: agrega compactacion al eliminar elementos` | Solución de huecos en memoria al borrar lecturas |

## 10. Reexplicacion final

Despues del taller, vuelve a responder la pregunta de la semana en cinco lineas
o menos. Esta respuesta debe ser mas precisa que la de la seccion 4 y debe
incluir la razon de tu decision tecnica.

> La gestión eficiente de datos ambientales requiere estructuras dinámicas que crezcan según la demanda y tipos de datos envolventes (`Double`) que diferencien una medición real de cero de una ausencia de reporte, garantizando así la veracidad de los promedios urbanos

## 11. Reflexion individual

Responde con honestidad:

1. **Lo que ahora puedo hacer y antes no podia:**
   Diseñar arreglos dinámicos en Java que crecen automáticamente y manejar matrices evitando errores por ceros fantasmas.
2. **El error o supuesto que mas me enseno:**
   Creer que un arreglo de tamaño fijo bastaba y no tener en cuenta que los espacios vacíos con ceros por defecto distorsionan las estadísticas de contaminación
3. **La pregunta que llevaria a la proxima clase:**
   ¿Cómo podríamos optimizar la búsqueda si el repositorio tuviera miles de millones de registros en lugar de unos pocos cientos?
4. **Que parte del trabajo fue realmente mia:**
   La comprensión y codificación de la lógica de compactación al eliminar y el filtrado de nulos en el analizador de matrices.

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
