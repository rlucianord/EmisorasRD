# Flujo profesional de desarrollo

1. Crear una rama descriptiva:

```bash
git checkout -b feat/nueva-categoria
```

2. Modificar código o catálogo.
3. Si se modifica `catalog/stations.csv`, ejecutar:

```bash
python scripts/validate_catalog.py
python scripts/sync_catalog_assets.py
```

4. Revisar cambios:

```bash
git diff
```

5. Ejecutar el build:

```bash
gradle assembleDebug
```

6. Hacer commit:

```bash
git add .
git commit -m "feat: add station category"
```

7. Push y Pull Request.
8. Esperar CI.
9. Revisar el APK generado antes de fusionar cambios importantes.
