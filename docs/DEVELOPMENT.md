# Guía de desarrollo

## Requisitos

- JDK 17
- Android SDK 36
- Build Tools 35+
- Gradle 8.13
- Android Studio reciente o VS Code

## Build

```bash
gradle clean
gradle assembleDebug
```

## Instalación por ADB

```bash
adb install -r app/build/outputs/apk/debug/app-debug.apk
```

## Lint

```bash
gradle lint
```

## Checklist antes de un release

- [ ] Cambiar `versionCode`.
- [ ] Cambiar `versionName`.
- [ ] Verificar URLs de Radio Browser.
- [ ] Verificar feeds RSS.
- [ ] Probar Wi-Fi.
- [ ] Probar datos móviles.
- [ ] Probar stream HTTPS.
- [ ] Probar stream HTTP.
- [ ] Probar HLS.
- [ ] Probar reproducción en segundo plano.
- [ ] Probar Android 13+ y permiso de notificaciones.
- [ ] Generar APK firmado.
- [ ] Revisar política de privacidad.
