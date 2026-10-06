# Modelo de datos

## Station

`Station` representa una emisora reproducible:

- `stationUuid`: identificador técnico del directorio.
- `name`: nombre detectado del stream.
- `streamUrl`: URL reproducible.
- `homepage`: sitio web.
- `favicon`: logo/fav icon remoto.
- `country/state/language`: metadatos del directorio.
- `tags`: etiquetas técnicas/editoriales.
- `codec/bitrate`: características del stream.
- `lastCheckOk`: último estado técnico reportado.
- `frequency/band/province/municipality`: metadatos editoriales.
- `editorialCategory/editorialSource`: clasificación y procedencia.

## EditorialStation

Es la fuente editorial mantenida por el proyecto. Se carga desde `catalog/stations_editorial.csv` y se mezcla con los streams descubiertos dinámicamente.

Esta separación permite actualizar una frecuencia o categoría sin depender de que el directorio técnico tenga el dato correcto.
