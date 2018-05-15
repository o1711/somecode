### Motivation.

This is a POC project that implements a instant comunication system. It is mainly built on top of **Hazelcast**, **Elastic Search** and **GWT**. The objective is study how to buiding a scalable and easy-to-maintain system.

### Sub project list
- fs-building:	Building script.
- fs-commons:		Common interface/tools or framework, all those stuff independt from any special bussiness.
- fs-dataservice:	Interfaces and implements for persistence data storage. Based on Elastic Search.
- fs-gridservice:	Interfaces and implements for in-memory data grid.Based on Hazelcast.
- fs-webserver:	Embeded jetty server in order to support deployment of web app.
- fs-expector:	Application layer logic implemented here. Defining backend in-memory data grid and persistence data schema.
- fs-uicommon:	Common api/tools or framework for building client with GWT.
- fs-uicore:		Core framework implementation based on fs-uicommon.
- fs-uiclient:	Application layer logic implemented here. Implementing front-end UI on top of uicore and uicommon.
- fs-integrated:	This project package every thing and deliver them as a runnable server kit.

### Entry point for reading and understanding the code:

The entry point class is this one:[fs-integrated/src/impl/java/com/fs/integrated/Server.java](fs-integrated/src/impl/java/com/fs/integrated/Server.java).It loads following SPI(service provider interface) providers. Providers are defined in resource file:[fs-integrated/src/impl/dist/conf/fs-spim.properties](fs-integrated/src/impl/dist/conf/fs-spim.properties).

[SPI.0.class=com.fs.commons.impl.CommonsSPI](fs-commons/src/impl/java/com/fs/commons/impl/CommonsSPI.java)

[SPI.1.class=com.fs.webserver.impl.WebServerSPI](fs-webserver/src/impl/java/com/fs/webserver/impl/WebServerSPI.java)

[SPI.2.class=com.fs.webcomet.impl.WebCometSPI](fs-webserver/src/impl/java/com/fs/webcomet/impl/WebCometSPI.java)

[SPI.3.class=com.fs.websocket.impl.WebSocketSPI](fs-webserver/src/impl/java/com/fs/websocket/impl/WebSocketSPI.java)

[SPI.4.class=com.fs.dataservice.core.impl.DataServiceCoreSPI](fs-dataservice/src/impl/java/com/fs/dataservice/core/impl/DataServiceCoreSPI.java)

[SPI.5.class=com.fs.gridservice.core.impl.GsCoreSPI](fs-gridservice/src/impl/java/com/fs/gridservice/core/impl/GsCoreSPI.java)

[SPI.6.class=com.fs.gridservice.commons.impl.GsCommonsSPI](fs-gridservice/src/impl/java/com/fs/gridservice/commons/impl/GsCommonsSPI.java)

[SPI.7.class=com.fs.expector.dataservice.impl.ExpectorDsSPI](fs-expector/src/impl/java/com/fs/expector/dataservice/impl/ExpectorDsSPI.java)

[SPI.8.class=com.fs.expector.gridservice.impl.ExpectorGsSPI](fs-expector/src/impl/java/com/fs/expector/gridservice/impl/ExpectorGsSPI.java)

[SPI.9.class=com.fs.uicore.impl.UiCoreImplSPI](fs-uicore/src/impl/java/com/fs/uicore/impl/UiCoreImplSPI.java)

[SPI.10.class=com.fs.uiclient.impl.UiClientImplSPI](fs-uiclient/src/impl/java/com/fs/uiclient/impl/UiClientImplSPI.java)

[SPI.11.class=com.fs.integrated.IntegratedSPI](fs-integrated/src/impl/java/com/fs/integrated/IntegratedSPI.java)

### If you want to build.(WARN:It's a bit hard,you may feel frustrated.)

See [fs-building/readme.txt](fs-building/readme.txt) for instructions of building.


