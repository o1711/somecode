/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 27, 2012
 */
package com.fs.dataservice.core.impl.elastic.operations;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHitField;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fs.commons.api.value.ErrorInfo;
import com.fs.commons.api.value.ErrorInfos;
import com.fs.dataservice.api.core.DataServiceI;
import com.fs.dataservice.api.core.NodeI;
import com.fs.dataservice.api.core.NodeType;
import com.fs.dataservice.api.core.operations.NodeSearchOperationI;
import com.fs.dataservice.api.core.result.NodeSearchResultI;
import com.fs.dataservice.api.core.wrapper.NodeWrapper;
import com.fs.dataservice.core.impl.elastic.ElasticClientI;
import com.fs.dataservice.core.impl.elastic.SearchHitNode;
import com.fs.dataservice.core.impl.elastic.support.NodeQueryOperationSupport;

/**
 * @author wu
 * 
 */
public class NodeSearchOperationE<W extends NodeWrapper> extends
		NodeQueryOperationSupport<NodeSearchOperationI<W>, W, NodeSearchResultI<W>> implements
		NodeSearchOperationI<W> {

	public static class Sort {
		private String field;
		private SortOrder order;
	}

	private static Logger LOG = LoggerFactory.getLogger(NodeSearchOperationE.class);

	protected List<Sort> sortList = new ArrayList<Sort>();

	private ElasticClientI elastic;


	/**
	 * @param ds
	 */
	public NodeSearchOperationE(DataServiceI ds) {
		super(ds, new NodeSearchResult<W>(ds));
		this.elastic = (ElasticClientI) ds;

	}

	/*
	 * Oct 27, 2012
	 */
	@Override
	protected void executeInternal(NodeSearchResultI<W> rst) throws Exception {
		Client client = elastic.getClient();
		BoolQueryBuilder qb = this.buildQuery(rst);
		if (rst.hasError()) {
			return;
		}
		String idx = this.elastic.getIndex();
		// end of matches
		SearchRequestBuilder srb = client.prepareSearch(idx)//
				.setSearchType(SearchType.DFS_QUERY_THEN_FETCH)//
				.setQuery(qb)//
				.setFrom(this.getFrom())//
				.setSize(this.getMaxSize())//
				.setExplain(this.explain)//

		;
		for (Sort s : this.sortList) {
			srb.addSort(s.field, s.order);
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("query l:" + srb);
		}
		SearchResponse response = srb.execute()//
				.actionGet();
		SearchHits shs = response.getHits();
		List<W> nL = new ArrayList<W>();

		NodeType ntype = this.getNodeType(true);// type is must.
		for (SearchHit sh : shs.getHits()) {
			Map<String, SearchHitField> shMap = sh.getFields();

			SearchHitNode shn = new SearchHitNode(ntype, this.dataService, sh);
			W w = (W) this.nodeConfig.newWraper();
			w.forOp(this.dataService);// NOTE
			w.attachTo(shn);
			nL.add(w);
		}
		rst.set(nL);//
	}

	private void validateKeyIsConfigedInType(String field, ErrorInfos errorInfo) {

		if (null == this.nodeConfig.getField(field, false)) {
			errorInfo.add(new ErrorInfo("no field:" + field + ",in type:" + this.nodeConfig.getNodeType()));
		}
	}

	/**
	 * Nov 3, 2012
	 */

	private void validateKeyIsConfigedInType(List<String> keyList, ErrorInfos errorInfo) {

		for (String k : keyList) {

			this.validateKeyIsConfigedInType(k, errorInfo);
		}
	}

	/*
	 * Oct 28, 2012
	 */
	@Override
	public NodeSearchOperationI<W> first(int from) {
		//
		this.parameter("first", from);
		return this;

	}

	/*
	 * Oct 28, 2012
	 */
	@Override
	public NodeSearchOperationI<W> maxSize(int maxs) {
		this.parameter("maxSize", maxs);
		return this;

	}

	/*
	 * Oct 28, 2012
	 */
	@Override
	public int getFrom() {
		//
		Integer rt = (Integer) this.parameters.getProperty("first");
		return rt == null ? 0 : rt;
	}

	/*
	 * Oct 28, 2012
	 */
	@Override
	public int getMaxSize() {
		Integer rt = (Integer) this.parameters.getProperty("maxSize");
		return rt == null ? 100 : rt;
	}

	/*
	 * Nov 14, 2012
	 */
	@Override
	public NodeSearchOperationI<W> sort(String key) {
		//
		return this.sort(key, false);
	}

	/*
	 * Nov 14, 2012
	 */
	@Override
	public NodeSearchOperationI<W> sort(String key, boolean desc) {
		//
		Sort sort = new Sort();
		sort.field = key;
		sort.order = desc ? SortOrder.DESC : SortOrder.ASC;
		this.sortList.add(sort);
		return this;
	}

	/*
	 * Nov 14, 2012
	 */
	@Override
	public NodeSearchOperationI<W> sortTimestamp(boolean desc) {
		//
		return this.sort(NodeI.PK_TIMESTAMP, desc);

	}

	/*
	 * Nov 28, 2012
	 */
	@Override
	public NodeSearchOperationI<W> singleNewest(boolean nf) {
		//
		this.first(0);
		this.maxSize(1);
		this.sortTimestamp(true);//
		return this;
	}

}
