# ☁️ Configuración de Google Drive para Trabajo en Equipo

Guía completa para configurar Google Drive Desktop y permitir que múltiples personas trabajen en la LibraryApp compartiendo los mismos datos en tiempo real.

---

## 📋 Tabla de Contenidos

1. [¿Por qué Google Drive?](#por-qué-google-drive)
2. [Setup para el Líder](#setup-para-el-líder-10-minutos)
3. [Setup para Miembros del Equipo](#setup-para-miembros-10-minutos)
4. [Verificación](#verificación)
5. [Troubleshooting](#troubleshooting)

---

## 🎯 ¿Por qué Google Drive?

### Ventajas

- ✅ **Sincronización automática** en tiempo real
- ✅ **Sin necesidad de servidor** o base de datos
- ✅ **Gratis** (15 GB con cuenta Google gratuita)
- ✅ **Funciona offline** (sincroniza cuando hay internet)
- ✅ **Detección automática** por la aplicación
- ✅ **Multiplataforma** (Windows, macOS)

### Cómo Funciona

```
Usuario A crea un libro en su PC
         ↓
Guarda en archivo local (Google Drive)
         ↓
Google Drive Desktop sincroniza a la nube (1-3 segundos)
         ↓
Google Drive sincroniza a otros PCs (2-10 segundos)
         ↓
Usuario B ve el nuevo libro automáticamente
```

**Tiempo total:** 5-15 segundos

---

## 👑 Setup para el Líder (10 minutos)

El líder es quien crea la estructura inicial y la comparte con el equipo.

### Paso 1: Instalar Google Drive Desktop (5 min)

#### macOS
1. Ir a https://www.google.com/drive/download/
2. Descargar "Google Drive para escritorio"
3. Abrir archivo `.dmg`
4. Arrastrar a Aplicaciones
5. Abrir Google Drive desde Aplicaciones
6. Iniciar sesión con tu cuenta Google
7. Completar wizard

#### Windows
1. Ir a https://www.google.com/drive/download/
2. Descargar "Google Drive para escritorio"
3. Ejecutar instalador `.exe`
4. Iniciar sesión con tu cuenta Google
5. Completar wizard

### Paso 2: Crear Estructura en Google Drive (3 min)

**Ve a:** https://drive.google.com

1. **Crear carpeta principal:**
   - Click "Nuevo" → "Nueva carpeta"
   - Nombre: `BD_1` (o el nombre de tu proyecto)
   - Click "Crear"

2. **Entrar a la carpeta y crear subcarpeta:**
   - Doble click en `BD_1`
   - Click "Nuevo" → "Nueva carpeta"
   - Nombre: `LibraryManagementApp`
   - Click "Crear"

3. **Crear carpeta de datos:**
   - Entrar a `LibraryManagementApp`
   - Click "Nuevo" → "Nueva carpeta"
   - Nombre: `data`
   - Click "Crear"

4. **Crear archivos (opcional):**
   - Los archivos `.txt` se crearán automáticamente cuando uses la app
   - O puedes crearlos manualmente vacíos

**Estructura final:**
```
Mi unidad/
└── BD_1/
    └── LibraryManagementApp/
        └── data/
            ├── users.txt (se crea automáticamente)
            ├── books.txt (se crea automáticamente)
            └── loans.txt (se crea automáticamente)
```

### Paso 3: Compartir con el Equipo (2 min)

1. **En drive.google.com:**
   - Navegar a la carpeta `LibraryManagementApp`
   - Click derecho en la carpeta
   - Seleccionar "Compartir"

2. **Agregar miembros:**
   - Ingresar emails de los miembros del equipo
   - **IMPORTANTE:** Cambiar permiso a **"Editor"** (no "Lector")
   - Click "Enviar"

3. **Los miembros recibirán email de invitación**

### Paso 4: Verificar Sincronización Local (1 min)

**macOS:**
```bash
# Verificar que la carpeta se sincronizó
ls ~/Library/CloudStorage/GoogleDrive-*/Mi\ unidad/BD_1/LibraryManagementApp/
```

**Windows:**
```powershell
# Verificar en File Explorer o PowerShell
dir "$env:USERPROFILE\Google Drive\BD_1\LibraryManagementApp"
```

**Debería mostrar:** `data`

---

## 👥 Setup para Miembros (10 minutos)

Los miembros del equipo siguen estos pasos para unirse.

### Paso 1: Instalar Google Drive Desktop (5 min)

Mismo proceso que el líder (ver arriba).

### Paso 2: Aceptar Invitación (1 min)

1. **Revisar email de invitación**
   - Buscar email de Google Drive
   - Asunto: "... compartió LibraryManagementApp contigo"

2. **Click en "Abrir en Drive"**
   - Te llevará a drive.google.com
   - Verás la carpeta en "Compartido conmigo"

### Paso 3: Agregar Shortcut a "Mi unidad" (1 min)

⚠️ **PASO CRÍTICO** - Sin esto la carpeta NO se sincroniza:

1. En drive.google.com, ir a **"Compartido conmigo"** (barra lateral)
2. Buscar `LibraryManagementApp`
3. **Click derecho** en la carpeta
4. Seleccionar: **"Organizar" → "Agregar acceso directo"**
5. Elegir: **"Mi unidad"**
6. Click **"Agregar"**

Ahora la carpeta aparecerá en "Mi unidad" y se sincronizará a tu PC.

### Paso 4: Esperar Sincronización (2 min)

**Verificar icono de Google Drive:**
- En la barra de tareas (Windows) o barra de menú (Mac)
- Debe mostrar check verde ✓ o "Copia de seguridad completada"

**Verificar en tu PC:**

**macOS:**
```bash
ls ~/Library/CloudStorage/GoogleDrive-*/Mi\ unidad/BD_1/LibraryManagementApp/data/
```

**Windows:**
```powershell
dir "$env:USERPROFILE\Google Drive\BD_1\LibraryManagementApp\data"
```

**Deberías ver los archivos:** `users.txt`, `books.txt`, `loans.txt`

### Paso 5: Clonar el Proyecto y Ejecutar (1 min)

```bash
# Clonar repositorio
git clone [URL_DEL_REPOSITORIO]
cd LibraryApp

# Compilar
javac -d bin -sourcepath src src/Main.java

# Ejecutar
java -cp bin Main

# O desde IntelliJ: Click derecho en Main.java → Run
```

**Deberías ver:**
```
=== File Manager Configuration ===
📁 Data directory: .../BD_1/LibraryManagementApp/data/
☁️  Google Drive sync enabled
⚠️  Wait for sync before closing app
===================================
```

✅ **Si ves "☁️ Google Drive sync enabled"** → ¡Listo!

---

## ✅ Verificación

### Test 1: Ver Datos del Líder

**Como miembro del equipo:**
1. Ejecutar la app desde IntelliJ o terminal
2. Ir a "User Management" → "List all users"
3. **Deberías ver los usuarios que creó el líder** ✅

### Test 2: Crear Datos y Verificar Sincronización

**Persona A:**
1. Crear usuario: `TEST_SYNC`
2. Salir de la app
3. Esperar 10 segundos

**Persona B:**
1. Ejecutar la app
2. Listar usuarios
3. **Deberías ver `TEST_SYNC`** ✅

### Test 3: Verificar en Web

1. Ve a https://drive.google.com
2. Navega a `BD_1/LibraryManagementApp/data/`
3. Abre `users.txt`
4. **Deberías ver todos los usuarios** ✅

---

## 🔧 Troubleshooting

### Problema: "Local storage (no cloud sync)"

**Síntomas:** La app dice "Local storage" en vez de "Google Drive sync enabled"

**Causas y Soluciones:**

#### Causa 1: Carpeta no sincronizada

**Verificar:**
```bash
# macOS
ls ~/Library/CloudStorage/GoogleDrive-*/Mi\ unidad/BD_1/

# Windows
dir "%USERPROFILE%\Google Drive\BD_1"
```

Si no aparece `LibraryManagementApp`:
- Verificar que agregaste el shortcut (Paso 3)
- Esperar 1-2 minutos más
- Verificar icono de Google Drive (debe mostrar ✓)

#### Causa 2: Google Drive Desktop no instalado

**Solución:**
- Descargar e instalar: https://www.google.com/drive/download/

#### Causa 3: Carpeta en ubicación no estándar

**Solución con variable de entorno:**

**macOS:**
```bash
export LIBRARY_DATA_PATH="$HOME/Library/CloudStorage/GoogleDrive-tu-email@gmail.com/Mi unidad/BD_1/LibraryManagementApp/data/"
./run_with_drive.sh
```

**Windows:**
```powershell
$env:LIBRARY_DATA_PATH="$env:USERPROFILE\Google Drive\BD_1\LibraryManagementApp\data\"
# Luego ejecutar la app
```

### Problema: "Permission denied"

**Causa:** Tienes permisos de "Lector" en vez de "Editor"

**Solución:**
1. Pedir al líder cambiar tus permisos
2. Líder: drive.google.com → Click derecho en carpeta → "Compartir"
3. Cambiar tu email de "Lector" a "Editor"
4. Esperar 1 minuto y reiniciar la app

### Problema: Cambios no se sincronizan

**Soluciones:**

#### Verificar Google Drive activo
- Click en icono de Google Drive
- Debe decir "Copia de seguridad completada"
- Si dice "Pausado" → Click "Reanudar"

#### Esperar más tiempo
- La sincronización tarda 5-30 segundos
- No cierres la app inmediatamente después de guardar

#### Verificar conexión a internet
```bash
# Test de conexión
ping google.com
```

### Problema: Archivos de conflicto

**Síntomas:** Aparecen archivos como:
```
users (conflicted copy 2026-02-04).txt
```

**Causa:** Dos personas editaron el mismo archivo simultáneamente

**Solución:**
1. Abrir ambos archivos (original y conflicted copy)
2. Comparar línea por línea
3. Copiar datos faltantes del conflicted copy al original
4. Eliminar el conflicted copy
5. **Coordinar mejor con el equipo**

### Problema: Múltiples Cuentas de Google

**Si tienes varias cuentas de Google Drive:**

**macOS:**
```bash
# Ver todas las cuentas
ls ~/Library/CloudStorage/

# Resultado ejemplo:
# GoogleDrive-personal@gmail.com
# GoogleDrive-trabajo@gmail.com
```

**Solución:** La app automáticamente busca en todas, pero si quieres especificar:
```bash
export LIBRARY_DATA_PATH="$HOME/Library/CloudStorage/GoogleDrive-personal@gmail.com/Mi unidad/BD_1/LibraryManagementApp/data/"
```

---

## 📍 Ubicaciones de Google Drive

### macOS
```
~/Library/CloudStorage/GoogleDrive-{email}/
  ├── Mi unidad/          (español)
  └── My Drive/           (inglés)
      └── BD_1/
          └── LibraryManagementApp/
              └── data/
```

### Windows
```
C:\Users\{Usuario}\Google Drive\
  └── BD_1\
      └── LibraryManagementApp\
          └── data\

O en unidad montada:
G:\My Drive\
  └── BD_1\
      └── LibraryManagementApp\
          └── data\
```

---

## ⚠️ Limitaciones y Mejores Prácticas

### Limitaciones

❌ **No hay control de concurrencia** - Si dos personas editan simultáneamente, el último sobrescribe
❌ **Posibles race conditions** - Los cambios pueden perderse si no se coordina
❌ **Archivos de conflicto** - Google Drive crea copias si detecta ediciones simultáneas

### Mejores Prácticas

✅ **Comunicarse con el equipo** antes de hacer cambios grandes
✅ **Esperar 10 segundos** después de guardar cambios
✅ **Verificar icono de Google Drive** antes de cerrar la app
✅ **No editar archivos .txt directamente** - usar siempre la aplicación
✅ **Revisar archivos de conflicto** periódicamente

---

## 🎓 Para Demo en la Universidad

### Opción 1: Llevar Laptop
- Conectar a hotspot móvil
- Demostrar desde tu laptop
- Proyectar pantalla

### Opción 2: Video/Screenshots
- Grabar video mostrando sincronización
- Screenshots del proceso

### Opción 3: Modo Local (Sin Google Drive)
La app funciona perfectamente sin Google Drive:
```
💾 Local storage (no cloud sync)
```

**Explicar al profesor:**
> "En casa usamos Google Drive Desktop para colaboración en tiempo real entre el equipo, pero el sistema tiene fallback automático para funcionar en cualquier entorno sin configuración."

---

## ✅ Checklist Final

### Para el Líder:
- [ ] Google Drive Desktop instalado
- [ ] Carpeta `BD_1/LibraryManagementApp/data/` creada en drive.google.com
- [ ] Carpeta compartida con equipo (permisos "Editor")
- [ ] Verificado que se sincronizó localmente
- [ ] App ejecuta y detecta Google Drive
- [ ] Datos de prueba creados

### Para Miembros:
- [ ] Google Drive Desktop instalado
- [ ] Invitación aceptada
- [ ] Shortcut agregado a "Mi unidad"
- [ ] Carpeta sincronizada localmente
- [ ] Proyecto clonado
- [ ] App ejecuta y detecta Google Drive
- [ ] Puedo ver datos del líder
- [ ] Mis cambios se sincronizan

---

## 📊 Resumen

**Detección Automática:**
- ✅ macOS (español e inglés)
- ✅ Windows (español e inglés)  
- ✅ Múltiples ubicaciones
- ✅ Sin configuración manual

**Requisitos:**
- Google Drive Desktop instalado
- Carpeta compartida con permisos "Editor"
- Shortcut agregado a "Mi unidad"

**Resultado:**
- Sincronización automática en 5-15 segundos
- Colaboración en tiempo real
- Sin necesidad de servidor o base de datos

---

**¡Configuración completa! 🎉**

Para uso detallado de la aplicación, consulta: `README.md`
