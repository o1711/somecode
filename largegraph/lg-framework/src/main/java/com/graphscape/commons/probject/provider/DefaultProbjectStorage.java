/**
 * Jan 24, 2014
 */
package com.graphscape.commons.probject.provider;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.graphscape.commons.debug.support.ProfileAwareSupport;
import com.graphscape.commons.lang.PropertiesI;
import com.graphscape.commons.probject.ProbjectI;
import com.graphscape.commons.probject.ProbjectStorageI;
import com.graphscape.commons.record.Position;
import com.graphscape.commons.record.RecordI;
import com.graphscape.commons.record.RecordType;
import com.graphscape.commons.record.SerializerI;
import com.graphscape.commons.record.TxListenerI;
import com.graphscape.commons.record.TxStorageI;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class DefaultProbjectStorage extends ProfileAwareSupport implements ProbjectStorageI, TxListenerI {

	protected DefaultProbjectStorageFactory factory;

	protected TxStorageI storage;

	protected IndexOperations<String> indexOperations;

	protected IndexOperations<PropertyKey> propertyIndexOperations;

	protected Map<Position, PropertyKey> propertyKeyMap;

	protected static final String PROFILE_COMMIT = DefaultProbjectStorage.class.getName() + ".commit";
	protected static final String PROFILE_ADD = DefaultProbjectStorage.class.getName() + ".add";
	protected static final String PROFILE_OPEN = DefaultProbjectStorage.class.getName() + ".open";
	protected static final String PROFILE_GET = DefaultProbjectStorage.class.getName() + ".get";
	protected static final String PROFILE_REMOVE = DefaultProbjectStorage.class.getName() + ".remove";
	protected static final String PROFILE_GETPROPERTY = DefaultProbjectStorage.class.getName()
			+ ".getProperty";
	protected static final String PROFILE_SETPROPERTY = DefaultProbjectStorage.class.getName()
			+ ".setProperty";

	public DefaultProbjectStorage(DefaultProbjectStorageFactory sf) {
		this.factory = sf;

	}

	public void open() {
		this.beforeProfile(PROFILE_OPEN);
		try {
			this.storage = this.factory.storageFactory.openTransaction(this);
			this.propertyKeyMap = new HashMap<Position, PropertyKey>();
			this.indexOperations = new IndexOperations<String>(this.factory.index);
			this.propertyIndexOperations = new IndexOperations<PropertyKey>(this.factory.propertyIndex);
			this.applyProfile(this.storage);//
			this.applyProfile(this.indexOperations);//
			this.applyProfile(this.propertyIndexOperations);//
		} finally {
			this.afterProfile();
		}
	}

	@Override
	public void commit() {
		this.beforeProfile(PROFILE_COMMIT);
		try {

			this.storage.commit();
			this.indexOperations.commit();
			this.propertyIndexOperations.commit();
		} finally {
			this.afterProfile();
		}
	}

	@Override
	public void rollback() {
		this.storage.rollback();
	}
	
	@Override
	public void remove(String id){		
		this.indexOperations.remove(id);		
	}

	@Override
	public ProbjectI add(String id) {
		this.beforeProfile(PROFILE_ADD);
		try {

			Position pos = this.storage.addRecord(RecordType.valueOf((byte) 0), new byte[] {});
			this.indexOperations.put(id, pos);
			this.beforeProfile("DefaultProbject");
			ProbjectI rt = new DefaultProbject(id, this);
			this.afterProfile();
			return rt;

		} finally {
			this.afterProfile();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.graphscape.commons.probject.ProbjectStorageI#add(com.graphscape.commons
	 * .lang.PropertiesI)
	 */
	@Override
	public ProbjectI add(String id, PropertiesI<Object> pts) {
		this.beforeProfile(PROFILE_ADD);
		try {

			ProbjectI rt = this.add(id);
			rt.setProperties(pts);//
			return rt;
		} finally {
			this.afterProfile();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.graphscape.commons.probject.ProbjectStorageI#get(java.lang.String)
	 */
	@Override
	public ProbjectI get(String id) {
		this.beforeProfile(PROFILE_GET);
		try {

			Position yes = this.indexOperations.get(id);
			if (yes == null) {
				return null;
			}

			return new DefaultProbject(id, this);
		} finally {
			this.afterProfile();
		}
	}

	protected void setProperty(String id, String key, Object value) {
		this.beforeProfile(PROFILE_SETPROPERTY);
		try {

			SerializerI ser = this.factory.typeFactory.getPropertyType(value.getClass(), true).serializer;

			byte[] content = ser.serialize(value);//
			PropertyType ptype = this.factory.typeFactory.getPropertyType(value.getClass());//

			Position pos = this.storage.addRecord(ptype.recordType, content);
			PropertyKey k = new PropertyKey(id, key);//
			this.propertyIndexOperations.put(k, pos);
		} finally {
			this.afterProfile();
		}

	}

	public Object getProperty(String id, String key) {
		this.beforeProfile(PROFILE_GETPROPERTY);
		try {

			PropertyKey pk = new PropertyKey(id, key);
			Position pos = this.propertyIndexOperations.get(pk);
			if (pos == null) {
				return null;
			}

			RecordI rec = this.storage.getRecord(pos);
			SerializerI ser = this.factory.typeFactory.getPropertyType(rec.getType(), true).serializer;
			Object rt = ser.deserialize(rec.getContent());//

			return rt;
		} finally {
			this.afterProfile();
		}
	}

	@Override
	public void onRecordPositionChanged(Position oldPos, Position newPos) {
		this.propertyIndexOperations.positionUpdate(oldPos, newPos);
	}

}
