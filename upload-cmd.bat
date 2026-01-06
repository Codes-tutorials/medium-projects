@echo off
echo Setting up Git and uploading to GitHub...

echo Checking Git status...
git status

echo Adding remote repository...
git remote -v

echo Pushing to GitHub...
git push -u origin main

echo Upload complete!
pause