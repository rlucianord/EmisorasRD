# Catálogo editorial de Emisoras RD

## Objetivo

El catálogo editorial es la capa propia de Emisoras RD. No sustituye al directorio de streaming: complementa sus datos con identidad, frecuencia, banda, provincia, municipio, categoría y procedencia.

## Jerarquía de fuentes

1. **INDOTEL**: referencia principal para asignaciones/frecuencias y datos regulatorios.
2. **Sitio oficial de la emisora**: identidad comercial, stream, logo y datos de contacto.
3. **Radio Browser**: descubrimiento y disponibilidad técnica de streams.
4. **Fuentes secundarias**: únicamente para investigación; no deben convertir un dato no verificado en dato oficial.

Radio Browser documenta filtros por país, estado, idioma, etiquetas, codec, bitrate y disponibilidad (`hidebroken`). citeturn0search0

## Estado de verificación

Cada registro debe poder clasificarse como:

- `verified`: confirmado por fuente primaria.
- `directory`: obtenido de un directorio técnico.
- `pending`: pendiente de comprobación.
- `deprecated`: ya no debe mostrarse como activo.

La primera semilla incluida en `app/src/main/assets/catalog/stations_editorial.csv` contiene nombres/frecuencias tomados de una publicación institucional de INDOTEL de 2025. Ese documento presenta un listado de 130 emisoras; algunos registros requieren comprobación adicional porque una misma frecuencia puede haber cambiado de marca o haber sido objeto de migraciones. citeturn1search0

## Regla de calidad

Nunca rellenar una frecuencia, provincia, URL de stream o logo por inferencia. Si no hay evidencia, dejar el campo vacío y marcarlo como pendiente.

## Flujo de actualización

1. Revisar cambios regulatorios en INDOTEL.
2. Confirmar identidad en el sitio oficial de la emisora.
3. Descubrir/probar el stream mediante Radio Browser u otra fuente pública.
4. Registrar la fuente y fecha de verificación.
5. Ejecutar `python scripts/validate_catalog.py`.
6. Abrir PR con evidencia del cambio.
