/**
 * Jan 22, 2014
 */
package com.graphscape.largegraph.core.provider.default_;

import java.util.UUID;

import com.graphscape.commons.file.FileManagerI;
import com.graphscape.commons.file.provider.FileManagers;
import com.graphscape.commons.lang.CallbackI;
import com.graphscape.commons.lang.GsException;
import com.graphscape.commons.lang.PropertiesI;
import com.graphscape.commons.lang.provider.default_.DefaultProperties;
import com.graphscape.commons.probject.ProbjectI;
import com.graphscape.commons.probject.ProbjectStorageFactoryI;
import com.graphscape.commons.probject.ProbjectStorageI;
import com.graphscape.commons.probject.provider.DefaultProbjectStorageFactory;
import com.graphscape.largegraph.core.EdgeI;
import com.graphscape.largegraph.core.GraphI;
import com.graphscape.largegraph.core.Label;
import com.graphscape.largegraph.core.VertexI;
import com.graphscape.largegraph.core.provider.support.LargeGraphSupport;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class DefaultLargeGraph extends LargeGraphSupport {

	protected ProbjectStorageFactoryI psf;

	public DefaultLargeGraph() {
		FileManagerI fm = FileManagers.tempFileManager();
		fm.open();
		psf = new DefaultProbjectStorageFactory(fm);
		psf.open();
	}

	@Override
	public GraphI getRootGraph() {
		return null;
	}

	@Override
	public GraphI getGraph(final String id) {
		return psf
				.executeInTransaction(new CallbackI<ProbjectStorageI, GraphI>() {

					@Override
					public GraphI execute(ProbjectStorageI t) {
						ProbjectI data = t.get(id);

						return new GraphElement(data);
					}
				});

	}

	private String nextId() {
		return UUID.randomUUID().toString();// TODO cache id
	}

	@Override
	public GraphI addGraph(final PropertiesI<Object> pts) {
		return psf
				.executeInTransaction(new CallbackI<ProbjectStorageI, GraphI>() {

					@Override
					public GraphI execute(ProbjectStorageI t) {

						String id = nextId();
						ProbjectI data = t.add(id, pts);
						GraphI rt = new GraphElement(data);
						return rt;

					}
				});
	}

	@Override
	public void deleteGraph(final String gId) {
		psf.executeInTransaction(new CallbackI<ProbjectStorageI, Object>() {

			@Override
			public Object execute(ProbjectStorageI t) {
				t.remove(gId);
				return null;
			}
		});
	}

	@Override
	public VertexI getVertex(final String id, final boolean force) {
		return psf
				.executeInTransaction(new CallbackI<ProbjectStorageI, VertexI>() {

					@Override
					public VertexI execute(ProbjectStorageI t) {
						ProbjectI data = t.get(id);
						if (data == null) {
							if (force) {
								throw new GsException("no vertex with id:" + id);
							}
							return null;
						}
						VertexI rt = new VertexElement(data);
						return rt;

					}
				});

	}

	@Override
	public VertexI addVertex(final PropertiesI<Object> pts) {
		return psf
				.executeInTransaction(new CallbackI<ProbjectStorageI, VertexI>() {

					@Override
					public VertexI execute(ProbjectStorageI t) {
						String id = nextId();
						ProbjectI data = t.add(id, pts);
						VertexI rt = new VertexElement(data);
						return rt;

					}
				});

	}

	@Override
	public EdgeI getEdge(String id) {
		return null;
	}

	@Override
	public EdgeI getEdge(String id, boolean force) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EdgeI addEdge(final Label label, final String fromId,
			final String toId) {
		return psf
				.executeInTransaction(new CallbackI<ProbjectStorageI, EdgeI>() {

					@Override
					public EdgeI execute(ProbjectStorageI t) {
						String id = nextId();
						PropertiesI<Object> pts = new DefaultProperties<Object>();
						pts.setProperty("label", label.getName());
						pts.setProperty("fromId", fromId);
						pts.setProperty("toId", toId);

						ProbjectI data = t.add(id, pts);
						EdgeI rt = new EdgeElement(data);
						return rt;

					}
				});

	}

}
