# Changelog

## [1.0.0] - 2026-10-01

### Added

- Catálogo dinámico de emisoras dominicanas.
- Clasificación por género.
- Búsqueda.
- Favoritos.
- Reproductor Media3/ExoPlayer.
- Servicio de reproducción en primer plano.
- Fuentes RSS iniciales.
- Documentación técnica.
- GitHub Actions para APK debug.

### Notes

Esta versión se considera base técnica para una futura edición editorial del catálogo.

## [1.1.0] - 2026-10-01

### Added
- Catálogo editorial local de emisoras.
- Metadatos de frecuencia, banda, provincia, municipio y categoría.
- Filtro por provincia.
- Capa de combinación entre catálogo editorial y Radio Browser.
- Política de verificación y gobernanza de datos.
- Validador `scripts/validate_catalog.py`.
- Documentación del modelo de datos.

### Changed
- `RadioRepository` ahora recibe `Context` y fusiona metadatos editoriales.
- La categoría de una emisora prioriza la clasificación editorial cuando existe.
