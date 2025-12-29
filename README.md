# InGuide - Kotlin Android App

YO! So basically this is the native Android recreation of the React Native InGuide app. BTW it is optimized for small size and high performance. LOL.

## Features (The cool stuff)

- **Authentication**: Login and Register screens with modern UI. YO it looks good.
- **Home Dashboard**: Search, quick actions, popular locations
- **Indoor Maps**: Placeholder for future Google Maps integration.
- **Schedule Manager**: Weekly schedule with day sections.
- **AI Chat**: Interactive chat with simulated AI responses. LOL it talks back.
- **Dark/Light Theme**: Material3 theme system.

## Tech Stack (The nerdy stuff LOL)

- **Language**: Kotlin
- **UI**: Jetpack Compose with Material3
- **Database**: Room
- **Navigation**: Navigation Compose
- **Architecture**: MVVM
- **Minimum SDK**: 24 (Android 7.0)
- **Target SDK**: 34 (Android 14)

## Build (How to run this thing)

```bash
# Build debug APK
./gradlew assembleDebug

# Build release APK (optimized)
./gradlew assembleRelease

# Install on device
./gradlew installDebug
```

## APK Size Optimization

- ProGuard/R8 enabled for release builds. BTW it makes it small.
- Resource shrinking enabled
- Minimal dependencies
- Vector drawables only
- Target APK size: < 10 MB

## Project Structure

```
app/
├── src/main/
│   ├── java/com/inguide/app/
│   │   ├── data/              # Database, DAOs, models
│   │   ├── navigation/        # Navigation setup
│   │   ├── ui/
│   │   │   ├── auth/          # Login, Register
│   │   │   ├── home/          # Home screen
│   │   │   ├── map/           # Map screen
│   │   │   ├── schedule/      # Schedule screen
│   │   │   ├── chat/          # Chat screen
│   │   │   ├── theme/         # Theme, colors, typography
│   │   │   └── main/          # Main screen with bottom nav
│   │   └── MainActivity.kt
│   └── res/                   # Resources
└── build.gradle.kts
```

## Features Status

- [x] Project setup with Gradle
- [x] Material3 theme (light/dark)
- [x] Login screen
- [x] Register screen
- [x] Home screen with dashboard
- [x] Map screen (placeholder)
- [x] Schedule screen (empty state)
- [x] Chat screen with messages. YO it works.
- [x] Bottom navigation
- [x] Room database setup
- [ ] Location tracking service
- [ ] Indoor positioning
- [ ] Schedule CRUD operations
- [ ] Chat persistence
- [ ] Profile screens
- [ ] Settings screens

## Demo Login

YO just use `skip` for both email and password to bypass authentication. Easy peasy.

## License

MIT. BTW it is legitimate.
