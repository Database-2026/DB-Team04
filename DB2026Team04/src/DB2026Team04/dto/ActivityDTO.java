package DB2026Team04.dto;

import java.time.LocalDateTime;

// 사용자 시청 기록 및 리뷰 정보를 저장하는 DTO 클래스
public class ActivityDTO {
    private int historyId; //시청 기록 ID
    private int reviewId; // 리뷰 ID
    private int userId; // 사용자 ID
    private int pcId; //플랫폼 콘텐츠 (platform content) ID

    private String username; // 사용자 이름
    private String title; // 콘텐츠 제목
    private String platformName; // 플랫폼 이름

    private String watchStatus; // 시청 상태 (시청 중, 완료, 중단)
    private LocalDateTime watchedDate; // 시청 날짜

    private int rating; // 리뷰 평점
    private String reviewText; //리뷰 내용
    private LocalDateTime reviewDate; // 리뷰 작성 날짜
    private boolean spoiler; // 스포일러 포함 여부

    public ActivityDTO() {} // 기본 생성자

    public int getHistoryId() { return historyId; } // 시청기록 ID 조회
    public void setHistoryId(int historyId) { this.historyId = historyId; } // 시청기록 ID 설정

    public int getReviewId() { return reviewId; } // 리뷰 ID 조회
    public void setReviewId(int reviewId) { this.reviewId = reviewId; } //리뷰 ID 설정

    public int getUserId() { return userId; } //사용자 ID 조회
    public void setUserId(int userId) { this.userId = userId; } // 사용자 ID 설정

    public int getPcId() { return pcId; } // platform content ID 조회
    public void setPcId(int pcId) { this.pcId = pcId; } // platform content ID 설정

    public String getUsername() { return username; } // 사용자 이름 조회
    public void setUsername(String username) { this.username = username; } // 사용자 이름 설정

    public String getTitle() { return title; } // 콘텐츠 제목 조회
    public void setTitle(String title) { this.title = title; } // 콘텐츠 제목 설정

    public String getPlatformName() { return platformName; } // 플랫폼 이름 조회
    public void setPlatformName(String platformName) { this.platformName = platformName; } //플랫폼 이름 설정

    public String getWatchStatus() { return watchStatus; } //시청 상태 조회
    public void setWatchStatus(String watchStatus) { this.watchStatus = watchStatus; } //시청 상태 설정

    public LocalDateTime getWatchedDate() { return watchedDate; } // 시청 날짜 조회
    public void setWatchedDate(LocalDateTime watchedDate) { this.watchedDate = watchedDate; } // 시청 날짜 설정

    public int getRating() { return rating; } // 평점 조회
    public void setRating(int rating) { this.rating = rating; } // 평점 설정

    public String getReviewText() { return reviewText; } //리뷰 내용 조회
    public void setReviewText(String reviewText) { this.reviewText = reviewText; } // 리뷰 내용 설정

    public LocalDateTime getReviewDate() { return reviewDate; } // 리뷰 작성 날짜 조회
    public void setReviewDate(LocalDateTime reviewDate) { this.reviewDate = reviewDate; } // 리뷰 작성 날짜 설정

    public boolean isSpoiler() { return spoiler; } //스포일러 여부 조회
    public void setSpoiler(boolean spoiler) { this.spoiler = spoiler; } // 스포일러 여부 설정

    // 객체 정보 문자열 형태로 반환
    @Override
    public String toString() {
        return "ActivityDTO{" +
                "historyId=" + historyId +
                ", reviewId=" + reviewId +
                ", userId=" + userId +
                ", pcId=" + pcId +
                ", username='" + username + '\'' +
                ", title='" + title + '\'' +
                ", platformName='" + platformName + '\'' +
                ", watchStatus='" + watchStatus + '\'' +
                ", watchedDate=" + watchedDate +
                ", rating=" + rating +
                ", reviewText='" + reviewText + '\'' +
                ", reviewDate=" + reviewDate +
                ", spoiler=" + spoiler +
                '}';
    }
}
