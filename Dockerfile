FROM picoded/ubuntu-openjdk-8-jdk
MAINTAINER Humberto Pinheiro <humbhenri@gmail.com>
VOLUME /tmp
ADD target/integracao-0.0.1-SNAPSHOT.jar /app.jar
RUN sh -c 'touch /app.jar'
ADD wait.sh /wait.sh
RUN chmod +x /wait.sh
RUN apt update && apt install netcat -y
CMD /wait.sh && java -Xmx100M -jar -Dspring.profiles.active=docker /app.jar
EXPOSE 8080