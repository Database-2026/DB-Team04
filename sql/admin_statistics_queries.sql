USE DBTeam04;

-- =========================================================
-- 관리자 모드 - 통계 조회 SQL
-- =========================================================


-- =========================================================
-- 1. 플랫폼별 콘텐츠 수 조회
-- =========================================================
-- 각 OTT 플랫폼에 등록된 콘텐츠 수를 조회한다.
-- Platform과 PlatformContent를 LEFT JOIN하여 콘텐츠가 없는 플랫폼도 조회되도록 한다.
-- =========================================================

SELECT
    P.platform_id,
    P.platform_name,
    P.platform_price,
    COUNT(DISTINCT CASE 
        WHEN PC.is_available = TRUE THEN PC.content_id 
    END) AS available_content_count,
    COUNT(DISTINCT PC.content_id) AS total_content_count
FROM Platform P
LEFT JOIN PlatformContent PC ON P.platform_id = PC.platform_id
GROUP BY
    P.platform_id,
    P.platform_name,
    P.platform_price
ORDER BY available_content_count DESC;


-- =========================================================
-- 1-1. 플랫폼별 콘텐츠 수 + 평균 플랫폼 평점 조회
-- =========================================================
-- 플랫폼별 제공 콘텐츠 수와 PlatformContent에 저장된 플랫폼 평점 평균을 함께 조회한다.
-- =========================================================

SELECT
    P.platform_id,
    P.platform_name,
    COUNT(DISTINCT CASE 
        WHEN PC.is_available = TRUE THEN PC.content_id 
    END) AS available_content_count,
    ROUND(AVG(CASE 
        WHEN PC.is_available = TRUE THEN PC.platform_rating 
    END), 2) AS avg_platform_rating
FROM Platform P
LEFT JOIN PlatformContent PC ON P.platform_id = PC.platform_id
GROUP BY
    P.platform_id,
    P.platform_name
ORDER BY available_content_count DESC, avg_platform_rating DESC;



-- =========================================================
-- 2. 콘텐츠별 평균 평점 조회
-- =========================================================
-- Review 테이블의 사용자 평점을 기준으로 콘텐츠별 평균 평점을 계산한다.
-- 하나의 콘텐츠가 여러 플랫폼에서 제공될 수 있으므로
-- Review -> PlatformContent -> Content 구조로 JOIN한다.
-- =========================================================

SELECT
    C.content_id,
    C.title,
    C.content_type,
    C.release_year,
    ROUND(AVG(R.rating), 2) AS avg_user_rating,
    COUNT(R.review_id) AS review_count
FROM Content C
LEFT JOIN PlatformContent PC ON C.content_id = PC.content_id
LEFT JOIN Review R ON PC.pc_id = R.pc_id
GROUP BY
    C.content_id,
    C.title,
    C.content_type,
    C.release_year
ORDER BY avg_user_rating DESC, review_count DESC;


-- =========================================================
-- 2-1. 콘텐츠별 평균 평점 + 제공 플랫폼 조회
-- =========================================================
-- 콘텐츠별 평균 평점과 해당 콘텐츠를 제공하는 플랫폼 목록을 함께 조회한다.
-- =========================================================

SELECT
    C.content_id,
    C.title,
    C.content_type,
    C.release_year,
    COALESCE(
        GROUP_CONCAT(DISTINCT P.platform_name ORDER BY P.platform_name SEPARATOR ', '),
        '제공 플랫폼 없음'
    ) AS platforms,
    ROUND(AVG(R.rating), 2) AS avg_user_rating,
    COUNT(DISTINCT R.review_id) AS review_count
FROM Content C
LEFT JOIN PlatformContent PC ON C.content_id = PC.content_id
LEFT JOIN Platform P ON PC.platform_id = P.platform_id
LEFT JOIN Review R ON PC.pc_id = R.pc_id
GROUP BY
    C.content_id,
    C.title,
    C.content_type,
    C.release_year
ORDER BY avg_user_rating DESC, review_count DESC;



-- =========================================================
-- 3. 장르별 콘텐츠 수 조회
-- =========================================================
-- Genre, ContentGenre, Content를 JOIN하여 장르별 콘텐츠 수를 계산한다.
-- 하나의 콘텐츠가 여러 장르를 가질 수 있으므로 COUNT(DISTINCT C.content_id)를 사용한다.
-- =========================================================

SELECT
    G.genre_id,
    G.genre_name,
    COUNT(DISTINCT C.content_id) AS content_count
FROM Genre G
LEFT JOIN ContentGenre CG ON G.genre_id = CG.genre_id
LEFT JOIN Content C ON CG.content_id = C.content_id
GROUP BY
    G.genre_id,
    G.genre_name
ORDER BY content_count DESC, G.genre_name;


-- =========================================================
-- 3-1. 장르별 콘텐츠 수 + 평균 사용자 평점 조회
-- =========================================================
-- 장르별 콘텐츠 개수와 해당 장르 콘텐츠들의 평균 리뷰 평점을 조회한다.
-- =========================================================

SELECT
    G.genre_id,
    G.genre_name,
    COUNT(DISTINCT C.content_id) AS content_count,
    ROUND(AVG(R.rating), 2) AS avg_user_rating
FROM Genre G
LEFT JOIN ContentGenre CG ON G.genre_id = CG.genre_id
LEFT JOIN Content C ON CG.content_id = C.content_id
LEFT JOIN PlatformContent PC ON C.content_id = PC.content_id
LEFT JOIN Review R ON PC.pc_id = R.pc_id
GROUP BY
    G.genre_id,
    G.genre_name
ORDER BY content_count DESC, avg_user_rating DESC;



-- =========================================================
-- 4. 인기 콘텐츠 조회
-- =========================================================
-- 인기 콘텐츠는 감상 기록 수, 리뷰 수, 평균 평점을 함께 고려하여 조회한다.
-- WatchHistory와 Review를 동시에 직접 JOIN하면 행이 중복되어 카운트가 부풀 수 있으므로,
-- 감상 기록 집계와 리뷰 집계를 각각 Subquery로 만든 뒤 Content에 연결한다.
-- =========================================================

SELECT
    C.content_id,
    C.title,
    C.content_type,
    C.release_year,
    COALESCE(WH.watch_count, 0) AS watch_count,
    COALESCE(RV.review_count, 0) AS review_count,
    RV.avg_user_rating,
    (
        COALESCE(WH.watch_count, 0) 
        + COALESCE(RV.review_count, 0)
    ) AS popularity_score
FROM Content C
LEFT JOIN (
    SELECT
        PC.content_id,
        COUNT(WH.history_id) AS watch_count
    FROM PlatformContent PC
    LEFT JOIN WatchHistory WH ON PC.pc_id = WH.pc_id
    GROUP BY PC.content_id
) WH ON C.content_id = WH.content_id
LEFT JOIN (
    SELECT
        PC.content_id,
        COUNT(R.review_id) AS review_count,
        ROUND(AVG(R.rating), 2) AS avg_user_rating
    FROM PlatformContent PC
    LEFT JOIN Review R ON PC.pc_id = R.pc_id
    GROUP BY PC.content_id
) RV ON C.content_id = RV.content_id
ORDER BY popularity_score DESC, RV.avg_user_rating DESC;


-- =========================================================
-- 4-1. 감상 기록 수 기준 인기 콘텐츠 조회
-- =========================================================

SELECT
    C.content_id,
    C.title,
    C.content_type,
    COUNT(WH.history_id) AS watch_count
FROM Content C
JOIN PlatformContent PC ON C.content_id = PC.content_id
LEFT JOIN WatchHistory WH ON PC.pc_id = WH.pc_id
GROUP BY
    C.content_id,
    C.title,
    C.content_type
ORDER BY watch_count DESC;


-- =========================================================
-- 4-2. 리뷰 수 기준 인기 콘텐츠 조회
-- =========================================================

SELECT
    C.content_id,
    C.title,
    C.content_type,
    COUNT(R.review_id) AS review_count,
    ROUND(AVG(R.rating), 2) AS avg_user_rating
FROM Content C
JOIN PlatformContent PC ON C.content_id = PC.content_id
LEFT JOIN Review R ON PC.pc_id = R.pc_id
GROUP BY
    C.content_id,
    C.title,
    C.content_type
ORDER BY review_count DESC, avg_user_rating DESC;



-- =========================================================
-- 5. 높은 평점 콘텐츠 조회
-- =========================================================
-- 전체 평균 평점보다 높은 평균 평점을 가진 콘텐츠를 조회한다.
-- 이 쿼리는 Subquery 요구사항 예시로 사용하기 좋다.
-- =========================================================

SELECT
    C.content_id,
    C.title,
    C.content_type,
    C.release_year,
    ROUND(AVG(R.rating), 2) AS avg_user_rating,
    COUNT(R.review_id) AS review_count
FROM Content C
JOIN PlatformContent PC ON C.content_id = PC.content_id
JOIN Review R ON PC.pc_id = R.pc_id
GROUP BY
    C.content_id,
    C.title,
    C.content_type,
    C.release_year
HAVING AVG(R.rating) > (
    SELECT AVG(rating)
    FROM Review
)
ORDER BY avg_user_rating DESC;


-- =========================================================
-- 5-1. 평균 평점 4.0 이상 콘텐츠 조회
-- =========================================================

SELECT
    C.content_id,
    C.title,
    C.content_type,
    C.release_year,
    ROUND(AVG(R.rating), 2) AS avg_user_rating,
    COUNT(R.review_id) AS review_count
FROM Content C
JOIN PlatformContent PC ON C.content_id = PC.content_id
JOIN Review R ON PC.pc_id = R.pc_id
GROUP BY
    C.content_id,
    C.title,
    C.content_type,
    C.release_year
HAVING AVG(R.rating) >= 4.0
ORDER BY avg_user_rating DESC;


-- =========================================================
-- 5-2. 플랫폼별 높은 평점 콘텐츠 조회
-- =========================================================
-- 플랫폼별로 사용자 리뷰 평점이 높은 콘텐츠를 조회한다.
-- =========================================================

SELECT
    P.platform_id,
    P.platform_name,
    C.content_id,
    C.title,
    C.content_type,
    ROUND(AVG(R.rating), 2) AS avg_user_rating,
    COUNT(R.review_id) AS review_count
FROM Platform P
JOIN PlatformContent PC ON P.platform_id = PC.platform_id
JOIN Content C ON PC.content_id = C.content_id
JOIN Review R ON PC.pc_id = R.pc_id
GROUP BY
    P.platform_id,
    P.platform_name,
    C.content_id,
    C.title,
    C.content_type
HAVING AVG(R.rating) >= 4.0
ORDER BY P.platform_name, avg_user_rating DESC;




/*
CREATE VIEW PlatformContentView AS
SELECT
    PC.pc_id,
    C.content_id,
    C.title,
    C.content_type,
    C.release_year,
    P.platform_id,
    P.platform_name,
    P.platform_price,
    PC.platform_rating,
    PC.is_available,
    PC.added_at
FROM PlatformContent PC
JOIN Content C ON PC.content_id = C.content_id
JOIN Platform P ON PC.platform_id = P.platform_id;
*/

/*
CREATE VIEW HighRatedContentView AS
SELECT
    C.content_id,
    C.title,
    C.content_type,
    C.release_year,
    ROUND(AVG(R.rating), 2) AS avg_user_rating,
    COUNT(R.review_id) AS review_count
FROM Content C
JOIN PlatformContent PC ON C.content_id = PC.content_id
JOIN Review R ON PC.pc_id = R.pc_id
GROUP BY
    C.content_id,
    C.title,
    C.content_type,
    C.release_year
HAVING AVG(R.rating) >= 4.0;
*/


-- =========================================================
-- View 사용 쿼리 예시
-- =========================================================
-- create.sql에서 위 View를 생성했다면 아래처럼 조회할 수 있다.
-- =========================================================

-- SELECT * FROM PlatformContentView;
-- SELECT * FROM HighRatedContentView;



-- =========================================================
-- 통계 조회 테스트용 기본 확인 쿼리
-- =========================================================

-- 전체 플랫폼 수
SELECT COUNT(*) AS platform_count
FROM Platform;

-- 전체 콘텐츠 수
SELECT COUNT(*) AS content_count
FROM Content;

-- 전체 장르 수
SELECT COUNT(*) AS genre_count
FROM Genre;

-- 전체 감상 기록 수
SELECT COUNT(*) AS watch_history_count
FROM WatchHistory;

-- 전체 리뷰 수
SELECT COUNT(*) AS review_count
FROM Review;

-- 전체 사용자 리뷰 평균 평점
SELECT ROUND(AVG(rating), 2) AS overall_avg_rating
FROM Review;