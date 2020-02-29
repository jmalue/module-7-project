package org.wcci.reviews;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertThat;

import java.util.Collection;
import java.util.Optional;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@DataJpaTest
public class JPAMappingsTest {

	@Resource
	private TestEntityManager entityManager;

	@Resource
	private AlbumRepository albumRepo;

	@Resource
	private ReviewRepository reviewRepo;
	
	@Resource
	private TrackRepository trackRepo;

	@Test
	public void shouldSaveAndLoadAlbum() {
		Album album = albumRepo.save(new Album("album"));
		long albumId = album.getId();

		entityManager.flush(); // forces jpa to hit the db when we try to find it
		entityManager.clear();

		Optional<Album> result = albumRepo.findById(albumId);
		album = result.get();
		assertThat(album.getName(), is("album"));
	}

	@Test
	public void shouldGenerateAlbumId() {
		Album album = albumRepo.save(new Album("album"));
		long albumId = album.getId();

		entityManager.flush();
		entityManager.clear();

		assertThat(albumId, is(greaterThan(0L)));
	}

	@Test
	public void shouldSaveAndLoadReview() {
		Review review = new Review("review name", "description");
		review = reviewRepo.save(review);
		long reviewId = review.getId();

		entityManager.flush();
		entityManager.clear();

		Optional<Review> result = reviewRepo.findById(reviewId);
		review = result.get();
		assertThat(review.getName(), is("review name"));
	}

	@Test
	public void shouldEstablishReviewsToAlbumRelationships() {
		// album is not the owner so we create these first
		Album abbeyRoad = albumRepo.save(new Album("Abbey Road"));
		Album rubberSoul = albumRepo.save(new Album("Rubber Soul"));

		Review review = new Review("Beatles Albums", "description", abbeyRoad, rubberSoul);
		review = reviewRepo.save(review);
		long reviewId = review.getId();

		entityManager.flush();
		entityManager.clear();

		Optional<Review> result = reviewRepo.findById(reviewId);
		review = result.get();

		assertThat(review.getAlbums(), containsInAnyOrder(abbeyRoad, rubberSoul));
	}

	@Test
	public void shouldFindReviewsForAlbum() {
		Album abbeyRoad = albumRepo.save(new Album("Abbey Road"));

		Review one = reviewRepo.save(new Review("Review One", "Description", abbeyRoad));
		Review two = reviewRepo.save(new Review("Review Two", "Description", abbeyRoad));

		entityManager.flush();
		entityManager.clear();

		Collection<Review> reviewsForAlbums = reviewRepo.findByAlbumsContains(abbeyRoad);

		assertThat(reviewsForAlbums, containsInAnyOrder(one, two));

	}

	@Test
	public void shouldFindReviewsforAlbumId() {
		Album rubberSoul = albumRepo.save(new Album("rubberSoul"));
		long albumId = rubberSoul.getId();

		Review one = reviewRepo.save(new Review("Review One", "Description", rubberSoul));
		Review two = reviewRepo.save(new Review("Review Two", "Description", rubberSoul));

		entityManager.flush();
		entityManager.clear();

		Collection<Review> reviewsForAlbums = reviewRepo.findByAlbumsId(albumId);

		assertThat(reviewsForAlbums, containsInAnyOrder(one, two));

	}
	@Test
	public void shouldEstablishTrackToReviewRelationship() {
		Review review = new Review("name", "description");
		reviewRepo.save(review);
		long reviewId = review.getId();
		
		Track song = new Track("title", review);
		trackRepo.save(song);
		
		Track song2 = new Track("title two", review);
		trackRepo.save(song2);
		
		entityManager.flush();
		entityManager.clear();
		
		Optional<Review>result = reviewRepo.findById(reviewId);
		review = result.get();
		assertThat(review.getTrack(), containsInAnyOrder(song, song2));
		
	}
	

}
