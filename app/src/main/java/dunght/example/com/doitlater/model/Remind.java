package dunght.example.com.doitlater.model;

public class Remind {

    private int id;
    private String date;
    private String time;
    private String state;
    private String title;
    private String content;
    private int img;
    private String phone;


    public Remind() {
    }

    public Remind(String date, String time, String state, String title, String content, int img, String phone) {
        this.date = date;
        this.time = time;
        this.state = state;
        this.title = title;
        this.content = content;
        this.img = img;
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }
}
