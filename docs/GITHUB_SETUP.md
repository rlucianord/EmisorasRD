# Publicar Emisoras RD en GitHub

## 1. Crear repositorio

En GitHub crea un repositorio vacío, por ejemplo:

`EmisorasRD`

No agregues README, .gitignore ni licencia desde GitHub si ya vienen incluidos en este proyecto.

## 2. Inicializar y subir desde VS Code

Abre la carpeta raíz `EmisorasRD` en VS Code y ejecuta en la terminal:

```powershell
git init
git add .
git commit -m "chore: initial Emisoras RD repository"
git branch -M main
git remote add origin https://github.com/TU_USUARIO/EmisorasRD.git
git push -u origin main
```

Cambia `TU_USUARIO` por tu usuario de GitHub.

## 3. Después de subirlo

GitHub Actions ejecutará automáticamente:

- validación del catálogo;
- compilación debug;
- publicación del APK como artifact.

Ve a **Actions → Android CI → ejecución más reciente → Artifacts → EmisorasRD-debug-apk**.

## 4. No subir secretos

Nunca guardes contraseñas, tokens, keystores privados ni credenciales en el repositorio.
