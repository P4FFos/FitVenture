
apt-get update 
cd ~


# Arduino install for container
apt-get install curl -y
curl  -fsSL https://raw.githubusercontent.com/arduino/arduino-cli/master/install.sh | sh
export PATH=$PATH:/root/bin
arudino-cli -version

# Wio terminal install
printf "board_manager:\n  additional_urls:\n    - https://files.seeedstudio.com/arduino/package_seeeduino_boards_index.json\n" > .arduino-cli.yaml
arduino-cli core update-index --config-file .arduino-cli.yaml
arduino-cli core install Seeeduino:samd --config-file .arduino-cli.yaml

# Necessary libraries for the embedded system
arduino-cli lib install "Seeed Arduino rpcWiFi@1.0.6"
arduino-cli lib install "PubSubClient@2.8"

cd -
