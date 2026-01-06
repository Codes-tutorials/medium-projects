@echo off
REM Git Setup Script for Spring Boot API Encryption Demo
REM This script initializes Git repository and prepares for upload to GitHub

echo 🔐 Spring Boot API Encryption - Git Setup
echo ==========================================

REM Repository configuration
set GITHUB_ORG=Codes-tutorials
set REPO_NAME=medium-projects
set PROJECT_NAME=spring-boot-api-encryption
set REMOTE_URL=https://github.com/%GITHUB_ORG%/%REPO_NAME%.git

echo Repository Configuration:
echo   Organization: %GITHUB_ORG%
echo   Repository: %REPO_NAME%
echo   Project: %PROJECT_NAME%
echo   Remote URL: %REMOTE_URL%

REM Check if Git is installed
git --version >nul 2>&1
if errorlevel 1 (
    echo [ERROR] Git is not installed. Please install Git to continue.
    pause
    exit /b 1
)

echo [SUCCESS] Git found
git --version

REM Initialize Git repository if not already initialized
if not exist ".git" (
    echo [INFO] Initializing Git repository...
    git init
    echo [SUCCESS] Git repository initialized
) else (
    echo [WARNING] Git repository already exists
)

REM Configure Git user if not already configured
for /f "tokens=*" %%i in ('git config user.name 2^>nul') do set GIT_USERNAME=%%i
if "%GIT_USERNAME%"=="" (
    echo [WARNING] Git user.name not configured
    set /p GIT_USERNAME="Please enter your Git username: "
    git config user.name "%GIT_USERNAME%"
    echo [SUCCESS] Git user.name configured: %GIT_USERNAME%
)

for /f "tokens=*" %%i in ('git config user.email 2^>nul') do set GIT_EMAIL=%%i
if "%GIT_EMAIL%"=="" (
    echo [WARNING] Git user.email not configured
    set /p GIT_EMAIL="Please enter your Git email: "
    git config user.email "%GIT_EMAIL%"
    echo [SUCCESS] Git user.email configured: %GIT_EMAIL%
)

REM Add all files to Git
echo [INFO] Adding files to Git...
git add .

REM Check if there are any changes to commit
git diff --staged --quiet
if errorlevel 1 (
    REM Create initial commit
    echo [INFO] Creating initial commit...
    git commit -m "🔐 Initial commit: Spring Boot API Encryption Demo

Features:
- Three encryption approaches (AOP, Filter, MessageConverter)
- AES and RSA encryption utilities
- Comprehensive documentation and examples
- Docker support with monitoring
- Production-ready configuration
- Complete test framework setup

This project demonstrates professional Spring Boot API data encryption/decryption
techniques with real-world applicable code and best practices."

    echo [SUCCESS] Initial commit created
) else (
    echo [WARNING] No changes to commit
)

REM Set up remote repository
echo [INFO] Setting up remote repository...

REM Remove existing remote if it exists
git remote get-url origin >nul 2>&1
if not errorlevel 1 (
    echo [WARNING] Remote 'origin' already exists, removing...
    git remote remove origin
)

REM Add the remote repository
git remote add origin %REMOTE_URL%
echo [SUCCESS] Remote repository added: %REMOTE_URL%

REM Create and switch to main branch
echo [INFO] Setting up main branch...
git branch -M main

echo [SUCCESS] Git setup completed successfully!
echo.
echo [INFO] Next steps:
echo 1. Make sure you have access to the repository: %REMOTE_URL%
echo 2. Run the upload script: upload-to-github.bat
echo 3. Or manually push: git push -u origin main
echo.
echo [WARNING] Note: You may need to authenticate with GitHub when pushing.
echo Consider using:
echo   - Personal Access Token (recommended)
echo   - SSH key authentication
echo   - GitHub CLI (gh auth login)
echo.
echo [INFO] Repository structure will be:
echo   %REPO_NAME%/
echo   └── %PROJECT_NAME%/
echo       ├── src/
echo       ├── README.md
echo       ├── pom.xml
echo       └── ... (all project files)

pause