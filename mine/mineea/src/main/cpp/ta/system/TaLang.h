

#ifndef TA_LANG_MQH_
#define TA_LANG_MAH_
#include "TaTop.h"
#include "TaRef.h"
#include "../common/lang/TaDateTime.h"
#include "../common/lang/TaColor.h"

class TaLang {
public:
	static const int FILE_CSV_;
	static const int FILE_READ_;
	static const int INVALID_HANDLE_;
	static const int OP_BUY_;
	static const int OP_SELL_;
	static const int PRICE_CLOSE_;
	static const int MODE_MAIN_;
	static const int PERIOD_D1_;
	static const int PERIOD_M1_;
	static const int PERIOD_M5_;
	static const int PERIOD_M15_;
	static const int SELECT_BY_POS_;
	static const int SELECT_BY_TICKET_;
	static const int MODE_TRADES_;
	static const int MODE_SMA_;
	static const int INIT_SUCCEEDED_;

public:

	static STRING __StringConcatenate(STRING s1,STRING s2 );

	static STRING __StringConcatenate(STRING s1,STRING s2,STRING s3 );

	static STRING __StringConcatenate(STRING s1,STRING s2,STRING s3,STRING s4 );

	static STRING __StringConcatenate(STRING s1,STRING s2 ,STRING s3,STRING s5,STRING s5);

	static void __SendNotification(STRING msg);
	static double __AccountBalance();
	static int getAccountNumber();
	static bool __IsDemo();

	static double __iATR(STRING symbol, int i1, int i2, int i3);
	static double __iMACD(STRING symbol, int i1, int i2, int i3, int i4, int i5,
			int i6, int i7);
	static STRING __DoubleToString(double d1);
	static int __FileOpen(STRING fname,int mode,int delimiter);
	static void __FileClose(int file);
	static bool __FileIsEnding(int file);
	static STRING __FileReadString(int file);
	static STRING __IntegerToString(int i);
	static STRING __CharToString(USHORT cha);
	static int __GetLastError();
	static double getAsk();
	static double getBid();
	static double getHigh(int idx);
	static double getLow(int idx);
	static double getOpen(int idx);
	static double getClose(int idx);
	static double getVolume(int idx);
	static bool isTester();
	static STRING __Symbol();
	static STRING __OrderSymbol();
	static double __OrderSelect(int i1,int i2);
	static double __OrderSelect(int i1, int i2, int i3);
	static int __OrdersTotal();
	static int __OrderType();
	static bool __OrderModify(int id, double d1, double d2, double d3,
			TaDateTime* expire, const TaColorRef& clr);
	static bool __OrderClose(int id, double lots, double price, int i2,
			const TaColorRef& clr);
	static double __OrderLots();
	static double __OrderOpenPrice();
	static double __OrderStopLoss();
	static double __OrderTakeProfit();
	static STRING __OrderComment();
	static int __OrderTicket();
	static int __OrderMagicNumber();
	static int __Period();
	static void __Print(STRING s1);
	static void __Print(STRING s1,int s2);
	static TaDateTime* getTime(int idx,TaDateTime* rst);
	static double __MathAbs(double d1);
	static double __MathMax(double d1,double d2);
	static STRING __StringFind(STRING s1, STRING s2, int idx);
	static int __StringLen(STRING s1);
	static STRING __StringSubstr(STRING s1, int i1);
	static STRING __StringSubstr(STRING s1, int i1,int i2);
	static int __StringToInteger(STRING s1);
	static double __StringToDouble(STRING s1);
	static int __StringSplit(STRING s1, int i1, STRING rst[]);
	static int __StringGetCharacter(STRING s1, int i1);
	static STRING __StringTrim(STRING s1,bool emptyAsNull);
	static int getDigits();
	static double __NormalizeDouble(double d1, int digits);
	static int __OrderSend(STRING symbol, int cmd, double lots,
			double price, int i3, double stoploss, double takeprofit,
			STRING comments, int magic, int i0, TaColorRef& clr);
	static double __AccountEquity();
	static int __DayOfYear();
	static int __DayOfWeek();

	static double __iMA(STRING symbol, int i0, int idays, int i01, int mode , int type, int idx);

};
#endif /*TA_LANG_MQH*/
