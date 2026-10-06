# Solución de problemas

## No aparecen emisoras

Comprueba:

1. conexión a Internet;
2. endpoint de Radio Browser;
3. respuesta HTTP;
4. que el filtro `countrycode=DO` siga siendo válido.

## Una emisora aparece pero no reproduce

La URL de streaming puede haber cambiado. La aplicación utiliza `url_resolved` cuando está disponible.

## HTTP bloqueado

Android moderno prefiere HTTPS. La aplicación mantiene `usesCleartextTraffic=true` porque algunos streams de radio heredados todavía utilizan HTTP. En una futura versión puede restringirse a hosts aprobados.

## RSS vacío

Algunos servidores RSS responden con `Content-Type: text/xml`. La app procesa XML directamente y no depende de un parser JSON.

## GitHub Actions falla

Verifica:

- JDK 17;
- Gradle 8.13;
- Android SDK 36;
- acceso a Google Maven y Maven Central.
