package dto;

/**
 * 플랫폼별 콘텐츠 정보를 담는 DTO 클래스
 */
public class PlatformContentDTO {
    private int pcId;                // 플랫폼 콘텐츠 고유 ID
    private int contentId;           // 콘텐츠 ID
    private int platformId;          // 플랫폼 ID
    private double platformRating;   // 해당 플랫폼에서의 평점
    private boolean isAvailable;     // 현재 제공 여부

    public PlatformContentDTO() {
    }

    public int getPcId() {
        return this.pcId;
    }

    public void setPcId(int pcId) {
        this.pcId = pcId;
    }

    public int getContentId() {
        return this.contentId;
    }

    public void setContentId(int contentId) {
        this.contentId = contentId;
    }

    public int getPlatformId() {
        return this.platformId;
    }

    public void setPlatformId(int platformId) {
        this.platformId = platformId;
    }

    public double getPlatformRating() {
        return this.platformRating;
    }

    public void setPlatformRating(double platformRating) {
        this.platformRating = platformRating;
    }

    public boolean isAvailable() {
        return this.isAvailable;
    }

    public void setAvailable(boolean available) {
        this.isAvailable = available;
    }
}