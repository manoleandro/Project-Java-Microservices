package br.org.ons.sager.parametrizacao.config.wxs;

import java.io.Serializable;
import java.util.Map.Entry;

import org.springframework.data.keyvalue.core.AbstractKeyValueAdapter;
import org.springframework.data.keyvalue.core.ForwardingCloseableIterator;
import org.springframework.data.util.CloseableIterator;
import org.springframework.util.Assert;

import com.ibm.websphere.objectgrid.ObjectGrid;
import com.ibm.websphere.objectgrid.ObjectGridException;
import com.ibm.websphere.objectgrid.ObjectMap;
import com.ibm.websphere.objectgrid.Session;

import br.org.ons.platform.common.exception.OnsRuntimeException;

/**
 * 
 *
 */
public class WXSKeyValueAdapter extends AbstractKeyValueAdapter {

	private Session session;
	
	public WXSKeyValueAdapter(ObjectGrid objectGrid) {
		try {
			this.session = objectGrid.getSession();
		} catch (ObjectGridException e) {
			throw new OnsRuntimeException(e);
		}
	}

	@Override
	public Object put(Serializable id, Object item, Serializable keyspace) {
		Assert.notNull(id);
		Assert.notNull(keyspace);
		try {
			getMap(keyspace).insert(id, item);
		} catch (ObjectGridException e) {
			throw new OnsRuntimeException(e);
		}
		return item;
	}

	@Override
	public boolean contains(Serializable id, Serializable keyspace) {
		Assert.notNull(id);
		Assert.notNull(keyspace);
		ObjectMap map = getMap(keyspace);
		try {
			return map != null ? map.containsKey(id) : false;
		} catch (ObjectGridException e) {
			throw new OnsRuntimeException(e);
		}
	}

	@Override
	public Object get(Serializable id, Serializable keyspace) {
		Assert.notNull(id);
		Assert.notNull(keyspace);
		ObjectMap map = getMap(keyspace);
		try {
			return map != null ? map.get(id) : null;
		} catch (ObjectGridException e) {
			throw new OnsRuntimeException(e);
		}
	}

	@Override
	public Object delete(Serializable id, Serializable keyspace) {
		Assert.notNull(id);
		Assert.notNull(keyspace);
		ObjectMap map = getMap(keyspace);
		try {
			return map != null ? map.remove(id) : null;
		} catch (ObjectGridException e) {
			throw new OnsRuntimeException(e);
		}
	}

	@Override
	public Iterable<?> getAllOf(Serializable keyspace) {
		Assert.notNull(keyspace);
		ObjectMap map = getMap(keyspace);
		return map != null ? map.getJavaMap().values() : null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public CloseableIterator<Entry<Serializable, Object>> entries(Serializable keyspace) {
		Assert.notNull(keyspace);
		ObjectMap map = getMap(keyspace);
		return map != null
				? new ForwardingCloseableIterator<>(map.getJavaMap().entrySet().iterator())
				: null;
	}

	@Override
	public void deleteAllOf(Serializable keyspace) {
		Assert.notNull(keyspace);
		ObjectMap map = getMap(keyspace);
		try {
			if (map != null)
				map.clear();
		} catch (ObjectGridException e) {
			throw new OnsRuntimeException(e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void clear() {
		session.getObjectGrid().getListOfMapNames().forEach(keyspace -> deleteAllOf((Serializable) keyspace));
	}

	@Override
	public long count(Serializable keyspace) {
		Assert.notNull(keyspace);
		ObjectMap map = getMap(keyspace);
		return map != null ? map.getJavaMap().size() : 0;
	}

	@Override
	public void destroy() throws Exception {
		session.close();
	}

	/**
	 * @param keyspace not null
	 * @return ObjectMap
	 */
	private ObjectMap getMap(Serializable keyspace) {
		try {
			return session.getMap(keyspace.toString());
		} catch (ObjectGridException e) {
			throw new OnsRuntimeException(e);
		}
	}
}
