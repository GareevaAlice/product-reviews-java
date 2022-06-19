package gareeva.ProductReviews.services;

import gareeva.ProductReviews.models.Review;
import gareeva.ProductReviews.repositories.ProductRepository;
import gareeva.ProductReviews.repositories.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ReviewService {
    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    ProductRepository productRepository;

    public Review addReview(long productId, Review review) throws RuntimeException {
        return productRepository.findById(productId).map(product -> {
            review.setProductId(product.getId());
            Review newReview = reviewRepository.save(review);
            product.addReview(newReview);
            productRepository.save(product);
            return newReview;
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                "ProductId " + productId + " not found"));
    }
}