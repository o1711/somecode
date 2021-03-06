package org.cellang.core.entity;

import java.util.List;

import org.cellang.commons.jdbc.CreateTableOperation;

public class NewDBUpgrader extends DBUpgrader {

	public NewDBUpgrader(DataVersion dv) {
		super(DataVersion.V_UNKNOW, DataVersion.V_0_0_3);
	}

	public static void createTableAndIndex(EntitySessionFactory esf, EntitySession es,
			Class<? extends EntityObject> class1) {
		EntityConfig ec = esf.getEntityConfigFactory().getEntityConfig(class1);
		List<IndexConfig> icL = esf.getEntityConfigFactory().getIndexConfigList(ec.getEntityClass());
		createTableAndIndex(ec, icL, es);
	}
	
	public static void createIndex(EntitySessionFactory esf, EntitySession es,
			Class<? extends EntityObject> class1) {
		EntityConfig ec = esf.getEntityConfigFactory().getEntityConfig(class1);		
		createIndex(esf, ec, es);
	}

	public static void createTableAndIndex(EntityConfig ec, List<IndexConfig> icL, EntitySession es) {
		String viewSql = ec.getCreateViewSql();
		// entity is table
		if (viewSql == null) {

			CreateTableOperation cto = new CreateTableOperation(ec.getTableName());

			ec.fillCreate(cto);
			es.execute(cto);
		} else {
			// entity is view.
			es.getDataAccessTemplate().executeUpdate(es.getConnection(), viewSql);
		}
		createIndex(icL, es);
	}

	public static void createIndex(EntitySessionFactory esf, EntityConfig ec, EntitySession es) {
		List<IndexConfig> icL = esf.getEntityConfigFactory().getIndexConfigList(ec.getEntityClass());
		createIndex(icL, es);
	}

	public static void createIndex(List<IndexConfig> icL, EntitySession es) {
		for (IndexConfig ic : icL) {
			createIndex(ic, es);
		}
	}

	public static void createIndex(IndexConfig ic, EntitySession es) {
		EntityConfig ec = es.getEntityConfigFactory().getEntityConfig(ic.getEntityCls());
		String tableName = ec.getTableName();
		String sql = "create index " + ic.getIndexName() + " on " + tableName + "(";
		String[] fs = ic.getFieldArray();
		for (int i = 0; i < fs.length; i++) {
			String f = fs[i];
			sql += f;
			if (i < fs.length - 1) {
				sql += ",";
			}
		}
		sql += ")";
		es.getDataAccessTemplate().executeUpdate(es.getConnection(), sql);
	}

	@Override
	public void doUpgrade(EntitySessionFactory esf, EntitySession es) {
		//
		// create table
		for (EntityConfig ec : esf.getEntityConfigFactory().getEntityConfigList()) {
			List<IndexConfig> icL = esf.getEntityConfigFactory().getIndexConfigList(ec.getEntityClass());

			createTableAndIndex(ec, icL, es);
		}
		// add dataVersion
		PropertyEntity pe = new PropertyEntity();
		String category = "core";
		String key = "data-version";
		String value = this.getTargetVersion().toString();
		pe.setId(category + "." + key);
		pe.setCategory(category);
		pe.setKey(key);
		pe.setValue(value);
		es.save(pe);//
	}

}
