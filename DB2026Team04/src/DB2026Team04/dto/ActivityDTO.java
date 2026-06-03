package DB2026Team04.dto;

import java.time.LocalDateTime;

public class ActivityDTO {
    private int historyId;
    private int reviewId;
    private int userId;
    private int pcId;

    private String username;
    private String title;
    private String platformName;

    private String watchStatus;
    private LocalDateTime watchedDate;

    private int rating;
    private String reviewText;
    private LocalDateTime reviewDate;
    private boolean spoiler;

    public ActivityDTO() {}

    public int getHistoryId() { return historyId; }
    public void setHistoryId(int historyId) { this.historyId = historyId; }

    public int getReviewId() { return reviewId; }
    public void setReviewId(int reviewId) { this.reviewId = reviewId; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public int getPcId() { return pcId; }
    public void setPcId(int pcId) { this.pcId = pcId; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getPlatformName() { return platformName; }
    public void setPlatformName(String platformName) { this.platformName = platformName; }

    public String getWatchStatus() { return watchStatus; }
    public void setWatchStatus(String watchStatus) { this.watchStatus = watchStatus; }

    public LocalDateTime getWatchedDate() { return watchedDate; }
    public void setWatchedDate(LocalDateTime watchedDate) { this.watchedDate = watchedDate; }

    public int getRating() { return rating; }
    public void setRating(int rating) { this.rating = rating; }

    public String getReviewText() { return reviewText; }
    public void setReviewText(String reviewText) { this.reviewText = reviewText; }

    public LocalDateTime getReviewDate() { return reviewDate; }
    public void setReviewDate(LocalDateTime reviewDate) { this.reviewDate = reviewDate; }

    public boolean isSpoiler() { return spoiler; }
    public void setSpoiler(boolean spoiler) { this.spoiler = spoiler; }

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
