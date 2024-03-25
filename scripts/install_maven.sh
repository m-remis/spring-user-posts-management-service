#!/bin/bash

# Check if Maven is already installed
if command -v mvn &>/dev/null; then
    echo "Maven is already installed."
    exit 0
fi

# Define Maven version
MAVEN_VERSION="3.9.6"

# Define installation directory
INSTALL_DIR="/opt"

# Download Maven
echo "Downloading Maven..."
wget -q "https://apache.osuosl.org/maven/maven-3/${MAVEN_VERSION}/binaries/apache-maven-${MAVEN_VERSION}-bin.tar.gz" -P /tmp

# Verify download
if [ ! -f "/tmp/apache-maven-${MAVEN_VERSION}-bin.tar.gz" ]; then
    echo "Failed to download Maven."
    exit 1
fi

# Extract Maven with correct permissions
echo "Extracting Maven..."
sudo tar xf /tmp/apache-maven-${MAVEN_VERSION}-bin.tar.gz -C ${INSTALL_DIR}

# Create a symbolic link if not exists
if [ ! -e ${INSTALL_DIR}/apache-maven ]; then
    sudo ln -s ${INSTALL_DIR}/apache-maven-${MAVEN_VERSION} ${INSTALL_DIR}/apache-maven
fi

# Add Maven to PATH
echo "export PATH=\$PATH:${INSTALL_DIR}/apache-maven/bin" | sudo tee -a /etc/profile.d/maven.sh

# Reload profile
source /etc/profile.d/maven.sh

# Clean up
rm /tmp/apache-maven-${MAVEN_VERSION}-bin.tar.gz

echo "Maven ${MAVEN_VERSION} has been installed."
