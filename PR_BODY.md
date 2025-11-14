# PR: feature/ui-reminders

## Summary

This PR implements the UI and persistence for reminders on the Modules/Subjects screen. It includes the following user-facing changes:

- Very light pale-blue app background for the Modules screen.
- Bottom reminder area occupying ~1/3 of the Modules screen with:
  - Fillable reminder `OutlinedTextField`.
  - Date picker for selecting a reminder date (Compose Material DatePicker inside a dialog).
  - Save and Clear actions with Snackbars for feedback.
- Local persistence of reminder text and date via `SharedPreferences`.
- Visual polish: rounded corners, subtle elevation, accent color tokens.

## Files changed

- `app/src/main/java/com/example/tracked/ModulesScreen.kt` — UI, date picker, persistence, snackbars.
- `app/src/main/java/com/example/tracked/ui/theme/Color.kt` — added `PaleBlue`, `AccentBlue`, and `SoftSurface` color tokens.
- `scripts/build_and_install.ps1` — helper to set `JAVA_HOME` in-session and build/install debug APK.
- `README.md` — updated with build/run instructions and screenshots placeholders.

## How to test

1. From your development machine (PowerShell), build and install the debug APK (example using helper script):

```powershell
scripts\build_and_install.ps1 -Install -UseBundledJbr
```

Or use Gradle directly after ensuring `JAVA_HOME` and `platform-tools` are set in your session:

```powershell
$env:JAVA_HOME = 'C:\\Program Files\\Android\\Android Studio\\jbr'
$env:Path = $env:JAVA_HOME + '\\bin;' + $env:Path
.\gradlew.bat assembleDebug
.\gradlew.bat installDebug
```

2. Launch the app on an emulator/device and open the Modules (Subjects) screen.
3. In the bottom reminder area:
   - Enter reminder text and tap Save — snackbar should confirm save.
   - Open the calendar field, choose a date, and verify it saves and shows in the field.
   - Restart the app — the saved reminder text and date should persist.

## Checklist

- [x] UI implemented with Compose Material3
- [x] Date picker implemented (Compose Material DatePicker dialog)
- [x] Reminders persisted to `SharedPreferences`
- [x] Build & install helper script added

## Notes for reviewer

- I used `SharedPreferences` for simple persistence; consider migrating to DataStore for more robust storage in a follow-up.
- No notifications/scheduling of reminders were implemented (out of scope).

If you want, I can open the PR using the `gh` CLI or paste a one-click web-URL to create it in the browser.
