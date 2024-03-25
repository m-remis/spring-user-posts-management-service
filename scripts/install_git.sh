#!/bin/bash

# Check if Git is already installed
if command -v git &>/dev/null; then
    echo "Git is already installed."
    exit 0
fi

# Update package lists
sudo apt update

# Install Git
sudo apt install -y git

# Verify installation
if ! command -v git &>/dev/null; then
    echo "Failed to install Git."
    exit 1
fi

echo "Git has been installed successfully."

git --version
