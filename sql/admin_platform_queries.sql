USE DBTeam04;

-- =========================================================
-- 관리자 모드 - 플랫폼 관리 SQL
-- =========================================================


-- =========================================================
-- 1. 플랫폼 등록
-- =========================================================

INSERT INTO Platform (
    platform_name,
    platform_price
)
VALUES (
    'Watcha',
    7900
);

-- 방금 등록한 플랫폼 ID 저장
SET @new_platform_id = LAST_INSERT_ID();

-- 등록 결과 확인
SELECT
    platform_id,
    platform_name,
    platform_price
FROM Platform
WHERE platform_id = @new_platform_id;



-- =========================================================
-- 2. 플랫폼 목록 조회
-- =========================================================

SELECT
    platform_id,
    platform_name,
    platform_price
FROM Platform
ORDER BY platform_id;


-- 플랫폼별 제공 콘텐츠 수 포함 조회

SELECT
    P.platform_id,
    P.platform_name,
    P.platform_price,
    COUNT(DISTINCT PC.content_id) AS content_count
FROM Platform P
LEFT JOIN PlatformContent PC ON P.platform_id = PC.platform_id
GROUP BY
    P.platform_id,
    P.platform_name,
    P.platform_price
ORDER BY P.platform_id;


-- =========================================================
-- 3. 플랫폼 검색
-- =========================================================


-- 3-1. 플랫폼명으로 검색

SELECT
    platform_id,
    platform_name,
    platform_price
FROM Platform
WHERE platform_name LIKE '%Netflix%'
ORDER BY platform_id;



-- 3-2. 가격 이하 검색
-- 예: 월 구독료가 10,000원 이하인 플랫폼 조회

SELECT
    platform_id,
    platform_name,
    platform_price
FROM Platform
WHERE platform_price <= 10000
ORDER BY platform_price;


-- 3-3. 복합 조건 검색 예시

SELECT
    platform_id,
    platform_name,
    platform_price
FROM Platform
WHERE 1 = 1
  AND platform_name LIKE '%play%'
  AND platform_price <= 10000
ORDER BY platform_price;


-- =========================================================
-- 4. 플랫폼 정보 수정
-- =========================================================

-- 수정 대상 플랫폼 ID 설정
SET @target_platform_id = 1;

-- 수정 전 확인
SELECT
    platform_id,
    platform_name,
    platform_price
FROM Platform
WHERE platform_id = @target_platform_id;


-- 4-1. 플랫폼명과 가격 수정

UPDATE Platform
SET
    platform_name = 'Netflix',
    platform_price = 17000
WHERE platform_id = @target_platform_id;


-- 수정 결과 확인
SELECT
    platform_id,
    platform_name,
    platform_price
FROM Platform
WHERE platform_id = @target_platform_id;


-- 4-2. 플랫폼 가격만 수정

UPDATE Platform
SET platform_price = 14900
WHERE platform_id = @target_platform_id;



-- =========================================================
-- 5. 플랫폼 삭제
-- =========================================================
-- 플랫폼 삭제 전 해당 플랫폼에 연결된 콘텐츠가 있는지 먼저 확인한다.
-- =========================================================

-- 삭제 대상 플랫폼 ID 설정
SET @delete_platform_id = @new_platform_id;


-- 5-1. 삭제 전 플랫폼 정보 및 연결 콘텐츠 수 확인

SELECT
    P.platform_id,
    P.platform_name,
    P.platform_price,
    COUNT(DISTINCT PC.content_id) AS linked_content_count
FROM Platform P
LEFT JOIN PlatformContent PC ON P.platform_id = PC.platform_id
WHERE P.platform_id = @delete_platform_id
GROUP BY
    P.platform_id,
    P.platform_name,
    P.platform_price;


-- 5-2. 플랫폼에 연결된 콘텐츠 목록 확인

SELECT
    PC.pc_id,
    C.content_id,
    C.title,
    P.platform_name,
    PC.platform_rating,
    PC.is_available
FROM PlatformContent PC
JOIN Content C ON PC.content_id = C.content_id
JOIN Platform P ON PC.platform_id = P.platform_id
WHERE P.platform_id = @delete_platform_id
ORDER BY C.title;


-- 5-3. 플랫폼 삭제
-- PlatformContent와 연결되어 있으면 FK 제약조건 때문에 삭제가 안 될 수 있다.
-- ON DELETE CASCADE가 설정되어 있으면 연결 데이터도 함께 삭제된다.

DELETE FROM Platform
WHERE platform_id = @delete_platform_id;


-- 삭제 결과 확인

SELECT
    platform_id,
    platform_name,
    platform_price
FROM Platform
WHERE platform_id = @delete_platform_id;


-- =========================================================
-- 6. 플랫폼별 제공 콘텐츠 조회
-- =========================================================


-- 6-1. 플랫폼명 기준 제공 콘텐츠 조회
-- 예: Netflix에서 제공 중인 콘텐츠 조회

SELECT
    P.platform_id,
    P.platform_name,
    P.platform_price,
    PC.pc_id,
    C.content_id,
    C.title,
    C.content_type,
    C.release_year,
    PC.platform_rating,
    PC.is_available
FROM Platform P
JOIN PlatformContent PC ON P.platform_id = PC.platform_id
JOIN Content C ON PC.content_id = C.content_id
WHERE P.platform_name = 'Netflix'
  AND PC.is_available = TRUE
ORDER BY C.title;


-- 6-2. 플랫폼 ID 기준 제공 콘텐츠 조회
-- Java 구현에서는 platform_id를 입력받는 방식이 안전하다.

SELECT
    P.platform_id,
    P.platform_name,
    P.platform_price,
    PC.pc_id,
    C.content_id,
    C.title,
    C.content_type,
    C.release_year,
    PC.platform_rating,
    PC.is_available
FROM Platform P
JOIN PlatformContent PC ON P.platform_id = PC.platform_id
JOIN Content C ON PC.content_id = C.content_id
WHERE P.platform_id = 1
  AND PC.is_available = TRUE
ORDER BY C.title;




-- =========================================================
-- 플랫폼 관리 관련 Index 생성 예시
-- Index 생성문은 create.sql에 넣는 게 나을듯?
-- =========================================================

-- 플랫폼명 검색용
-- CREATE INDEX idx_platform_name ON Platform(platform_name);

-- 플랫폼 가격 검색용
-- CREATE INDEX idx_platform_price ON Platform(platform_price);

-- 플랫폼별 제공 콘텐츠 조회용
-- CREATE INDEX idx_platformcontent_platform ON PlatformContent(platform_id);


-- =========================================================
-- 플랫폼 관리 기능 테스트용 조회
-- =========================================================

-- 전체 플랫폼 수 확인
SELECT COUNT(*) AS platform_count
FROM Platform;

-- 전체 플랫폼-콘텐츠 연결 수 확인
SELECT COUNT(*) AS platform_content_count
FROM PlatformContent;

-- 현재 플랫폼 전체 확인
SELECT *
FROM Platform
ORDER BY platform_id;

-- 현재 콘텐츠 제공 정보 전체 확인
SELECT
    PC.pc_id,
    C.title,
    P.platform_name,
    PC.platform_rating,
    PC.is_available
FROM PlatformContent PC
JOIN Content C ON PC.content_id = C.content_id
JOIN Platform P ON PC.platform_id = P.platform_id
ORDER BY P.platform_name, C.title;