package DBTeam04.dto;

public class PlatformContentDTO {

    private int pcId;
    private int contentId;
    private int platformId;
    private double platformRating;
    private boolean isAvailable;

    public int getPcId() {
        return pcId;
    }

    public void setPcId(int pcId) {
        this.pcId = pcId;
    }

    public int getContentId() {
        return contentId;
    }

    public void setContentId(int contentId) {
        this.contentId = contentId;
    }

    public int getPlatformId() {
        return platformId;
    }

    public void setPlatformId(int platformId) {
        this.platformId = platformId;
    }

    public double getPlatformRating() {
        return platformRating;
    }

    public void setPlatformRating(double platformRating) {
        this.platformRating = platformRating;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }
}