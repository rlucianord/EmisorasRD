# Gobierno del catálogo

El catálogo de Emisoras RD es un activo editorial versionado.

## Principios

- No inventar datos.
- Preferir fuentes institucionales y sitios oficiales de las emisoras.
- Mantener la fuente de cada dato importante.
- Separar frecuencia autorizada de URL de streaming.
- Separar nombre legal, nombre comercial y nombre mostrado cuando sea necesario.
- Registrar incertidumbre.
- Registrar fecha de última verificación.

## Jerarquía de fuentes

1. INDOTEL y documentación regulatoria aplicable.
2. Sitio oficial de la emisora o grupo propietario.
3. Directorios técnicos de streaming como Radio Browser.
4. Directorios secundarios, únicamente como pista para investigación.

Radio Browser es útil para descubrir y comprobar streams, pero no debe considerarse autoridad regulatoria. Su API permite filtrar estaciones por país, estado, etiquetas, codec, bitrate y otros campos. 

## Cambios de frecuencia

Una frecuencia puede cambiar por resolución, migración o adecuación. Por eso el catálogo debe conservar la fuente y fecha de verificación, no solamente el número de frecuencia.

## Revisión

Todo Pull Request que cambie una frecuencia, propietario, ubicación o sitio oficial debe explicar la fuente utilizada.
