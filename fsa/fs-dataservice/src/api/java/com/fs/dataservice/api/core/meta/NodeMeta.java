/**
 * All right is from Author of the file,to be explained in comming days.
 * Nov 2, 2012
 */
package com.fs.dataservice.api.core.meta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.fs.commons.api.lang.ClassUtil;
import com.fs.commons.api.lang.FsException;
import com.fs.dataservice.api.core.NodeI;
import com.fs.dataservice.api.core.NodeType;
import com.fs.dataservice.api.core.wrapper.NodeWrapper;

/**
 * @author wu
 * 
 */
public class NodeMeta {

	private Map<String, FieldMeta> fieldConfigMap = new HashMap<String, FieldMeta>();

	private Class<? extends NodeWrapper> wrapperClass;

	private NodeType nodeType;

	public NodeMeta(NodeType ntype, Class<? extends NodeWrapper> wcls) {
		this.nodeType = ntype;
		this.wrapperClass = wcls;
		this.addField(NodeI.PK_UNIQUE_ID).manditory(false);
		this.addField(NodeI.PK_ID).manditory(false);
		this.addField(NodeI.PK_TIMESTAMP).type(FieldType.DATE).manditory(false);
		this.addField(NodeI.PK_EXTEND1).manditory(false);
		this.addField(NodeI.PK_EXTEND2).manditory(false);
		this.addField(NodeI.PK_EXTEND3).manditory(false);
		this.addField(NodeI.PK_EXTEND4).manditory(false);
		this.addField(NodeI.PK_EXTEND5).manditory(false);
		this.addField(NodeI.PK_EXTEND6).manditory(false);
		this.addField(NodeI.PK_EXTEND7).manditory(false);
		this.addField(NodeI.PK_EXTEND8).manditory(false);
		this.addField(NodeI.PK_EXTEND9).manditory(false);
		
	}

	public NodeMeta field(String name) {
		return field(name, true);
	}

	public NodeMeta field(String name, AnalyzerType atype) {
		this.addField(name).setAnalyzer(atype);
		return this;
	}

	public NodeMeta field(String name, FieldType ft) {
		this.addField(name, ft);
		return this;

	}

	public NodeMeta field(String name, boolean mand) {
		this.addField(name).manditory(mand);
		return this;

	}

	public FieldMeta addField(String fname) {
		return this.addField(fname, null);
	}

	public FieldMeta addField(String fname, FieldType type) {

		FieldMeta rt = new FieldMeta(this, fname, true, type);
		if (null != this.getField(fname, false)) {
			throw new FsException("field already exist:" + fname + " for type:" + this.nodeType);
		}
		this.fieldConfigMap.put(fname, rt);

		return rt;
	}

	/**
	 * @return the nodeType
	 */
	public NodeType getNodeType() {
		return nodeType;
	}

	/**
	 * @return the wrapperClass
	 */
	public Class<? extends NodeWrapper> getWrapperClass() {
		return wrapperClass;
	}

	public Set<String> keySet() {
		return this.fieldConfigMap.keySet();
	}

	public NodeWrapper newWraper() {
		NodeWrapper rt = ClassUtil.newInstance(this.wrapperClass);

		return rt;
	}

	public List<FieldMeta> getFieldList() {
		return new ArrayList<FieldMeta>(this.fieldConfigMap.values());
	}

	/**
	 * Nov 2, 2012
	 */
	public FieldMeta getField(String fname, boolean force) {
		//
		FieldMeta rt = this.fieldConfigMap.get(fname);
		if (force && rt == null) {
			throw new FsException("no field:" + fname + " found for type:" + this.nodeType);
		}
		return rt;
	}
}
