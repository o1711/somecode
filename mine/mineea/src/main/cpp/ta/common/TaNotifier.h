#ifndef TaNotifier_MQH
#define TaNotifier_MQH

#include "../system/TaObject.h"

class TaNotifier: public TaObject {
public:
	TaNotifier();
public:
	void send(STRING message);
public:
	void send(STRING message1, STRING message2);

};
#endif
