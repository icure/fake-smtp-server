FROM azul/zulu-openjdk-alpine:17.0.4.1-17.36.17
EXPOSE 5025
EXPOSE 5080
ADD ./build/libs/fake-smtp-server-0.1.0-SNAPSHOT.jar app.jar
ADD ./docker/run.sh run.sh
ENTRYPOINT ["sh","run.sh"]
