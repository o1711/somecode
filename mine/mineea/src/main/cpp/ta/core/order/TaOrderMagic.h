#ifndef TaOrderMagic_MQH
#define TaOrderMagic_MQH



class TaOrderMagic {


public:
	TaOrderMagic();

	TaOrderMagic(int magic);

	virtual ~TaOrderMagic();

	const static int MAX_STRATEGY;

	void setStrategy(int pStrategy);

	int encode();

protected:
	int strategy;
};
#endif
