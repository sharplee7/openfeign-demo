#이 버전(openfeign-k8s-ingress)의 소스는 minikube에 deployment하는 과정을 포함하고 있습니다.
1. Caller와 Callee의 jar 파일 생성
   ./gradlew build
2. Caller 모듈에서 Dockerfile을 이용한 caller-service 도커 이미지 생성
   docker build --tag sharplee7/caller-service:1.0 .
3. Callee 모듈에서 Dockerfile을 이용한 callee-service 도커 이미지 생성
   docker build --tag sharplee7/callee-service:1.0 .
   3-1. 만약 로컬에서 caller-service 도커 이미지를 테스트 해볼려면 caller-service 도커 컨테이너 생성 및 실행, 단 k8s 실행시 불필요
     docker run -d -e RUN_MODE=server -e CALLER_SERVICE_URL=euideokcbookpro:8081 -e CALLEE_SERVICE_URL=euideokcbookpro:8082 -p 8081:8081 sharplee7/caller-service:1.0
     *euideokcbookpro는 docker가 운영중인 컴퓨터의 호스트명
   3-2. 만약 로컬에서 callee-service 도커 이미지를 테스트 해볼려면 callee-service 도커 컨테이너 생성 및 실행 필요, 단 k8s 실행시 불필요
     docker run -d -e RUN_MODE=server -e CALLER_SERVICE_URL=euideokcbookpro:8081 -e CALLEE_SERVICE_URL=euideokcbookpro:8082 -p 8082:8082 sharplee7/callee-service:1.0
     *euideokcbookpro는 docker가 운영중인 컴퓨터의 호스트명
4. http://hub.docker.com에 로그인, 코맨드 명령어로 실행
   docker login -u sharplee7 -p
5. 생성된 caller-service 도커 이미지 push
   docker push sharplee7/caller-service:1.0
6. 생성된 callee-service 도커 이미지 push
   docker push sharplee7/callee-service:1.0
7. caller-service k8s에 deploy (deployment와 service)
   kubectl apply -f caller.yml
8. 웹브라우저를 통해 접속(yml에서 지정한 서비스 명)
   minikube service caller-service
9. callee-service k8s에 deploy (deployment와 service)
   kubectl apply -f callee.yml
10.웹브라우저를 통해 접속(yml에서 지정한 서비스 명)
   minikube service callee-service
11.minikube에 ingress enable(minikube addons list 명령어를 통해 minikube가 확성화 되어있는지 체크하고 않되어 있다면 실행)
   minikube addons enable ingress
12.ingress 배포
   minikube apply -f ingress.yml
13.ingress ip확인
   kubectl get ingress

   e.g.
   EuiDeokcBookPro:k8s euideoklee$ kubectl get ingress
   NAME                    CLASS    HOSTS                                       ADDRESS          PORTS   AGE
   caller-callee-ingress   <none>   callee.sharplee7.org,caller.sharplee7.org   192.168.99.100   80      23s

14.ingress ip를 통해 확인된 ip와 hosts를 /etc/hosts에 등록(sudo vi /etc/hosts)
   e.g.
   127.0.0.1       localhost
   255.255.255.255 broadcasthost
   ::1             localhost
   192.168.99.100 callee.sharplee7.org,caller.sharplee7.org

# ingress 참조
# https://kubernetes.io/docs/tasks/access-application-cluster/ingress-minikube/
#
# minikube vm-driver 변경
# $>minikube start — vm-driver=virtualbox
# $>minikube config set vm-driver virtualbox
# $>kubectl config use-context minikube
