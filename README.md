##### openfeign-demo 프로젝트에는 3개 버전의 데모가 포함되어 있습니다. git tag를 통해 확인하시기 바랍니다.
###### e.g. openfeign-docker, openfeign-local, openfeign-k8s-ingress
##### 각 버전 간의 이동은 git tag <tag명>으로 이동 가능합니다.
 
## caller와 callee로 구성
#####caller테스트 예)
curl -X GET --header 'Accept: application/json' 'http://locahost:8081/greet/[testmessage]' 
#####callee테스트 예)
curl -X POST --header 'Content-Type: application/json' --header 'Accept: application/json' -d '{ \ 
   "message": "string", \ 
   "name": "string" \ 
 }' 'http://callee.sharplee7.org:80/courtesy'
 #####
 caller가 호출되면 내부적으로 openfeign을 통해 callee를 호출
 
 ./gradlew build 를 통해 컴파일
 
 openfeign-demo/callee/src/main/java/org/example/CalleeApplicationMain.java를 통해 Callee 실행
 
 openfeign-demo/caller/src/main/java/org/example/CallerApplicationMain.java를 통해 Caller 실행
 
 기동 후 http://localhost:8081/swagger-ui.html을 통해 테스트 가능
 
 
