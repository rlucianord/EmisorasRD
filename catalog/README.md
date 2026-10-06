# Catálogo maestro de Emisoras RD

Este directorio contiene la fuente editorial versionada del proyecto.

## Regla de oro

No se agregan frecuencias, provincias, streams, logos o sitios oficiales por inferencia. Cada dato debe tener una fuente y, cuando corresponda, una fecha de verificación.

## Archivos

- `stations.csv`: catálogo editorial principal.
- `source_registry.json`: registro de fuentes utilizadas.

La aplicación copia el catálogo a `app/src/main/assets/catalog/` durante el proceso de publicación del proyecto.

## Estados recomendados

- `verified`: verificado recientemente contra una fuente confiable.
- `needs_review`: existe una fuente, pero requiere comprobación adicional.
- `unverified`: dato descubierto en una fuente secundaria y todavía no confirmado.

## Flujo de actualización

1. Modificar `stations.csv`.
2. Ejecutar `python scripts/validate_catalog.py`.
3. Ejecutar `python scripts/sync_catalog_assets.py`.
4. Revisar el diff.
5. Crear Pull Request explicando la fuente del cambio.
