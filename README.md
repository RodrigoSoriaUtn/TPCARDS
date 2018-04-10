# TPCARDS
tercer tp

### Persistencia utilizada:
 * JDBC con MySql
 
### Motivo: 
 * Para practicar lo observado en clase, examinar cómo es el comportamiento del mismo y la forma en que se debería emplear. Así cómo también por su sencillez y simpleza, su fácil curva de aprendizaje y velocidad a la hora de usarse. Otro de los beneficios es que al estar usando JAVA en los demás trabajos y durante la cursada, la sinergia hace que el aprendizaje y uso del mismo sea mucho más cómodo.
 * Una base de datos relacional (MySql) para manejar la independencia de datos, ya que el sistema genera y guarda las cartas en tiempo de ejecución siempre que éstas no existan. Mantener la coherencia de los datos y evitar inconsistencias de los mismos.
 * La combinación es completamente escalable para el proyecto y garantiza que cuando sea necesario realizar cambios a nivel de sistema o de datos, se realicen los cambios mínimos y el costo del escalamiento (Y mantenimiento) sea bajo.
 
### Funcionalidad del juego:
 * un Dealer tira cartas en una mesa, de una en una (las tira siempre que no haya ya una carta en la misma), y los jugadores van intentando agarrar esas cartas lo antes posible. Así también hay un relator que explica el transcurso del juego, diciendo qué jugador agarra cada carta cada.
 * La partida termina cuando el Dealer se queda sin cartas, y se suman los puntos de cada carta en cada jugador. Se define el ganador mientras se cálculan los puntos, y se guardan las puntuaciones en la base de datos mediante procedimientos almacenados. Así cómo también las cartas del ganador.

