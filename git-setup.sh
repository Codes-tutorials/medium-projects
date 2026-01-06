#!/bin/bash

# Git Setup Script for Spring Boot API Encryption Demo
# This script initializes Git repository and prepares for upload to GitHub

set -e

echo "🔐 Spring Boot API Encryption - Git Setup"
echo "=========================================="

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

# Check if Git is installed
if ! command -v git &> /dev/null; then
    print_error "Git is not installed. Please install Git to continue."
    exit 1
fi

print_success "Git found: $(git --version)"

# Repository configuration
GITHUB_ORG="Codes-tutorials"
REPO_NAME="medium-projects"
PROJECT_NAME="spring-boot-api-encryption"
REMOTE_URL="https://github.com/${GITHUB_ORG}/${REPO_NAME}.git"

print_status "Repository Configuration:"
echo "  Organization: ${GITHUB_ORG}"
echo "  Repository: ${REPO_NAME}"
echo "  Project: ${PROJECT_NAME}"
echo "  Remote URL: ${REMOTE_URL}"

# Initialize Git repository if not already initialized
if [ ! -d ".git" ]; then
    print_status "Initializing Git repository..."
    git init
    print_success "Git repository initialized"
else
    print_warning "Git repository already exists"
fi

# Configure Git user (if not already configured)
if [ -z "$(git config user.name)" ]; then
    print_warning "Git user.name not configured"
    echo "Please enter your Git username:"
    read -r GIT_USERNAME
    git config user.name "$GIT_USERNAME"
    print_success "Git user.name configured: $GIT_USERNAME"
fi

if [ -z "$(git config user.email)" ]; then
    print_warning "Git user.email not configured"
    echo "Please enter your Git email:"
    read -r GIT_EMAIL
    git config user.email "$GIT_EMAIL"
    print_success "Git user.email configured: $GIT_EMAIL"
fi

# Add all files to Git
print_status "Adding files to Git..."
git add .

# Check if there are any changes to commit
if git diff --staged --quiet; then
    print_warning "No changes to commit"
else
    # Create initial commit
    print_status "Creating initial commit..."
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

    print_success "Initial commit created"
fi

# Set up remote repository
print_status "Setting up remote repository..."

# Remove existing remote if it exists
if git remote get-url origin &> /dev/null; then
    print_warning "Remote 'origin' already exists, removing..."
    git remote remove origin
fi

# Add the remote repository
git remote add origin "$REMOTE_URL"
print_success "Remote repository added: $REMOTE_URL"

# Create and switch to main branch
print_status "Setting up main branch..."
git branch -M main

print_success "Git setup completed successfully!"

echo ""
print_status "Next steps:"
echo "1. Make sure you have access to the repository: ${REMOTE_URL}"
echo "2. Run the upload script: ./upload-to-github.sh"
echo "3. Or manually push: git push -u origin main"

echo ""
print_warning "Note: You may need to authenticate with GitHub when pushing."
echo "Consider using:"
echo "  - Personal Access Token (recommended)"
echo "  - SSH key authentication"
echo "  - GitHub CLI (gh auth login)"

echo ""
print_status "Repository structure will be:"
echo "  ${REPO_NAME}/"
echo "  └── ${PROJECT_NAME}/"
echo "      ├── src/"
echo "      ├── README.md"
echo "      ├── pom.xml"
echo "      └── ... (all project files)"