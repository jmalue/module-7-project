package org.wcci.reviews;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

public class ReviewControllerTest {

	@InjectMocks
	private ReviewController underTest;
	@Mock
	private Review review;

	@Mock
	private Review anotherReview;
	
	@Mock
	private Album anotherAlbum;

	@Mock
	private ReviewRepository reviewRepo;
	
	@Mock
	private AlbumRepository albumRepo;
	
	@Mock
	private Album album;

	@Mock
	private Model model;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void shouldAddSingleReviewToModel() throws ReviewNotFoundException {
		long arbitraryReviewId = 1;
		when(reviewRepo.findById(arbitraryReviewId)).thenReturn(Optional.of(review));

		underTest.findOneReview(arbitraryReviewId, model);
		verify(model).addAttribute("reviews", review);

	}

	@Test
	public void shouldAddAllReviewsToModel() {
		Collection<Review> allReviews = Arrays.asList(review, anotherReview);
		when(reviewRepo.findAll()).thenReturn(allReviews);
		
		underTest.findAllReviews(model);
		verify(model).addAttribute("reviews", allReviews);
	}
	
	@Test
	public void shouldAddSingleAlbumToModel() throws AlbumNotFoundException {
		long arbitraryAlbumId = 1;
		when(albumRepo.findById(arbitraryAlbumId)).thenReturn(Optional.of(album));
		
		underTest.findOneAlbum(arbitraryAlbumId, model);
		
		verify(model).addAttribute("album", album);
		
	}
	
	@Test
	public void shouldAddAllAlbumsToModel() {
		Collection<Album> allAlbums = Arrays.asList(album, anotherAlbum);
		when(albumRepo.findAll()).thenReturn(allAlbums);
		
		underTest.findAllAlbums(model);
		verify(model).addAttribute("albums", allAlbums);
		
	}
	
	
	
	
	
}	
	
	









	
	




