# 📊 Guía de Reportes PDF

## 🚀 Configuración Rápida

### Prerequisitos
- Java 11+
- Maven (gestión automática de dependencias)

### Setup en IntelliJ IDEA
1. **Abrir el proyecto** seleccionando el archivo `pom.xml`
2. IntelliJ detectará automáticamente Maven
3. Click en **"Load Maven Changes"** o **"Enable Auto-Import"**
4. Maven descargará automáticamente PDFBox y dependencias
5. **Build → Rebuild Project**
6. **Run → Run 'Main'**

### Setup desde Terminal
```bash
# Descargar dependencias
mvn clean install

# Compilar y ejecutar
mvn compile exec:java
```

---

## 🎓 Plantilla Institucional Estándar

### 📋 Encabezado Automático en Todos los Reportes

Cada PDF incluye automáticamente:

```
════════════════════════════════════════════════
           Universidad El Bosque
    Facultad de Ingeniería - Bases de Datos I
          Library Management System
────────────────────────────────────────────────
                 REPORTE
          [Tipo de Reporte Específico]
        Fecha de Generación: 11/02/2026
────────────────────────────────────────────────

[Tu contenido del reporte comienza aquí]
```

### ✨ Uso de la Plantilla

**Una sola línea de código:**

```java
// Agrega todo el encabezado institucional automáticamente
float yPosition = addReportHeader(contentStream, page, "Nombre del Reporte");

// Continúa escribiendo contenido desde yPosition
contentStream.setFont(PDType1Font.HELVETICA, FONT_SIZE);
contentStream.beginText();
contentStream.newLineAtOffset(MARGIN, yPosition);
contentStream.showText("Tu contenido aquí...");
contentStream.endText();
```

**Características:**
- ✅ Información institucional completa
- ✅ Fecha automática (DD/MM/YYYY)
- ✅ Formato centrado y profesional
- ✅ Líneas separadoras
- ✅ Sin configuración adicional

---

## 📋 Reportes Disponibles

### Menú de Reportes (Opción 6 en menú principal)

```
======== GENERATE REPORTS ========
1. Users Living at Address
2. Books Loaned by Month
3. Users with Overdue Loans
4. Inactive Users (No Loans Last Month)
5. Users with Birthdays in Month
6. Generate Example Report (Demo) ✅
0. Back to Main Menu
==================================
```

### 📌 Reporte 1: Usuarios por Dirección ✅
- **Input**: Dirección (búsqueda parcial)
- **Output**: PDF con usuarios filtrados
- **Estado**: ✅ IMPLEMENTADO
- **Características**: 
  - Encabezado institucional estándar
  - Resumen con total de usuarios
  - Lista detallada con ID, nombre, email, teléfono, dirección y estado
  - Paginación automática

### 📌 Reporte 2: Libros Prestados por Mes
- **Input**: Año y mes
- **Output**: PDF con préstamos del mes especificado
- **Estado**: ⏳ TODO
- **Dificultad**: ⭐⭐⭐

### 📌 Reporte 3: Préstamos Vencidos
- **Input**: Ninguno (usa fecha actual)
- **Output**: PDF con usuarios y préstamos vencidos
- **Estado**: ⏳ TODO
- **Dificultad**: ⭐⭐⭐

### 📌 Reporte 4: Usuarios Inactivos
- **Input**: Ninguno (últimos 30 días)
- **Output**: PDF con usuarios sin préstamos recientes
- **Estado**: ⏳ TODO
- **Dificultad**: ⭐⭐

### 📌 Reporte 5: Cumpleaños por Mes
- **Input**: Mes (1-12)
- **Output**: PDF con usuarios que cumplen años
- **Estado**: ⏳ TODO
- **Dificultad**: ⭐⭐

### ✅ Reporte 6: Ejemplo (IMPLEMENTADO)
- **Input**: Ninguno
- **Output**: PDF de demostración con datos de ejemplo
- **Estado**: ✅ FUNCIONAL
- **Uso**: Referencia para implementar los demás reportes

---

## 👥 Trabajo en Equipo - División de Reportes

### Archivo a Modificar
```
src/co/edu/unbosque/utils/PDFReportGenerator.java
```

### Asignación Sugerida
Cada miembro implementa un reporte:

| Reporte | Archivos a Modificar | Estado | Asignado |
|---------|---------------------|--------|----------|
| 1 - Users by Address | Controller + PDFReportGenerator | ✅ LISTO | - |
| 2 - Loans by Month | Controller (línea ~380) + PDFReportGenerator (línea ~320) | ⏳ TODO | `[Nombre]` |
| 3 - Overdue Loans | Controller (línea ~395) + PDFReportGenerator (línea ~340) | ⏳ TODO | `[Nombre]` |
| 4 - Inactive Users | Controller (línea ~410) + PDFReportGenerator (línea ~360) | ⏳ TODO | `[Nombre]` |
| 5 - Birthday Users | Controller (línea ~425) + PDFReportGenerator (línea ~375) | ⏳ TODO | `[Nombre]` |

**Cada compañero debe implementar en AMBOS archivos:**
1. `LibraryController.java`: Lógica de filtrado y cálculos
2. `PDFReportGenerator.java`: Generación y formateo del PDF

### 📝 Qué Hacer en Cada Archivo

**En `LibraryController.java` (Método privado correspondiente):**
```java
private void generateMiReporte() {
    // 1. Leer inputs del usuario (ya está hecho)
    // 2. Obtener datos: library.getAllUsers(), etc.
    // 3. FILTRAR/CALCULAR datos según criterio del reporte
    // 4. Validar si hay resultados
    // 5. Llamar a PDFReportGenerator con datos YA procesados
    String fileName = PDFReportGenerator.generateMiReporte(datosFiltrados, ...);
    view.showSuccess("Report generated: " + fileName);
}
```

**En `PDFReportGenerator.java` (Método estático público):**
```java
public static String generateMiReporte(List<DTO> datosFiltrados, ...) {
    // 1. Crear PDDocument, PDPage, PDPageContentStream
    // 2. Usar plantilla: float yPosition = addReportHeader(...)
    // 3. Escribir resumen
    // 4. Loop por datosFiltrados y escribir cada línea
    // 5. Guardar documento
}
```

---

## 🏗️ Arquitectura de Reportes (IMPORTANTE)

### Separación de Responsabilidades

El sistema sigue el **Principio de Responsabilidad Única (SRP)**:

```
┌─────────────────────────────────────────────────────────┐
│ 1. CONTROLLER (LibraryController.java)                 │
│    RESPONSABILIDAD: Lógica de negocio                   │
│    - Obtener datos de la Facade                         │
│    - Filtrar datos según criterios del reporte          │
│    - Calcular valores (días vencidos, usuarios activos) │
│    - Ordenar/agrupar datos                              │
│    - Validar inputs del usuario                         │
└─────────────────┬───────────────────────────────────────┘
                  │ Pasa datos YA procesados
                  ▼
┌─────────────────────────────────────────────────────────┐
│ 2. PDFReportGenerator (utils/)                          │
│    RESPONSABILIDAD: Solo generación de PDF              │
│    - Crear documento PDF                                │
│    - Formatear datos en el PDF                          │
│    - Escribir texto y diseñar layout                    │
│    - Guardar archivo                                    │
│    - NO hace filtrado ni cálculos                       │
└─────────────────────────────────────────────────────────┘
```

### ⚠️ REGLA DE ORO

**PDFReportGenerator NUNCA filtra ni calcula:**
- ❌ NO filtrar listas
- ❌ NO calcular días vencidos
- ❌ NO identificar usuarios activos/inactivos
- ❌ NO ordenar por criterios de negocio
- ✅ SOLO formatear y escribir en PDF

**Toda la lógica de negocio va en el Controller.**

---

## 💻 Guía de Implementación

### Paso 1: Entender el Flujo

**Ejemplo: Reporte de Usuarios por Dirección**

```java
// EN EL CONTROLLER (LibraryController.java)
private void generateUsersByAddressReport() {
    String address = view.readString("Enter address: ");
    
    // 1. Obtener todos los usuarios
    List<UserDTO> allUsers = library.getAllUsers();
    
    // 2. FILTRAR (responsabilidad del Controller)
    List<UserDTO> filteredUsers = allUsers.stream()
        .filter(u -> u.getAddress().toLowerCase().contains(address.toLowerCase()))
        .collect(Collectors.toList());
    
    // 3. Validar
    if (filteredUsers.isEmpty()) {
        view.showMessage("No users found");
        return;
    }
    
    // 4. Llamar a PDFReportGenerator con datos YA filtrados
    String fileName = PDFReportGenerator.generateUsersByAddressReport(filteredUsers, address);
    view.showSuccess("Report generated: " + fileName);
}

// EN PDFReportGenerator (utils/PDFReportGenerator.java)
public static String generateUsersByAddressReport(List<UserDTO> filteredUsers, String address) {
    // Solo formateo - los usuarios ya están filtrados
    try (PDDocument document = new PDDocument()) {
        // ... crear PDF con los usuarios filtrados ...
        return fileName;
    }
}
```

### Paso 2: Plantilla Institucional Estándar

Todos los reportes incluyen un **encabezado institucional estandarizado**:

```
┌─────────────────────────────────────────┐
│     Universidad El Bosque               │
│  Facultad de Ingeniería - Bases de      │
│            Datos I                      │
│    Library Management System            │
│  ───────────────────────────────────    │
│           REPORTE                       │
│      [Tipo de Reporte]                  │
│   Fecha de Generación: DD/MM/YYYY       │
│  ───────────────────────────────────    │
│                                         │
│   [Contenido del reporte aquí]          │
└─────────────────────────────────────────┘
```

**Cómo usar el header:**

```java
try (PDDocument document = new PDDocument()) {
    PDPage page = new PDPage(PDRectangle.A4);
    document.addPage(page);
    
    PDPageContentStream contentStream = new PDPageContentStream(document, page);
    
    // ⭐ USAR EL HEADER ESTÁNDAR (retorna yPosition para continuar)
    float yPosition = addReportHeader(contentStream, page, "Usuarios por Direccion");
    
    // Continuar escribiendo contenido desde yPosition
    contentStream.setFont(PDType1Font.HELVETICA, FONT_SIZE);
    contentStream.beginText();
    contentStream.newLineAtOffset(MARGIN, yPosition);
    contentStream.showText("Total usuarios: " + filteredUsers.size());
    contentStream.endText();
    yPosition -= LINE_HEIGHT;
    
    // ... resto del contenido ...
}
```

### Paso 3: Estudiar el Ejemplo Funcional

El método `generateExampleReport()` (línea ~100 en PDFReportGenerator.java) muestra cómo usar la plantilla.

**Estructura básica de PDFReportGenerator:**

```java
public static String generateUsersByAddressReport(List<UserDTO> filteredUsers, String address) {
    // NOTE: filteredUsers already comes filtered from the Controller
    // NO business logic here - only PDF formatting!
    
    // 1. Crear directorio y nombre de archivo
    new File(REPORTS_FOLDER).mkdirs();
    String fileName = REPORTS_FOLDER + "/users_by_address_" + System.currentTimeMillis() + ".pdf";
    
    // 2. Crear PDF
    try (PDDocument document = new PDDocument()) {
        PDPage page = new PDPage(PDRectangle.A4);
        document.addPage(page);
        
        PDPageContentStream contentStream = new PDPageContentStream(document, page);
        
        // 3. ⭐ AGREGAR HEADER INSTITUCIONAL ESTÁNDAR
        float yPosition = addReportHeader(contentStream, page, "Usuarios por Direccion: " + address);
        
        // 4. Escribir resumen (Controller ya filtró)
        contentStream.setFont(PDType1Font.HELVETICA, FONT_SIZE);
        contentStream.beginText();
        contentStream.newLineAtOffset(MARGIN, yPosition);
        contentStream.showText("Total users found: " + filteredUsers.size());
        contentStream.endText();
        yPosition -= 30;
        
        // 6. Iterar datos YA filtrados
        for (UserDTO user : filteredUsers) {
            // Verificar espacio en página
            if (yPosition < MARGIN + 50) {
                contentStream.close();
                page = new PDPage(PDRectangle.A4);
                document.addPage(page);
                contentStream = new PDPageContentStream(document, page);
                yPosition = page.getMediaBox().getHeight() - MARGIN;
            }
            
            // Escribir datos del usuario
            contentStream.beginText();
            contentStream.newLineAtOffset(MARGIN, yPosition);
            contentStream.showText("ID: " + user.getId() + " | Name: " + user.getName());
            contentStream.endText();
            yPosition -= LINE_HEIGHT;
            
            contentStream.beginText();
            contentStream.newLineAtOffset(MARGIN + 10, yPosition);
            contentStream.showText("Email: " + user.getEmail());
            contentStream.endText();
            yPosition -= LINE_HEIGHT;
            
            contentStream.beginText();
            contentStream.newLineAtOffset(MARGIN + 10, yPosition);
            contentStream.showText("Address: " + user.getAddress());
            contentStream.endText();
            yPosition -= 25;
        }
        
        // 7. Cerrar y guardar
        contentStream.close();
        document.save(fileName);
        return fileName;
        
    } catch (IOException e) {
        System.err.println("Error generating PDF: " + e.getMessage());
        return null;
    }
}
```

### Paso 2: Elementos de PDFBox

#### Fuentes Disponibles
```java
PDType1Font.HELVETICA           // Normal
PDType1Font.HELVETICA_BOLD      // Negrita
PDType1Font.HELVETICA_OBLIQUE   // Itálica
PDType1Font.TIMES_ROMAN         // Times New Roman
PDType1Font.COURIER             // Courier
```

#### Escribir Texto
```java
contentStream.setFont(PDType1Font.HELVETICA, FONT_SIZE);
contentStream.beginText();
contentStream.newLineAtOffset(x, y);  // Posición (x, y)
contentStream.showText("Texto aquí");
contentStream.endText();
```

#### Constantes Predefinidas
```java
REPORTS_FOLDER = "reports"
MARGIN = 50
FONT_SIZE = 12
TITLE_FONT_SIZE = 18
LINE_HEIGHT = 15
```

### Paso 3: Manejo de Páginas

```java
// Verificar si se acabó el espacio vertical
if (yPosition < MARGIN + 50) {
    contentStream.close();
    page = new PDPage(PDRectangle.A4);
    document.addPage(page);
    contentStream = new PDPageContentStream(document, page);
    yPosition = page.getMediaBox().getHeight() - MARGIN;
}
```

---

## 🧪 Cómo Probar

### Opción 1: Desde IntelliJ
1. **Run → Run 'Main'**
2. Seleccionar `6. Generate Reports`
3. Seleccionar tu reporte (1-6)
4. Ingresar parámetros solicitados
5. Abrir el PDF generado en `reports/`

### Opción 2: Desde Terminal
```bash
# Compilar y ejecutar
mvn compile exec:java

# Navegar en el menú
# 6 → [número de reporte] → [parámetros]

# Ver PDFs generados
open reports/
```

---

## 📚 Recursos Útiles

### Trabajar con Fechas
```java
// Parsear fecha
LocalDate date = LocalDate.parse("2024-02-11");

// Obtener componentes
int year = date.getYear();
int month = date.getMonthValue(); // 1-12
int day = date.getDayOfMonth();

// Comparar
boolean isBefore = date1.isBefore(date2);
boolean isAfter = date1.isAfter(date2);

// Diferencia en días
long days = ChronoUnit.DAYS.between(date1, date2);
```

### Filtrar Listas (Java Streams)
```java
// Filtrar activos
List<UserDTO> active = users.stream()
    .filter(u -> u.isActive())
    .collect(Collectors.toList());

// Filtrar por dirección
List<UserDTO> filtered = users.stream()
    .filter(u -> u.getAddress().contains("Calle"))
    .collect(Collectors.toList());

// Ordenar por nombre
List<UserDTO> sorted = users.stream()
    .sorted((u1, u2) -> u1.getName().compareTo(u2.getName()))
    .collect(Collectors.toList());
```

---

## ✅ Checklist de Implementación

Para cada reporte:

- [ ] Eliminar `throw new UnsupportedOperationException(...)`
- [ ] Crear directorio de reportes con `mkdirs()`
- [ ] Filtrar/procesar datos según requerimiento
- [ ] Crear `PDDocument` y `PDPage`
- [ ] Crear `PDPageContentStream`
- [ ] Escribir título en negrita (`TITLE_FONT_SIZE`)
- [ ] Escribir resumen con estadísticas
- [ ] Iterar datos y escribir líneas
- [ ] Manejar cambios de página cuando `yPosition < MARGIN + 50`
- [ ] Cerrar `contentStream` con `close()`
- [ ] Guardar con `document.save(fileName)`
- [ ] Probar con datos reales
- [ ] Verificar formato del PDF generado

---

## 🎯 Estado de Implementación

- [x] **Reporte 1: Users by Address** ✅ IMPLEMENTADO (Controller + PDFReportGenerator)
- [ ] **Reporte 2: Books Loaned by Month** ⏳ TODO (Controller + PDFReportGenerator)
- [ ] **Reporte 3: Users with Overdue Loans** ⏳ TODO (Controller + PDFReportGenerator)
- [ ] **Reporte 4: Inactive Users** ⏳ TODO (Controller + PDFReportGenerator)
- [ ] **Reporte 5: Birthday Users** ⏳ TODO (Controller + PDFReportGenerator)
- [x] **Reporte 6: Example Report** ✅ REFERENCIA (Plantilla institucional)

---

## 💬 Tips para el Equipo

### Antes de Empezar
1. Cada miembro elige un reporte
2. Actualizar la tabla de asignación con nombres reales
3. Estudiar el ejemplo funcional (opción 6)

### Durante el Desarrollo
- Hacer commits frecuentes
- Mensajes descriptivos: `"feat: implement users by address report"`
- Probar antes de push

### Al Terminar
- Marcar como completado en este documento
- Probar con diferentes datos
- Hacer commit y push

---

## 📄 Archivos Relevantes

```
src/
├── co/edu/unbosque/
│   ├── controller/
│   │   └── LibraryController.java    # Controlador que llama reportes
│   ├── utils/
│   │   └── PDFReportGenerator.java   # ⭐ IMPLEMENTAR AQUÍ
│   └── view/
│       └── ViewConsole.java          # Menú de reportes

reports/                              # PDFs generados aquí
pom.xml                               # Dependencias Maven (PDFBox)
```

---

**¡El reporte de ejemplo (opción 6) es tu mejor referencia!** 🚀

Todos los métodos TODO incluyen documentación detallada en el código.

---

## 📐 Resumen: Dónde va cada Responsabilidad

### ✅ En el Controller (`LibraryController.java`)

**SIEMPRE haces aquí:**
- Obtener datos: `library.getAllUsers()`, `library.getAllLoans()`, etc.
- Filtrar: `.filter(u -> u.getAddress().contains(address))`
- Calcular: días vencidos, usuarios activos, etc.
- Ordenar: `.sorted((a, b) -> ...)`
- Agrupar datos
- Validar inputs del usuario
- Manejar casos vacíos (`if (filteredUsers.isEmpty())`)

**Ejemplo:**
```java
// Controller hace TODO el trabajo de lógica de negocio
List<UserDTO> allUsers = library.getAllUsers();
List<UserDTO> filtered = allUsers.stream()
    .filter(u -> u.getAddress().contains(address))
    .collect(Collectors.toList());

if (filtered.isEmpty()) {
    view.showMessage("No users found");
    return;
}

// Pasa datos YA procesados al generador
String file = PDFReportGenerator.generateReport(filtered, address);
```

### ✅ En PDFReportGenerator (`utils/PDFReportGenerator.java`)

**SOLO haces aquí:**
- Crear `PDDocument` y `PDPage`
- Crear `PDPageContentStream`
- Escribir texto con `contentStream.showText(...)`
- Formatear layout (títulos, márgenes, saltos de línea)
- Manejar paginación (crear nueva página si `yPosition < MARGIN`)
- Guardar documento: `document.save(fileName)`
- Lookups simples (buscar libro por ID en lista que ya te pasaron)

**Ejemplo:**
```java
// PDFReportGenerator SOLO formatea - NO filtra
public static String generateReport(List<UserDTO> filteredUsers, String address) {
    try (PDDocument document = new PDDocument()) {
        // ... crear página ...
        
        // Escribir título
        contentStream.showText("Users at: " + address);
        
        // Escribir datos (YA filtrados por el Controller)
        for (UserDTO user : filteredUsers) {
            contentStream.showText("Name: " + user.getName());
        }
        
        document.save(fileName);
        return fileName;
    }
}
```

### ❌ NUNCA hagas en PDFReportGenerator

- ❌ `.filter(...)` - El Controller ya filtró
- ❌ `.stream().filter(...)` - El Controller ya filtró
- ❌ Calcular días, meses, diferencias - El Controller ya calculó
- ❌ `if (loan.isActive() && returnDate.isBefore(today))` - El Controller ya validó
- ❌ Ordenar por criterios de negocio - El Controller ya ordenó

---

## 🎯 Principio Clave

> **"PDFReportGenerator es un pintor, no un analista."**
> 
> El Controller analiza los datos y decide QUÉ mostrar.
> PDFReportGenerator solo pinta lo que le dicen CÓMO mostrarlo.
