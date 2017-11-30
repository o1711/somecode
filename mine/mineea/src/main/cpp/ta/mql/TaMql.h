
#ifndef TaMql_MQH
#define TaMql_MQH
#include "../system/TaLang.h"
#include "TaMqlInfos.h"
#include "TaAccountInfos.h"

class TaMql : public TaObject{
public:


	public:
		TaMql();
		virtual ~TaMql();
		TaMqlInfos* getInfos();
		TaAccountInfos* getAccountInfos();
	private:
			TaMqlInfos * infos;
			TaRef ref_infos;
			TaAccountInfos * accountInfos;
};

#endif

