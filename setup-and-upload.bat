@echo off
REM Complete Git Setup and Upload Script for Spring Boot API Encryption Demo
REM This script runs both git setup and upload in sequence

echo 🔐 Spring Boot API Encryption - Complete Setup and Upload
echo =========================================================

echo [INFO] Step 1: Setting up Git repository...
call git-setup.bat
if errorlevel 1 (
    echo [ERROR] Git setup failed. Aborting upload.
    pause
    exit /b 1
)

echo.
echo [INFO] Step 2: Uploading to GitHub...
call upload-to-github.bat
if errorlevel 1 (
    echo [ERROR] Upload failed.
    pause
    exit /b 1
)

echo.
echo [SUCCESS] 🎉 Complete setup and upload finished successfully!
echo.
echo Your Spring Boot API Encryption project is now available at:
echo https://github.com/Codes-tutorials/medium-projects
echo.
echo The project will appear as a subdirectory in the medium-projects repository.

pause