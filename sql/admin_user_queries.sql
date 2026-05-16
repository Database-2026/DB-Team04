USE DBTeam04;

-- =========================================================
-- 관리자 모드 - 회원 관리 SQL
-- =========================================================

-- ---------------------------------------------------------
-- 1. 회원 등록
-- 신규 회원 정보를 Users 테이블에 삽입한다.
-- ---------------------------------------------------------

INSERT INTO Users (
    username,
    email,
    password,
    role,
    membership,
    status
)
VALUES (
    '홍길동',
    'hong@example.com',
    'pass1234',
    'USER',
    'BASIC',
    'ACTIVE'
);


-- ---------------------------------------------------------
-- 2. 회원 목록 조회
-- 전체 회원 정보를 조회한다.
-- ---------------------------------------------------------

SELECT
    user_id,
    username,
    email,
    role,
    membership,
    status,
    signup_date
FROM Users
ORDER BY user_id;


-- ---------------------------------------------------------
-- 2-1. 일반 사용자 목록 조회
-- role이 USER인 회원만 조회한다.
-- ---------------------------------------------------------

SELECT
    user_id,
    username,
    email,
    role,
    membership,
    status,
    signup_date
FROM Users
WHERE role = 'USER'
ORDER BY user_id;


-- ---------------------------------------------------------
-- 2-2. 관리자 목록 조회
-- role이 ADMIN인 계정만 조회한다.
-- ---------------------------------------------------------

SELECT
    user_id,
    username,
    email,
    role,
    membership,
    status,
    signup_date
FROM Users
WHERE role = 'ADMIN'
ORDER BY user_id;


-- ---------------------------------------------------------
-- 3. 회원 검색 - 이름 기준
-- username에 특정 검색어가 포함된 회원을 조회한다.
-- Dynamic Query 구현 시 사용자의 입력값을 LIKE 조건에 반영한다.
-- ---------------------------------------------------------

SELECT
    user_id,
    username,
    email,
    role,
    membership,
    status,
    signup_date
FROM Users
WHERE username LIKE '%김%'
ORDER BY user_id;


-- ---------------------------------------------------------
-- 3. 회원 검색 - 이메일 기준
-- email에 특정 검색어가 포함된 회원을 조회한다.
-- ---------------------------------------------------------

SELECT
    user_id,
    username,
    email,
    role,
    membership,
    status,
    signup_date
FROM Users
WHERE email LIKE '%gmail.com%'
ORDER BY user_id;


-- ---------------------------------------------------------
-- 3. 회원 검색 - 멤버십 기준
-- 특정 멤버십 등급의 회원을 조회한다.
-- ---------------------------------------------------------

SELECT
    user_id,
    username,
    email,
    role,
    membership,
    status,
    signup_date
FROM Users
WHERE membership = 'PREMIUM'
ORDER BY user_id;


-- ---------------------------------------------------------
-- 3. 회원 검색 - 상태 기준
-- ACTIVE, INACTIVE, BANNED 등 상태별 회원을 조회한다.
-- ---------------------------------------------------------

SELECT
    user_id,
    username,
    email,
    role,
    membership,
    status,
    signup_date
FROM Users
WHERE status = 'ACTIVE'
ORDER BY user_id;


-- ---------------------------------------------------------
-- 3. 회원 검색 - 복합 조건 검색 예시
-- 이름, 이메일, 멤버십, 상태 조건을 함께 사용할 수 있다.
-- ---------------------------------------------------------

SELECT
    user_id,
    username,
    email,
    role,
    membership,
    status,
    signup_date
FROM Users
WHERE 1 = 1
  AND username LIKE '%김%'
  AND email LIKE '%gmail.com%'
  AND membership = 'PREMIUM'
  AND status = 'ACTIVE'
ORDER BY user_id;


-- ---------------------------------------------------------
-- 4. 회원 정보 수정
-- user_id를 기준으로 회원 정보를 수정한다.
-- ---------------------------------------------------------

UPDATE Users
SET
    username = '김수정',
    email = 'new_email@example.com',
    membership = 'STANDARD',
    status = 'ACTIVE'
WHERE user_id = 1;


-- ---------------------------------------------------------
-- 4-1. 회원 멤버십 수정
-- 특정 회원의 멤버십 등급을 수정한다.
-- ---------------------------------------------------------

UPDATE Users
SET membership = 'PREMIUM'
WHERE user_id = 2;


-- ---------------------------------------------------------
-- 4-2. 회원 상태 수정
-- 특정 회원의 상태를 수정한다.
-- ---------------------------------------------------------

UPDATE Users
SET status = 'INACTIVE'
WHERE user_id = 3;


-- ---------------------------------------------------------
-- 4-3. 비밀번호 수정
-- 특정 회원의 비밀번호를 수정한다.
-- ---------------------------------------------------------

UPDATE Users
SET password = 'newpass1234'
WHERE user_id = 1;


-- ---------------------------------------------------------
-- 5. 회원 삭제 / 탈퇴 처리
-- 실제 DELETE보다 status를 INACTIVE로 변경하는 방식을 권장한다.
-- 감상 기록과 리뷰 데이터를 보존할 수 있기 때문이다.
-- ---------------------------------------------------------

UPDATE Users
SET status = 'INACTIVE'
WHERE user_id = 4;


-- ---------------------------------------------------------
-- 5-1. 부적절 사용자 제재 처리
-- 관리자가 특정 사용자의 상태를 BANNED로 변경한다.
-- ---------------------------------------------------------

UPDATE Users
SET status = 'BANNED'
WHERE user_id = 5;


-- ---------------------------------------------------------
-- 5-2. 회원 완전 삭제
-- 실제로 Users 테이블에서 회원을 삭제한다.
-- ON DELETE CASCADE가 설정된 경우 해당 회원의 감상 기록과 리뷰도 함께 삭제될 수 있다.
-- 테스트할 때 주의
-- ---------------------------------------------------------

DELETE FROM Users
WHERE user_id = 999;


-- ---------------------------------------------------------
-- 6. 회원 관리 기능 확인용 쿼리
-- 특정 회원의 현재 상태를 확인한다.
-- ---------------------------------------------------------

SELECT
    user_id,
    username,
    email,
    role,
    membership,
    status,
    signup_date
FROM Users
WHERE user_id = 1;