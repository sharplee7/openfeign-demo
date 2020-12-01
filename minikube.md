minikube version v1.15.1
kubectl version client/server v1.19
* istio와 관련된 설치는 https://istio.io/latest/docs/setup/getting-started/ 를 참

1. brew cask install virtualbox
2. brew install kubectl
   (OS 별 설치는 https://kubernetes.io/docs/tasks/tools/install-kubectl)
3. brew cask install minikube
4. minikube start --vm-driver=virtualbox
5. isto 다운로드 
   https://github.com/istio/istio/releases 에서 사용하는 OS에 최신 버전 다운
   * 여기서는 2020.11.20 최신의 istio인 1.8.0 사용
   * https://github.com/istio/istio/releases/download/1.8.0/istioctl-1.8.0-osx.tar.gz
6. istio 압축풀기
   $HOME/istio-1.8.0 등의 이름으로 압축을 풀도록 한다.
7. istoctl을 패스에 넣기
   e.g. isto가 설치된 디렉토리의 bin에 있는 istioctl 파일을 /usr/local/bin 에 복사해 둔다.
   * cp istioctl /usr/local/bin
8. demo프로파일을 이용해 istio 설치
   istioctl install --set profile=demo -y 
   * demo프로파일은 테스트를위한 일반적 기본값 세트를 갖도록 선택되었지만 프로덕션 또는 성능 테스트를위한 다른 프로필이 있습니다.인
9. envoy sidecar 자동 삽입 명령어 추가 
   kubectl label namespace default istio-injection=enabled
   
   
* 샘플 앱 배포 및 테스   
1. 샘플 어플리케이션 배포(istio-1.8.0 홈디렉토리에서 실행)
   kubectl apply -f samples/bookinfo/platform/kube/bookinfo.yaml
2. 샘플 어플리케이션 배포 확인
   kubectl get services 및 kubectl get pods
   * 각 포드가 준비되면 Istio 사이드카가 함께 배포됩니다.
3. 샘플 어플리케이션 실행
   kubectl exec "$(kubectl get pod -l app=ratings -o jsonpath='{.items[0].metadata.name}')" -c ratings -- curl -s productpage:9080/productpage | grep -o "<title>.*</title>"
   * 결과로 <title>Simple Bookstore App</title> 이 나오면 Ok
4. istio-gateway 연결을 통해 샘플 어플리케이션 외부에서 접속
   kubectl apply -f samples/bookinfo/networking/bookinfo-gateway.yaml
   * Bookinfo 응용 프로그램이 배포되었지만 외부에서 액세스 할 수 없습니다. 
   * 액세스 할 수 있도록하려면 메시 가장자리의 경로에 경로를 매핑 하는 Istio Ingress Gateway 를 만들어야 합니다.
5. istioctl analyze 를 통해 이상 유무 체크
6. 수신 포트 설정
   export INGRESS_PORT=$(kubectl -n istio-system get service istio-ingressgateway -o jsonpath='{.spec.ports[?(@.name=="http2")].nodePort}')
   export SECURE_INGRESS_PORT=$(kubectl -n istio-system get service istio-ingressgateway -o jsonpath='{.spec.ports[?(@.name=="https")].nodePort}')
7. 수신 포트 할당 확인
   echo "$INGRESS_PORT"
   echo "$SECURE_INGRESS_PORT"
8. 수신 ip 할당 
   export INGRESS_HOST=$(minikube ip)
9. ip 할당 확인
   echo "$INGRESS_HOST"
10.##새 터널 창을 열고 istiogateway와 통신을 위한 터널 생성
   minikube tunnel
11.GATEWAY_URL 환경 변수 설정 
   export GATEWAY_URL=$INGRESS_HOST:$INGRESS_PORT
12.환경변수 할당 확인
   echo "$GATEWAY_URL"
13.외부접속포인트 확인
   echo "http://$GATEWAY_URL/productpage"
14.외부접속포이트에서 나온 출력 결과를 복사해 웹브라우저에서 호출 확인

* 대시보드 보기
Prometheus, Grafana 및 Jaeger 와 함께 Kiali 대시 보드를 배포
1. Prometheus, Grafana, Jaeger 및 Kiali 설치
   kubectl apply -f samples/addons 
   * istio-1-8.0의 samples/addons 디렉토리 아래에 Prometheus, Grafana, Jaeger 및 Kiali 설치용 yaml 파일들 존재
   * 애드온을 설치하는 중에 오류가 발생하면 명령을 다시 실행하십시오. 명령이 다시 실행될 때 해결 될 몇 가지 타이밍 문제가있을 수 있습니다.
2. kiali 접속
  istioctl dashboard kiali
  왼쪽 탐색 메뉴에서 Graph를 선택 하고 Namespace 드롭 다운에서 default를 선택 합니다.
  Kiali 대시 보드는 Bookinfo샘플 애플리케이션 의 서비스 간 관계와 함께 메시의 개요를 보여줍니다 . 또한 트래픽 흐름을 시각화하는 필터를 제공합니다.