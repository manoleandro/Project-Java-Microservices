package br.org.ons.platform.domain.util;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import org.hibernate.type.SerializationException;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

public class ZonedDateTimeConverterUnitTest {

	private static final Instant INSTANT = Instant.ofEpochMilli(0L);
	private static final ZonedDateTime ZDT = ZonedDateTime.ofInstant(INSTANT, ZoneOffset.UTC);

	private ZonedDateTimeConverter converter = new ZonedDateTimeConverter();

	@Test
	public void assemble() {
		assertThat(converter.assemble(ZDT, null)).isEqualTo(ZDT);
	}

	@Test
	public void deepCopy() {
		assertThat(converter.deepCopy(ZDT)).isEqualTo(ZDT);
	}

	@Test
	public void disassemble() {
		assertThat(converter.disassemble(ZDT)).isEqualTo(ZDT);
	}

	@Test
	public void disassembleNull() {
		assertThat(converter.disassemble(null)).isEqualTo(null);
	}

	@Test(expected = SerializationException.class)
	public void disassembleNotSerializable() {
		converter.disassemble(new Object());
	}

	@Test
	public void testEquals() {
		assertThat(converter.equals(ZDT, ZonedDateTime.ofInstant(INSTANT, ZoneOffset.UTC))).isTrue();
		assertThat(converter.equals(ZDT, ZDT)).isTrue();
		assertThat(converter.equals(ZDT, null)).isFalse();
		assertThat(converter.equals(null, ZDT)).isFalse();
		assertThat(converter.equals(null, null)).isTrue();
	}

	@Test
	public void testHashCode() {
		assertThat(converter.hashCode(ZDT)).isEqualTo(ZDT.hashCode());
	}
	
	@Test(expected=AssertionError.class)
	public void testHashCodeNull() {
		converter.hashCode(null);
	}

	@Test
	public void isMutable() {
		assertThat(converter.isMutable()).isFalse();
	}

	@Test
	public void replace() {
		assertThat(converter.replace(ZDT, null, null)).isEqualTo(ZDT);
	}

	@Test
	public void nullSafeGet() throws Exception {
		ResultSet rs = mock(ResultSet.class);
		when(rs.getTimestamp(anyString())).thenReturn(Timestamp.from(INSTANT));

		assertThat(converter.nullSafeGet(rs, new String[] { "name" }, null, null)).isEqualTo(ZDT);
	}

	@Test
	public void nullSafeGetNull() throws Exception {
		ResultSet rs = mock(ResultSet.class);
		when(rs.getTimestamp(anyString())).thenReturn(null);

		assertThat(converter.nullSafeGet(rs, new String[] { "name" }, null, null)).isNull();
	}

	@Test
	public void nullSafeSet() throws Exception {
		PreparedStatement stmt = mock(PreparedStatement.class);

		converter.nullSafeSet(stmt, ZDT, 1, null);

		ArgumentCaptor<Integer> indexCaptor = ArgumentCaptor.forClass(Integer.class);
		ArgumentCaptor<Timestamp> timestampCaptor = ArgumentCaptor.forClass(Timestamp.class);
		verify(stmt, times(1)).setTimestamp(indexCaptor.capture(), timestampCaptor.capture());
		assertThat(indexCaptor.getValue()).isEqualTo(1);
		assertThat(timestampCaptor.getValue()).isEqualTo(Timestamp.from(INSTANT));
	}
	
	@Test
	public void nullSafeSetNull() throws Exception {
		PreparedStatement stmt = mock(PreparedStatement.class);
		
		converter.nullSafeSet(stmt, null, 1, null);
		
		ArgumentCaptor<Integer> indexCaptor = ArgumentCaptor.forClass(Integer.class);
		ArgumentCaptor<Integer> typeCaptor = ArgumentCaptor.forClass(Integer.class);
		verify(stmt, times(1)).setNull(indexCaptor.capture(), typeCaptor.capture());
		assertThat(indexCaptor.getValue()).isEqualTo(1);
		assertThat(typeCaptor.getValue()).isEqualTo(Types.TIMESTAMP);
	}

	@Test
	public void returnedClass() {
		assertThat(converter.returnedClass()).isEqualTo(ZDT.getClass());
	}
	
	@Test
	public void sqlTypes() {
		assertThat(converter.sqlTypes()).containsOnly(Types.TIMESTAMP);
	}
}
