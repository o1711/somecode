/**
 * Jan 24, 2014
 */
package com.graphscape.commons.probject.provider;

import com.graphscape.commons.debug.support.ProfileAwareSupport;
import com.graphscape.commons.file.FileManagerI;
import com.graphscape.commons.lang.CallbackI;
import com.graphscape.commons.lang.GsException;
import com.graphscape.commons.probject.ProbjectStorageFactoryI;
import com.graphscape.commons.probject.ProbjectStorageI;
import com.graphscape.commons.probject.provider.comparators.PropertyKeyComparator;
import com.graphscape.commons.probject.provider.comparators.StringComparator;
import com.graphscape.commons.probject.provider.serializers.PositionSerializer;
import com.graphscape.commons.record.IndexI;
import com.graphscape.commons.record.Position;
import com.graphscape.commons.record.TxStorageFactoryI;
import com.graphscape.commons.record.provider.index.rbtree.RedBlackTreeIndex;
import com.graphscape.commons.record.provider.serializer.StringSerializer;
import com.graphscape.commons.record.provider.storage.DefaultTxStorageManager;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class DefaultProbjectStorageFactory extends ProfileAwareSupport
		implements ProbjectStorageFactoryI {

	protected TxStorageFactoryI storageFactory;

	protected IndexI<String, Position> index;

	protected IndexI<PropertyKey, Position> propertyIndex;

	protected PropertyTypeFactory typeFactory;

	public DefaultProbjectStorageFactory(FileManagerI fm) {
		this.typeFactory = new PropertyTypeFactory();

		this.storageFactory = new DefaultTxStorageManager(fm);

		this.propertyIndex = RedBlackTreeIndex.valueOf("propertis",
				new PropertyKeyComparator(), new PropertyKeySerializer(),
				new PositionSerializer(), fm);
		this.index = RedBlackTreeIndex.valueOf("probjects",
				new StringComparator(), new StringSerializer(),
				new PositionSerializer(), fm);
		this.applyProfile(this.index);
	}

	@Override
	public void open() {
		this.storageFactory.open();
		this.index.open();
		this.propertyIndex.open();
	}

	@Override
	public void close() {
		this.propertyIndex.close();
		this.index.close();
		this.storageFactory.close();
	}

	@Override
	public ProbjectStorageI openTransaction() {

		DefaultProbjectStorage rt = new DefaultProbjectStorage(this);
		this.applyProfile(rt);
		rt.open();
		return rt;

	}

	@Override
	public <T> T executeInTransaction(CallbackI<ProbjectStorageI, T> callback) {
		ProbjectStorageI ps = this.openTransaction();
		try {
			T rt = callback.execute(ps);
			ps.commit();
			return rt;
		} catch (Throwable e) {
			ps.rollback();
			throw GsException.toRuntimeException(e);//
		}

	}

}
