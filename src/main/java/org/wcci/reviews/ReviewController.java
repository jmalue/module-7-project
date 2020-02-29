package org.wcci.reviews;

import java.util.Optional;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ReviewController {

	@Resource
	ReviewRepository reviewRepo;

	@Resource
	AlbumRepository albumRepo;

	@RequestMapping("/review")
	public String findOneReview(@RequestParam(value = "id") long id, Model model) throws ReviewNotFoundException {
		Optional<Review> review = reviewRepo.findById(id);

		if (review.isPresent()) {
			model.addAttribute("reviews", review.get());
			return "review";
		}
		throw new ReviewNotFoundException();

	}

	@RequestMapping("/show-reviews")
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

}
