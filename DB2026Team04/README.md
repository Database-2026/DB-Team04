# DB-Team04

## 1. 데이터베이스(MySQL) 환경 세팅
본 프로그램은 데이터베이스 이름, 계정명, 비밀번호를 모두 DB2026Team04로 통일하여 설계되었습니다.

MySQL Workbench 등 관리 도구에서 root 계정으로 접속하신 후, 아래 스크립트를 순서대로 실행해 주십시오.

### SQL
```
-- 1. 전용 데이터베이스 생성
CREATE DATABASE IF NOT EXISTS DB2026Team04;

-- 2. 기존 동일 계정 찌꺼기 제거 및 전용 계정 생성 (ID/PW 동일)
DROP USER IF EXISTS 'DB2026Team04'@'localhost';
FLUSH PRIVILEGES;
CREATE USER 'DB2026Team04'@'localhost' IDENTIFIED BY 'DB2026Team04';

-- 3. 데이터베이스 권한 부여 및 적용
GRANT ALL PRIVILEGES ON DB2026Team04.* TO 'DB2026Team04'@'localhost';
FLUSH PRIVILEGES;
```

## 2. SQL 스크립트 실행 순서
생성된 DB2026Team04 스키마 내부에서 동봉된 SQL 파일들을 아래 순서대로 실행하여 테이블 구조와 기본 테스트 데이터를 구축해 주십시오.

MySQL Connection을 새로 설정할 경우, MySQL Connection옆의 +를 눌러
Connection Name : DB2026Team04
Username : DB2026Team04
Default Schema : DB2026Team04
로 설정해주세요.

1. Workbench 열고 접속
2. `File` → `Open SQL Script` → `sql/dropdb.sql` 선택 → ⚡ 실행
3. `File` → `Open SQL Script` → `sql/create.sql` 선택 → ⚡ 실행
4. `File` → `Open SQL Script` → `sql/insert.sql` 선택 → ⚡ 실행

**유의사항: 반드시 drop.sql → create.sql→ insert.sql 순서로 실행**

## 3. 자바(Java) 프로젝트 Import 및 실행 방법

**[이클립스 세팅]**
1. `File` → `New` → `Java Project` → 프로젝트명 입력 → Finish
2. 압축 푼 폴더의 `src/DBTeam04` 폴더를 이클립스 `src` 폴더로 드래그 앤 드롭
3. 프로젝트 우클릭 → `Build Path` → `Add External Archives` → `mysql-connector-j-xxx.jar` 선택
4. `DBTeam04/db/DBConnection.java` 열어서 본인 MySQL 비밀번호로 수정 : `private static final String PASSWORD = "본인 비밀번호";`

프로젝트 빌드 환경에 따라 MySQL 드라이버 라이브러리 경로가 깨져 보일 수 있습니다.
프로젝트 우클릭 ➔ Properties ➔ Java Build Path ➔ Libraries 탭으로 이동합니다.
기존에 누락된(Missing) mysql-connector-j-*.jar 항목이 있다면 제거([Remove])합니다.
[Add External JARs...] 버튼을 눌러 실행자의 로컬 환경의 mysql-connector-j 드라이버 파일(v9.x 권장)을 새로 연결해 주십시오.

### (3) 프로그램 실행

DB2026Team04.Main.java 파일을 선택한 후 Run As ➔ Java Application으로 실행합니다.


**[깃허브에서 다운받기]**

1. https://github.com/Database-2026/DB-Team04 접속
2. 초록색 `Code` 버튼 클릭 → `Download ZIP` 클릭
3. 압축 풀기


**[테스트 계정]**

- 일반 사용자: 이메일 `gunwook@gmail.com` / 비밀번호 `pass123!`
    
    → 본인 아이디로 회원가입 가능
    
- 관리자: 이메일 `ewhakim@daum.net` / 비밀번호 `onion_love`
