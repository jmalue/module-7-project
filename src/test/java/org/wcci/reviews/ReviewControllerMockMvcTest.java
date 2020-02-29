package org.wcci.reviews;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest(ReviewController.class)
public class ReviewControllerMockMvcTest {

	@Resource
	private MockMvc mvc;

	@MockBean
	private ReviewRepository reviewRepo;

	@MockBean
	private AlbumRepository albumRepo;

	@Mock
	Review review;

	@Mock
	Review anotherReview;

	@Mock
	Album album;

	@Mock
	Album anotherAlbum;

	@Test
	public void shouldRouteToSingleReviewView() throws Exception {
		long arbitraryReviewId = 1;
		when(reviewRepo.findById(arbitraryReviewId)).thenReturn(Optional.of(review));
		mvc.perform(get("/review?id=1")).andExpect(view().name(is("review")));

	}

	@Test
	public void shouldBeOkForSingleReview() throws Exception {
		long arbitraryReviewId = 1;
		when(reviewRepo.findById(arbitraryReviewId)).thenReturn(Optional.of(review));
		mvc.perform(get("/review?id=1")).andExpect(status().isOk());
	}

	@Test
	public void shouldNotBeOkForSingleReview() throws Exception {

		mvc.perform(get("/review?id=1")).andExpect(status().isNotFound());
	}

	@Test
	public void shouldPutSingleReviewIntoModel() throws Exception {
		when(reviewRepo.findById(1L)).thenReturn(Optional.of(review));

		mvc.perform(get("/review?id=1")).andExpect(model().attribute("reviews", is(review)));
	}

	@Test
	public void shouldRouteToAllReviewsView() throws Exception {
		mvc.perform(get("/show-reviews")).andExpect(view().name(is("reviews")));
	}

	@Test
	public void shouldBeOKForAllReviews() throws Exception {
		mvc.perform(get("/show-reviews")).andExpect(status().isOk());
	}

	@Test
	public void shouldPutAllReviewsIntoModel() throws Exception {
		Collection<Review> allReviews = Arrays.asList(review, anotherReview);
		when(reviewRepo.findAll()).thenReturn(allReviews);

		mvc.perform(get("/show-reviews")).andExpect(model().attribute("reviews", is(allReviews)));

	}

	@Test
	public void shouldRouteToSingleAlbumView() throws Exception {
		long arbitraryAlbumId = 1;
		when(albumRepo.findById(arbitraryAlbumId)).thenReturn(Optional.of(album));
		mvc.perform(get("/album?id=1")).andExpect(view().name(is("album")));

	}

	@Test
	public void shouldBeOkForSingleAlbum() throws Exception {
		long arbitraryAlbumId = 1;
		when(albumRepo.findById(arbitraryAlbumId)).thenReturn(Optional.of(album));
		mvc.perform(get("/album?id=1")).andExpect(status().isOk());
	}

	@Test
	public void shouldNotBeOkForSingleAlbum() throws Exception {

		mvc.perform(get("/album?id=1")).andExpect(status().isNotFound());
	}

	@Test
	public void shouldPutSingleAlbumIntoModel() throws Exception {
		when(albumRepo.findById(1L)).thenReturn(Optional.of(album));

		mvc.perform(get("/album?id=1")).andExpect(model().attribute("album", is(album)));
	}

	@Test
	public void shouldRouteToAllAlbumView() throws Exception {
		mvc.perform(get("/show-albums")).andExpect(view().name(is("albums")));
	}

	@Test
	public void shouldBeOKForAllAlbums() throws Exception {
		mvc.perform(get("/show-albums")).andExpect(status().isOk());
	}

	@Test
	public void shouldPutAllAlbumsIntoModel() throws Exception {
		Collection<Album> allAlbums = Arrays.asList(album, anotherAlbum);
		when(albumRepo.findAll()).thenReturn(allAlbums);

		mvc.perform(get("/show-albums")).andExpect(model().attribute("albums", is(allAlbums)));

	}

}
