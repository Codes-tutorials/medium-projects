#!/bin/bash

# Spring Boot API Encryption Demo - Run Script
# This script runs the application with different profiles

set -e

echo "🔐 Spring Boot API Encryption Demo - Run Script"
echo "==============================================="

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

# Function to show usage
show_usage() {
    echo "Usage: $0 [PROFILE] [OPTIONS]"
    echo ""
    echo "Profiles:"
    echo "  dev      - Development profile (default)"
    echo "  prod     - Production profile"
    echo "  test     - Test profile"
    echo "  docker   - Docker profile"
    echo ""
    echo "Options:"
    echo "  --port PORT     - Override server port (default: 8080)"
    echo "  --debug         - Enable debug mode"
    echo "  --help          - Show this help message"
    echo ""
    echo "Examples:"
    echo "  $0                    # Run with dev profile"
    echo "  $0 prod               # Run with production profile"
    echo "  $0 dev --port 9090    # Run with dev profile on port 9090"
    echo "  $0 --debug            # Run with debug enabled"
}

# Default values
PROFILE="dev"
PORT="8080"
DEBUG_MODE=""
JAVA_OPTS=""

# Parse command line arguments
while [[ $# -gt 0 ]]; do
    case $1 in
        dev|prod|test|docker)
            PROFILE="$1"
            shift
            ;;
        --port)
            PORT="$2"
            shift 2
            ;;
        --debug)
            DEBUG_MODE="--debug"
            JAVA_OPTS="$JAVA_OPTS -Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=5005"
            shift
            ;;
        --help)
            show_usage
            exit 0
            ;;
        *)
            print_error "Unknown option: $1"
            show_usage
            exit 1
            ;;
    esac
done

# Check if JAR file exists
JAR_FILE=$(find target -name "*.jar" -not -name "*-sources.jar" 2>/dev/null | head -n 1)
if [ ! -f "$JAR_FILE" ]; then
    print_warning "JAR file not found. Building the project..."
    ./scripts/build.sh
    JAR_FILE=$(find target -name "*.jar" -not -name "*-sources.jar" | head -n 1)
fi

if [ ! -f "$JAR_FILE" ]; then
    print_error "Failed to find or build JAR file!"
    exit 1
fi

print_success "Found JAR file: $JAR_FILE"

# Set environment variables based on profile
case $PROFILE in
    prod)
        export SPRING_PROFILES_ACTIVE="prod"
        export ENCRYPTION_AES_KEY="${ENCRYPTION_AES_KEY:-ProductionKey123}"
        JAVA_OPTS="$JAVA_OPTS -Xmx1g -Xms512m"
        ;;
    test)
        export SPRING_PROFILES_ACTIVE="test"
        export ENCRYPTION_AES_KEY="TestSecretKey123"
        ;;
    docker)
        export SPRING_PROFILES_ACTIVE="docker"
        export ENCRYPTION_AES_KEY="DockerSecretKey123"
        ;;
    *)
        export SPRING_PROFILES_ACTIVE="dev"
        export ENCRYPTION_AES_KEY="DevSecretKey123"
        ;;
esac

# Set server port
export SERVER_PORT="$PORT"

print_status "Starting application with profile: $PROFILE"
print_status "Server port: $PORT"
print_status "Java options: $JAVA_OPTS"

if [ -n "$DEBUG_MODE" ]; then
    print_warning "Debug mode enabled - Remote debugging available on port 5005"
fi

echo ""
print_status "Application will be available at:"
echo "   🌐 Main Application: http://localhost:$PORT"
echo "   📚 Swagger UI: http://localhost:$PORT/swagger-ui.html"
echo "   📖 API Docs: http://localhost:$PORT/v3/api-docs"
echo "   🏥 Health Check: http://localhost:$PORT/actuator/health"
echo ""

# Create logs directory if it doesn't exist
mkdir -p logs

# Run the application
print_status "Starting Spring Boot API Encryption Demo..."
echo ""

java $JAVA_OPTS \
    -Dspring.profiles.active="$SPRING_PROFILES_ACTIVE" \
    -Dserver.port="$SERVER_PORT" \
    -jar "$JAR_FILE" \
    $DEBUG_MODE