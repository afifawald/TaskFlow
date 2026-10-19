# TaskFlow - Modern Android Productivity App

A clean, modern, and offline-first task management application built natively for Android using **Kotlin**, **Jetpack Compose (Material 3)**, and **Room Database**.

---

## 🌟 Key Features

- **Intuitive Task Management**: Create, view, complete, edit, and delete tasks.
- **Priority Categorization**: Categorize tasks by urgency (`Low`, `Medium`, `High`, `Urgent`) with dynamic color indicators.
- **Category Tags**: Tag tasks (`Work`, `Personal`, `Fitness`, `Study`, `Finance`, `Other`) with one-tap filtering chips.
- **Live Search & Filter**: Real-time reactive search filtering by task title or description.
- **Offline-First Persistence**: Powered by SQLite via Android Jetpack Room with Kotlin Coroutines `Flow`.
- **Material 3 Design**: Supports dynamic theming (Material You), smooth animations, and edge-to-edge support.
- **Completed Tasks Management**: Separate collapsible pending and completed task lists with one-tap batch cleanup.

---

## 🏗️ Architecture

TaskFlow is architected according to modern Android development best practices:

- **UI Layer**: Jetpack Compose declarative UI with Material 3 components.
- **State Management**: Model-View-ViewModel (MVVM) with reactive `StateFlow` and unidirectional data flow (`TaskUiEvent` -> `TaskViewModel` -> `TaskUiState`).
- **Data Layer**:
  - `TaskDatabase`: Room SQLite database singleton.
  - `TaskDao`: Reactive data access object with Coroutine Flows.
  - `TaskRepository`: Abstract repository interface and implementation decoupling business logic from persistence.

---

## 🚀 Getting Started

### 1. Recommended: Open in Android Studio
1. Download and install [Android Studio](https://developer.android.com/studio).
2. Launch Android Studio and choose **Open**.
3. Select this folder: `c:\Users\aerin\Documents\Apps\TaskFlow`.
4. Android Studio will automatically download the Gradle wrapper, sync dependencies, and index the project.
5. Click **Run 'app'** (`Shift + F10`) to launch on an Android Virtual Device (AVD) or physical device connected via USB.

### 2. Command Line Build (Optional)
If you prefer building from the terminal:

1. Ensure **JDK 17 or 21** is installed:
   ```powershell
   winget install Microsoft.OpenJDK.17
   ```
2. Run the Gradle build:
   ```cmd
   gradlew.bat assembleDebug
   ```
3. Run the unit tests:
   ```cmd
   gradlew.bat test
   ```
4. Install to an attached emulator or device:
   ```cmd
   gradlew.bat installDebug
   ```

---

## 📁 Project Structure

```
TaskFlow/
├── app/
│   ├── build.gradle.kts           # Module dependencies (Compose, Room, Coroutines)
│   └── src/
│       ├── main/
│       │   ├── AndroidManifest.xml # Application configuration
│       │   ├── java/com/taskflow/app/
│       │   │   ├── TaskFlowApplication.kt # App container
│       │   │   ├── MainActivity.kt        # Compose host activity
│       │   │   ├── data/
│       │   │   │   ├── model/             # Entities & Enums
│       │   │   │   ├── local/             # Room DB, Dao, Converters
│       │   │   │   └── repository/        # Repository pattern
│       │   │   └── ui/
│       │   │       ├── components/        # Cards, Chips, Badges, Dialogs
│       │   │       ├── screens/           # TaskListScreen & TaskViewModel
│       │   │       └── theme/             # Material 3 Theme & Colors
│       │   └── res/                       # XML resources & backup configs
│       └── test/                          # Unit tests
├── gradle/
│   └── libs.versions.toml                 # Version catalog
├── build.gradle.kts                       # Root build script
├── settings.gradle.kts                    # Root settings
└── README.md
```
