# 📱 TrackEdbuilddb - Android Frontend

TrackEdbuilddb is a modern Android application built with **Kotlin** and **Jetpack Compose**.
This repository contains the **frontend** of the app — UI components, screens, and design system.

**Latest changes:**


---

## 🚀 Getting Started

---

### 1️⃣ Prerequisites
Before you start, make sure you have the following installed:

- **Android Studio Giraffe / Jellyfish or newer**
- **JDK 17 or higher** (bundled with Android Studio)
- **Gradle** (handled automatically by Android Studio)
- **Git** installed on your system

Optional but recommended:
- **Android Emulator or physical device** running **Android 7.0 (API 24)** or newer

---

### 2️⃣ Clone the Repository

Run this command in your terminal:

```bash
git clone https://github.com/YOUR-USERNAME/TrackEdbuilddb.git
```

---

### 3️⃣ Build & Run (command line)

If you prefer building from PowerShell, a helper script is available at `scripts\build_and_install.ps1`.

From project root (PowerShell):

```powershell
# Use the bundled Android Studio JBR as JAVA_HOME if needed, build, and install to a connected device
.\scripts\build_and_install.ps1 -UseBundledJbr -Install

# Or just build without installing
.\scripts\build_and_install.ps1
```

Notes:

- Ensure Android SDK, Platform Tools (adb), and an emulator or device are available.
- If Gradle reports `JAVA_HOME is not set`, install a JDK and set `JAVA_HOME` to the JDK path.

### 4️⃣ Open in Android Studio (recommended)

1. Open Android Studio.
2. Choose `Open` and select the project folder `TrackEdbuilddb`.
3. Let Gradle sync and then click Run to launch on an emulator or connected device.
