<div  align="center">
  <img width="50%" src="https://github.com/user-attachments/assets/3f6e1382-0e36-4523-b1fd-3111d6f22fb2" alt="LOGO">
</div>
</br>
<h3 align="center">LLM기반 여행 추천 서비스</h3>

`Tripflow` 서비스는 **LLM기반** 여행일정 **사용자 맞춤 추천** & 여행 후기 공유 **SNS서비스**입니다. </br>

---

- **팀 명 :**  따봉
- **프로젝트 명 :** Tripflow
- **프로젝트 기간 :** 2024.06.26 - 2024.08.30
- **팀원 :** 성현규(리더), 김주한, 이지은, 장명근
- **Youtube :** https://youtu.be/RRFOuGhYeX0

<br>

## 🥅 프로젝트 목표
1. **사용자-LLM간 채팅을 통해 사용자를 분석해 맞춤 추천 여행 일정 자동 생성 서비스를 제공합니다.**

2. **사용자는 다녀온 여행 후기를 후기 게시판을 통해 공유할 수 있고, 타 사용자는 해당 게시글을 '좋아요'체크와 댓글을 달 수 있습니다.**

3. **사용자간 상호작용을 통해 나이대별, 동행자에 따른 관광지 협업필터링기 추천 알고리즘을 제공합니다.**

<br>

## 🏢 인프라 아키텍처 & 적용 기술

### 🗺️ 아키텍처 설계도

<img alt="" src="https://github.com/user-attachments/assets/14859c3f-7578-4e87-8ab3-612b3c0a94e5"  width="800">

<br>

### 🥽 적용 기술
![image (5) (1)](https://github.com/user-attachments/assets/4eb4a069-33c0-46d9-8a61-e2674786d3f7)

**🔗Infrastructure**

<img src="https://img.shields.io/badge/Amazon Web Services-232F3E?style=for-the-badge&logo=Amazon Web Services&logoColor=white"><img src="https://img.shields.io/badge/Amazon S3-569A31?style=for-the-badge&logo=Amazon S3&logoColor=white"><img src="https://img.shields.io/badge/Amazon EC2-FF9900?style=for-the-badge&logo=Amazon EC2&logoColor=white">

**💻Frontend**

<img src="https://img.shields.io/badge/Dart-0175C2?style=for-the-badge&logo=Dart&logoColor=white"><img src="https://img.shields.io/badge/Flutter-02569B?style=for-the-badge&logo=Flutter&logoColor=white">

**💻Backend**

<img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=java&logoColor=white"><img src="https://img.shields.io/badge/Spring Boot-6DB33F?style=for-the-badge&logo=Spring Boot&logoColor=white"><img src="https://img.shields.io/badge/Spring Security-6DB33F?style=for-the-badge&logo=Spring Security&logoColor=white"><img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white">

**🛠Tools**

<img src="https://img.shields.io/badge/GitHub-181717?style=for-the-badge&logo=GitHub&logoColor=white"><img src="https://img.shields.io/badge/Slack-4A154B?style=for-the-badge&logo=Slack&logoColor=white">

<br>

## 🔀 Flow Chart

### 여행일정 생성
![image (18)](https://github.com/user-attachments/assets/536801fc-8a3b-4d6d-87e2-9ea8fc916c35)

### 여행일정 수정
![image (19)](https://github.com/user-attachments/assets/587f3860-45f8-4260-99c6-96c3a0b043f3)


## ✨ 주요 기능

### 챗봇 채팅 서비스
- LLM기반 챗봇과 대화를 통해 여행 일정 생성
- **멀티턴** 대화방식을 적용
- 이전 대화 내역을 기억하며 대화

<br>

#### 📌 로직

##### 1. 채팅 시작
<div>
  <img src="https://github.com/user-attachments/assets/c9848eb9-f951-43c4-9368-99ee85a6e471" width=80%/>
</div>
- 신규 채팅방 생성 및 키워드 초기화
  - 키워드 종류 : 총 여행 일수, 교통수단, 동반자 정보, 여행 테마, 좋아하는 음식

<br>

##### 2. 채팅 중
<div>
  <img src="https://github.com/user-attachments/assets/ffae1724-f941-4efe-a6bf-389926eceec9" width=80%/>
</div>
<details>
  <summary>이전 대화 내역에서 키워드 기억을 위해 데이터 파이프라인 구축</summary>
    <img src="https://github.com/user-attachments/assets/1613f51b-1db4-48b1-ae79-6f3ccd811fbe"/>
    <img src="https://github.com/user-attachments/assets/95c7a701-1caf-4055-bb8c-5b916355c3a9"/>
</details>
  - LLM이 여행일정 생성을 위한 키워드가 미입력되었다면
    사용자로부터 해당 키워드 답변 유도를 위해 시나리오 수행
    그럼에도 미입력되었다면 기본값을 반영해 여행일정을 생성
    
<br>

##### 3. 채팅 마무리
<div>
  <img src="https://github.com/user-attachments/assets/33ae210d-88aa-447f-98f7-98737760455a" width=80%/>
</div>
- 필수 키워드를 모두 추출했거나 필수 키워드에 대한 질문을 완료했다면 대화 종료
    
<br>

#### 📌 일정생성 과정 데이터 흐름
<div>
  <img src="https://github.com/user-attachments/assets/5117dedf-5d29-4345-b818-e723a0902d42" width=50%/>
</div>
- 회원-챗봇간 채팅으로 여행일정 생성
- 생성된 여행일정에는 각 여행지별 장소들이 저장
- 채팅로그 맵핑 : 사용자가 마지막으로 입력한 채팅정보를 기억해, 채팅로그를 링크드 리스트 형태로 관리
    
<br>

#### 📌 결과
![image (8) (1)](https://github.com/user-attachments/assets/0b86fb36-a224-4774-8838-556190fd2da5)
![image (6) (1)](https://github.com/user-attachments/assets/9a9b5ba7-4d11-48fc-be11-385948610b0f)

---

### 여행일정 확인 서비스
- LLM기반 챗봇과 대화를 통해 생성된 여행 일정을 확인
- 일정 순서에 맞는 여행 경로 알고리즘 적용

<br>

#### 📌 로직

##### 채팅 마무리 후 여행일정 생성
- 초기 서비스라 사용자 맞춤 조건을 반영할 데이터 가짓수가 적어 비슷한 조건의 사용자에게 같은 결과가 도출될 수 있음.
  - 답변의 다양화를 위해 도출된 키워드기반 여행일정을 10개 생성하고 이 중 랜덤으로 1개를 추천.
    
<br>

#### 📌 결과
![image (9) (1)](https://github.com/user-attachments/assets/ccfe8a9d-fe0f-4741-8c3b-93c340f9474e)

---

### 여행 후기 게시글 조회 및 작성 서비스
- 서비스 내 SNS 게시글 상세 조회 및 작성 기능
    
<br>

#### 📌 여행후기 작성 과정 데이터 흐름
- 생성된 일정을 기반으로 여행일정-여행후기는 1:1관계.
- 게시글 작성시 자유롭게 해시태그를 사용할 수 있도록 구현.
- 사용자는 타인의 여행 후기에 ‘**좋아요**’나 ‘**댓글**’을 남길 수 있다.
<div>
  <img src="https://github.com/user-attachments/assets/67cff4b6-947d-441a-89c8-18e5082acbd2"  width=50%/>
</div>

#### 📌 협업필터링기반 추천 알고리즘
- 좋아요 : 좋아요 테이블
  - 사용자는 여행 후기 게시글에 ‘**좋아요**’를 체크할 수 있다.
  - 사용자의 나이대로 경향성을 파악해, **추천 서비스를 고도화** 하는데 활용
    - 근거 자료 : 부산 관광 산업 동향 분석
<div>
  <img src="https://github.com/user-attachments/assets/006cf390-e5fb-4c0c-a30a-f45f44576d25" />
</div>
    
<br>

#### 📌 결과
![image (10) (1)](https://github.com/user-attachments/assets/4df3f222-b23e-4f1f-b050-c9edfa60b106)

## 🏆 결과 및 성과 
- 6개의 프로젝트 중 **최우수 프로젝트** 선정(2등)
- 기존 SNS서비스 기능 개발에 더해 **AI서비스를 융합**
  - AI서비스를 위한 **AI서버 핸들링**
    - RestTemplate기반 AI서버와 JSON Data를 주고 받는 API 구현 경험
    - Spring Boot - Flask 연동
- **사용자 정보와 관심도를 기반으로 맞춤 추천**을 위한 **데이터 파이프라인** 구축
- 수정 챗봇을 위한 **이전 챗봇과 대화내역 및 Data를 관리**한 **데이터 파이프라인** 구축
- **GPT모델 프롬프트 엔지니어링** 학습 및 적용
- youtube) https://youtu.be/RRFOuGhYeX0
