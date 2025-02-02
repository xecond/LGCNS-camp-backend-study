## 프로젝트 소개
- 요약: 게임 아이템 정보 조회 사이트 (간단한 CRUD + 필터링)
- 목적: LG CNS AM camp 1기 백엔드 과정 Springboot 미니 프로젝트
- 기간: 2025.02.02 ~ 2025.02.02

<br>

## 개발 환경
- Spring Boot
- JDK 21
- MySQL
- HikariCP
- Thymeleaf

<br>

## 데이터베이스
### 데이터베이스 구성
- MySQL에서 `springbootdb` 데이터베이스 생성
- username과 password를 설정하고 `src/main/resources/application.properties`에 알맞게 작성


### 테이블 구성
**Items**: 아이템 정보
> 등급: '일반', '희귀', '전설', '신화' 중 택1

> 종류: '무기', '방어구', '식품' 중 택1
```sql
create table Items (
    item_id 	        int(11) 	not null auto_increment 	comment "아이템 번호", 
    item_name 		varchar(300)    not null 			comment "이름", 
    rarity		varchar(300)	not null			comment "등급",
    item_type           varchar(300)	not null 			comment "종류", 
    ability_value	smallint(10)	not null default "0" 		comment "능력치", 
    contents		text		not null			comment "내용", 
    created_dt		datetime	not null 			comment "작성일시", 
    updated_dt		datetime	null 				comment "수정일시", 
    primary key (item_id)
);
```
**ItemFiles**: 아이템에 등록된 파일
```sql
create table ItemFiles (
    file_id                int(10) unsigned   not null auto_increment   comment '파일 번호', 
    item_id                int(10) unsigned   not null                  comment '아이템 번호', 
    original_file_name     varchar(255)       not null                  comment '원본 파일 이름', 
    stored_file_path       varchar(500)       not null                  comment '파일 저장 경로',
    file_size              int(15) unsigned   not null                  comment '파일 크기', 

    created_dt             datetime           not null                  comment '작성 일시', 
    updated_dt             datetime           null                      comment '수정 시간', 
    primary key (file_id)
);
```

### 예시 데이터 삽입
```sql
INSERT INTO Items (item_name, rarity, item_type, ability_value, contents, created_dt)
VALUES 
('붉은 기사의 대검', '전설', '무기', 400, '근거리 대미지+2, 근거리 명중+2, CON+1', now());
INSERT INTO Items (item_name, rarity, item_type, ability_value, contents, created_dt)
VALUES 
('판금 갑옷', '희귀', '방어구', 150, '대미지 감소+1, MR+3, HP 회복+1', now());
INSERT INTO Items (item_name, rarity, item_type, ability_value, contents, created_dt)
VALUES 
('닭고기 스프', '일반', '식품', 50, '저레벨 지역에서만 사용 가능. 89레벨까지 사용 가능.', now());
```

<br>

## 결과 화면
http://localhost:8080/item/openItemList.do 로 접속하여 확인

### 아이템 목록 (등급과 종류에 따른 필터링 가능)
  ![image](https://github.com/user-attachments/assets/47690dda-1992-4cad-87a8-8de81f045af7)

### 새 아이템 등록
  ![image](https://github.com/user-attachments/assets/42109c4f-17d8-4033-b48e-db6a49b2a5cf)

### 아이템 세부 정보 (수정, 삭제, 첨부파일 다운로드)
  ![image](https://github.com/user-attachments/assets/3d237f45-3108-4bdb-bdcc-f501540b2288)

