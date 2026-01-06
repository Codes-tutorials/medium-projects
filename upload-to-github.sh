#!/bin/bash

# Upload Script for Spring Boot API Encryption Demo
# This script uploads the project to GitHub repository

set -e

echo "🚀 Spring Boot API Encryption - GitHub Upload"
echo "=============================================="

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# Function to print colored output
print_status() {
    echo -e "${BLUE}[INFO]${NC} $1"
}

print_success() {
    echo -e "${GREEN}[SUCCESS]${NC} $1"
}

print_warning() {
    echo -e "${YELLOW}[WARNING]${NC} $1"
}

print_error() {
    echo -e "${RED}[ERROR]${NC} $1"
}

# Repository configuration
GITHUB_ORG="Codes-tutorials"
REPO_NAME="medium-projects"
PROJECT_NAME="spring-boot-api-encryption"
REMOTE_URL="https://github.com/${GITHUB_ORG}/${REPO_NAME}.git"

# Check if Git is initialized
if [ ! -d ".git" ]; then
    print_error "Git repository not initialized. Please run ./git-setup.sh first."
    exit 1
fi

# Check if remote is configured
if ! git remote get-url origin &> /dev/null; then
    print_error "Remote repository not configured. Please run ./git-setup.sh first."
    exit 1
fi

print_status "Preparing to upload to: $REMOTE_URL"

# Check Git status
print_status "Checking Git status..."
if ! git diff --quiet || ! git diff --staged --quiet; then
    print_warning "You have uncommitted changes. Committing them now..."
    
    git add .
    
    echo "Enter commit message (or press Enter for default):"
    read -r COMMIT_MESSAGE
    
    if [ -z "$COMMIT_MESSAGE" ]; then
        COMMIT_MESSAGE="📝 Update: Spring Boot API Encryption Demo - $(date '+%Y-%m-%d %H:%M:%S')"
    fi
    
    git commit -m "$COMMIT_MESSAGE"
    print_success "Changes committed"
fi

# Check if we're on the main branch
CURRENT_BRANCH=$(git branch --show-current)
if [ "$CURRENT_BRANCH" != "main" ]; then
    print_warning "Not on main branch. Switching to main..."
    git checkout -B main
fi

# Pre-upload checks
print_status "Running pre-upload checks..."

# Check if essential files exist
ESSENTIAL_FILES=("README.md" "pom.xml" "src/main/java" ".gitignore")
for file in "${ESSENTIAL_FILES[@]}"; do
    if [ ! -e "$file" ]; then
        print_error "Essential file/directory missing: $file"
        exit 1
    fi
done

print_success "All essential files present"

# Check if build is successful
if [ -f "pom.xml" ]; then
    print_status "Running build test..."
    if command -v mvn &> /dev/null; then
        if mvn clean compile -q; then
            print_success "Build test passed"
        else
            print_warning "Build test failed, but continuing with upload..."
        fi
    else
        print_warning "Maven not found, skipping build test"
    fi
fi

# Show what will be uploaded
print_status "Files to be uploaded:"
git ls-files | head -20
if [ $(git ls-files | wc -l) -gt 20 ]; then
    echo "... and $(( $(git ls-files | wc -l) - 20 )) more files"
fi

echo ""
print_warning "This will upload the project to:"
echo "  Repository: ${REMOTE_URL}"
echo "  Branch: main"
echo "  Project will be in the root of the repository"

echo ""
echo "Do you want to continue? (y/N)"
read -r CONFIRM

if [[ ! "$CONFIRM" =~ ^[Yy]$ ]]; then
    print_status "Upload cancelled by user"
    exit 0
fi

# Attempt to push to GitHub
print_status "Uploading to GitHub..."

# Try to push
if git push -u origin main; then
    print_success "Successfully uploaded to GitHub!"
    
    echo ""
    print_success "🎉 Project uploaded successfully!"
    echo ""
    echo "📍 Repository URL: ${REMOTE_URL}"
    echo "🌐 View online: https://github.com/${GITHUB_ORG}/${REPO_NAME}"
    echo "📚 Documentation: https://github.com/${GITHUB_ORG}/${REPO_NAME}#readme"
    echo ""
    echo "🔗 Direct links:"
    echo "  • Main README: https://github.com/${GITHUB_ORG}/${REPO_NAME}/blob/main/README.md"
    echo "  • Source Code: https://github.com/${GITHUB_ORG}/${REPO_NAME}/tree/main/src"
    echo "  • Documentation: https://github.com/${GITHUB_ORG}/${REPO_NAME}/tree/main"
    
else
    print_error "Failed to upload to GitHub"
    echo ""
    print_status "Possible solutions:"
    echo "1. Check your GitHub authentication:"
    echo "   - Use Personal Access Token: git config --global credential.helper store"
    echo "   - Or use SSH: git remote set-url origin git@github.com:${GITHUB_ORG}/${REPO_NAME}.git"
    echo "   - Or use GitHub CLI: gh auth login"
    echo ""
    echo "2. Check repository permissions:"
    echo "   - Make sure you have write access to ${REMOTE_URL}"
    echo "   - Verify the repository exists and is accessible"
    echo ""
    echo "3. Manual upload steps:"
    echo "   git remote -v  # Verify remote URL"
    echo "   git status     # Check repository status"
    echo "   git push -u origin main --verbose  # Push with verbose output"
    
    exit 1
fi

# Create a summary file
print_status "Creating upload summary..."
cat > UPLOAD-SUCCESS.md << EOF
# 🎉 Upload Successful!

## Repository Information
- **Organization**: ${GITHUB_ORG}
- **Repository**: ${REPO_NAME}
- **Project**: ${PROJECT_NAME}
- **Upload Date**: $(date)
- **Branch**: main

## Links
- **Repository**: ${REMOTE_URL}
- **View Online**: https://github.com/${GITHUB_ORG}/${REPO_NAME}
- **README**: https://github.com/${GITHUB_ORG}/${REPO_NAME}#readme

## Project Structure
\`\`\`
${PROJECT_NAME}/
├── src/main/java/com/example/encryption/
├── src/main/resources/
├── src/test/
├── scripts/
├── README.md
├── pom.xml
├── Dockerfile
├── docker-compose.yml
└── ... (other files)
\`\`\`

## Next Steps
1. Visit the repository online to verify the upload
2. Update repository description and topics on GitHub
3. Consider adding GitHub Actions for CI/CD
4. Share the repository with the community

## Features Uploaded
- ✅ Three encryption approaches (AOP, Filter, MessageConverter)
- ✅ AES and RSA encryption utilities
- ✅ Comprehensive documentation
- ✅ Docker support with monitoring
- ✅ Production-ready configuration
- ✅ Complete test framework setup
- ✅ Build and deployment scripts

Happy coding! 🚀
EOF

print_success "Upload summary created: UPLOAD-SUCCESS.md"