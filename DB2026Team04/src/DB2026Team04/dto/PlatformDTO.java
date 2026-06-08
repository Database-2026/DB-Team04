package DB2026Team04.dto;
/**
 * 플랫폼 정보를 저장하고 전달하기 위한 DTO 클래스
 */
public class PlatformDTO {
    //플랫폼 기본 정보
    private int platformId;
    private String platformName;
    private double platformPrice;
    // 플랫폼별 제공 콘텐츠 수 조회용
    private int contentCount; 
    //getter / setter
    public int getPlatformId() {
        return platformId;
    }

    public void setPlatformId(int platformId) {
        this.platformId = platformId;
    }

    public String getPlatformName() {
        return platformName;
    }

    public void setPlatformName(String platformName) {
        this.platformName = platformName;
    }

    public double getPlatformPrice() {
        return platformPrice;
    }

    public void setPlatformPrice(double platformPrice) {
        this.platformPrice = platformPrice;
    }

    public int getContentCount() {
        return contentCount;
    }

    public void setContentCount(int contentCount) {
        this.contentCount = contentCount;
    }
}
