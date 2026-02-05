# Diseño de Datos - Library Management System

## Tabla de Contenidos
- [Visión General](#visión-general)
- [Estructura de Archivos](#estructura-de-archivos)
- [Entidad: Usuario (User)](#entidad-usuario-user)
- [Entidad: Libro (Book)](#entidad-libro-book)
- [Entidad: Préstamo (Loan)](#entidad-préstamo-loan)
- [Relaciones Entre Entidades](#relaciones-entre-entidades)
- [Decisiones de Diseño](#decisiones-de-diseño)

---

## Visión General

El sistema utiliza **archivos de texto plano** con formato **pipe-delimited** (`|`) para almacenar los datos. Esta decisión permite:

- ✅ **Simplicidad**: No requiere servidor de base de datos
- ✅ **Portabilidad**: Los archivos se pueden compartir fácilmente
- ✅ **Sincronización**: Compatible con Google Drive/Dropbox
- ✅ **Legibilidad**: Los datos son legibles en cualquier editor de texto
- ✅ **Control de versiones**: Se pueden rastrear cambios fácilmente

---

## Estructura de Archivos

El sistema utiliza tres archivos principales ubicados en la carpeta `data/` (o en Google Drive):

```
data/
├── users.txt    # Información de usuarios registrados
├── books.txt    # Catálogo de libros
└── loans.txt    # Registro de préstamos
```

### Formato General

- **Delimitador**: Pipe (`|`)
- **Codificación**: UTF-8
- **Formato de fechas**: ISO 8601 (`yyyy-MM-dd`)
- **Una línea por registro**
- **Sin encabezados** (solo datos)

---

## Entidad: Usuario (User)

### Archivo: `users.txt`

### Estructura
```
id|name|email|phone|address|birthDate|registrationDate|isActive
```

### Ejemplo Real
```
U001|Juan Pérez|juan.perez@email.com|3001234567|Calle 123 #45-67|1990-05-15|2024-01-10|true
U002|María García|maria.garcia@email.com|3109876543|Av. Libertador 890|1985-08-22|2024-01-12|true
```

### Atributos Detallados

| Campo | Tipo | Obligatorio | Descripción | Razón de Inclusión |
|-------|------|-------------|-------------|-------------------|
| **id** | String | ✅ Sí | Identificador único del usuario (ej: U001, U002) | Permite identificar de forma única cada usuario sin ambigüedades. Prefijo "U" facilita reconocimiento visual. |
| **name** | String | ✅ Sí | Nombre completo del usuario | Esencial para identificación personal y comunicación. |
| **email** | String | ✅ Sí | Correo electrónico | Canal de comunicación principal, único por usuario. Útil para notificaciones y contacto. |
| **phone** | String | ✅ Sí | Número telefónico | Canal de comunicación alternativo. String para soportar formatos internacionales y prefijos. |
| **address** | String | ✅ Sí | Dirección física completa | Necesario para localización física del usuario en caso de requerirse. |
| **birthDate** | LocalDate | ⚪ Opcional | Fecha de nacimiento (yyyy-MM-dd) | Permite validar mayoría de edad y personalizar servicios. Opcional porque puede ser información sensible. |
| **registrationDate** | LocalDate | ✅ Sí | Fecha de registro en el sistema | Auditoría: permite rastrear antigüedad del usuario y análisis temporales. Se asigna automáticamente. |
| **isActive** | Boolean | ✅ Sí | Estado del usuario (true/false) | Permite desactivar usuarios sin eliminar su historial. Importante para préstamos activos. |

### Decisiones de Diseño - Usuario

1. **ID como String (no numérico)**
   - Permite prefijos descriptivos ("U001")
   - Más flexible que integers
   - Fácil de leer y comunicar verbalmente

2. **Email único**
   - Usado como identificador secundario
   - Facilita recuperación de cuentas
   - Estándar en sistemas modernos

3. **Phone como String**
   - Soporta formatos internacionales: +57, (601), etc.
   - Permite guiones y espacios para legibilidad
   - Evita problemas con ceros iniciales

4. **isActive en lugar de eliminar**
   - **Soft delete**: preserva historial
   - Protege integridad referencial con préstamos
   - Permite reactivación futura

5. **birthDate opcional**
   - Respeta privacidad del usuario
   - No crítico para operación básica
   - Puede agregarse después si es necesario

---

## Entidad: Libro (Book)

### Archivo: `books.txt`

### Estructura
```
id|title|author|isbn|available
```

### Ejemplo Real
```
B001|Cien Años de Soledad|Gabriel García Márquez|978-0307474728|true
B002|Don Quijote de la Mancha|Miguel de Cervantes|978-8491050643|false
B003|1984|George Orwell|978-0451524935|true
```

### Atributos Detallados

| Campo | Tipo | Obligatorio | Descripción | Razón de Inclusión |
|-------|------|-------------|-------------|-------------------|
| **id** | String | ✅ Sí | Identificador único del libro (ej: B001, B002) | Identificador interno del sistema. Prefijo "B" identifica visualmente como libro. |
| **title** | String | ✅ Sí | Título completo del libro | Identificación principal del libro. Usado en búsquedas y listados. |
| **author** | String | ✅ Sí | Nombre del autor | Esencial para identificación y búsquedas. Permite filtrar por autor favorito. |
| **isbn** | String | ✅ Sí | ISBN (International Standard Book Number) | Estándar internacional que identifica única e inequívocamente el libro. Útil para catalogación y búsqueda externa. |
| **available** | Boolean | ✅ Sí | Disponibilidad para préstamo (true/false) | Estado en tiempo real. `true` = disponible, `false` = prestado. Se actualiza automáticamente con los préstamos. |

### Decisiones de Diseño - Libro

1. **ISBN como String**
   - Incluye guiones: 978-0-307-47472-8
   - Evita problemas con ceros iniciales
   - Estándar global para identificación de libros

2. **Autor como String simple**
   - Suficiente para biblioteca pequeña/mediana
   - Evita complejidad de múltiples autores
   - Puede incluir varios autores separados por coma si es necesario

3. **available en lugar de estado complejo**
   - Binario y simple: disponible o no
   - Se sincroniza automáticamente con préstamos
   - Suficiente para operación básica

4. **Sin campos de editorial, año, género**
   - **Principio YAGNI** (You Aren't Gonna Need It)
   - Se pueden agregar después si se requieren
   - Mantiene el modelo simple y enfocado

5. **Sin cantidad/copias**
   - Cada copia física = registro separado
   - Simplifica lógica de préstamos
   - Ejemplo: 3 copias de "1984" = B003, B003-2, B003-3

---

## Entidad: Préstamo (Loan)

### Archivo: `loans.txt`

### Estructura
```
id|userId|bookId|loanDate|returnDate|active
```

### Ejemplo Real
```
L1738896543210|U001|B002|2024-02-01|null|true
L1738982943210|U002|B001|2024-01-15|2024-01-29|false
L1739069343210|U001|B003|2024-02-03|null|true
```

### Atributos Detallados

| Campo | Tipo | Obligatorio | Descripción | Razón de Inclusión |
|-------|------|-------------|-------------|-------------------|
| **id** | String | ✅ Sí | Identificador único del préstamo (ej: L1738896543210) | Generado automáticamente usando timestamp: "L" + milisegundos. Garantiza unicidad global. |
| **userId** | String | ✅ Sí | ID del usuario que realiza el préstamo | **Llave foránea** a User. Establece relación usuario-préstamo. |
| **bookId** | String | ✅ Sí | ID del libro prestado | **Llave foránea** a Book. Establece relación libro-préstamo. |
| **loanDate** | LocalDate | ✅ Sí | Fecha en que se realizó el préstamo | Auditoría. Permite calcular días de préstamo y generar reportes temporales. |
| **returnDate** | LocalDate | ⚪ Opcional | Fecha de devolución del libro | `null` mientras está prestado. Se completa cuando el usuario devuelve el libro. |
| **active** | Boolean | ✅ Sí | Estado del préstamo (true = activo, false = devuelto) | Indica si el préstamo está vigente. Crítico para lógica de disponibilidad de libros. |

### Decisiones de Diseño - Préstamo

1. **ID generado con timestamp**
   - Formato: `L + System.currentTimeMillis()`
   - Ejemplo: `L1738896543210`
   - Garantiza unicidad sin base de datos
   - Ordenamiento cronológico implícito
   - No requiere contador centralizado

2. **userId y bookId como referencias**
   - **Relaciones por ID** (no objetos embebidos)
   - Evita duplicación de datos
   - Permite actualizar User/Book independientemente
   - Normalización básica de datos

3. **returnDate nullable**
   - `null` = préstamo activo (no devuelto)
   - Fecha válida = libro ya devuelto
   - Evita fechas ficticias como "9999-12-31"

4. **active redundante pero útil**
   - Podría derivarse de `returnDate != null`
   - **Trade-off**: redundancia por simplicidad
   - Consultas más rápidas sin cálculos
   - Claridad en la lógica de negocio

5. **Sin fecha de vencimiento**
   - Simplifica el modelo inicial
   - Se puede calcular dinámicamente (loanDate + días permitidos)
   - Fácil de agregar después si se requiere

---

## Relaciones Entre Entidades

```
┌─────────────┐         ┌──────────────┐         ┌─────────────┐
│    USER     │         │     LOAN     │         │    BOOK     │
├─────────────┤         ├──────────────┤         ├─────────────┤
│ id (PK)     │◄────────│ userId (FK)  │         │ id (PK)     │
│ name        │ 1     * │ bookId (FK)  │─────────┤ title       │
│ email       │         │ loanDate     │ *     1 │ author      │
│ phone       │         │ returnDate   │         │ isbn        │
│ ...         │         │ active       │         │ available   │
└─────────────┘         └──────────────┘         └─────────────┘
```

### Reglas de Integridad

1. **User → Loan** (1:N)
   - Un usuario puede tener múltiples préstamos
   - Un préstamo pertenece a un solo usuario
   - **Restricción**: No se puede eliminar un usuario con préstamos activos

2. **Book → Loan** (1:N)
   - Un libro puede tener múltiples préstamos (histórico)
   - Un préstamo se refiere a un solo libro
   - **Restricción**: No se puede eliminar un libro con préstamos activos

3. **Sincronización Book.available**
   - Cuando se crea un Loan (active=true) → Book.available = false
   - Cuando se devuelve un Loan (active=false) → Book.available = true
   - **Crítico**: Mantener consistencia entre ambas entidades

---

## Decisiones de Diseño

### 1. ¿Por qué archivos de texto y no base de datos?

**Contexto del Proyecto:**
- Sistema académico/estudiantil
- Equipo colaborativo (datos compartidos en Google Drive)
- Sin infraestructura de servidor
- Requisito explícito: NO usar base de datos

**Ventajas de archivos de texto:**
- ✅ Cero configuración
- ✅ Sincronización automática vía Google Drive
- ✅ Accesible desde cualquier computadora
- ✅ Backup natural (Google Drive mantiene historial)
- ✅ Inspección directa (abrir en Excel/editor)

**Desventajas asumidas:**
- ⚠️ No hay transacciones ACID
- ⚠️ Posibles conflictos con edición simultánea
- ⚠️ Rendimiento limitado con muchos registros (>10,000)
- ⚠️ Sin integridad referencial automática

### 2. ¿Por qué pipe (`|`) como delimitador?

**Alternativas consideradas:**
- ❌ Coma (`,`): Común en nombres "García, Juan"
- ❌ Tab (`\t`): Invisible, problemas de copia-pega
- ❌ Punto y coma (`;`): Usado en direcciones
- ✅ **Pipe (`|`)**: Raro en texto normal, visible, estándar

**Ventajas del pipe:**
- No aparece comúnmente en nombres, direcciones o títulos
- Fácil de identificar visualmente
- Soportado nativamente por herramientas Unix (cut, awk)
- Estándar en sistemas legacy

### 3. ¿Por qué IDs con prefijos (U001, B001, L001)?

**Alternativas:**
- ❌ Números puros (1, 2, 3): Ambigüedad entre entidades
- ❌ UUIDs: Demasiado largos, no legibles
- ✅ **Prefijo + número/timestamp**: Balance perfecto

**Ventajas:**
- Identificación visual inmediata del tipo
- Legible en logs y mensajes de error
- Fácil de comunicar verbalmente ("usuario U cero cero uno")
- Evita confusión: ¿Es "123" un usuario o un libro?

### 4. ¿Por qué LocalDate y no String para fechas?

**Decisión:**
- **Almacenamiento**: String en formato ISO (`yyyy-MM-dd`)
- **Aplicación**: `java.time.LocalDate` (Java 8+)

**Razones:**
- ✅ Formato estándar internacional (ISO 8601)
- ✅ Ordenamiento alfabético = ordenamiento cronológico
- ✅ `LocalDate` previene errores de formato
- ✅ Operaciones de fecha facilitadas (añadir días, comparar)
- ✅ Sin zonas horarias (innecesarias para este caso)

### 5. ¿Por qué soft delete (isActive) en lugar de eliminar?

**Soft Delete (Usuario.isActive = false):**
- ✅ Preserva historial de préstamos
- ✅ Permite auditoría y reportes históricos
- ✅ Recuperable (reactivación simple)
- ✅ Protege integridad referencial

**Hard Delete (eliminar registro):**
- ❌ Rompe relaciones con préstamos
- ❌ Pérdida de historial
- ❌ Irreversible

### 6. ¿Por qué no incluir más campos (género, editorial, etc.)?

**Principio aplicado: YAGNI (You Aren't Gonna Need It)**

Campos considerados pero NO incluidos:
- ❌ `User.membershipType` (básico, premium)
- ❌ `Book.genre` (ficción, no-ficción)
- ❌ `Book.publisher` (editorial)
- ❌ `Book.publicationYear`
- ❌ `Loan.dueDate` (fecha límite de devolución)
- ❌ `Loan.fine` (multa por retraso)

**Razón:**
- Agregar complejidad innecesaria para MVP
- Fácil de extender después si se necesita
- Principio de diseño: empezar simple, iterar según necesidad
- **Regla 80/20**: Los 4-6 campos cubren el 80% de casos de uso

### 7. ¿Por qué Boolean y no enum para estados?

**Decisión:**
- `User.isActive`: Boolean (true/false)
- `Book.available`: Boolean (true/false)
- `Loan.active`: Boolean (true/false)

**Alternativa descartada: Enum**
```java
// NO implementado (por ahora)
enum UserStatus { ACTIVE, INACTIVE, SUSPENDED, BANNED }
enum BookStatus { AVAILABLE, LOANED, MAINTENANCE, LOST }
enum LoanStatus { ACTIVE, RETURNED, OVERDUE, CANCELLED }
```

**Razón:**
- Boolean cubre 95% de casos actuales
- Enum agrega complejidad de serialización/deserialización
- Más estados = más lógica de transición
- **Principio**: Empezar simple, evolucionar si se necesita

---

## Resumen de Capacidades del Modelo

### Lo que el modelo PUEDE hacer:
✅ Registrar usuarios con información completa  
✅ Catalogar libros con ISBN estándar  
✅ Rastrear préstamos activos e históricos  
✅ Sincronizar entre múltiples computadoras (Google Drive)  
✅ Prevenir préstamos de libros no disponibles  
✅ Mantener historial completo de transacciones  
✅ Desactivar usuarios sin perder datos  
✅ Consultar préstamos por usuario o libro  

### Lo que el modelo NO puede hacer (por diseño):
❌ Transacciones concurrentes seguras (conflictos posibles)  
❌ Múltiples copias del mismo libro (requiere ID diferente por copia)  
❌ Multas automáticas por retraso (no hay fecha límite)  
❌ Reservas de libros  
❌ Categorización avanzada (géneros, editoriales)  
❌ Validación de unicidad de email en tiempo real  
❌ Control de acceso/permisos (todos los usuarios son iguales)  

---

## Extensiones Futuras Posibles

Si el sistema crece, se podría considerar:

1. **Migración a SQLite**
   - Mantiene simplicidad (archivo único)
   - Agrega integridad referencial
   - Transacciones ACID
   - Sin necesidad de servidor

2. **Campos adicionales:**
   - `Book.quantity` (múltiples copias)
   - `Loan.dueDate` y `Loan.fine`
   - `User.membershipLevel`
   - `Book.category` y `Book.publicationYear`

3. **Entidades nuevas:**
   - `Reservation` (reservas de libros)
   - `Fine` (multas)
   - `Category` (categorías de libros)
   - `Admin` (usuarios administradores)

4. **Mejoras técnicas:**
   - Índices para búsquedas rápidas
   - Caché en memoria
   - Validación de integridad referencial
   - Sistema de logs de auditoría

---

## Conclusión

Este diseño de datos prioriza:
- **Simplicidad** sobre sofisticación
- **Funcionalidad** sobre características
- **Mantenibilidad** sobre optimización prematura
- **Colaboración** (Google Drive) sobre rendimiento máximo

Es un modelo **fit-for-purpose**: adecuado para una biblioteca pequeña/mediana con equipo colaborativo sin infraestructura de servidor. Las decisiones de diseño reflejan las restricciones y objetivos del proyecto académico, permitiendo fácil comprensión y evolución futura.
