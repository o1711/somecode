/**
 * 2013 ����11:02:39
 */
package com.graphscape.largegraph.test.apps.large_graph_server_app;

import java.util.List;

import junit.framework.Assert;

import com.graphscape.commons.container.ContainerI;
import com.graphscape.commons.lang.ErrorInfos;
import com.graphscape.commons.lang.PropertiesI;
import com.graphscape.commons.lang.provider.default_.DefaultProperties;
import com.graphscape.commons.message.MessageI;
import com.graphscape.commons.modulization.ApplicationI;
import com.graphscape.commons.modulization.ModuleI;
import com.graphscape.largegraph.client.ClientBuilder;
import com.graphscape.largegraph.client.ClientI;
import com.graphscape.largegraph.client.provider.default_.ClientLargeGraph;
import com.graphscape.largegraph.core.EdgeI;
import com.graphscape.largegraph.core.GraphI;
import com.graphscape.largegraph.core.Label;
import com.graphscape.largegraph.core.LargeGraphI;
import com.graphscape.largegraph.core.VertexI;
import com.graphscape.largegraph.core.event.VertexEvent;
import com.graphscape.largegraph.test.support.TestCaseEnvironment;
import com.graphscape.largegraph.test.support.TestCaseSupport;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class LargeGraphServerAppTest extends TestCaseSupport {
	public static final Label MYEDGE = Label.valueOf("my-edge");

	public LargeGraphServerAppTest() {

	}

	public void testStartUp_SimpleOperations_and_Shutdown() throws Exception {
		ApplicationI ls = this.buildApplication(this.serverEnv, LargeGraphServerAppTest.class.getName()
				+ ".application");
		ls.start();

		ModuleI rm = ls.getRootModule();//
		ContainerI c = rm.getContainer();
		List<ContainerI> cL = c.getChildContainerList();

		Assert.assertEquals("child container:" + cL, 3, cL.size());
		ContainerI coreC = c.getChildContainer(
				com.graphscape.largegraph.modules.large_graph_core.Module.class.getName(), false);
		Assert.assertNotNull("no core container found ", coreC);

		LargeGraphI lg = coreC.find(LargeGraphI.class);
		PropertiesI<Object> pts = new DefaultProperties<Object>();
		
		Assert.assertNotNull("no large path api found in container of core module", lg);

		VertexI v0 = lg.addVertex();
		v0.setEventHandler(MyMessageHandler.class.getName());

		VertexI v1 = lg.addVertex();

		v1.setEventHandler(MyMessageHandler.class.getName());

		EdgeI e0 = lg.addEdge(MYEDGE, v0.getId(), v1.getId());
		{
			ClientBuilder builder = new ClientBuilder();
			ClientI client = builder.uri(TestCaseEnvironment.URI).build();//
			client.connect("root:root").get(timeout);
			
			lg = new ClientLargeGraph(client);

		}
		{

			MessageI msg = new VertexEvent(v0.getId());
			MessageI res = lg.putMessage(msg).get(timeout);
			Assert.assertNotNull("response is null", res);
			ErrorInfos eis = res.getErrorInfos();
			Assert.assertFalse("" + eis, eis.hasError());
			// check the handle result.
			List<String> leafIdL = (List<String>) res.getPayload("leafIdList", true);

			Assert.assertNotNull(leafIdL);
			Assert.assertEquals(1, leafIdL.size());
			Assert.assertEquals(v1.getId(), leafIdL.get(0));

		}

		ls.shutdown();
	}
}
