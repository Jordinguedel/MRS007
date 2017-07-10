/**
 * 
 */
package ch.fhnw.edu.rental.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import ch.fhnw.edu.rental.model.ChildrenPriceCategory;
import ch.fhnw.edu.rental.model.Movie;
import ch.fhnw.edu.rental.model.PriceCategory;
import ch.fhnw.edu.rental.model.RegularPriceCategory;

/**
 * @author christoph.denzler
 * 
 */
public class MovieTest {
    private LocalDate d;

    /**
     * Expected exception message.
     */
    private static final String MESSAGE = "not all input parameters are set!";

    @Before
    public void setup() {
        d = LocalDate.now();
    }
    
    /**
     * Hashcode and equals are always overridden and tested together. Test method for
     * {@link ch.fhnw.edu.rental.model.Movie#hashCode()}.
     * 
     * @throws InterruptedException should not be thrown
     */
    @Test
    public void testHashCode() throws InterruptedException {
        PriceCategory pc = RegularPriceCategory.getInstance();
        Movie x = new Movie();
        Movie y = new Movie("A", d, pc);
        Movie z = new Movie("A", d, pc);

        // do we get consistently the same result?
        int h = x.hashCode();
        assertEquals(h, x.hashCode());
        h = y.hashCode();
        assertEquals(h, y.hashCode());

        // do we get the same result from two equal objects?
        h = y.hashCode();
        assertEquals(h, z.hashCode());

        // still the same hashcode after changing rented state?
        z.setRented(true);
        assertEquals(h, z.hashCode());

        z = new Movie("A", d, pc); // get a new Movie
        z.setPriceCategory(ChildrenPriceCategory.getInstance());
        assertEquals(h, z.hashCode());

        z.setId(42);
        assertFalse(h == z.hashCode());
    }

    /**
     * Test method for {@link ch.fhnw.edu.rental.model.Movie#Movie()}.
     */
    @Test
    public void testMovie() {
        Movie m = new Movie();
        assertNull(m.getPriceCategory());
        assertNull(m.getReleaseDate());
        assertNull(m.getTitle());
        assertFalse(m.isRented());
    }

    /**
     * Test method for
     * {@link ch.fhnw.edu.rental.model.Movie#Movie(java.lang.String, ch.fhnw.edu.rental.model.PriceCategory)}
     * .
     * 
     * @throws InterruptedException must not be thrown
     */
    @Test
    public void testMovieStringPriceCategory() throws InterruptedException {
        Movie m = new Movie("A", RegularPriceCategory.getInstance());
        assertionsForMovieCtorTests(m, LocalDate.now());
    }
    
    private void assertionsForMovieCtorTests(Movie m, LocalDate rd) {
        assertEquals("A", m.getTitle());
        assertEquals(RegularPriceCategory.class, m.getPriceCategory().getClass());
        LocalDate releaseDate = m.getReleaseDate();
        assertNotNull(releaseDate);
        Period d = Period.between(releaseDate, rd);
        assertTrue(d.get(ChronoUnit.DAYS) < 2);
        assertEquals(rd, releaseDate);
        assertFalse(m.isRented());
    }

    /**
     * Test method for
     * {@link ch.fhnw.edu.rental.model.Movie#Movie(java.lang.String, ch.fhnw.edu.rental.model.PriceCategory)}
     * .
     */
    @Test
    public void testExceptionMovieStringPriceCategory() {
        Movie m = null;
        try {
            m = new Movie(null, RegularPriceCategory.getInstance());
        } catch (NullPointerException e) {
            assertEquals(MESSAGE, e.getMessage());
        }
        try {
            m = new Movie("A", null);
        } catch (NullPointerException e) {
            assertEquals(MESSAGE, e.getMessage());
        }
        assertNull(m);
    }

    /**
     * Test method for
     * {@link ch.fhnw.edu.rental.model.Movie#Movie(java.lang.String, java.util.Date, ch.fhnw.edu.rental.model.PriceCategory)}
     * .
     */
    @Test
    public void testMovieStringDatePriceCategory() {
        LocalDate d = LocalDate.now();
        Movie m = new Movie("B", d, RegularPriceCategory.getInstance());

        assertNotNull(m);
        assertEquals("B", m.getTitle());
        assertEquals(RegularPriceCategory.class, m.getPriceCategory().getClass());
        LocalDate releaseDate = m.getReleaseDate();
        assertNotNull(releaseDate);
        assertEquals(d, releaseDate);
        assertFalse(m.isRented());
    }

    /**
     * Test method for
     * {@link ch.fhnw.edu.rental.model.Movie#Movie(java.lang.String, java.util.Date, ch.fhnw.edu.rental.model.PriceCategory)}
     * .
     */
    @Test
    public void testExceptionMovieStringDatePriceCategory() {
        Movie m = null;
        try {
            m = new Movie(null, LocalDate.now(), RegularPriceCategory.getInstance());
        } catch (NullPointerException e) {
            assertEquals(MESSAGE, e.getMessage());
        }
        try {
            m = new Movie("A", null, RegularPriceCategory.getInstance());
        } catch (NullPointerException e) {
            assertEquals(MESSAGE, e.getMessage());
        }
        try {
            m = new Movie("A", LocalDate.now(), null);
        } catch (NullPointerException e) {
            assertEquals(MESSAGE, e.getMessage());
        }
        assertNull(m);
    }

    /**
     * Test method for {@link ch.fhnw.edu.rental.model.Movie#equals(java.lang.Object)}.
     */
    @Test
    @Ignore
    public void testEqualsObject() {
    }

    @Test(expected = IllegalStateException.class)
    public void testSetTitle() {
        Movie m = new Movie();
        m.setTitle("Hallo");
        assertEquals("Hallo", m.getTitle());
        m.setTitle(null);
    }

    @Test(expected = IllegalStateException.class)
    public void testSetReleaseDate() {
        Movie m = new Movie();
        LocalDate d = LocalDate.now();
        m.setReleaseDate(d);
        assertEquals(d, m.getReleaseDate());
        m.setReleaseDate(null);
    }
}
