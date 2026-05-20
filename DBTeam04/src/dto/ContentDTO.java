package dto;
// dto란? : db 데이터를 담는 객체.
public class ContentDTO {

    private int content_id;
    private String title;
    private String content_type;
    private int release_year;
    private String age_rating;
    private String description;
    
    private String genre;
    private String platformName;
    private double platformRating;

    public ContentDTO(){}
    
    public ContentDTO(
            int content_id,
            String title,
            String content_type,
            int release_year,
            String age_rating,
            String description, 
            String genre,
            String platformName,
            double platformRating) {

        this.content_id = content_id;
        this.title = title;
        this.content_type = content_type;
        this.release_year = release_year;
        this.age_rating = age_rating;
        this.description = description;
        this.genre=genre;
        this.platformName=platformName;
        this.platformRating=platformRating;
    }

    public int getContentId() {
        return content_id;
    }

    public void setContentId(int content_id) {
        this.content_id = content_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContentType() {
        return content_type;
    }

    public void setContentType(String content_type) {
        this.content_type = content_type;
    }

    public int getReleaseYear() {
        return release_year;
    }

    public void setReleaseYear(int release_year) {
        this.release_year = release_year;
    }

    public String getAgeRating() {
        return age_rating;
    }

    public void setAgeRating(String age_rating) {
        this.age_rating = age_rating;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getPlatformName() {
        return platformName;
    }

    public void setPlatformName(String platformName) {
        this.platformName = platformName;
    }

    public double getPlatformRating() {
        return platformRating;
    }

    public void setPlatformRating(double platformRating) {
        this.platformRating = platformRating;
    }
   

}
