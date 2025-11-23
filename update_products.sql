-- Script para actualizar los campos description y review que están en NULL
-- Ejecutar este script en DBeaver después de eliminar las columnas duplicadas

-- Actualizar productos de Juegos de Mesa
UPDATE products SET description = 'Juego de estrategia de comercio y construcción de civilizaciones en la isla de Catan.', review = 'Un clásico moderno, esencial para cualquier colección. ¡Perfecto para noches de estrategia!' WHERE id = 'GM001';
UPDATE products SET description = 'Juego de cartas narrativo y de imaginación, donde se adivinan conceptos a través de ilustraciones surrealistas.', review = 'Excelente para fiestas. Despierta la creatividad y la interpretación de imágenes. ¡Arte bellísimo!' WHERE id = 'GM002';
UPDATE products SET description = 'Aventura ferroviaria para coleccionar y conectar rutas de tren a través de Europa.', review = 'Ideal para la familia. Reglas sencillas y alta rejugabilidad.' WHERE id = 'GM003';
UPDATE products SET description = 'Versión simplificada de Ticket to Ride para introducir a los más jóvenes en los juegos de estrategia.', review = 'La mejor opción para iniciar a los niños. Rápido y muy colorido.' WHERE id = 'GM004';
UPDATE products SET description = 'Juego cooperativo donde el equipo debe curar y contener cuatro plagas que se extienden por el mundo.', review = 'Un desafío cooperativo intenso y emocionante. ¡El trabajo en equipo es crucial para ganar!' WHERE id = 'GM005';
UPDATE products SET description = 'Juego asimétrico donde un jugador es un fantasma que guía a un grupo de médiums a resolver un misterio de asesinato con cartas de visiones.', review = 'Una experiencia única, mezcla de Dixit y Cluedo. Muy atmosférico.' WHERE id = 'GM006';
UPDATE products SET description = 'Juego de palabras por equipos donde los espías deben dar pistas de una sola palabra para que su equipo identifique a sus agentes secretos en el tablero.', review = 'Rápido, ingenioso y excelente para grupos grandes. ¡Altamente adictivo!' WHERE id = 'GM007';
UPDATE products SET description = 'Juego de coleccionar aves y desarrollar hábitats, centrado en la gestión de motores de recursos y la obtención de puntos.', review = 'Un juego visualmente hermoso y muy tranquilo. Perfecto para gamers experimentados.' WHERE id = 'GM008';
UPDATE products SET description = 'Juego de draft de cartas ultrarrápido donde compites por el mejor menú de sushi.', review = 'Pequeño, portable y muy divertido. Ideal para empezar o terminar una noche de juegos.' WHERE id = 'GM009';
UPDATE products SET description = 'Juego de coleccionar gemas y adquirir cartas de desarrollo para ganar prestigio.', review = 'Mecánicas sencillas con mucha profundidad estratégica. Rápido de jugar y altamente rejugable.' WHERE id = 'GM010';
UPDATE products SET description = 'Juego de estrategia económico y científico donde las corporaciones compiten por terraformar Marte.', review = 'Profundo y temático. Un juego que requiere varias horas y mucha planificación.' WHERE id = 'GM011';
UPDATE products SET description = 'Juego de abstracto de colocación de azulejos para decorar las paredes del Palacio Real de Evora.', review = 'Elegante y muy satisfactorio. Fácil de aprender, pero con decisiones difíciles.' WHERE id = 'GM012';

-- Actualizar productos de Periféricos Gamer
UPDATE products SET description = 'Teclado mecánico con switches táctiles y retroiluminación RGB personalizable.', review = 'Excelente respuesta y tacto. Ideal para sesiones de juego intensas.' WHERE id = 'PG001';
UPDATE products SET description = 'Ratón gamer ultraligero (65g) con sensor óptico de 16000 DPI y conexión de baja latencia.', review = 'Increíblemente ligero y preciso. La libertad del inalámbrico sin sacrificar rendimiento.' WHERE id = 'PG002';
UPDATE products SET description = 'Auriculares con sonido envolvente 7.1 virtual y micrófono con cancelación de ruido.', review = 'Audio inmersivo y claro. Muy cómodos para largas jornadas de juego.' WHERE id = 'PG003';
UPDATE products SET description = 'Alfombrilla extragrande (90x40 cm) con superficie de tela optimizada para velocidad y base antideslizante.', review = 'Espacio más que suficiente para teclado y ratón. Mejora drásticamente la precisión.' WHERE id = 'PG004';
UPDATE products SET description = 'Mando de juego profesional con botones traseros programables y gatillos de alta respuesta.', review = 'Precisión y personalización de nivel competitivo. La ergonomía es perfecta.' WHERE id = 'PG005';
UPDATE products SET description = 'Cámara web con resolución 4K a 30 FPS y enfoque automático, ideal para streaming de alta calidad.', review = 'Imágenes cristalinas y excelente rendimiento con poca luz.' WHERE id = 'PG006';
UPDATE products SET description = 'Volante de carreras con Force Feedback de doble motor, pedales sensibles y rotación de 900 grados.', review = 'Experiencia de conducción increíblemente realista. Ideal para simulación.' WHERE id = 'PG007';
UPDATE products SET description = 'Ratón gamer cableado, ligero y ambidiestro, con 7200 DPI y 6 botones programables.', review = 'El mejor valor por precio. Diseño sencillo y muy funcional para eSports.' WHERE id = 'PG008';
UPDATE products SET description = 'Silla ergonómica de alto respaldo con soporte lumbar y reposabrazos 4D.', review = 'Comodidad excepcional para maratones de juego. Gran inversión en salud postural.' WHERE id = 'PG009';
UPDATE products SET description = 'Micrófono de condensador USB con patrón cardioide, ideal para podcasting y streaming.', review = 'Calidad de estudio profesional sin necesidad de interfaces de audio. Plug and play.' WHERE id = 'PG010';
UPDATE products SET description = 'Monitor curvo de 27 pulgadas con panel VA, 144Hz de tasa de refresco y 1ms de tiempo de respuesta.', review = 'Imágenes fluidas y colores vibrantes. Mejora significativamente la experiencia de juego inmersiva.' WHERE id = 'PG011';

-- Actualizar productos de Consolas
UPDATE products SET description = 'Consola de última generación con gráficos 4K a 120 FPS y SSD ultrarrápido.', review = 'El salto generacional que se esperaba. Tiempos de carga casi nulos.' WHERE id = 'CN001';
UPDATE products SET description = 'La consola más potente del mercado. Velocidad de fotogramas de hasta 120 FPS y compatibilidad con miles de juegos.', review = 'Una bestia de rendimiento. El Game Pass es la guinda del pastel.' WHERE id = 'CN002';
UPDATE products SET description = 'Consola híbrida para jugar en casa o llevar a cualquier lugar. Pantalla OLED vibrante y mandos Joy-Con desmontables.', review = 'Imprescindible por sus exclusivas. La flexibilidad de jugar donde quieras no tiene precio.' WHERE id = 'CN003';
UPDATE products SET description = 'Controlador oficial para NovaPlay 5 con respuesta háptica y gatillos adaptativos.', review = 'Tecnología innovadora que realmente mejora la inmersión en los juegos.' WHERE id = 'CN004';
UPDATE products SET description = 'RPG de ciencia ficción de mundo abierto. Explora más de 1000 planetas en una épica galáctica.', review = 'Una odisea espacial que define una generación. Enorme contenido y libertad.' WHERE id = 'CN005';
UPDATE products SET description = 'Remake del aclamado juego de acción y aventura con gráficos de última generación.', review = 'Una historia desgarradora con la mejor puesta en escena técnica.' WHERE id = 'CN006';
UPDATE products SET description = 'El juego de carreras arcade más vendido para Switch, con 48 pistas y multijugador local.', review = 'Diversión infinita y accesible para todos. El rey de las fiestas.' WHERE id = 'CN007';
