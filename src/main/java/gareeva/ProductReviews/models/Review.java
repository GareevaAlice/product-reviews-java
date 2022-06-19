package gareeva.ProductReviews.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@Table
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @Column
    private String message;

    @NotNull
    @Column
    private int rating;

    private long productId;

    public long getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) throws RuntimeException {
        if (Objects.equals(message, "")) {
            throw new RuntimeException("Message can not be empty");
        }
        this.message = message;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) throws RuntimeException {
        if (rating < 0 || 5 < rating) {
            throw new RuntimeException("Rating should be from 0 to 5");
        }
        this.rating = rating;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }
}
