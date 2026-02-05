# 📚 Library Management System

Sistema de gestión de biblioteca desarrollado en Java con persistencia en archivos y sincronización automática con Google Drive para colaboración en equipo.

---

## ✨ Características

### Funcionalidades
- 👤 **Gestión de Usuarios**: Crear, actualizar, eliminar y buscar usuarios
- 📖 **Gestión de Libros**: CRUD completo para libros
- 📋 **Gestión de Préstamos**: Registro de préstamos y devoluciones
- 🔍 **Sistema de Búsqueda**: Buscar usuarios por nombre, libros por título, préstamos por usuario
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
- Java JDK 8+
- Google Drive Desktop (opcional, para trabajo en equipo)
- IntelliJ IDEA (recomendado)

### Opción 1: Trabajo Individual (Sin Google Drive)

```bash
# Compilar
javac -d bin -sourcepath src src/Main.java

# Ejecutar
java -cp bin Main
```

Los datos se guardan en la carpeta local `data/`.

### Opción 2: Trabajo en Equipo (Con Google Drive)

**Ver guía completa:** `GOOGLE_DRIVE_SETUP.md`

**Resumen rápido:**
1. Instalar Google Drive Desktop
2. Aceptar invitación del líder
3. Agregar shortcut a "Mi unidad"
4. Ejecutar desde IntelliJ o terminal

**¡Detecta automáticamente Google Drive!** Sin configuración manual.

---

## 📁 Estructura del Proyecto

```
LibraryApp/
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
├── run_with_drive.sh                       # Script para ejecutar con Google Drive
├── README.md                               # Este archivo
└── GOOGLE_DRIVE_SETUP.md                   # Guía de configuración de Google Drive
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

---

## 💻 Uso de la Aplicación

### Compilar y Ejecutar

**Desde IntelliJ IDEA:**
1. Abrir proyecto
2. Click derecho en `Main.java`
3. "Run 'Main.main()'"

**Desde Terminal:**
```bash
# Compilar
javac -d bin -sourcepath src src/Main.java

# Ejecutar
java -cp bin Main
```

### Menú Principal

```
========== MAIN MENU ==========
1. User Management
2. Book Management
3. Loan Management
4. Search
5. View Active Loans
0. Exit
================================
```

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

- `GOOGLE_DRIVE_SETUP.md` - Guía completa de configuración de Google Drive para trabajo en equipo

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

### Errores de compilación

```bash
# Limpiar y recompilar
rm -rf bin/*
javac -d bin -sourcepath src src/Main.java
java -cp bin Main
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

- **Lenguaje:** Java 8+
- **Líneas de código:** ~2,500+
- **Clases:** 17
- **Patrones de diseño:** 5
- **Formato de archivos:** Pipe-separated values (`.txt`)
- **Sistemas operativos:** macOS, Windows, Linux

---

## 📄 Licencia

Proyecto educativo - Universidad El Bosque - 2026

---

## 🎉 ¡Listo para usar!

**Trabajo individual:**
```bash
javac -d bin -sourcepath src src/Main.java && java -cp bin Main
```

**Trabajo en equipo:**
1. Configurar Google Drive (ver `GOOGLE_DRIVE_SETUP.md`)
2. Ejecutar desde IntelliJ o terminal
3. La app detecta automáticamente Google Drive

**¡Happy coding! 📚✨**
