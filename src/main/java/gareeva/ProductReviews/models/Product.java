package gareeva.ProductReviews.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    private String description;

    @OneToMany
    @JoinColumn(name = "productId")
    private List<Review> reviewList = new ArrayList<>();

    private long sumRating = 0;

    public long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) throws RuntimeException {
        if (Objects.equals(description, "")) {
            throw new RuntimeException("Description can not be empty");
        }
        this.description = description;
    }

    public void addReview(Review review) {
        this.reviewList.add(review);
        this.sumRating += review.getRating();
    }

    public List<Review> getReviewList() {
        return reviewList;
    }

    public double getMeanRating() {
        if (reviewList.size() == 0) {
            return 0;
        }
        return (double) sumRating / reviewList.size();
    }
}
