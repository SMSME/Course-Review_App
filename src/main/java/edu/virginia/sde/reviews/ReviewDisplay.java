package edu.virginia.sde.reviews;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ReviewDisplay {
    private IntegerProperty rating;
    private StringProperty comment;
    public ReviewDisplay(int rating, String comment) {
        this.rating = new SimpleIntegerProperty(rating);
        this.comment = new SimpleStringProperty(comment);
    }
    public IntegerProperty getDisplayRating() {
        return rating;
    }
    public StringProperty getDisplayComment() {
        return comment;
    }
}
