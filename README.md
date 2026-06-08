# DB-Team04
**OTT 플랫폼 통합 관리 시스템** | 이화여자대학교 데이터베이스 2026 3분반 Team04

---

## 1. 압축 해제

**방법 A — 사이버캠퍼스에서 받은 경우**

다운로드한 ZIP 파일을 압축 해제합니다.

**방법 B — 깃허브에서 받는 경우**

1. https://github.com/Database-2026/DB-Team04 접속
2. 초록색 `Code` 버튼 → `Download ZIP` 클릭
3. 압축 해제


## 2. 데이터베이스(MySQL) 환경 세팅

본 프로그램은 데이터베이스 이름, 계정명, 비밀번호를 모두 `DB2026Team04`로 통일하여 설계되었습니다.

MySQL Workbench에서 **root 계정**으로 접속한 후 아래 SQL 스크립트를 순서대로 실행해 주세요.

> ⚠️ **반드시 아래 순서대로 실행해야 합니다.**

| 순서 | 파일 | 설명 |
|------|------|------|
| 1 | `sql/dropdb.sql` | 기존 DB 및 계정 삭제 (초기화) |
| 2 | `sql/create.sql` | 테이블, 뷰, 인덱스 생성 + 초기 데이터 삽입 |

### Workbench에서 실행하는 방법

1. Workbench 실행 후 root 계정으로 접속
2. `File` → `Open SQL Script` → `sql/dropdb.sql` 선택 → ⚡ 실행
3. `File` → `Open SQL Script` → `sql/create.sql` 선택 → ⚡ 실행

### 새 커넥션을 만들어 접속하는 경우

MySQL Connections 옆 `+` 버튼을 눌러 아래와 같이 설정하세요.

- **Connection Name**: `DB2026Team04`
- **Username**: `DB2026Team04`
- **Default Schema**: `DB2026Team04`


## 3. Java 프로젝트 실행 방법

### Eclipse 세팅

1. Eclipse 실행 후 `File` → `New` → `Java Project` → 프로젝트명 `DB2026Team04` 입력 → Finish
2. 압축 푼 폴더의 `src/DB2026Team04` 폴더를 Eclipse 프로젝트의 `src` 폴더 안으로 드래그 앤 드롭
3. 프로젝트 우클릭 → `Build Path` → `Add External Archives` → `mysql-connector-j-xxx.jar` 선택

**src 패키지에 module-info.java 파일이 생겼을 경우, 'module-info.java' 파일을 삭제해야 합니다!**

> **라이브러리 경로가 깨진 경우**
> 프로젝트 우클릭 → `Properties` → `Java Build Path` → `Libraries` 탭에서
> Missing 항목 `Remove` 후 `Add External JARs`로 재연결 (v9.x 권장)

### 프로그램 실행

`src/DB2026Team04/Main.java` 우클릭 → `Run As` → `Java Application`


## 4. 테스트 계정

| 구분 | 이메일 | 비밀번호 |
|------|--------|----------|
| 일반 사용자 | `gunwook@gmail.com` | `pass123!` |
| 관리자 | `ewhakim@daum.net` | `onion_love` |

> 일반 사용자는 회원가입으로 직접 계정 생성도 가능합니다.
