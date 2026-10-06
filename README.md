# Emisoras RD 📻🇩🇴

Aplicación Android para descubrir, clasificar y reproducir emisoras de República Dominicana, con un catálogo editorial versionado y fuentes técnicas de streaming.

> **Estado:** MVP profesional / base de producción. El catálogo editorial está diseñado para crecer mediante verificación documentada. No se inventan datos faltantes.

## Funcionalidades

- Catálogo de emisoras de República Dominicana.
- Categorías por género/uso editorial.
- Filtro por provincia y municipio.
- Búsqueda por nombre.
- Favoritos.
- Reproducción mediante AndroidX Media3/ExoPlayer.
- Soporte para streams HLS.
- Reproducción en segundo plano.
- Fuentes RSS de noticias separadas del catálogo de radio.
- Catálogo editorial local + descubrimiento técnico de Radio Browser.
- Validación automática del catálogo.
- GitHub Actions para generar APK debug.

## Arquitectura de datos

```text
INDOTEL / fuentes oficiales
          │
          ▼
   catalog/stations.csv
          │
          ├── validación
          │
          └── sync_catalog_assets.py
                         │
                         ▼
              Android assets/catalog
                         │
             ┌───────────┴───────────┐
             ▼                       ▼
       datos editoriales       Radio Browser
             │                       │
             └──────────┬────────────┘
                        ▼
                 RadioRepository
                        │
                        ▼
                 Media3 Player
```

Radio Browser se usa como fuente técnica para descubrir/comprobar streams, no como autoridad regulatoria. Su API permite filtrar estaciones por país, estado, etiquetas, codec, bitrate y otros campos. citeturn0search4

La documentación oficial de Android muestra Media3 1.11.1 como versión estable actual y documenta su uso con ExoPlayer para reproducción. citeturn0search1turn0search5

## Estructura

```text
EmisorasRD/
├── app/                         # Aplicación Android
├── catalog/                     # Fuente editorial versionada
│   ├── stations.csv
│   ├── source_registry.json
│   └── README.md
├── docs/                        # Documentación técnica y operativa
├── scripts/                     # Validación y sincronización
├── .github/workflows/           # CI/CD
├── README.md
├── CONTRIBUTING.md
├── SECURITY.md
├── PRIVACY.md
└── CHANGELOG.md
```

## Compilación rápida en Windows

### 1. Requisitos

- Windows 10/11.
- Git.
- Python 3.12+.
- JDK 17.
- Android SDK API 36.
- Gradle 8.13 o Android Studio para gestionar el SDK/Gradle.

### 2. Abrir en VS Code

`File → Open Folder → EmisorasRD`

### 3. Validar catálogo

```powershell
python scripts\validate_catalog.py
```

### 4. Sincronizar catálogo

```powershell
python scripts\sync_catalog_assets.py
```

### 5. Compilar

```powershell
gradle assembleDebug
```

APK:

```text
app\build\outputs\apk\debug\app-debug.apk
```

### 6. Instalar en dispositivo de prueba

Con un dispositivo Android autorizado por USB debugging:

```powershell
adb install -r app\build\outputs\apk\debug\app-debug.apk
```

## Compilación con GitHub Actions

No necesitas instalar Gradle localmente para obtener el APK desde CI.

1. Crea un repositorio vacío en GitHub.
2. Sube el proyecto.
3. Abre **Actions → Android CI**.
4. Espera a que termine el workflow.
5. Descarga el artifact **EmisorasRD-debug-apk**.

Ver `docs/GITHUB_SETUP.md` para los comandos exactos.

## Catálogo editorial

El archivo principal es:

`catalog/stations.csv`

Cada registro debe poder rastrearse a una fuente. Para modificarlo:

```powershell
python scripts\validate_catalog.py
python scripts\sync_catalog_assets.py
```

La política completa está en `docs/CATALOG_GOVERNANCE.md` y `docs/VERIFICATION_POLICY.md`.

## Fuentes regulatorias

INDOTEL publica documentación institucional relacionada con concesiones, licencias y frecuencias de radiodifusión sonora. Las resoluciones pueden reflejar cambios posteriores, por lo que una frecuencia no debe considerarse inmutable sin revisar su fuente y fecha. citeturn0search24turn0search26

## Contribuir

Consulta `CONTRIBUTING.md`.

Los cambios de frecuencia, ubicación, propietario o sitio oficial deben incluir una fuente verificable.

## Seguridad y privacidad

Consulta `SECURITY.md` y `PRIVACY.md`.

## Licencia

MIT. Ver `LICENSE`.
