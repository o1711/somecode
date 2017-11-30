#ifndef TaBootstrap_MQH
#define TaBootstrap_MQH


#include "TaTd0001Driver.h"

class TaBootstrap : public TaObject {
	private:
		
	public:
		TaBootstrap();
    	virtual ~TaBootstrap();
    	TaDriverRef load(STRING configFile);
};
#endif
