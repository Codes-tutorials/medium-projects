#!/bin/bash

# Spring Boot API Encryption Demo - Build Script
# This script builds the project and runs tests

set -e

echo "🔐 Spring Boot API Encryption Demo - Build Script"
echo "================================================="

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

# Check if Maven is installed
if ! command -v mvn &> /dev/null; then
    print_error "Maven is not installed. Please install Maven to continue."
    exit 1
fi

print_success "Maven found: $(mvn --version | head -n 1)"

# Check Java version
if ! command -v java &> /dev/null; then
    print_error "Java is not installed. Please install Java 17 or higher."
    exit 1
fi

JAVA_VERSION=$(java -version 2>&1 | head -n 1 | cut -d'"' -f2 | cut -d'.' -f1)
if [ "$JAVA_VERSION" -lt 17 ]; then
    print_error "Java 17 or higher is required. Current version: $JAVA_VERSION"
    exit 1
fi

print_success "Java version check passed"

# Clean previous builds
print_status "Cleaning previous builds..."
mvn clean

# Compile the project
print_status "Compiling the project..."
mvn compile

# Run tests
print_status "Running tests..."
mvn test

# Package the application
print_status "Packaging the application..."
mvn package -DskipTests

# Check if JAR was created
JAR_FILE=$(find target -name "*.jar" -not -name "*-sources.jar" | head -n 1)
if [ -f "$JAR_FILE" ]; then
    print_success "JAR file created: $JAR_FILE"
    JAR_SIZE=$(du -h "$JAR_FILE" | cut -f1)
    print_status "JAR file size: $JAR_SIZE"
else
    print_error "JAR file not found!"
    exit 1
fi

# Run integration tests (if any)
print_status "Running integration tests..."
mvn verify

# Generate test coverage report
print_status "Generating test coverage report..."
mvn jacoco:report

# Check if coverage report was generated
if [ -f "target/site/jacoco/index.html" ]; then
    print_success "Test coverage report generated: target/site/jacoco/index.html"
else
    print_warning "Test coverage report not generated"
fi

# Build Docker image (optional)
if command -v docker &> /dev/null; then
    print_status "Building Docker image..."
    docker build -t spring-boot-api-encryption:latest .
    print_success "Docker image built successfully"
else
    print_warning "Docker not found, skipping Docker image build"
fi

echo ""
print_success "Build completed successfully!"
echo ""
echo "📁 Generated files:"
echo "   - JAR: $JAR_FILE"
echo "   - Test reports: target/surefire-reports/"
echo "   - Coverage report: target/site/jacoco/index.html"
echo ""
echo "🚀 To run the application:"
echo "   java -jar $JAR_FILE"
echo ""
echo "🐳 To run with Docker:"
echo "   docker run -p 8080:8080 spring-boot-api-encryption:latest"
echo ""
echo "📚 API Documentation will be available at:"
echo "   http://localhost:8080/swagger-ui.html"