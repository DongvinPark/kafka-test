# Aphche Kafka Simple Setting

<br><br/>
- 1 대의 머신으로만 구성된 카프카 클러스터를 셋팅하고 스프링부트를 이용해서 카프카 클러스터에 pub/sub하는 방법을 설명한다.
  <br>build.gradle 내의 스프링 버전 & 스프링 카프카 라이브러 버전 간의 호환성 관련 주석을 반드시 확인한 후 시작할 것을 권장한다.

<br><br/>
- 두 대의 물리 컴퓨터를 준비하고, 둘 다 동일한 wifi 라우터에 접속 시킨다.
  <br>두 대의 물리 컴퓨터가 없다면, EC2를 만들고 거기에서 카프카를 실행하면 된다.
  <br>한 대는 ubuntu 리눅스로 준비하여 이곳에서 카프카를 실행시킬 예정이고,
  <br>나머지 한 대는 본 리포지토리의 스프링부트를 실행하여 프로듀서 및 컨슈머의 역할을 맡는다.
  <br>카프카가 실행되는 우분투 내부에서 ifconfig 명령어를 쳐서 해당 우분투의 public IP를 기억해 놓는다.
  <br>이 IP 주소는 나중에 카프카 컨슈머를 만들면서 bootstrap server들을 설정할 때 IP-Addr:9092 이렇게 입력하게 된다.

<br><br/>
- 우분투의 home/{your-linux-user-name} 디렉토리 내부에 kafka 라는 디렉토리를 만들고,
  <br>이 안에 아파치 카프카 바이너리 파일을 다운 받아서 옮겨 놓는다.
  <br>source code 파일이 아니라, 반드시 binary 파일로 다운 받아야 별도의 빌드 없이
  br>실행용 셸스크립트 파일을 ./...sh 라는 명령어로 실행하는 것만으로 간단하게 카프카 서버를 구동시킬 수 있다!

<br><br/>
- 다운로드를 완료 했다면,
  <br>tar -xzf {아까 다운로드한 파이너리 파일 tgz 파일명}
  <br>이라는 명령어를 실행해서 압축을 해제한다. 그 후 새롭게 생긴 디렉토리에 들어간다.

<br><br/>
- 본격적으로 카프카 관련 파일들이 들어 있는데, 이중 bin 디렉토리에는 카프카 실행을 위한 셸스크립트 파일들이 들어있고,
  <br>config 디렉토리에는 설정을 위한 파일들이 들어 있다.
  <br>config 디렉토리에 들어가서 server.properties 파일을 열고,
  <br>advertized.listeners=PLAINTEXT://your.host.name:9092 부분을
  <br>advertized.listeners=PLAINTEXT://{아까기록한 우분투 머신의 퍼블릭 IP 주소}:9092
  <br>로 바꿔 준다. 주석은 당연히 해제해야 한다.

<br><br/>
- 그후, bin 디렉토리에 들어가고, 터미널 창을 하나 더 열어서 kafka 바이너리를 압축해제 해서
  새로 생긴 디렉토리 내의 config 디렉토리에 들어간다. 두 개의 명령어를 각각 실행할 것이다.
  <br>첫 번째 터미널 창에서는
  <br>./zookeeper-server-start.sh /home/{your-linux-user-name}/kafka/{압축해제후생긴디렉토리이름}/config/zookeeper.properties
  <br>를 실행하여 카프카 코디테이션 시스템인 주키퍼를 먼저 실행시킨다.
  <br>두 번째 터미널 창에서는
  <br>./kafka-server-start.sh /home/{your-linux-user-name}/kafka/{압축해제후생긴디렉토리이름}/config/server.properties
  <br>를 실행하여 실제로 카프카를 시작시킨다.

<br><br/>
- 토픽을 만들어 준다. 카프카 관련 셸스크립트 파일들이 있는 bin 디렉토리에 들어가서
  <br>./kafka-topics.sh --create --topic test-topic --bootstrap-server localhost:9092
  <br>를 실행한다.

<br><br/>
- 이제 현재 깃허브 리포지토리에 있는 스프링부트 프로젝트를 두 번째 물리 컴퓨터에 클론한 후,
  <br>카프카 컨슈머의 bootstrap server 설정 부분의 IP 주소를 kafka가 돌아가고 있는 머신의 퍼블릭 IP로 입력한 후
  <br>스프링 부트 프로젝트를 실행한다.
  <br>그 후, postman api tester 등의 api tester로 컨트롤러의 메서드들을 호출해보면 카프카 서버로 pub하고, 카프카 서버로부터 sub하는 메시지들을 볼 수 있다. 