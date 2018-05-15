
###Sub project list
fs-building:	Building script.
fs-commons:		Common interface/tools or framework, all those stuff independt from any special bussiness.
fs-dataservice:	Interfaces and implements for persistence data storage. Based on Elastic Search.
fs-gridservice:	Interfaces and implements for in-memory data grid.Based on Hazelcast.
fs-webserver:	Embeded jetty server in order to support deployment of web app.
fs-expector:	Application layer logic implemented here. Defining backend in-memory data grid and persistence data schema.
fs-uicommon:	Common api/tools or framework for building client with GWT.
fs-uicore:		Core framework implementation based on fs-uicommon.
fs-uiclient:	Application layer logic implemented here. Implementing front-end UI on top of uicore and uicommon.
fs-integrated:	This project package every thing and deliver them as a runnable server kit.

###Entry point for reading and understanding the code:

Following SPIs(service provider interface) is the entry points for understanding what kind of function/component are provided. This list can be found [here](fs-integrated/src/impl/dist/conf/fs-spim.properties).

SPI.0.class=com.fs.commons.impl.CommonsSPI
SPI.1.class=com.fs.webserver.impl.WebServerSPI
SPI.2.class=com.fs.webcomet.impl.WebCometSPI
SPI.3.class=com.fs.websocket.impl.WebSocketSPI
SPI.4.class=com.fs.dataservice.core.impl.DataServiceCoreSPI
SPI.5.class=com.fs.gridservice.core.impl.GsCoreSPI
SPI.6.class=com.fs.gridservice.commons.impl.GsCommonsSPI
SPI.7.class=com.fs.expector.dataservice.impl.ExpectorDsSPI
SPI.8.class=com.fs.expector.gridservice.impl.ExpectorGsSPI
SPI.9.class=com.fs.uicore.impl.UiCoreImplSPI
SPI.10.class=com.fs.uiclient.impl.UiClientImplSPI
SPI.11.class=com.fs.integrated.IntegratedSPI

###If you want to build.

Click [here](fs-building/readme.txt) for instructions of building.


