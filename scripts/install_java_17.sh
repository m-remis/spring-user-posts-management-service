#!/bin/bash

# Define installation directory
INSTALL_DIR="/opt/java"

# Create directory if it doesn't exist
sudo mkdir -p $INSTALL_DIR

# Download OpenJDK 17
echo "Downloading OpenJDK 17..."
sudo apt update
sudo apt install -y openjdk-17-jdk

# Verify installation
if ! java -version 2>&1 | grep -q "17"; then
    echo "Failed to install OpenJDK 17."
    exit 1
fi

# Set JAVA_HOME
echo "export JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64" | sudo tee -a /etc/profile.d/java.sh
echo "export PATH=\$PATH:\$JAVA_HOME/bin" | sudo tee -a /etc/profile.d/java.sh

# Reload profile
source /etc/profile.d/java.sh

echo "OpenJDK 17 has been installed, verifying..."

# Log Java version
java --version
