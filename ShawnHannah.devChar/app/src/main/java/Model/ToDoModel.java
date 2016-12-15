package Model;

/**
 * Created by User on 12/15/2016.
 */

public class ToDoModel {
    String title;
    String description;


    public ToDoModel(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
