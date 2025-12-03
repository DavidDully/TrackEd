<#
PowerShell helper to set JAVA_HOME (optional), build the app, and install it to a connected device/emulator.

Usage (PowerShell):
  .\scripts\build_and_install.ps1 [-UseBundledJbr] [-Install]

Options:
  -UseBundledJbr  : Try to use Android Studio's bundled JBR as JAVA_HOME (common install path).
  -Install        : After building, run `gradlew installDebug` to push the APK to a connected device.

This script performs simple checks and then calls Gradle wrapper in the project root.
#>

param(
    [switch]$UseBundledJbr,
    [switch]$Install
)

Write-Host "Running TrackEd build helper..."

$projectRoot = Split-Path -Parent $MyInvocation.MyCommand.Path
Push-Location $projectRoot

function Ensure-Java {
    if (-not (Get-Command java -ErrorAction SilentlyContinue)) {
        if ($UseBundledJbr) {
            $defaultJbr = 'C:\Program Files\Android\Android Studio\jbr'
            if (Test-Path $defaultJbr) {
                Write-Host "Setting JAVA_HOME to bundled JBR: $defaultJbr"
                [Environment]::SetEnvironmentVariable('JAVA_HOME', $defaultJbr, 'User')
                $env:JAVA_HOME = $defaultJbr
                $env:Path = "$defaultJbr\bin;" + $env:Path
            } else {
                Write-Warning "Bundled JBR not found at $defaultJbr. Please install Java or point JAVA_HOME to a JDK."
            }
        } else {
            Write-Warning "No 'java' found in PATH. Please install a JDK and set JAVA_HOME."
        }
    } else {
        Write-Host "Java found: $(java -version 2>&1 | Select-String '"' -Quiet)"
    }
}

Ensure-Java

Write-Host "Starting Gradle build (assembleDebug)..."
try {
    & .\gradlew assembleDebug -x lint
} catch {
    Write-Error "Gradle build failed. Fix environment / build errors and try again."
    Pop-Location
    exit 1
}

if ($Install) {
    Write-Host "Installing debug APK to connected device/emulator..."
    try {
        & .\gradlew installDebug
    } catch {
        Write-Error "Install failed. Ensure an emulator/device is connected and adb is available."
        Pop-Location
        exit 1
    }
}

Write-Host "Build (and optional install) finished."
Pop-Location
