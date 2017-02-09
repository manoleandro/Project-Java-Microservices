package br.org.ons.platform.domain.util;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.type.SerializationException;
import org.hibernate.usertype.UserType;

/**
 * Conversor utilizado pelo Hibernate para persistir classes de datas
 */
public class ZonedDateTimeConverter implements UserType {

	@Override
	public Object assemble(Serializable cached, Object owner) {
		return deepCopy(cached);
	}

	@Override
	public Object deepCopy(Object value) {
		return value;
	}

	@Override
	public Serializable disassemble(Object value) throws HibernateException {
		final Serializable result;
		if (value == null) {
			result = null;
		} else {
			final Object deepCopy = deepCopy(value);
			if (!(deepCopy instanceof Serializable)) {
				throw new SerializationException(String.format("deepCopy of %s is not serializable", value), null);
			}
			result = (Serializable) deepCopy;
		}
		return result;
	}

	@Override
	public boolean equals(Object x, Object y) throws HibernateException {
		if ((x == null) && (y == null)) {
			return true;
		}
		if ((x == null) || (y == null)) {
			return false;
		}
		if (x == y) {
			return true;
		}
		return x.equals(y);
	}

	@Override
	public int hashCode(Object value) throws HibernateException {
		assert value != null;
		return value.hashCode();
	}

	@Override
	public boolean isMutable() {
		return false;
	}

	@Override
	public Object replace(Object original, Object target, Object owner) {
		return deepCopy(original);
	}

	@Override
	public Object nullSafeGet(ResultSet rs, String[] names, SessionImplementor session, Object owner)
			throws HibernateException, SQLException {
		Timestamp value = rs.getTimestamp(names[0]);
		if (value == null) {
			return null;
		}
		return ZonedDateTime.ofInstant(value.toInstant(), ZoneOffset.UTC);
	}

	@Override
	public void nullSafeSet(PreparedStatement stmt, Object value, int index, SessionImplementor session)
			throws HibernateException, SQLException {
		if (value == null) {
			stmt.setNull(index, Types.TIMESTAMP);
			return;
		}
		stmt.setTimestamp(index, Timestamp.from(((ZonedDateTime) value).toInstant()));
	}

	@Override
	public Class<ZonedDateTime> returnedClass() {
		return ZonedDateTime.class;
	}

	@Override
	public int[] sqlTypes() {
		return new int[] { Types.TIMESTAMP };
	}
}