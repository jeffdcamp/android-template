# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

Android template app demonstrating modern Android development with Kotlin Multiplatform architecture. The app is split into two modules:
- `app`: Android-specific UI and presentation layer (Compose)
- `shared`: Kotlin Multiplatform module containing domain logic, data layer, and business logic (supports Android, JVM, iOS)

## Build Commands

### Building
```bash
# Clean build
./gradlew clean

# Build debug
./gradlew assembleDebug

# Build all variants
./gradlew assembleAlpha bundleAlpha
./gradlew assembleBeta bundleBeta
./gradlew assembleRelease bundleRelease
```

### Testing
```bash
# Run all unit tests (app + shared)
./gradlew test jvmTest

# Run app module unit tests only
./gradlew test

# Run shared module JVM tests only
./gradlew jvmTest

# Run single test class (must use specific test task, not aggregate 'test')
./gradlew testDebugUnitTest --tests "org.jdc.template.ux.directory.DirectoryViewModelTest"

# Run Android instrumentation tests
./gradlew connectedAndroidTest
```

### Code Quality
```bash
# Run Detekt (static analysis)
./gradlew detekt
./gradlew detektDebug  # with type checking

# Run lint
./gradlew lint
./gradlew lintDebug
./gradlew lintAlpha

# Generate test coverage reports (Kover)
./gradlew koverHtmlReportDebug   # HTML report
./gradlew koverXmlReportDebug    # XML report
./gradlew koverVerifyDebug       # Verify coverage threshold (25%)

# Analyze bundle/APK size (Ruler)
./gradlew analyzeDebugBundle
```

### Dependency Management
```bash
# Check for dependency updates
./gradlew dependencyUpdates -Drevision=release

# Analyze dependency usage
./gradlew app:projectHealth
```

### License Management
```bash
# Generate license reports
./gradlew createLicenseReport
./gradlew --stacktrace -i createLicenseReport
```

## Architecture

### Module Structure

**app module** - Android-specific code:
- `di/`: Koin dependency injection modules (ViewModels, Workers, Analytics)
- `ux/`: UI screens organized by feature (main, directory, individual, about, settings, chat)
- `ui/`: Reusable UI components (compose utilities, theme, navigation)
- `work/`: WorkManager workers
- `startup/`: App initializers
- `analytics/`: Firebase Analytics wrapper

**shared module** - Kotlin Multiplatform code:
- `model/domain/`: Domain models with value classes (IndividualId, FirstName, etc.)
- `model/db/main/`: Room database (entities, DAOs, migrations, converters)
- `model/datastore/`: DataStore preferences (User + Device)
- `model/repository/`: Data repositories (single source of truth)
- `model/webservice/`: Ktor HTTP client and API services
- `domain/usecase/`: Business logic use cases
- `di/`: Shared Koin modules
- `util/`: Utilities (file, network, serialization, logging)

### Dependency Injection (Koin 4.x)

All DI is constructor-based using Koin. Modules are split by responsibility:

**App modules** (`app/src/main/kotlin/org/jdc/template/di/AppKoinModules.kt`):
- `appModule`: Analytics, RemoteConfig, WorkScheduler
- `viewModelModule`: All ViewModels registered with `viewModelOf()`
- `workersModule`: WorkManager workers with `workerOf()`

**Shared modules** (`shared/src/commonMain/kotlin/org/jdc/template/shared/di/SharedKoinModules.kt`):
- `coroutineModule`: CoroutineScope and CoroutineDispatchers
- `databaseModule`: Room database and platform-specific builders (expect/actual)
- `datastoreModule`: DataStore setup (expect/actual)
- `repositoryModule`: All repositories
- `serviceModule`: Ktor HttpClient and API services
- `useCaseModule`: Domain use cases

When adding a new ViewModel, register it in `viewModelModule` using `viewModelOf()`. The Konsist architecture tests will fail if ViewModels are not properly registered.

### Database (Room - Kotlin Multiplatform)

Room database is in the shared module and supports Android, JVM, and iOS targets:

**Entities:**
- `IndividualEntity` - person records
- `HouseholdEntity` - household groupings
- `ChatThreadEntity` - chat conversations
- `ChatMessageEntity` - individual messages

**Database Views:**
- `DirectoryItemEntityView` - optimized view for directory listing

All DAOs expose Flow-based reactive queries. Database uses:
- Type converters for Kotlin DateTime and value classes
- Auto-migrations with specs (e.g., `MainAutoMigrationSpec3`)
- Manual migrations for complex schema changes
- Bundled SQLite driver
- Platform-specific builders using expect/actual pattern

Database location is managed by `AppFileSystem` utility.

### ViewModel & UI State Pattern

ViewModels follow a consistent sealed interface pattern for UI state:

```kotlin
sealed interface XxxUiState {
    data object Loading : XxxUiState
    data class Ready(...) : XxxUiState
    data object Empty : XxxUiState
}
```

ViewModels:
- Extend `androidx.lifecycle.ViewModel`
- Use constructor injection via Koin
- Expose `StateFlow<UiState>` for UI observation
- Combine multiple flows using `combine()` and `stateInDefault()`
- Separate concerns: `viewModelScope` for UI coroutines, `applicationScope` for long-running operations
- Implement `ViewModelNavigation3` interface for type-safe navigation

UI screens collect state with `collectAsStateWithLifecycle()`.

### Navigation (Compose Navigation3)

Uses experimental Jetpack Compose Navigation3 with type-safe routing:

**Routes** are serializable objects:
```kotlin
@Serializable
object DirectoryRoute : NavKey

@Serializable
data class IndividualRoute(val individualId: IndividualId) : NavKey
```

**Navigation setup** in `MainScreen.kt`:
- `rememberNavigationState()` with start route and top-level routes
- `TopLevelBackStackNavigator` for multi-backstack support
- Entry provider maps routes to screens
- ViewModels scoped to navigation entries
- State restoration with decorators

**ViewModelNavigation3 interface**:
- ViewModels delegate navigation via `ViewModelNavigation3Impl`
- Expose `navigationActionFlow: StateFlow<Navigation3Action?>`
- Methods: `navigate()`, `popBackStack()`, `navigateWithBackStack()`
- UI observes flow and consumes navigation actions

When adding new screens:
1. Create a `@Serializable` route object implementing `NavKey`
2. Add entry to `entryProvider` in `MainScreen.kt`
3. Pass `Navigator` and ViewModel to screen
4. If ViewModel needs navigation, implement `ViewModelNavigation3`

### Repository Pattern

Repositories are the single source of truth for data:
- Combine multiple data sources (database, network, preferences)
- Expose Flow-based reactive streams
- Handle Entity ↔ Domain model transformations
- All repositories are singletons in Koin

Example: `IndividualRepository` mediates between Room DAOs and domain layer.

### Value Classes (Type Safety)

Domain IDs and primitives use inline value classes:
- `IndividualId`, `HouseholdId`, `ChatThreadId`, `ChatMessageId`
- `FirstName`, `LastName`, `Phone`, `Email`
- Custom serializers for each type
- Validation in constructors

When adding new value classes, create custom serializers and register in `InlineSerializersModule`.

### Testing Strategy

**Konsist architecture tests** verify:
- All ViewModels registered in Koin
- All Workers registered in Koin
- No duplicate DI definitions
- Abstract classes excluded from module checks

Run with: `./gradlew test --tests "*KoinModuleTest*"`

**Room migration tests** verify database migrations don't break.

**Repository tests** use MockK for dependencies.

### WorkManager

Workers are registered in Koin with `workerOf()`:
- `SyncWorker` - periodic data sync
- `RemoteConfigSyncWorker` - Firebase Remote Config sync

Versioned work scheduling cancels all work on version changes. Work is constrained to network availability.

## Development Notes

### Build Configuration

- **Min SDK**: 23 (Android 6.0)
- **Target/Compile SDK**: 36 (Android 15)
- **Java Version**: 17
- **Kotlin Version**: Defined in `libs.versions.toml`
- **Application ID**: `org.jdc.template` (dev: `.dev`, alpha: `.alpha`)

### Build Variants

- **debug**: Development build with LeakCanary, no signing by default
- **release**: Signed with upload keystore, R8 enabled, Firebase App Distribution
- **alpha**: Based on release with `.alpha` suffix
- **beta**: Based on release with `.beta` suffix

### Signing Configuration

Signing keys come from:
1. Gradle properties (`~/.gradle/gradle.properties`): `appUploadKeystore`, etc.
2. Environment variables: `SIGNING_KEYSTORE`, `SIGNING_STORE_PASSWORD`, etc.

### Detekt Configuration

Detekt downloads config from external URL on first run:
- Config cached in `build/config/detektConfig.yml`
- Runs on all Kotlin source sets (common, android, jvm, desktop)
- Excludes ImageVector files (`**/ui/compose/icons/**`)
- `allRules = true` - strict mode

### Dependency Analysis

Uses Gradle Dependency Analysis plugin:
- Fails on unused dependencies (except LeakCanary and specific test libs)
- Ignores transitive dependencies, incorrect configuration, compile-only, runtime-only
- Run: `./gradlew app:projectHealth`

### Kotlin Compiler Flags

Enabled opt-ins:
- `kotlin.time.ExperimentalTime`
- `kotlin.uuid.ExperimentalUuidApi`
- `androidx.compose.material3.ExperimentalMaterial3Api`
- `androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi`

Features:
- `-Xexplicit-backing-fields` (Kotlin 2.3+) - used in ViewModels for StateFlow backing

## File Paths Reference

**Key files:**
- App DI: `app/src/main/kotlin/org/jdc/template/di/AppKoinModules.kt`
- Shared DI: `shared/src/commonMain/kotlin/org/jdc/template/shared/di/SharedKoinModules.kt`
- Database: `shared/src/commonMain/kotlin/org/jdc/template/shared/model/db/main/MainDatabase.kt`
- Navigation: `app/src/main/kotlin/org/jdc/template/ux/main/MainScreen.kt`
- Navigation Interface: `app/src/main/kotlin/org/jdc/template/ui/navigation3/ViewModelNavigation3.kt`
- App Info: `buildSrc/src/main/kotlin/AppInfo.kt`
- Value Classes: `shared/src/commonMain/kotlin/org/jdc/template/shared/model/domain/inline/`

## Stack Summary

- Jetpack Compose with Material3
- Compose Navigation3 (type-safe routing)
- Kotlin Coroutines & Flow
- Kotlin Serialization
- Room (Kotlin Multiplatform)
- Koin 4.x (dependency injection)
- Ktor Client (networking)
- DataStore (preferences)
- WorkManager (background tasks)
- Firebase (Analytics, Crashlytics, Remote Config, App Distribution)
- Kermit (logging)
- kotlinx-datetime
- Detekt (linting)
- Kover (test coverage)
- Konsist (architecture testing)
- Gradle Play Publisher (Triple-T)
- Ruler (APK analysis)
