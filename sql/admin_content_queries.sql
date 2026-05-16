USE DBTeam04;

-- =========================================================
-- 관리자 모드 - 콘텐츠 관리 SQL
-- =========================================================

-- =========================================================
-- 1. 콘텐츠 등록 및 제공 플랫폼 연결
-- =========================================================

START TRANSACTION;

-- 1-1. 콘텐츠 기본 정보 등록
INSERT INTO Content (
    title,
    content_type,
    release_year,
    age_rating,
    description
)
VALUES (
    '테스트 콘텐츠',
    '영화',
    2026,
    '15세',
    '관리자 콘텐츠 등록 기능 테스트용 데이터입니다.'
);

-- 방금 등록한 콘텐츠 ID 저장
SET @new_content_id = LAST_INSERT_ID();

-- 1-2. 콘텐츠 장르 연결
-- 예시: 장르 ID 1 = 로맨스, 장르 ID 5 = 코미디
INSERT INTO ContentGenre (
    content_id,
    genre_id
)
VALUES
    (@new_content_id, 1),
    (@new_content_id, 5);

-- 1-3. 콘텐츠 제공 플랫폼 연결
-- 예시: Platform ID 3 = Netflix, Platform ID 5 = Tving
INSERT INTO PlatformContent (
    content_id,
    platform_id,
    platform_rating,
    is_available
)
VALUES
    (@new_content_id, 3, 4.2, TRUE),
    (@new_content_id, 5, 4.0, TRUE);

COMMIT;


-- 등록 결과 확인
SELECT
    C.content_id,
    C.title,
    C.content_type,
    C.release_year,
    C.age_rating,
    GROUP_CONCAT(DISTINCT G.genre_name ORDER BY G.genre_name SEPARATOR ', ') AS genres,
    GROUP_CONCAT(DISTINCT P.platform_name ORDER BY P.platform_name SEPARATOR ', ') AS platforms
FROM Content C
LEFT JOIN ContentGenre CG ON C.content_id = CG.content_id
LEFT JOIN Genre G ON CG.genre_id = G.genre_id
LEFT JOIN PlatformContent PC ON C.content_id = PC.content_id
LEFT JOIN Platform P ON PC.platform_id = P.platform_id
WHERE C.content_id = @new_content_id
GROUP BY
    C.content_id,
    C.title,
    C.content_type,
    C.release_year,
    C.age_rating;



-- =========================================================
-- 2. 콘텐츠 목록 조회
-- =========================================================
-- 등록된 콘텐츠 목록을 조회한다.
-- 장르와 제공 플랫폼은 여러 개일 수 있으므로 GROUP_CONCAT으로 묶어 보여준다.
-- =========================================================

SELECT
    C.content_id,
    C.title,
    C.content_type,
    C.release_year,
    C.age_rating,
    COALESCE(GROUP_CONCAT(DISTINCT G.genre_name ORDER BY G.genre_name SEPARATOR ', '), '장르 없음') AS genres,
    COALESCE(GROUP_CONCAT(DISTINCT P.platform_name ORDER BY P.platform_name SEPARATOR ', '), '제공 플랫폼 없음') AS platforms
FROM Content C
LEFT JOIN ContentGenre CG ON C.content_id = CG.content_id
LEFT JOIN Genre G ON CG.genre_id = G.genre_id
LEFT JOIN PlatformContent PC ON C.content_id = PC.content_id
LEFT JOIN Platform P ON PC.platform_id = P.platform_id
GROUP BY
    C.content_id,
    C.title,
    C.content_type,
    C.release_year,
    C.age_rating
ORDER BY C.content_id;


-- 콘텐츠 목록 간단 조회
SELECT
    content_id,
    title,
    content_type,
    release_year,
    age_rating
FROM Content
ORDER BY content_id;


-- =========================================================
-- 3. 콘텐츠 검색
-- =========================================================
-- 제목, 콘텐츠 유형, 장르, 플랫폼 기준으로 검색한다.
-- 사용자 입력값을 WHERE 조건에 반영하므로 Dynamic Query로 구현
-- =========================================================


-- 3-1. 제목으로 콘텐츠 검색

SELECT
    C.content_id,
    C.title,
    C.content_type,
    C.release_year,
    C.age_rating
FROM Content C
WHERE C.title LIKE '%무빙%'
ORDER BY C.content_id;


-- 3-2. 콘텐츠 유형으로 검색
-- 예: 영화, 드라마, 예능, 애니메이션 등

SELECT
    C.content_id,
    C.title,
    C.content_type,
    C.release_year,
    C.age_rating
FROM Content C
WHERE C.content_type = '드라마'
ORDER BY C.content_id;


-- 3-3. 장르로 콘텐츠 검색
-- Genre, ContentGenre, Content를 JOIN하여 특정 장르의 콘텐츠를 조회한다.

SELECT
    C.content_id,
    C.title,
    C.content_type,
    C.release_year,
    C.age_rating,
    G.genre_name
FROM Content C
JOIN ContentGenre CG ON C.content_id = CG.content_id
JOIN Genre G ON CG.genre_id = G.genre_id
WHERE G.genre_name = '로맨스'
ORDER BY C.content_id;


-- 3-4. 플랫폼으로 콘텐츠 검색
-- 특정 OTT 플랫폼에서 제공되는 콘텐츠를 조회한다.

SELECT
    C.content_id,
    C.title,
    C.content_type,
    C.release_year,
    C.age_rating,
    P.platform_name,
    PC.platform_rating,
    PC.is_available
FROM Content C
JOIN PlatformContent PC ON C.content_id = PC.content_id
JOIN Platform P ON PC.platform_id = P.platform_id
WHERE P.platform_name = 'Netflix'
  AND PC.is_available = TRUE
ORDER BY C.content_id;


-- 3-5. 복합 조건 검색 예시
-- 예: 제목 + 유형 + 장르 + 플랫폼 조건을 함께 사용

SELECT
    C.content_id,
    C.title,
    C.content_type,
    C.release_year,
    C.age_rating,
    GROUP_CONCAT(DISTINCT G.genre_name ORDER BY G.genre_name SEPARATOR ', ') AS genres,
    GROUP_CONCAT(DISTINCT P.platform_name ORDER BY P.platform_name SEPARATOR ', ') AS platforms
FROM Content C
LEFT JOIN ContentGenre CG ON C.content_id = CG.content_id
LEFT JOIN Genre G ON CG.genre_id = G.genre_id
LEFT JOIN PlatformContent PC ON C.content_id = PC.content_id
LEFT JOIN Platform P ON PC.platform_id = P.platform_id
WHERE 1 = 1
  AND C.title LIKE '%사랑%'
  AND C.content_type = '영화'
  AND G.genre_name = '로맨스'
  AND P.platform_name = 'Netflix'
GROUP BY
    C.content_id,
    C.title,
    C.content_type,
    C.release_year,
    C.age_rating
ORDER BY C.content_id;


-- =========================================================
-- 3-Dynamic Query 설계 
-- =========================================================
-- 콘텐츠 검색 기능은 사용자가 입력한 조건에 따라 WHERE절을 동적으로 구성한다.
--
-- 예시:
-- 제목 입력값이 있으면      AND C.title LIKE ?
-- 유형 선택값이 있으면      AND C.content_type = ?
-- 장르 선택값이 있으면      AND G.genre_name = ?
-- 플랫폼 선택값이 있으면    AND P.platform_name = ?
-- =========================================================


-- =========================================================
-- 4. 콘텐츠 상세 정보 조회
-- =========================================================
-- 특정 콘텐츠의 기본 정보, 장르, 제공 플랫폼, 평균 리뷰 평점, 리뷰 수를 함께 조회한다.
-- =========================================================

SELECT
    C.content_id,
    C.title,
    C.content_type,
    C.release_year,
    C.age_rating,
    C.description,
    COALESCE(GROUP_CONCAT(DISTINCT G.genre_name ORDER BY G.genre_name SEPARATOR ', '), '장르 없음') AS genres,
    COALESCE(GROUP_CONCAT(DISTINCT P.platform_name ORDER BY P.platform_name SEPARATOR ', '), '제공 플랫폼 없음') AS platforms,
    ROUND(AVG(R.rating), 2) AS avg_user_rating,
    COUNT(DISTINCT R.review_id) AS review_count
FROM Content C
LEFT JOIN ContentGenre CG ON C.content_id = CG.content_id
LEFT JOIN Genre G ON CG.genre_id = G.genre_id
LEFT JOIN PlatformContent PC ON C.content_id = PC.content_id
LEFT JOIN Platform P ON PC.platform_id = P.platform_id
LEFT JOIN Review R ON PC.pc_id = R.pc_id
WHERE C.content_id = 1
GROUP BY
    C.content_id,
    C.title,
    C.content_type,
    C.release_year,
    C.age_rating,
    C.description;


-- 4-1. 특정 콘텐츠의 제공 플랫폼 상세 조회

SELECT
    PC.pc_id,
    C.content_id,
    C.title,
    P.platform_id,
    P.platform_name,
    P.platform_price,
    PC.platform_rating,
    PC.is_available,
    PC.added_at
FROM PlatformContent PC
JOIN Content C ON PC.content_id = C.content_id
JOIN Platform P ON PC.platform_id = P.platform_id
WHERE C.content_id = 1
ORDER BY P.platform_name;


-- 4-2. 특정 콘텐츠의 리뷰 조회

SELECT
    R.review_id,
    U.username,
    C.title,
    P.platform_name,
    R.rating,
    R.review_text,
    R.review_date,
    R.is_spoiler
FROM Review R
JOIN Users U ON R.user_id = U.user_id
JOIN PlatformContent PC ON R.pc_id = PC.pc_id
JOIN Content C ON PC.content_id = C.content_id
JOIN Platform P ON PC.platform_id = P.platform_id
WHERE C.content_id = 1
ORDER BY R.review_date DESC;


-- =========================================================
-- 5. 콘텐츠 기본 정보 수정
-- =========================================================
-- Content 테이블에 저장된 콘텐츠 기본 정보를 수정한다.
-- 제공 플랫폼 정보는 PlatformContent에서 따로 수정한다.
-- =========================================================

UPDATE Content
SET
    title = '테스트 콘텐츠 수정',
    content_type = '드라마',
    release_year = 2026,
    age_rating = '15세',
    description = '관리자 콘텐츠 기본 정보 수정 테스트입니다.'
WHERE content_id = @new_content_id;


-- 5-1. 콘텐츠 장르 수정
-- 기존 장르 연결을 삭제한 뒤 새 장르 연결을 삽입한다.
-- 여러 테이블 변경이므로 Transaction으로 처리한다.

START TRANSACTION;

DELETE FROM ContentGenre
WHERE content_id = @new_content_id;

INSERT INTO ContentGenre (
    content_id,
    genre_id
)
VALUES
    (@new_content_id, 2),
    (@new_content_id, 7);

COMMIT;


-- 5-2. 수정 결과 확인

SELECT
    C.content_id,
    C.title,
    C.content_type,
    C.release_year,
    C.age_rating,
    GROUP_CONCAT(DISTINCT G.genre_name ORDER BY G.genre_name SEPARATOR ', ') AS genres
FROM Content C
LEFT JOIN ContentGenre CG ON C.content_id = CG.content_id
LEFT JOIN Genre G ON CG.genre_id = G.genre_id
WHERE C.content_id = @new_content_id
GROUP BY
    C.content_id,
    C.title,
    C.content_type,
    C.release_year,
    C.age_rating;



-- =========================================================
-- 6. 콘텐츠 제공 플랫폼 수정 / 삭제
-- =========================================================
-- 특정 콘텐츠가 어떤 플랫폼에서 제공되는지 관리한다.
-- PlatformContent 테이블을 수정하거나 삭제한다.
-- =========================================================


-- 6-1. 콘텐츠 제공 플랫폼 목록 확인
-- 수정/삭제 전에 pc_id를 확인하기 위한 쿼리

SELECT
    PC.pc_id,
    C.content_id,
    C.title,
    P.platform_id,
    P.platform_name,
    PC.platform_rating,
    PC.is_available,
    PC.added_at
FROM PlatformContent PC
JOIN Content C ON PC.content_id = C.content_id
JOIN Platform P ON PC.platform_id = P.platform_id
WHERE C.content_id = @new_content_id
ORDER BY PC.pc_id;


-- 6-2. 플랫폼 내 평점 수정

UPDATE PlatformContent
SET platform_rating = 4.5
WHERE pc_id = 1;


-- 6-3. 제공 여부 수정
-- 콘텐츠가 특정 플랫폼에서 더 이상 제공되지 않는 경우 삭제 대신 is_available을 FALSE로 변경할 수 있다.

UPDATE PlatformContent
SET is_available = FALSE
WHERE pc_id = 1;


-- 6-4. 제공 여부 다시 활성화

UPDATE PlatformContent
SET is_available = TRUE
WHERE pc_id = 1;


-- 6-5. 콘텐츠 제공 플랫폼 추가
-- 이미 등록된 콘텐츠를 새로운 플랫폼에 연결한다.
-- UNIQUE(content_id, platform_id) 제약조건 때문에 같은 조합은 중복 등록되지 않는다.

INSERT INTO PlatformContent (
    content_id,
    platform_id,
    platform_rating,
    is_available
)
VALUES (
    1,
    5,
    4.1,
    TRUE
);


-- 6-6. 콘텐츠 제공 플랫폼 연결 삭제
-- 실제 삭제가 필요한 경우 사용한다.
-- 해당 pc_id를 참조하는 WatchHistory, Review가 있으면 ON DELETE CASCADE로 함께 삭제될 수 있으므로 주의한다.

DELETE FROM PlatformContent
WHERE pc_id = 999;



-- =========================================================
-- 7. 콘텐츠 삭제
-- =========================================================
-- 콘텐츠를 삭제하면 ContentGenre, PlatformContent가 함께 삭제된다.
-- PlatformContent가 삭제되면 해당 pc_id를 참조하는 WatchHistory, Review도 함께 삭제될 수 있다.
-- =========================================================


-- 7-1. 삭제 전 콘텐츠 확인

SELECT
    C.content_id,
    C.title,
    C.content_type,
    C.release_year,
    C.age_rating,
    COUNT(DISTINCT PC.pc_id) AS platform_count,
    COUNT(DISTINCT R.review_id) AS review_count
FROM Content C
LEFT JOIN PlatformContent PC ON C.content_id = PC.content_id
LEFT JOIN Review R ON PC.pc_id = R.pc_id
WHERE C.content_id = @new_content_id
GROUP BY
    C.content_id,
    C.title,
    C.content_type,
    C.release_year,
    C.age_rating;


-- 7-2. 콘텐츠 삭제
-- ON DELETE CASCADE 설정이 되어 있으므로 연결 테이블 데이터도 함께 삭제된다.

DELETE FROM Content
WHERE content_id = @new_content_id;


-- 7-3. 삭제 결과 확인

SELECT *
FROM Content
WHERE content_id = @new_content_id;


-- =========================================================
-- 콘텐츠 관리 기능 테스트용 조회
-- =========================================================

-- 전체 콘텐츠 수 확인
SELECT COUNT(*) AS content_count
FROM Content;

-- 전체 콘텐츠-플랫폼 연결 수 확인
SELECT COUNT(*) AS platform_content_count
FROM PlatformContent;

-- 전체 콘텐츠-장르 연결 수 확인
SELECT COUNT(*) AS content_genre_count
FROM ContentGenre;

-- 현재 등록된 장르 목록 확인
SELECT *
FROM Genre
ORDER BY genre_id;

-- 현재 등록된 플랫폼 목록 확인
SELECT *
FROM Platform
ORDER BY platform_id;