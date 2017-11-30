
#ifdef MQL
#property version "@version@"
#endif

#include "../ta/TaLib.cpp"
//--- input parameters
STRING project = "@project@";
STRING version = "@version@";

TaDriverRef driver;
TaTickHandler* tickHandler;
//+------------------------------------------------------------------+
//| Expert initialization function                                   |
//+------------------------------------------------------------------+
int OnInit(){
	/*
	TaRefStack* stack = new TaRefStack();
	TaRef r1(new TaString(""));
	stack->push(r1);
	r1 = stack->pop();

	TaRef lR(new TaRefList());
	TaRefList* l1 = lR.obj();
	for(int i=0;i<100;i++){
		TaRef ref;
		l1->add(ref);
	}

	TaRefList* list = new TaRefList();
	TaRef ref1;
	list->add(ref1);
*/
	TaBootstrap* boot = new TaBootstrap();
	TaRef ref(boot);
	STRING configFile = TaLang::__StringConcatenate(project,"-",version,"\\conf\\config.json");
	if(TaLang::isTester()){
		configFile = TaLang::__StringConcatenate(project,"\\conf\\config.json");
	}
	driver = boot->load(configFile);

   	return(TaLang::INIT_SUCCEEDED_);
}

//+------------------------------------------------------------------+
//| Expert deinitialization function                                 |
//+------------------------------------------------------------------+
void OnDeinit(const int reason)
  {
//--- destroy timer   
  // delete driver;
  // TaGlobal::destroy();
  }
//+------------------------------------------------------------------+
//| Expert tick function                                             |
//+------------------------------------------------------------------+
void OnTick(){
//---   
  TaTickHandler* th = driver.o()->getMainTickHandler().obj();
  th->onTick();
}

//+------------------------------------------------------------------+
//| Timer function                                                   |
//+------------------------------------------------------------------+
void OnTimer()
  {
//---
  }
//+------------------------------------------------------------------+
//| Tester function                                                  |
//+------------------------------------------------------------------+
double OnTester()
  {
//---
   double ret=0.0;

//---
   return(ret);
  }
