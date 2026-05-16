USE DBTeam04;

-- =========================================================
-- 관리자 모드 - 사용자 활동 관리 SQL
-- =========================================================


-- =========================================================
-- 공통 테스트용 변수
-- 실제 테스트할 때 DB에 존재하는 user_id, content_id, review_id로 바꿔서 사용
-- =========================================================

SET @target_user_id = 1;
SET @target_content_id = 1;
SET @target_review_id = 999;


-- =========================================================
-- 1. 전체 감상 기록 조회
-- =========================================================
-- 모든 사용자의 감상 기록을 조회한다.
-- Users, WatchHistory, PlatformContent, Content, Platform, Genre를 JOIN하여
-- 사용자가 어떤 플랫폼에서 어떤 콘텐츠를 감상했는지 확인한다.
-- =========================================================

SELECT
    WH.history_id,
    U.user_id,
    U.username,
    U.email,
    C.content_id,
    C.title,
    C.content_type,
    C.release_year,
    COALESCE(GROUP_CONCAT(DISTINCT G.genre_name ORDER BY G.genre_name SEPARATOR ', '), '장르 없음') AS genres,
    P.platform_id,
    P.platform_name,
    WH.watch_status,
    WH.watched_date
FROM WatchHistory WH
JOIN Users U ON WH.user_id = U.user_id
JOIN PlatformContent PC ON WH.pc_id = PC.pc_id
JOIN Content C ON PC.content_id = C.content_id
JOIN Platform P ON PC.platform_id = P.platform_id
LEFT JOIN ContentGenre CG ON C.content_id = CG.content_id
LEFT JOIN Genre G ON CG.genre_id = G.genre_id
GROUP BY
    WH.history_id,
    U.user_id,
    U.username,
    U.email,
    C.content_id,
    C.title,
    C.content_type,
    C.release_year,
    P.platform_id,
    P.platform_name,
    WH.watch_status,
    WH.watched_date
ORDER BY WH.watched_date DESC;



-- =========================================================
-- 2. 사용자별 감상 기록 조회
-- =========================================================
-- 특정 사용자의 감상 기록을 조회한다.
-- 관리자 메뉴에서는 user_id, username, email 등을 기준으로 검색할 수 있다.
-- =========================================================


-- 2-1. user_id 기준 사용자별 감상 기록 조회

SELECT
    WH.history_id,
    U.user_id,
    U.username,
    U.email,
    C.content_id,
    C.title,
    P.platform_name,
    WH.watch_status,
    WH.watched_date
FROM WatchHistory WH
JOIN Users U ON WH.user_id = U.user_id
JOIN PlatformContent PC ON WH.pc_id = PC.pc_id
JOIN Content C ON PC.content_id = C.content_id
JOIN Platform P ON PC.platform_id = P.platform_id
WHERE U.user_id = @target_user_id
ORDER BY WH.watched_date DESC;



-- 2-2. username 기준 사용자별 감상 기록 조회
-- Dynamic Query 또는 검색 기능 구현 시 사용할 수 있다.

SELECT
    WH.history_id,
    U.user_id,
    U.username,
    U.email,
    C.title,
    P.platform_name,
    WH.watch_status,
    WH.watched_date
FROM WatchHistory WH
JOIN Users U ON WH.user_id = U.user_id
JOIN PlatformContent PC ON WH.pc_id = PC.pc_id
JOIN Content C ON PC.content_id = C.content_id
JOIN Platform P ON PC.platform_id = P.platform_id
WHERE U.username LIKE '%김%'
ORDER BY WH.watched_date DESC;


-- 2-3. email 기준 사용자별 감상 기록 조회

SELECT
    WH.history_id,
    U.user_id,
    U.username,
    U.email,
    C.title,
    P.platform_name,
    WH.watch_status,
    WH.watched_date
FROM WatchHistory WH
JOIN Users U ON WH.user_id = U.user_id
JOIN PlatformContent PC ON WH.pc_id = PC.pc_id
JOIN Content C ON PC.content_id = C.content_id
JOIN Platform P ON PC.platform_id = P.platform_id
WHERE U.email LIKE '%example.com%'
ORDER BY WH.watched_date DESC;


-- =========================================================
-- 3. 콘텐츠별 감상 기록 조회
-- =========================================================
-- 특정 콘텐츠를 감상한 사용자들의 기록을 조회한다.
-- 하나의 콘텐츠가 여러 플랫폼에서 제공될 수 있으므로 content_id 기준으로 조회한다.
-- =========================================================


-- 3-1. content_id 기준 콘텐츠별 감상 기록 조회

SELECT
    WH.history_id,
    C.content_id,
    C.title,
    P.platform_name,
    U.user_id,
    U.username,
    U.email,
    WH.watch_status,
    WH.watched_date
FROM WatchHistory WH
JOIN Users U ON WH.user_id = U.user_id
JOIN PlatformContent PC ON WH.pc_id = PC.pc_id
JOIN Content C ON PC.content_id = C.content_id
JOIN Platform P ON PC.platform_id = P.platform_id
WHERE C.content_id = @target_content_id
ORDER BY WH.watched_date DESC;




-- 3-2. 콘텐츠 제목 기준 감상 기록 조회
-- title LIKE 조건을 사용하므로 Dynamic Query 예시로도 활용 가능하다.

SELECT
    WH.history_id,
    C.content_id,
    C.title,
    P.platform_name,
    U.username,
    WH.watch_status,
    WH.watched_date
FROM WatchHistory WH
JOIN Users U ON WH.user_id = U.user_id
JOIN PlatformContent PC ON WH.pc_id = PC.pc_id
JOIN Content C ON PC.content_id = C.content_id
JOIN Platform P ON PC.platform_id = P.platform_id
WHERE C.title LIKE '%무빙%'
ORDER BY WH.watched_date DESC;


-- =========================================================
-- 4. 전체 리뷰 조회
-- =========================================================
-- 전체 사용자의 리뷰와 평점을 조회한다.
-- Users, Review, PlatformContent, Content, Platform을 JOIN한다.
-- =========================================================

SELECT
    R.review_id,
    U.user_id,
    U.username,
    U.email,
    C.content_id,
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
ORDER BY R.review_date DESC;



-- =========================================================
-- 5. 사용자별 리뷰 조회
-- =========================================================
-- 특정 사용자가 작성한 리뷰를 조회한다.
-- =========================================================


-- 5-1. user_id 기준 사용자별 리뷰 조회

SELECT
    R.review_id,
    U.user_id,
    U.username,
    C.content_id,
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
WHERE U.user_id = @target_user_id
ORDER BY R.review_date DESC;


-- 5-2. username 기준 사용자별 리뷰 조회

SELECT
    R.review_id,
    U.user_id,
    U.username,
    U.email,
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
WHERE U.username LIKE '%김%'
ORDER BY R.review_date DESC;


-- =========================================================
-- 6. 콘텐츠별 리뷰 조회
-- =========================================================
-- 특정 콘텐츠에 작성된 리뷰를 조회한다.
-- 하나의 콘텐츠가 여러 플랫폼에 있을 수 있으므로 content_id 기준으로 조회한다.
-- =========================================================


-- 6-1. content_id 기준 콘텐츠별 리뷰 조회

SELECT
    R.review_id,
    C.content_id,
    C.title,
    P.platform_name,
    U.user_id,
    U.username,
    R.rating,
    R.review_text,
    R.review_date,
    R.is_spoiler
FROM Review R
JOIN Users U ON R.user_id = U.user_id
JOIN PlatformContent PC ON R.pc_id = PC.pc_id
JOIN Content C ON PC.content_id = C.content_id
JOIN Platform P ON PC.platform_id = P.platform_id
WHERE C.content_id = @target_content_id
ORDER BY R.review_date DESC;




-- 6-2. 콘텐츠 제목 기준 리뷰 조회

SELECT
    R.review_id,
    C.content_id,
    C.title,
    P.platform_name,
    U.username,
    R.rating,
    R.review_text,
    R.review_date,
    R.is_spoiler
FROM Review R
JOIN Users U ON R.user_id = U.user_id
JOIN PlatformContent PC ON R.pc_id = PC.pc_id
JOIN Content C ON PC.content_id = C.content_id
JOIN Platform P ON PC.platform_id = P.platform_id
WHERE C.title LIKE '%무빙%'
ORDER BY R.review_date DESC;


-- 6-3. 콘텐츠별 평균 평점 및 리뷰 수 조회

SELECT
    C.content_id,
    C.title,
    ROUND(AVG(R.rating), 2) AS avg_rating,
    COUNT(R.review_id) AS review_count
FROM Review R
JOIN PlatformContent PC ON R.pc_id = PC.pc_id
JOIN Content C ON PC.content_id = C.content_id
WHERE C.content_id = @target_content_id
GROUP BY
    C.content_id,
    C.title;


-- =========================================================
-- 7. 부적절한 리뷰 삭제
-- =========================================================
-- 현재 Review 테이블에는 신고 여부 컬럼이 없으므로,
-- 관리자가 전체 리뷰/콘텐츠별 리뷰를 조회한 뒤 review_id를 기준으로 삭제한다.
-- =========================================================


-- 7-1. 삭제 전 리뷰 확인
-- 실제 삭제하기 전에 review_id, 작성자, 콘텐츠, 리뷰 내용을 확인한다.

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
WHERE R.review_id = @target_review_id;


-- 7-2. 부적절한 리뷰 삭제
-- @target_review_id 값을 실제 삭제할 review_id로 변경한 후 실행한다.
-- 주의: DELETE는 실제 데이터가 삭제되므로 반드시 SELECT로 먼저 확인한다.

DELETE FROM Review
WHERE review_id = @target_review_id;


-- 7-3. 삭제 결과 확인

SELECT
    review_id,
    user_id,
    pc_id,
    rating,
    review_text,
    review_date,
    is_spoiler
FROM Review
WHERE review_id = @target_review_id;



-- =========================================================
-- 부적절한 리뷰 후보 조회 예시
-- =========================================================
-- Review 테이블에 신고 여부 컬럼이 없기 때문에,
-- 아래 쿼리는 예시용이다.
-- 특정 키워드가 포함된 리뷰 또는 스포일러 표시 리뷰를 관리자가 확인할 수 있다.
-- =========================================================


-- 7-4. 스포일러 포함 리뷰 조회

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
WHERE R.is_spoiler = TRUE
ORDER BY R.review_date DESC;


-- 7-5. 특정 키워드 포함 리뷰 조회
-- 실제 부적절성 판단은 관리자가 수행한다.

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
WHERE R.review_text LIKE '%욕설%'
   OR R.review_text LIKE '%스포%'
ORDER BY R.review_date DESC;



-- =========================================================
-- 사용자 활동 관리 기능 테스트용 조회
-- =========================================================

-- 전체 감상 기록 수
SELECT COUNT(*) AS watch_history_count
FROM WatchHistory;

-- 전체 리뷰 수
SELECT COUNT(*) AS review_count
FROM Review;

-- 사용자별 감상 기록 수
SELECT
    U.user_id,
    U.username,
    COUNT(WH.history_id) AS watch_count
FROM Users U
LEFT JOIN WatchHistory WH ON U.user_id = WH.user_id
GROUP BY
    U.user_id,
    U.username
ORDER BY watch_count DESC;

-- 사용자별 리뷰 수
SELECT
    U.user_id,
    U.username,
    COUNT(R.review_id) AS review_count
FROM Users U
LEFT JOIN Review R ON U.user_id = R.user_id
GROUP BY
    U.user_id,
    U.username
ORDER BY review_count DESC;