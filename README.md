# TaskFlow — Modern Native Android Productivity App ⚡

[![Android](https://img.shields.io/badge/Android-API%2026%20--%2035-3DDC84?style=for-the-badge&logo=android&logoColor=white)](https://developer.android.com/)
[![Kotlin](https://img.shields.io/badge/Kotlin-2.0-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white)](https://kotlinlang.org/)
[![Jetpack Compose](https://img.shields.io/badge/Jetpack_Compose-Material_3-4285F4?style=for-the-badge&logo=jetpackcompose&logoColor=white)](https://developer.android.com/jetpack/compose)
[![Room Database](https://img.shields.io/badge/Room-SQLite_Persistence-F89820?style=for-the-badge&logo=sqlite&logoColor=white)](https://developer.android.com/training/data-storage/room)
[![Architecture](https://img.shields.io/badge/Architecture-MVVM_%2B_Flow-00C4B4?style=for-the-badge)](https://developer.android.com/topic/architecture)
[![License: MIT](https://img.shields.io/badge/License-MIT-00F0A8?style=for-the-badge&logoColor=black)](LICENSE)

> **Tactile, offline-first personal productivity engine** engineered natively for Android using **Kotlin 2.0**, **Jetpack Compose (Material 3)**, and **Room SQLite Database**. Built for instantaneous responsiveness, zero cloud dependency, and fluid Material You aesthetics by **Muhamad Afif bin Awaludin**.

---

## 🌟 Key Capabilities & Highlights

- ⚡ **Offline-First Persistence**: Powered by SQLite via Android Jetpack Room with asynchronous Kotlin Coroutines `Flow` for real-time reactive UI updates.
- 🎨 **Dynamic Material You Theming**: Full edge-to-edge Material 3 design supporting dynamic color extraction from device wallpaper, fluid light/dark mode transitions, and crisp typographic hierarchy.
- 🎯 **Priority Urgency System**: Categorize tasks across four urgency tiers (`Low`, `Medium`, `High`, `Urgent`) with high-contrast chromatic indicators for rapid visual triaging.
- 🏷️ **Smart Category Filtering**: Tag tasks across domains (`Work`, `Personal`, `Fitness`, `Study`, `Finance`, `Other`) with single-tap horizontal filter chips.
- 🔍 **Real-Time Reactive Search**: Instant search filtering as you type across both task titles and detailed notes.
- 🧹 **Completed Tasks Lifecycle**: Dedicated collapsible completed tasks section with progress metrics and one-tap batch cleanup to keep the workspace clutter-free.
- 🛡️ **Zero Telemetry & 100% Privacy**: No third-party analytics, no account requirements, and no network egress—all user data resides strictly within local device storage.

---

## 🏗️ Architecture & System Design

TaskFlow adheres strictly to official **Android Modern Architecture (MAD)** guidelines, utilizing unidirectional data flow (**UDF**) and the **MVVM** pattern:

```
┌────────────────────────────────────────────────────────┐
│                   UI Layer (Compose)                   │
│   TaskListScreen • TaskCards • FilterChips • Dialogs   │
└───────────────────────────▲────────────────────────────┘
                            │ TaskUiState (StateFlow)
                            │ User Actions (TaskUiEvent)
┌───────────────────────────▼────────────────────────────┐
│              ViewModel Layer (State Holder)            │
│       TaskViewModel (StateFlow & CoroutineScope)       │
└───────────────────────────▲────────────────────────────┘
                            │ Repositories
┌───────────────────────────▼────────────────────────────┐
│                      Data Layer                        │
│   TaskRepository (Interface) ──► TaskRepositoryImpl    │
│                            │                           │
│                      TaskDao (Room)                    │
│                            │                           │
│                   TaskDatabase (SQLite)                │
└────────────────────────────────────────────────────────┘
```

- **UI Layer**: Pure Jetpack Compose declarative UI; decoupled stateless composables driven by immutable `TaskUiState`.
- **State Management**: Model-View-ViewModel (MVVM) leveraging `StateFlow`, reactive `SharingStarted.WhileSubscribed(5000)`, and clean event dispatching (`TaskUiEvent`).
- **Data Layer**:
  - `TaskEntity`: Room-annotated SQLite entity with type converters for priority and category enums.
  - `TaskDao`: Reactive Data Access Object exposing `Flow<List<TaskEntity>>` for continuous stream observation.
  - `TaskRepository`: Abstract repository pattern decoupling SQLite implementation details from business and presentation logic.

---

## 🛠️ Tech Stack & Dependencies

| Layer | Technology | Version | Purpose |
| :--- | :--- | :--- | :--- |
| **Language** | Kotlin | `2.0+` | Modern type-safe programming language with coroutines |
| **UI Framework** | Jetpack Compose | BOM `2024.09+` | Modern declarative UI toolkit for native Android |
| **Design System** | Material 3 (M3) | Compose M3 | Material You dynamic theming, elevation, and components |
| **Local Database** | Android Room | `2.6+` | SQLite abstraction layer with compile-time query verification |
| **Annotation Processing** | KSP | `2.0+` | Fast Kotlin Symbol Processing for Room schema generation |
| **Concurrency** | Kotlin Coroutines & Flow | `1.8+` | Asynchronous programming and reactive data streams |
| **Target Platform** | Android SDK | `minSdk 26` / `targetSdk 35` | Compatibility across Android 8.0 (Oreo) to Android 15 |
| **Build System** | Gradle Kotlin DSL | `8.x` | Type-safe build configurations with Version Catalog (`libs.versions.toml`) |

---

## 📁 Project Directory Structure

```
TaskFlow/
├── app/
│   ├── build.gradle.kts                   # Module build config & dependency catalog
│   ├── proguard-rules.pro                 # Release optimization rules
│   └── src/
│       ├── main/
│       │   ├── AndroidManifest.xml        # Application declarations
│       │   ├── java/com/taskflow/app/
│       │   │   ├── TaskFlowApplication.kt # Application container singleton
│       │   │   ├── MainActivity.kt        # Edge-to-edge Compose host activity
│       │   │   ├── data/
│       │   │   │   ├── model/             # Task entity, Priority, Category enums
│       │   │   │   ├── local/             # TaskDatabase, TaskDao, TypeConverters
│       │   │   │   └── repository/        # TaskRepository interface & implementation
│       │   │   └── ui/
│       │   │       ├── components/        # TaskCard, PriorityBadge, CategoryChip, AddTaskDialog
│       │   │       ├── screens/           # TaskListScreen, TaskViewModel, TaskUiState
│       │   │       └── theme/             # Color.kt, Theme.kt, Type.kt (Material 3)
│       │   └── res/                       # App icons, strings, and XML resources
│       └── test/                          # Unit tests (Coroutines, DAO, ViewModel)
├── gradle/
│   ├── wrapper/                           # Gradle wrapper runtime
│   └── libs.versions.toml                 # Centralized dependencies version catalog
├── build.gradle.kts                       # Root project build configuration
├── settings.gradle.kts                    # Root repository and module settings
└── README.md
```

---

## 🚀 Getting Started & Build Instructions

### Method 1: Android Studio (Recommended)

1. **Clone the repository**:
   ```bash
   git clone https://github.com/afifawald/TaskFlow.git
   ```
2. **Open the project**:
   - Launch **Android Studio (Ladybug / Koala or newer)**.
   - Select **File > Open** and browse to the cloned `TaskFlow` directory.
3. **Sync & Index**:
   - Allow Gradle to sync dependencies and generate KSP sources automatically.
4. **Run the App**:
   - Connect an Android device (USB Debugging enabled) or start an Android Virtual Device (API 26+).
   - Press `Shift + F10` or click the green **Run 'app'** button.

### Method 2: Command Line (Gradle CLI)

1. Ensure **JDK 17** or **JDK 21** is active in your terminal environment.
2. Build the Debug APK:
   ```bash
   # On Windows PowerShell
   .\gradlew.bat assembleDebug

   # On macOS / Linux
   ./gradlew assembleDebug
   ```
3. The generated APK will be available at:
   ```
   app/build/outputs/apk/debug/app-debug.apk
   ```
4. Run automated unit tests:
   ```bash
   .\gradlew.bat test
   ```
5. Install directly to an attached ADB device:
   ```bash
   .\gradlew.bat installDebug
   ```

---

## 📬 Contact & Connect

- 💼 **LinkedIn**: [linkedin.com/in/muhamad-afif-awaludin](https://linkedin.com/in/muhamad-afif-awaludin)
- 🐙 **GitHub**: [github.com/afifawald](https://github.com/afifawald)
- 📧 **Email**: [muhd.afif.awaludin@gmail.com](mailto:muhd.afif.awaludin@gmail.com)
- 🌐 **Portfolio**: [afifawald.github.io/portfolio](https://afifawald.github.io/portfolio/)
- 📍 **Location**: Kuala Lumpur, Malaysia

---

© 2026 **Muhamad Afif bin Awaludin**. Released under the [MIT License](LICENSE).
