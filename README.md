# TrackEd - Study Habit Tracker

TrackEd is a modern Android application designed to help students track and improve their study habits. Built with Jetpack Compose and powered by a Supabase backend, this app provides a clean, intuitive interface for managing tasks, study sessions, and academic progress.

## ✨ Features

- **Dashboard:** A home screen with a "Bento Box" layout showing key stats at a glance.
- **User Authentication:** Secure sign-up, login, and session management using Supabase GoTrue.
- **Study Timer:** A built-in focus timer to manage study sessions effectively.
- **Task & Session Management:** Create and track tasks, assignments, and study sessions.
- **Modules:** A dedicated section for course materials with links to external resources like Google Drive.
- **Modern UI:** A clean, responsive, and modern user interface built entirely with Jetpack Compose.

## 🛠️ Tech Stack

- **Frontend:** Android Native (Kotlin & Jetpack Compose)
- **Backend:** Supabase (PostgreSQL Database, Authentication)
- **IDE:** Android Studio
- **Navigation:** Jetpack Navigation for Compose

## 🚀 Getting Started

To clone and run this project locally, follow these steps:

1.  **Clone the repository:**
    ```sh
    git clone https://github.com/marie2255667/TrackEd.git
    ```

2.  **Open in Android Studio:**
    -   Open Android Studio.
    -   Select `File > Open` and navigate to the cloned project directory.

3.  **Set up Supabase:**
    -   The project is already configured to connect to a Supabase backend.
    -   To use your own backend, navigate to `app/src/main/java/com/example/tracked/SupabaseClient.kt` and replace the placeholder `supabaseUrl` and `supabaseKey` with your own Supabase project credentials.

4.  **Run the App:**
    -   Let Android Studio sync the Gradle files.
    -   Click the **Run (▶️)** button to build and launch the app on an emulator or a physical device.
