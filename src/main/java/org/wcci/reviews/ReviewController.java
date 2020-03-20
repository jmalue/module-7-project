package org.wcci.reviews;

import java.util.Optional;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ReviewController {

	@Resource
	ReviewRepository reviewRepo;

	@Resource
	AlbumRepository albumRepo;

	@RequestMapping("/review")
	public String findOneReview(@PathVariable(value = "id") long id, Model model) throws ReviewNotFoundException {
		Optional<Review> review = reviewRepo.findById(id);

		if (review.isPresent()) {
			model.addAttribute("review", review.get());
			return "review";
		}
		throw new ReviewNotFoundException();

	}

	@RequestMapping("/reviews")
	public String findAllReviews(Model model) {
		model.addAttribute("reviews", reviewRepo.findAll());
		return ("reviews");
	}

	@RequestMapping("/album")
	public String findOneAlbum(@RequestParam(value = "id") long id, Model model) throws AlbumNotFoundException {
		Optional<Album> album = albumRepo.findById(id);

		if (album.isPresent()) {
			model.addAttribute("album", album.get());
			return "album";
		}
		throw new AlbumNotFoundException();
	}

	@RequestMapping("/show-albums")
	public String findAllAlbums(Model model) {
		model.addAttribute("albums", albumRepo.findAll());
		return ("albums");

	}

	@RequestMapping("/add-review")
	public String addReview(String reviewName, String reviewDescription, String albumName) {

		Album album = albumRepo.findByName(albumName);
		if (album == null) {
			album = new Album(albumName);
			albumRepo.save(album);
		
		}
			
		Review newReview = reviewRepo.findByName(reviewName);
		

		if (newReview == null) {
			newReview = new Review(reviewName, reviewDescription, album);
			reviewRepo.save(newReview);
		}
		return "redirect:/show-reviews";

	}

	@RequestMapping("delete-review")
	public String deleteReviewByName(String reviewName) {

		if (reviewRepo.findByName(reviewName) != null)
			;
		{
			Review deletedReview = reviewRepo.findByName(reviewName);
			reviewRepo.delete(deletedReview);
		}
		return "redirect:/show-reviews";

	}

	@RequestMapping("/del-review")
	public String deleteReviewById(Long reviewId) {

		reviewRepo.deleteById(reviewId);

		return "redirect:/show-reviews";

	}

	@RequestMapping("/find-by-Album")
	public String findReviewsByAlbum(String albumName, Model model) {
		Album album = albumRepo.findByName(albumName);
		model.addAttribute("reviews", reviewRepo.findByAlbumsContains(album));

		return "/album";

	}

	@RequestMapping("/sort-reviews")
	public String sortReviews(Model model) {
		model.addAttribute("reviews", reviewRepo.findAllByOrderByNameAsc());

		return "redirect:/show-reviews";
	}

}
