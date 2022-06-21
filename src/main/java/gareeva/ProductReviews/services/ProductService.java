package gareeva.ProductReviews.services;

import gareeva.ProductReviews.models.Product;
import gareeva.ProductReviews.repositories.ProductRepository;
import gareeva.ProductReviews.repositories.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;

    @Autowired
    ReviewRepository reviewRepository;

    public Product createProduct(Product product) throws RuntimeException {
        return productRepository.save(product);
    }

    public Product getProduct(long productId) throws RuntimeException {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "ProductId " + productId + " not found"));
    }

    public ResponseEntity<?> deleteProduct(long productId) throws RuntimeException {
        return productRepository.findById(productId).map(product -> {
            reviewRepository.deleteAll(product.getReviewList());
            productRepository.delete(product);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                "ProductId " + productId + " not found"));
    }
}
