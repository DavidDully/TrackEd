# Release Notes — v0.1.0

Release date: 2025-11-14

## Overview

v0.1.0 introduces a first UX iteration for in-app reminders on the Modules/Subjects screen. This release focuses on UI improvements and local persistence for user reminders.

## Highlights

- New pale-blue background for the Modules/Subjects screen to improve contrast and readability.
- Reminders UI: a fillable text field plus a calendar date picker in a bottom area (~1/3 of the screen).
- Local persistence of reminder text + date using `SharedPreferences`.
- Compose Material DatePicker integrated (dialog presentation).
- Small visual polish: rounded cards, elevated surfaces, snackbar feedback.

## Files of Interest

- `app/src/main/java/com/example/tracked/ModulesScreen.kt`
- `app/src/main/java/com/example/tracked/ui/theme/Color.kt`
- `scripts/build_and_install.ps1`

## Testing / Verification

1. Build and install `app:debug` to a device/emulator.
2. Open Modules/Subjects screen.
3. Type a reminder, pick a date, press Save — the Snackbar confirms. Restart the app to verify persistence.

## Migration / Upgrade Notes

- The app currently uses `SharedPreferences` for persistence. For larger or structured reminder data, migrate to DataStore or Room.

## Known Limitations

- No scheduling/notification implementation is included in this release.
- Screenshots and QA evidence not included in repository; please run locally to verify visuals.
