package gareeva.ProductReviews.repositories;

import gareeva.ProductReviews.models.Review;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends CrudRepository<Review, Long>
{
}
