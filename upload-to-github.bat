@echo off
REM Upload Script for Spring Boot API Encryption Demo
REM This script uploads the project to GitHub repository as a subdirectory

echo 🚀 Spring Boot API Encryption - GitHub Upload
echo ==============================================

REM Repository configuration
set GITHUB_ORG=Codes-tutorials
set REPO_NAME=medium-projects
set PROJECT_NAME=spring-boot-api-encryption
set REMOTE_URL=https://github.com/%GITHUB_ORG%/%REPO_NAME%.git

REM Check if Git is initialized
if not exist ".git" (
    echo [ERROR] Git repository not initialized. Please run git-setup.bat first.
    pause
    exit /b 1
)

REM Check if remote is configured
git remote get-url origin >nul 2>&1
if errorlevel 1 (
    echo [ERROR] Remote repository not configured. Please run git-setup.bat first.
    pause
    exit /b 1
)

echo [INFO] Preparing to upload to: %REMOTE_URL%

REM Check Git status
echo [INFO] Checking Git status...
git diff --quiet
set UNSTAGED=%errorlevel%
git diff --staged --quiet
set STAGED=%errorlevel%

if %UNSTAGED% neq 0 (
    echo [WARNING] You have unstaged changes. Adding them now...
    git add .
)

if %STAGED% neq 0 (
    echo [WARNING] You have uncommitted changes. Committing them now...
    
    set /p COMMIT_MESSAGE="Enter commit message (or press Enter for default): "
    if "%COMMIT_MESSAGE%"=="" (
        for /f "tokens=2 delims= " %%i in ('date /t') do set CURRENT_DATE=%%i
        for /f "tokens=1 delims= " %%i in ('time /t') do set CURRENT_TIME=%%i
        set COMMIT_MESSAGE=📝 Update: Spring Boot API Encryption Demo - %CURRENT_DATE% %CURRENT_TIME%
    )
    
    git commit -m "%COMMIT_MESSAGE%"
    echo [SUCCESS] Changes committed
)

REM Check if we're on the main branch
for /f "tokens=*" %%i in ('git branch --show-current') do set CURRENT_BRANCH=%%i
if not "%CURRENT_BRANCH%"=="main" (
    echo [WARNING] Not on main branch. Switching to main...
    git checkout -B main
)

REM Pre-upload checks
echo [INFO] Running pre-upload checks...

REM Check if essential files exist
set ESSENTIAL_FILES=README.md pom.xml src\main\java .gitignore
for %%f in (%ESSENTIAL_FILES%) do (
    if not exist "%%f" (
        echo [ERROR] Essential file/directory missing: %%f
        pause
        exit /b 1
    )
)

echo [SUCCESS] All essential files present

REM Check if build is successful (if Maven is available)
where mvn >nul 2>&1
if not errorlevel 1 (
    echo [INFO] Running build test...
    mvn clean compile -q >nul 2>&1
    if not errorlevel 1 (
        echo [SUCCESS] Build test passed
    ) else (
        echo [WARNING] Build test failed, but continuing with upload...
    )
) else (
    echo [WARNING] Maven not found, skipping build test
)

REM Show what will be uploaded
echo [INFO] Files to be uploaded:
git ls-files | findstr /n "^" | head -20
echo ... and more files

echo.
echo [WARNING] This will upload the project to:
echo   Repository: %REMOTE_URL%
echo   Branch: main
echo   Project will be in the root of the repository
echo.
set /p CONFIRM="Do you want to continue? (y/N): "

if /i not "%CONFIRM%"=="y" (
    echo [INFO] Upload cancelled by user
    pause
    exit /b 0
)

REM Attempt to push to GitHub
echo [INFO] Uploading to GitHub...

REM Try to push
git push -u origin main
if not errorlevel 1 (
    echo [SUCCESS] Successfully uploaded to GitHub!
    echo.
    echo [SUCCESS] 🎉 Project uploaded successfully!
    echo.
    echo 📍 Repository URL: %REMOTE_URL%
    echo 🌐 View online: https://github.com/%GITHUB_ORG%/%REPO_NAME%
    echo 📚 Documentation: https://github.com/%GITHUB_ORG%/%REPO_NAME%#readme
    echo.
    echo 🔗 Direct links:
    echo   • Main README: https://github.com/%GITHUB_ORG%/%REPO_NAME%/blob/main/README.md
    echo   • Source Code: https://github.com/%GITHUB_ORG%/%REPO_NAME%/tree/main/src
    echo   • Documentation: https://github.com/%GITHUB_ORG%/%REPO_NAME%/tree/main
    
    REM Create a summary file
    echo [INFO] Creating upload summary...
    (
        echo # 🎉 Upload Successful!
        echo.
        echo ## Repository Information
        echo - **Organization**: %GITHUB_ORG%
        echo - **Repository**: %REPO_NAME%
        echo - **Project**: %PROJECT_NAME%
        echo - **Upload Date**: %date% %time%
        echo - **Branch**: main
        echo.
        echo ## Links
        echo - **Repository**: %REMOTE_URL%
        echo - **View Online**: https://github.com/%GITHUB_ORG%/%REPO_NAME%
        echo - **README**: https://github.com/%GITHUB_ORG%/%REPO_NAME%#readme
        echo.
        echo ## Project Structure
        echo ```
        echo %PROJECT_NAME%/
        echo ├── src/main/java/com/example/encryption/
        echo ├── src/main/resources/
        echo ├── src/test/
        echo ├── scripts/
        echo ├── README.md
        echo ├── pom.xml
        echo ├── Dockerfile
        echo ├── docker-compose.yml
        echo └── ... (other files^)
        echo ```
        echo.
        echo ## Next Steps
        echo 1. Visit the repository online to verify the upload
        echo 2. Update repository description and topics on GitHub
        echo 3. Consider adding GitHub Actions for CI/CD
        echo 4. Share the repository with the community
        echo.
        echo ## Features Uploaded
        echo - ✅ Three encryption approaches (AOP, Filter, MessageConverter^)
        echo - ✅ AES and RSA encryption utilities
        echo - ✅ Comprehensive documentation
        echo - ✅ Docker support with monitoring
        echo - ✅ Production-ready configuration
        echo - ✅ Complete test framework setup
        echo - ✅ Build and deployment scripts
        echo.
        echo Happy coding! 🚀
    ) > UPLOAD-SUCCESS.md
    
    echo [SUCCESS] Upload summary created: UPLOAD-SUCCESS.md
    
) else (
    echo [ERROR] Failed to upload to GitHub
    echo.
    echo [INFO] Possible solutions:
    echo 1. Check your GitHub authentication:
    echo    - Use Personal Access Token: git config --global credential.helper store
    echo    - Or use SSH: git remote set-url origin git@github.com:%GITHUB_ORG%/%REPO_NAME%.git
    echo    - Or use GitHub CLI: gh auth login
    echo.
    echo 2. Check repository permissions:
    echo    - Make sure you have write access to %REMOTE_URL%
    echo    - Verify the repository exists and is accessible
    echo.
    echo 3. Manual upload steps:
    echo    git remote -v  # Verify remote URL
    echo    git status     # Check repository status
    echo    git push -u origin main --verbose  # Push with verbose output
    
    pause
    exit /b 1
)

pause