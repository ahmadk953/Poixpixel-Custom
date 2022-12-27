sudo apt install openjdk-17-jre-headless -y
sudo apt install openjdk-17-jre-headless -y
wget https://mirrors.estointernet.in/apache/maven/maven-3/3.6.3/binaries/apache-maven-3.6.3-bin.tar.gz
tar -xvf apache-maven-3.6.3-bin.tar.gz
mv apache-maven-3.6.3 /opt/
mvn clean package
