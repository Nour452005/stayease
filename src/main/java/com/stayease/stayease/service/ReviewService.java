package com.stayease.stayease.service;

import com.stayease.stayease.model.Review;
import com.stayease.stayease.model.Room;
import com.stayease.stayease.model.User;
import com.stayease.stayease.repository.ReservationRepository;
import com.stayease.stayease.repository.ReviewRepository;
import com.stayease.stayease.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final RoomService roomService;
    private final ReservationRepository reservationRepository;

    public Review createReview(Long roomId, Integer rating,
                               String comment, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Room room = roomService.getRoomById(roomId);

        boolean hasStayed = reservationRepository.findByUser(user)
                .stream()
                .anyMatch(r ->
                        r.getRoom().getId().equals(roomId) &&
                                (r.getStatus().name().equals("CHECKED_OUT") ||
                                        r.getStatus().name().equals("CONFIRMED"))
                );

        if (!hasStayed) {
            throw new RuntimeException("You can only review rooms you have stayed in");
        }

        Review review = new Review();
        review.setUser(user);
        review.setRoom(room);
        review.setRating(rating);
        review.setComment(comment);
        review.setCreatedAt(LocalDateTime.now());

        return reviewRepository.save(review);
    }

    public List<Review> getReviewsByRoom(Long roomId) {
        return reviewRepository.findByRoomId(roomId);
    }
}