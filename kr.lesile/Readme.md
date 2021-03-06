
 * 프로세스 구조 
 1. Manager 클래스가 다른 프로세스들의 구동 및 구동 상태 관리
 2. Generator는 거래 로그를 생성하는 가상 고객 Thread
 3. Handler는 각각 성격에 따라 객체화된 거래 로그를 다루는 Thread
 4. SparkJava를 이용해 현재 고객에 대한 정보를 Restful API를 통해 조회 가능
 
 * Clean Code로써 고려한 사항
  
 1. 클래스, 변수와 메소드의 네이밍을 직관적인 예상 가능한 이름으로 명명
    - 클래스, 변수 	: 명사
    - 메소드 		: 동사, 동사 + 목적어
    
 2. 코드의 흐름을 이해 하기 쉽도록 로직을 메소드화해서 동작들로 이해할 수 있게 표현
 
 3. 메소드 하나에서는 하나의 작업만 하도록 설계
 
 4. 작성 형식을 맞춤
  - 클래스 내부의 메소드 간 줄 간격 일정하게 조절
  - 언급된 순서대로 메소드 정의 
 
 5. 불필요한 변수 제거
 
 6. 상세 설명이 필요한 부분에는 주석 추가
 
 7. 공통 코드화를 통해 같은 코드의 중복 제거

 8. null 반환되는 메소드 없음
 
 9. 오류 처리 핸들링
  
 10. 설정값 관리를 용이하게 하기위해서 properties 파일 사용
 
 