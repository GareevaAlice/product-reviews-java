package gareeva.ProductReviews.controller;

import gareeva.ProductReviews.models.Product;
import gareeva.ProductReviews.models.Review;
import gareeva.ProductReviews.services.ProductService;
import gareeva.ProductReviews.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/product")
public class ProductReviewsController {
    @Autowired
    ProductService productService;

    @Autowired
    ReviewService reviewService;

    @PostMapping
    public Product createProduct(@Valid @RequestBody Product product) {
        return productService.createProduct(product);
    }

    @GetMapping("/{productId}")
    public Product getProduct(@PathVariable long productId) {
        return productService.getProduct(productId);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable long productId) {
        return productService.deleteProduct(productId);
    }

    @PostMapping("/{productId}/review")
    public Review addReview(@PathVariable int productId, @Valid @RequestBody Review review) {
        return reviewService.addReview(productId, review);
    }
}