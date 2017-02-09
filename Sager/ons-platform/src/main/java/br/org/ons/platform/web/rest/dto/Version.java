package br.org.ons.platform.web.rest.dto;

import java.util.NoSuchElementException;
import java.util.StringTokenizer;

/**
 * Representa a versão de um aggregate, na forma "major.minor", sendo que a
 * versão major indica a timeline e a versão minor indica o número de eventos
 * aplicados ao estado do aggregate
 */
public class Version implements Comparable<Version> {

	private static final String SEPARATOR = ".";
	
	private Long major;
	private Long minor;

	public Version(Long major, Long minor) {
		assert major != null;
		assert minor != null;
		this.major = major;
		this.minor = minor;
	}

	public Version(String version) {
		Long majorVersion = 0L;
		Long minorVersion = 0L;
		try {
			StringTokenizer st = new StringTokenizer(version, SEPARATOR, true);
			majorVersion = Long.parseLong(st.nextToken());
			if (st.hasMoreTokens()) {
				st.nextToken();
				minorVersion = Long.parseLong(st.nextToken());
				if (st.hasMoreTokens()) {
					throw new IllegalArgumentException("Invalid version format");
				}
			}
		} catch (NoSuchElementException e) {
			throw new IllegalArgumentException("Invalid version format", e);
		}
		this.major = majorVersion;
		this.minor = minorVersion;
	}

	public Long getMajor() {
		return major;
	}

	public void setMajor(Long major) {
		assert major != null;
		this.major = major;
	}

	public Long getMinor() {
		return minor;
	}

	public void setMinor(Long minor) {
		assert minor != null;
		this.minor = minor;
	}

	@Override
	public String toString() {
		return major + SEPARATOR + minor;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + major.hashCode();
		result = prime * result + minor.hashCode();
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Version other = (Version) obj;
		if (!major.equals(other.major)) {
			return false;
		}
		if (!minor.equals(other.minor)) {
			return false;
		}
		return true;
	}

	@Override
	public int compareTo(Version o) {
		if (o == this) {
			return 0;
		}
		Long result = major - o.major;
		if (result != 0) {
			return result.intValue();
		}

		result = minor - o.minor;
		return result.intValue();
	}
}
