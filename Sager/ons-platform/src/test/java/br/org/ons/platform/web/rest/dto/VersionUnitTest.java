package br.org.ons.platform.web.rest.dto;

import static org.assertj.core.api.Assertions.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * Unit test for Version
 */
@RunWith(JUnit4.class)
public class VersionUnitTest {

	private static final Long DEFAULT_MAJOR = 0L;
	private static final Long DEFAULT_MINOR = 1L;
	private static final Long UPDATED_MAJOR = 2L;
	private static final Long UPDATED_MINOR = 3L;
	private static final String DEFAULT_STRING = "0.1";
	private static final String UPDATED_STRING = "2.3";
	
	@Test(expected=AssertionError.class)
	public void testNullMajor() {
		new Version(null, DEFAULT_MINOR);
	}
	
	@Test(expected=AssertionError.class)
	public void testNullMinor() {
		new Version(DEFAULT_MAJOR, null);
	}
	
	@Test(expected=AssertionError.class)
	public void testNullMajorMinor() {
		new Version(null, null);
	}

	@Test
	public void testToString() {
		Version version = new Version(DEFAULT_MAJOR, DEFAULT_MINOR);
		assertThat(version.toString()).isEqualTo(DEFAULT_STRING);
	}
		
	@Test
	public void testSetMajorMinor() {
		Version version = new Version(DEFAULT_MAJOR, DEFAULT_MINOR);
		version.setMajor(UPDATED_MAJOR);
		version.setMinor(UPDATED_MINOR);
		assertThat(version.toString()).isEqualTo(UPDATED_STRING);
	}
	
	@Test(expected=AssertionError.class)
	public void testSetMajorNull() {
		Version version = new Version(DEFAULT_MAJOR, DEFAULT_MINOR);
		version.setMajor(null);
	}
	
	@Test(expected=AssertionError.class)
	public void testSetMinorNull() {
		Version version = new Version(DEFAULT_MAJOR, DEFAULT_MINOR);
		version.setMinor(null);
	}

	@Test
	public void testString() {
		Version version = new Version(DEFAULT_STRING);
		assertThat(version.getMajor()).isEqualTo(DEFAULT_MAJOR);
		assertThat(version.getMinor()).isEqualTo(DEFAULT_MINOR);
	}

	@Test
	public void testString2() {
		Version version = new Version("1");
		assertThat(version.getMajor()).isEqualTo(1);
		assertThat(version.getMinor()).isEqualTo(0);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testInvalidString() {
		new Version("");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testInvalidString2() {
		new Version("0.0.0");
	}
	
	@Test
	public void testEquals() {
		Version version = new Version(DEFAULT_STRING);
		Version otherVersion = new Version(DEFAULT_MAJOR, DEFAULT_MINOR);
		assertThat(version).isEqualTo(version);
		assertThat(version).isNotEqualTo(null);
		assertThat(version).isNotEqualTo(DEFAULT_STRING);
		assertThat(version).isEqualTo(otherVersion);
		assertThat(version).isNotEqualTo(new Version(DEFAULT_MAJOR, UPDATED_MINOR));
		assertThat(version).isNotEqualTo(new Version(UPDATED_MAJOR, DEFAULT_MINOR));
		assertThat(version.hashCode()).isEqualTo(otherVersion.hashCode());
	}
	
	@Test
	public void testCompare() {
		Version version = new Version(DEFAULT_MAJOR, DEFAULT_MINOR);
		Version otherVersion = new Version(UPDATED_MAJOR, UPDATED_MINOR);
		assertThat(version).isLessThan(otherVersion);
		assertThat(version).isLessThan(new Version(DEFAULT_MAJOR, UPDATED_MINOR));
		assertThat(version).isLessThanOrEqualTo(version);
	}
}
