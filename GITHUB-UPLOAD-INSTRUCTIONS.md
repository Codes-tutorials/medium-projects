# 🚀 GitHub Upload Instructions

## Quick Start (Windows CMD)

### Option 1: Complete Setup and Upload (Recommended)
```cmd
setup-and-upload.bat
```

### Option 2: Step by Step
```cmd
# Step 1: Setup Git repository
git-setup.bat

# Step 2: Upload to GitHub
upload-to-github.bat
```

### Option 3: Manual Commands
```cmd
# Initialize Git
git init
git add .
git commit -m "Initial commit: Spring Boot API Encryption Demo"

# Add remote repository
git remote add origin https://github.com/Codes-tutorials/medium-projects.git
git branch -M main

# Push to GitHub
git push -u origin main
```

## Repository Information

- **Organization**: Codes-tutorials
- **Repository**: medium-projects
- **Project Name**: spring-boot-api-encryption
- **Final URL**: https://github.com/Codes-tutorials/medium-projects

## Prerequisites

1. **Git installed** - Download from https://git-scm.com/
2. **GitHub access** - Make sure you have write access to the repository
3. **Authentication** - Set up one of the following:
   - Personal Access Token (recommended)
   - SSH key authentication
   - GitHub CLI (`gh auth login`)

## Authentication Setup

### Personal Access Token (Recommended)
1. Go to GitHub → Settings → Developer settings → Personal access tokens
2. Generate new token with `repo` permissions
3. When prompted for password during push, use the token instead

### SSH Key Authentication
```cmd
# Generate SSH key
ssh-keygen -t rsa -b 4096 -C "your_email@example.com"

# Add to GitHub account
# Copy the public key and add it to GitHub → Settings → SSH keys

# Change remote URL to SSH
git remote set-url origin git@github.com:Codes-tutorials/medium-projects.git
```

### GitHub CLI
```cmd
# Install GitHub CLI from https://cli.github.com/
gh auth login
```

## Project Structure After Upload

```
medium-projects/
├── README.md (repository main README)
├── spring-boot-api-encryption/
│   ├── src/
│   │   ├── main/java/com/example/encryption/
│   │   ├── main/resources/
│   │   └── test/
│   ├── scripts/
│   ├── README.md
│   ├── pom.xml
│   ├── Dockerfile
│   ├── docker-compose.yml
│   └── ... (other project files)
└── ... (other projects in the repository)
```

## Troubleshooting

### Common Issues

1. **Authentication Failed**
   - Use Personal Access Token instead of password
   - Check if you have write access to the repository

2. **Repository Not Found**
   - Verify the repository exists: https://github.com/Codes-tutorials/medium-projects
   - Check if you have access to the organization

3. **Git Not Found**
   - Install Git from https://git-scm.com/
   - Restart command prompt after installation

4. **Permission Denied**
   - Check your GitHub permissions
   - Try using SSH authentication instead of HTTPS

### Manual Verification

After upload, verify the project at:
- Repository: https://github.com/Codes-tutorials/medium-projects
- Project folder: https://github.com/Codes-tutorials/medium-projects/tree/main/spring-boot-api-encryption
- README: https://github.com/Codes-tutorials/medium-projects/blob/main/spring-boot-api-encryption/README.md

## Support

If you encounter issues:
1. Check the error messages in the command prompt
2. Verify your GitHub authentication
3. Ensure you have write access to the repository
4. Try the manual commands if the batch files fail

## Next Steps After Upload

1. Visit the repository online to verify the upload
2. Update repository description and topics on GitHub
3. Consider adding GitHub Actions for CI/CD
4. Share the repository with the community
5. Add the project to your portfolio or documentation

---

Happy coding! 🚀