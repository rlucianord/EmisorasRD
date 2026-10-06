# Fuentes de datos

## Radio Browser

Fuente:

https://docs.radio-browser.info/

Endpoint usado:

`https://de1.api.radio-browser.info/json/stations/search`

Parámetros:

- `countrycode=DO`
- `countrycodeExact=true`
- `hidebroken=true`
- `order=name`
- `limit=500`

Campos utilizados:

- `stationuuid`
- `name`
- `url`
- `url_resolved`
- `homepage`
- `favicon`
- `country`
- `state`
- `language`
- `tags`
- `codec`
- `bitrate`
- `lastcheckok`

## RSS

Página oficial consultada:

https://www.radiorepublica.do/rss/listado/

Feeds utilizados:

- https://www.radiorepublica.do/rss/deportes/
- https://www.radiorepublica.do/rss/economia/
- https://www.radiorepublica.do/rss/nacional/
- https://www.radiorepublica.do/rss/cultura/
- https://www.radiorepublica.do/rss/entretenimiento/

## Reglas de atribución

La aplicación no presenta contenido editorial externo como si fuera propiedad de Emisoras RD. Los titulares y enlaces RSS deben mantenerse atribuidos a su fuente.

## Operación

Si una fuente deja de funcionar:

1. Confirmar la URL en el sitio oficial.
2. Actualizar `RssRepository.kt`.
3. Ejecutar las pruebas.
4. Registrar el cambio en `CHANGELOG.md`.


## Fuente regulatoria

**INDOTEL** es la referencia prioritaria para información de asignación/frecuencia. El proyecto no debe interpretar una lista histórica como garantía de que una marca o frecuencia continúe vigente.

## Directorio técnico

**Radio Browser** se utiliza para descubrimiento y estado técnico de streams. Su API documenta filtros por `countrycode`, `state`, `language`, `tag`, `codec`, `bitrate` y `hidebroken`.

## Regla

El catálogo editorial no copia automáticamente todos los datos del directorio técnico. Solo incorpora metadatos que el proyecto pueda mantener y auditar.
