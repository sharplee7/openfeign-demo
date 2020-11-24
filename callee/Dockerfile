FROM java:8
VOLUME /tmp
ADD ./build/libs/callee-1.0-SNAPSHOT.jar app.jar
RUN bash -c 'touch /app.jar'
ENTRYPOINT ["java","-jar","/app.jar"]

# docker run -d -e RUN_MODE=server -e CALLER_SERVICE_URL=euideokcbookpro:8081 -e CALLEE_SERVICE_URL=euideokcbookpro:8082 -p 8082:8082 sharplee7/callee-service:1.0
# *euideokcbookpro는 docker가 운영중인 컴퓨터의 호스트명