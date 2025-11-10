# User-App

This project is a sample Android application that demonstrates a modern, modular, and clean architecture approach to Android development.

## Architecture

The application follows the principles of **Clean Architecture**, which separates concerns into distinct layers, making the codebase easier to manage, test, and scale. The architecture is composed of three main layers:

- **Presentation Layer (`:features`)**: This layer is responsible for the UI and user interactions. It is built using Jetpack Compose and follows an MVI (Model-View-Intent) or MVVM pattern. It observes state changes from the domain layer and presents them to the user.

- **Domain Layer (`:domain`)**: This is the core of the application. It contains the business logic, use cases, and repository interfaces. This layer is a pure Kotlin module with no dependencies on the Android framework, making it highly reusable and testable.

- **Data Layer (`:data`)**: This layer implements the repository interfaces defined in the domain layer. It is responsible for fetching data from various sources (like a remote API or a local database) and exposing it to the domain layer.

Dependency injection is managed using **Hilt** to provide dependencies throughout the app.


The project is divided into several modules, each with a specific responsibility:

- **`:app`**: The main application module that integrates all other modules to build the final APK. It handles top-level navigation and application-wide setup.

- **`:domain`**: The business logic layer. It defines the core rules and use cases of the application (e.g., `GetUsersUseCase`).

- **`:data`**: The data management layer. It provides concrete implementations for the repositories defined in the `:domain` layer, handling data from remote and/or local data sources.

- **`:features`**: These are self-contained feature modules that represent a specific functionality of the app.
  - **`:features:user`**: Contains all the UI and logic related to displaying the list of users.
  - **`:features:common-ui`**: A module with shared Jetpack Compose components, themes, and resources that are used across multiple feature modules to ensure a consistent UI.

## Modules[Screen_recording_20251110_211001.mp4](../../../Screen_recording_20251110_211001.mp4)

### Dependency Graph

```
:app -> :features:user
:features:user -> :domain, :data, :features:common-ui
:data -> :domain
:domain -> (none)
:features:common-ui -> (none)
```
