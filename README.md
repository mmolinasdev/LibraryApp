# 📚 Library Management System

Sistema de gestión de biblioteca desarrollado en Java con persistencia en archivos y sincronización automática con Google Drive para colaboración en equipo.

---

## ✨ Características

### Funcionalidades
- 👤 **Gestión de Usuarios**: Crear, actualizar, eliminar y buscar usuarios
- 📖 **Gestión de Libros**: CRUD completo para libros
- 📋 **Gestión de Préstamos**: Registro de préstamos y devoluciones
- 🔍 **Sistema de Búsqueda**: Buscar usuarios por nombre, libros por título, préstamos por usuario
- 📊 **Reportes en PDF**: Sistema de generación con plantilla institucional estandarizada (Universidad El Bosque, Facultad, fecha automática)
- ☁️ **Sincronización Google Drive**: Colaboración en tiempo real entre miembros del equipo

### Características Técnicas
- 🏗️ **Arquitectura MVC**: Separación clara de responsabilidades
- 📦 **Patrón DTO**: Transferencia de datos entre capas
- 🎨 **Patrón Facade**: Interfaz simplificada a la lógica de negocio
- 🗄️ **Patrón DAO**: Abstracción de persistencia de datos
- ☁️ **Detección Automática de Google Drive**: Sin configuración manual (Mac y Windows)
- 💾 **Persistencia en archivos de texto**: Formato pipe-delimited (|)

---

## 🚀 Inicio Rápido

### Requisitos
- Java JDK 11+
- Maven 3.6+ (para gestión de dependencias)
- Google Drive Desktop (opcional, para trabajo en equipo)
- IntelliJ IDEA (recomendado)

### Configuración Inicial (Primera vez)

```bash
# Descargar dependencias (PDFBox, FontBox, commons-logging)
mvn clean install
```

### Opción 1: Ejecutar con Maven

```bash
# Compilar y ejecutar
mvn compile exec:java

# O en dos pasos
mvn compile
mvn exec:java
```

### Opción 2: Ejecutar con IntelliJ IDEA

1. **Abrir el proyecto** en IntelliJ
2. IntelliJ **detectará automáticamente** el `pom.xml` y descargará dependencias
3. **Run → Run 'Main'**

Los datos se guardan en la carpeta local `data/` o en Google Drive si está configurado.

### Trabajo en Equipo (Con Google Drive)

**Ver guía completa:** `GOOGLE_DRIVE_SETUP.md`

**Resumen rápido:**
1. Instalar Google Drive Desktop
2. Aceptar invitación del líder
3. Agregar shortcut a "Mi unidad"
4. Ejecutar con Maven o IntelliJ

**¡Detecta automáticamente Google Drive!** Sin configuración manual.

---

## 📁 Estructura del Proyecto

```
LibraryApp/
├── pom.xml                                 # Configuración Maven (gestión automática de dependencias)
├── src/
│   ├── Main.java                           # Punto de entrada
│   └── co/edu/unbosque/
│       ├── model/                          # Entidades del dominio
│       │   ├── User.java
│       │   ├── Book.java
│       │   ├── Loan.java
│       │   ├── dto/                        # Data Transfer Objects
│       │   │   ├── UserDTO.java
│       │   │   ├── BookDTO.java
│       │   │   └── LoanDTO.java
│       │   └── persistence/                # Capa de persistencia
│       │       ├── FileManager.java        # Gestión de archivos
│       │       ├── LibraryDAO.java         # Acceso a datos
│       │       ├── *Mapper.java            # DTO ↔ Entity
│       │       └── *FileMapper.java        # Entity ↔ File
│       ├── utils/                          # Utilidades
│       │   ├── DateFormatter.java
│       │   └── PDFReportGenerator.java     # Generación de PDFs
│       ├── view/                           # Interfaz de usuario
│       │   └── ViewConsole.java
│       └── controller/                     # Controladores
│           ├── LibraryController.java
│           └── facade/
│               └── Library.java
│
├── data/                                   # Datos locales (si no usa Google Drive)
│   ├── users.txt
│   ├── books.txt
│   └── loans.txt
│
├── reports/                                # PDFs generados automáticamente
│   └── report_*.pdf
│
├── target/                                 # Archivos compilados (Maven)
│
├── README.md                               # Este archivo
├── REPORTS_GUIDE.md                        # Guía completa de reportes PDF
├── GOOGLE_DRIVE_SETUP.md                   # Configuración de Google Drive
└── DATA_DESIGN.md                          # Decisiones de diseño de datos
```

---

## 🏗️ Arquitectura

### Capas de la Aplicación

```
┌─────────────────┐
│  ViewConsole    │  ← Interfaz de usuario (entrada/salida)
└────────┬────────┘
         │
┌────────▼────────┐
│  Controller     │  ← Control de flujo de la aplicación
└────────┬────────┘
         │
┌────────▼────────┐
│  Facade         │  ← Interfaz simplificada (convierte DTO ↔ Entity)
└────────┬────────┘
         │
┌────────▼────────┐
│  DAO            │  ← Lógica de negocio y CRUD
└────────┬────────┘
         │
┌────────▼────────┐
│  FileManager    │  ← Persistencia en archivos (convierte Entity ↔ String)
└────────┬────────┘
         │
┌────────▼────────┐
│  Archivos .txt  │  ← Almacenamiento (local o Google Drive)
└─────────────────┘
```

### Flujo de Datos

```
Usuario → ViewConsole → Controller → Facade
                                       ↓
                                    DTO ↔ Entity (DTO Mapper)
                                       ↓
                                      DAO
                                       ↓
                                  FileManager
                                       ↓
                               Entity ↔ String (File Mapper)
                                       ↓
                           Archivo .txt (local o Google Drive)
```

---

## 📝 Formato de Archivos

### users.txt
```
ID|Name|Email|Phone|Address|BirthDate|RegistrationDate|IsActive
12344567|Juan Gomez|juan@email.com|3122233445|Cra 34 # 11-22|1999-03-01|2026-02-04|true
```

### books.txt
```
ID|Title|Author|ISBN|IsAvailable
B001|Clean Code|Robert Martin|978-0132350884|true
```

### loans.txt
```
ID|UserID|BookID|LoanDate|ReturnDate|IsActive
L001|12344567|B001|2026-02-04|null|true
```

**Delimitador:** Carácter pipe (`|`)

**📖 Para más detalles sobre la estructura de datos y decisiones de diseño, ver:** [`DATA_DESIGN.md`](DATA_DESIGN.md)

---

## 💻 Uso de la Aplicación

### Compilar y Ejecutar

**Opción 1: Maven (Recomendado)**
```bash
mvn compile exec:java
```

**Opción 2: IntelliJ IDEA**
1. IntelliJ detecta automáticamente `pom.xml` y descarga dependencias
2. **Run → Run 'Main'**

### Menú Principal

```
========== MAIN MENU ==========
1. User Management
2. Book Management
3. Loan Management
4. Search
5. View Active Loans
6. Generate Reports
0. Exit
================================
```

**Generación de Reportes:** 6 tipos de reportes con **plantilla institucional estandarizada**. Cada PDF incluye automáticamente: Universidad El Bosque, Facultad de Ingeniería, Bases de Datos I, nombre del sistema y fecha de generación. 1 reporte completo funcional (Usuarios por Dirección), 1 ejemplo de plantilla, 4 reportes pendientes para el equipo. Ver [`REPORTS_GUIDE.md`](REPORTS_GUIDE.md).

---

## ☁️ Google Drive para Trabajo en Equipo

### ¿Por qué Google Drive?

- ✅ Sincronización automática en tiempo real
- ✅ Acceso compartido para todo el equipo
- ✅ Sin necesidad de base de datos
- ✅ Sin necesidad de servidor
- ✅ Funciona offline (sincroniza cuando hay internet)
- ✅ Detección automática (sin configuración manual)

### ¿Cómo funciona?

1. **Líder del equipo**: Instala Google Drive Desktop, crea carpeta `BD_1/LibraryManagementApp/data/`, comparte con el equipo
2. **Miembros del equipo**: Instalan Google Drive Desktop, aceptan invitación, agregan shortcut
3. **Todos ejecutan la app**: Detecta automáticamente Google Drive y usa esos archivos
4. **Cambios sincronizados**: Cuando alguien crea/modifica datos, se sincroniza automáticamente con todos

### Setup Completo

**Ver:** `GOOGLE_DRIVE_SETUP.md` para instrucciones detalladas.

---

## ⚠️ Limitaciones y Consideraciones

### Limitaciones de Archivos vs Base de Datos

| Aspecto | Archivos de Texto | Base de Datos |
|---------|-------------------|---------------|
| **Concurrencia** | ❌ Sin control | ✅ Locks y transacciones |
| **Integridad** | ❌ No garantizada | ✅ Constraints y validaciones |
| **Performance** | ❌ Lectura completa | ✅ Índices y queries optimizados |
| **Escalabilidad** | ❌ Limitada | ✅ Alta capacidad |
| **ACID** | ❌ No soportado | ✅ Totalmente soportado |

### Race Conditions Posibles

**Escenario:**
```
Usuario A lee users.txt (3 usuarios)
Usuario B lee users.txt (3 usuarios)
Usuario A agrega usuario → guarda (4 usuarios)
Usuario B agrega usuario → guarda (4 usuarios)  ← ¡Sobrescribe cambio de A!
```

**Mitigación:**
- Coordinar con el equipo
- Evitar ediciones simultáneas
- Revisar archivos de conflicto de Google Drive: `users (conflicted copy).txt`
- Hacer merge manual si es necesario

### Propósito Educativo

Este proyecto demuestra:
1. **Por qué las bases de datos son necesarias** (limitaciones de archivos)
2. **Arquitectura de software limpia** (patrones de diseño)
3. **Persistencia de datos** (sin framework ORM)
4. **Colaboración en equipo** (Google Drive como solución temporal)

---

## 🎓 Patrones de Diseño Implementados

1. **MVC (Model-View-Controller)**: Separación de responsabilidades
2. **DTO (Data Transfer Object)**: Desacoplamiento entre capas
3. **Facade**: Interfaz simplificada para operaciones complejas
4. **DAO (Data Access Object)**: Abstracción de persistencia
5. **Mapper**: Conversión entre diferentes representaciones de datos

---

## 🔧 Configuración Avanzada

### Variable de Entorno (Opcional)

Si la detección automática falla, puedes configurar manualmente:

**macOS/Linux:**
```bash
export LIBRARY_DATA_PATH="/ruta/completa/a/data/"
```

**Windows:**
```powershell
[Environment]::SetEnvironmentVariable("LIBRARY_DATA_PATH", "C:\ruta\a\data\", "User")
```

### Prioridad de Ubicaciones

1. Variable de entorno `LIBRARY_DATA_PATH` (máxima prioridad)
2. Google Drive Desktop auto-detectado
3. Dropbox auto-detectado
4. Carpeta local `data/` (fallback por defecto)

---

## 📚 Documentación Adicional

- **[`REPORTS_GUIDE.md`](REPORTS_GUIDE.md)** - 📊 Guía completa de reportes PDF con Apache PDFBox (setup, implementación, trabajo en equipo)
- **[`GOOGLE_DRIVE_SETUP.md`](GOOGLE_DRIVE_SETUP.md)** - ☁️ Configuración de Google Drive para colaboración en tiempo real
- **[`DATA_DESIGN.md`](DATA_DESIGN.md)** - 🗄️ Decisiones de diseño: estructura de datos, atributos, relaciones

---

## 🐛 Troubleshooting

### "Local storage (no cloud sync)"

**Causa:** No detecta Google Drive.

**Solución:**
1. Verificar que Google Drive Desktop esté instalado
2. Verificar que la carpeta `BD_1/LibraryManagementApp/data/` exista en Google Drive
3. Si la carpeta está en "Compartido conmigo", agregar shortcut a "Mi unidad"
4. Esperar 1-2 minutos para sincronización

### Archivos de conflicto

Si aparecen archivos como `users (conflicted copy).txt`:
1. Abrir ambos archivos
2. Comparar cambios
3. Hacer merge manual
4. Eliminar archivo de conflicto
5. Coordinar mejor con el equipo

### Errores de compilación con Maven

```bash
# Limpiar y recompilar
mvn clean compile

# Si hay problemas con dependencias
mvn clean install -U
```

---

## 👥 Trabajo en Equipo

### Recomendaciones

✅ **DO:**
- Comunicarse antes de hacer cambios grandes
- Esperar 10 segundos después de guardar (para sincronización)
- Verificar icono de Google Drive antes de cerrar la app
- Usar la aplicación (no editar archivos .txt directamente)

❌ **DON'T:**
- Editar archivos .txt manualmente
- Cerrar inmediatamente después de guardar
- Trabajar sin conexión a internet
- Editar simultáneamente con otro miembro

---

## 📊 Estadísticas del Proyecto

- **Lenguaje:** Java 11+
- **Gestión de dependencias:** Maven
- **Líneas de código:** ~2,500+
- **Clases:** 24
- **Patrones de diseño:** MVC, DAO, DTO, Facade
- **Librerías externas:** Apache PDFBox 2.0.30
- **Formato de archivos:** Pipe-separated values (`.txt`)
- **Sistemas operativos:** macOS, Windows, Linux

---

## 📄 Licencia

Proyecto educativo - Universidad El Bosque - 2026

---

## 🎉 ¡Listo para usar!

**Inicio rápido:**
```bash
# Descargar dependencias (solo primera vez)
mvn clean install

# Ejecutar
mvn compile exec:java
```

**IntelliJ IDEA:**
1. Abrir proyecto (IntelliJ detecta Maven automáticamente)
2. **Run → Run 'Main'**

**Trabajo en equipo:**
- Ver guía completa en [`GOOGLE_DRIVE_SETUP.md`](GOOGLE_DRIVE_SETUP.md)
- La app detecta automáticamente Google Drive

**Reportes PDF:**
- Ver [`REPORTS_GUIDE.md`](REPORTS_GUIDE.md) para implementar los 5 reportes pendientes

**¡Happy coding! 📚✨**
