package com.stayease.stayease.controller;

import com.stayease.stayease.model.Review;
import com.stayease.stayease.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/api/rooms/{roomId}/reviews")
    public ResponseEntity<Review> createReview(
            @PathVariable Long roomId,
            @RequestParam Integer rating,
            @RequestParam String comment,
            Authentication authentication) {
        String email = authentication.getName();
        return ResponseEntity.status(201)
                .body(reviewService.createReview(roomId, rating, comment, email));
    }

    @GetMapping("/api/rooms/{roomId}/reviews")
    public ResponseEntity<List<Review>> getReviewsByRoom(@PathVariable Long roomId) {
        return ResponseEntity.ok(reviewService.getReviewsByRoom(roomId));
    }
}
