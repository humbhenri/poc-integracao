FROM maven
MAINTAINER Humberto Pinheiro <humbhenri@gmail.com>
VOLUME /tmp
ADD . /tmp
WORKDIR /tmp
ADD wait.sh /wait.sh
RUN mvn clean package -DskipTests\
    && chmod +x /wait.sh \
	&& apt update \
	&& apt install netcat -y
CMD /wait.sh && java -Xmx100M -jar -Dspring.profiles.active=docker target/integracao-0.0.1-SNAPSHOT.jar
EXPOSE 8080