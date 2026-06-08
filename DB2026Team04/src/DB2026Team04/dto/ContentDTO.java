package DB2026Team04.dto;
// 콘텐츠 정보를 저장하는 DTO 클래스
public class ContentDTO {
    private int contentId; // 콘텐츠 ID
    private String title; // 콘텐츠 제목
    private String contentType; // 콘텐츠 유형
    private int releaseYear; // 개봉 연도
    private String ageRating; // 연령 등급
    private String description; // 콘텐츠 설명

    //콘텐츠 ID 조회
    public int getContentId() {
        return contentId;
    }
    // 콘텐츠 ID 설정
    public void setContentId(int contentId) {
        this.contentId = contentId;
    }
    // 콘텐츠 제목 조회
    public String getTitle() {
        return title;
    }
    // 콘텐츠 제목 설정
    public void setTitle(String title) {
        this.title = title;
    }
    // 콘텐츠 유형 조회
    public String getContentType() {
        return contentType;
    }
    // 콘텐츠 유형 설정
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
    // 개봉 연도 조회
    public int getReleaseYear() {
        return releaseYear;
    }
    // 개봉 연도 설정
    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }
    // 연령 등급 조회
    public String getAgeRating() {
        return ageRating;
    }
    // 연령 등급 설정
    public void setAgeRating(String ageRating) {
        this.ageRating = ageRating;
    }
    // 콘텐츠 설명 조회
    public String getDescription() {
        return description;
    }
    // 콘텐츠 설명 설정
    public void setDescription(String description) {
        this.description = description;
    }
}
