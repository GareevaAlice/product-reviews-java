package gareeva.ProductReviews.services;

import gareeva.ProductReviews.models.Review;
import gareeva.ProductReviews.repositories.ProductRepository;
import gareeva.ProductReviews.repositories.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ReviewService {
    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    ProductRepository productRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
    public Review addReview(long productId, Review review) throws RuntimeException {
        return productRepository.findById(productId).map(product -> {
            review.setProductId(product.getId());
            return reviewRepository.save(review);
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                "ProductId " + productId + " not found"));
    }
}