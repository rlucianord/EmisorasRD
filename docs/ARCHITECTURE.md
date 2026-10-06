# Arquitectura técnica

## Objetivo

Emisoras RD separa las responsabilidades para permitir que el catálogo, la reproducción y las noticias evolucionen independientemente.

## Capas

### UI

`MainActivity` controla la navegación inicial y conecta los repositorios con la interfaz.

En una versión posterior se recomienda migrar a una arquitectura MVVM completa con `StateFlow` y Jetpack Compose si el producto necesita una interfaz más avanzada.

### Data

`RadioRepository` obtiene emisoras de Radio Browser.

`RssRepository` consume XML RSS público y transforma cada `<item>` en `NewsItem`.

`FavoritesStore` mantiene favoritos localmente usando SharedPreferences. Para una colección grande se recomienda Room.

### Playback

`RadioPlaybackService` utiliza AndroidX Media3. El reproductor es ExoPlayer y se expone mediante MediaSession.

Media3 1.11.1 es la versión estable utilizada en esta versión del proyecto.

## Decisiones

### ¿Por qué Radio Browser?

Porque permite descubrir emisoras dinámicamente en lugar de obligar al desarrollador a publicar una nueva APK cada vez que cambia un stream.

### ¿Por qué Media3?

Media3 es la línea actual de Android para reproducción multimedia y proporciona ExoPlayer, MediaSession y soporte HLS.

### ¿Por qué no una base de datos local de emisoras?

El catálogo de emisoras cambia. La primera versión utiliza una fuente remota y deja el modelo preparado para una base editorial propia.

## Evolución recomendada

```text
v1.0
  API + favoritos + reproductor + RSS

v1.1
  MVVM + caché + provincias + logos

v1.2
  base editorial propia + administración

v2.0
  Android Auto + Chromecast + estadísticas anónimas opcionales
```
