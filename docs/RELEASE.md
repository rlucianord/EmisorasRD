# Release checklist

## Debug

`gradle assembleDebug`

## Release

Configurar una firma privada mediante propiedades de Gradle o secretos de CI.

Nunca guardar el keystore dentro del repositorio.

Variables recomendadas para CI:

- `ANDROID_KEYSTORE_BASE64`
- `ANDROID_KEY_ALIAS`
- `ANDROID_KEY_PASSWORD`
- `ANDROID_KEYSTORE_PASSWORD`

## Versionado

Se utiliza Semantic Versioning:

`MAJOR.MINOR.PATCH`

Ejemplo:

`1.1.0`

cuando se agregue una funcionalidad compatible hacia atrás.

## Distribución

Antes de publicar en Google Play:

- completar ficha de aplicación;
- política de privacidad;
- clasificación de contenido;
- declaración de datos;
- pruebas en dispositivos reales;
- firma Play App Signing.
