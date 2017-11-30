#ifndef TAAGENT_H
#define TAAGENT_H

class TaAgent : public TaObject{
private:
	static TaLoggerRef LOG;
public:
	TaAgent();
	~TaAgent();
	void configure(TaConfigRef& config);
	void start();
	void onTick();
	void stop();
protected:
	TaJsonObjectRef request(TaJsonObjectRef& obj);
	void uploadTimeBars();
	TaJsonObject* createAccountData();
private:
	STRING url;
	STRING sessionId;
	TaWebClient* webClient;
	TaJsonParser* jsonParser;
	TaTimeBar* timeBar;
	TaAccountInfos* accountInfos;
};

#endif
