# Entrega del proyecto

## Qué recibe el desarrollador

Este repositorio contiene:

- código fuente Android;
- configuración Gradle;
- recursos XML;
- repositorios de datos;
- servicio de reproducción;
- lector RSS;
- almacenamiento de favoritos;
- workflows CI;
- documentación;
- política de privacidad;
- licencia;
- changelog.

## Primeros pasos

```bash
git clone <URL_DEL_REPOSITORIO>
cd EmisorasRD
gradle assembleDebug
```

## Para Devin

Prompt recomendado:

> Lee README.md, docs/ARCHITECTURE.md, docs/DATA_SOURCES.md y docs/DEVELOPMENT.md antes de modificar el proyecto. Mantén separación entre catálogo, RSS y reproducción. No reemplaces Radio Browser por una lista fija sin justificarlo. Ejecuta lint y assembleDebug después de cambios.

## Para VS Code

Instala extensiones de Kotlin/Gradle y configura JDK 17. La compilación puede ejecutarse desde terminal.

## Para Android Studio

Open > selecciona la carpeta raíz del proyecto.

## Próxima etapa

La siguiente fase debe introducir una fuente editorial propia para corregir nombres, logos, provincias, frecuencia AM/FM y URLs de streaming que no estén correctamente mantenidas por directorios externos.
