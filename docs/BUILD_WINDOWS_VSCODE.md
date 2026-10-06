# Compilar Emisoras RD en Windows + VS Code

## Requisitos

- Windows 10/11 de 64 bits.
- Git.
- JDK 17.
- VS Code.
- Android SDK con Android API 36.
- Gradle 8.13 si vas a compilar desde la terminal sin Android Studio.

La aplicación usa Java/Kotlin 17 y Android API 36. La reproducción utiliza AndroidX Media3 1.11.1.

## Opción recomendada: Android Studio para instalar el SDK, VS Code para editar

1. Instala Android Studio.
2. Desde SDK Manager instala Android SDK Platform 36 y Build-Tools correspondientes.
3. Configura `ANDROID_HOME` o `ANDROID_SDK_ROOT` al directorio del SDK.
4. Abre VS Code.
5. `File → Open Folder` y selecciona `EmisorasRD`.
6. Abre una terminal PowerShell.

Comprueba Java:

```powershell
java -version
```

Debe mostrar Java 17.

Comprueba el SDK:

```powershell
adb --version
```

## Validar catálogo

Desde la raíz:

```powershell
python scripts\validate_catalog.py
```

Resultado esperado:

```text
OK: 47 editorial station records validated
```

## Sincronizar catálogo con Android

```powershell
python scripts\sync_catalog_assets.py
```

## Compilar debug

Si tienes Gradle instalado:

```powershell
gradle assembleDebug
```

APK:

```text
app\build\outputs\apk\debug\app-debug.apk
```

## Instalar en un teléfono conectado

Activa Opciones de desarrollador y Depuración USB en el dispositivo de prueba.

Luego:

```powershell
adb devices
adb install -r app\build\outputs\apk\debug\app-debug.apk
```

## Compilar desde GitHub sin configurar Gradle localmente

Después de hacer push:

1. Abre GitHub.
2. Entra en `Actions`.
3. Selecciona `Android CI`.
4. Abre la ejecución.
5. Espera a que termine `Build debug APK`.
6. Descarga el artifact `EmisorasRD-debug-apk`.

## Release firmado

El proyecto deliberadamente no contiene una clave privada.

Para publicar en Google Play habrá que crear un keystore y almacenarlo de forma segura como GitHub Actions Secrets. Consulta `docs/RELEASE.md` antes de hacer ese proceso.
