package dto;
// dto란? : db 데이터를 담는 객체.
public class ContentDTO {

    private int content_id;
    private String title;
    private String content_type;
    private int release_year;
    private String age_rating;
    private String description;
//기본생성자. 나중에 setter로 값 채움
    public ContentDTO() {
    }
//한번에 값 넣는 생성자
    public ContentDTO(
            int content_id,
            String title,
            String content_type,
            int release_year,
            String age_rating,
            String description) {

        this.content_id = content_id;
        this.title = title;
        this.content_type = content_type;
        this.release_year = release_year;
        this.age_rating = age_rating;
        this.description = description;
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

   
    public String toString() {

        String result = "";

        if(content_id != 0) {
            result += "  content_id=" + content_id;
        }

        if(title != null) {
            result += "  title=" + title;
        }

        if(content_type != null) {
            result += "  content_type=" + content_type;
        }

        if(release_year != 0) {
            result += "  release_year=" + release_year;
        }

        if(age_rating != null) {
            result += "  age_rating=" + age_rating;
        }

        if(description != null) {
            result += "  description=" + description;
        }

        return result;
    }
}
