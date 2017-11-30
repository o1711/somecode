#include "TaLang.h"

#ifdef MQL
const int TaLang::INVALID_HANDLE_ = INVALID_HANDLE;
const int TaLang::FILE_CSV_ = FILE_CSV;
const int TaLang::FILE_READ_ = FILE_READ;
const int TaLang::OP_BUY_= OP_BUY;
const int TaLang::OP_SELL_= OP_SELL;
const int TaLang::PRICE_CLOSE_= PRICE_CLOSE;
const int TaLang::MODE_MAIN_= MODE_MAIN;
const int TaLang::PERIOD_D1_= PERIOD_D1;
const int TaLang::PERIOD_M1_= PERIOD_M1;
const int TaLang::PERIOD_M5_= PERIOD_M5;
const int TaLang::PERIOD_M15_= PERIOD_M15;
const int TaLang::SELECT_BY_POS_= SELECT_BY_POS;
const int TaLang::SELECT_BY_TICKET_= SELECT_BY_TICKET;
const int TaLang::MODE_TRADES_= MODE_TRADES;
const int TaLang::MODE_SMA_= MODE_SMA;
const int TaLang::INIT_SUCCEEDED_= INIT_SUCCEEDED;

//TaGlobal::set("Red_",TaConstants::COLOR_RED);
//TaGlobal::set("Green_",TaConstants::COLOR_GREEN);
int TaLang::getAccountNumber(){
	return AccountNumber();
}
double TaLang::__AccountBalance(){
	return AccountBalance();
}
bool TaLang::__IsDemo(){
	return IsDemo();
}
bool TaLang::isTester() {
	return MQLInfoInteger(MQL_TESTER);
}
STRING TaLang::__StringConcatenate(STRING s1, STRING s2) {
	return StringConcatenate(s1,s2);
}

STRING TaLang::__StringConcatenate(STRING s1, STRING s2,STRING s3) {
	return StringConcatenate(s1,s2,s3);
}

STRING TaLang::__StringConcatenate(STRING s1, STRING s2,STRING s3,STRING s4) {
	return StringConcatenate(s1,s2,s3,s4);
}

STRING TaLang::__StringConcatenate(STRING s1, STRING s2,STRING s3,STRING s4,STRING s5) {
	return StringConcatenate(s1,s2,s3,s4,s5);
}

void TaLang::__SendNotification(STRING msg) {
	SendNotification(msg);
}

int TaLang::__FileOpen(STRING fName,int mode,int delimiter) {

	Print("datapath:",TerminalInfoString(TERMINAL_DATA_PATH));

	Print(fName,",",mode,",",delimiter);
	int rt = FileOpen(fName,mode,delimiter);
	Print(rt);

	return rt;
}
int TaLang::__GetLastError() {
	return GetLastError();
}
bool TaLang::__FileIsEnding(int file) {
	return FileIsEnding(file);
}
STRING TaLang::__FileReadString(int file) {
	return FileReadString(file);
}
void TaLang::__FileClose(int file) {
	FileClose(file);
}

STRING TaLang::__IntegerToString(int i) {
	return IntegerToString(i);
}

STRING TaLang::__CharToString(USHORT cha) {
	STRING rt = " ";
	StringSetCharacter(rt,0,cha);
	return rt;
}
double TaLang::__iATR(STRING symbol, int i1, int i2, int i3) {
	return iATR(symbol,i1,i2,i3);
}
double TaLang::__iMACD(STRING symbol, int i1, int i2, int i3, int i4, int i5,
		int i6, int i7) {
	return iMACD(symbol, i1,i2,i3,i4,i5,i6,i7);
}
STRING TaLang::__DoubleToString(double d1) {
	return DoubleToString(d1);
}
double TaLang::getAsk() {
	return Ask;
}
double TaLang::getBid() {
	return Bid;
}
double TaLang::getHigh(int idx) {
	return High[idx];
}

double TaLang::getLow(int idx) {
	return Low[idx];
}
double TaLang::getOpen(int idx) {
	return Open[idx];
}
double TaLang::getClose(int idx) {
	return Close[idx];
}
double TaLang::getVolume(int idx) {
	return Volume[idx];
}

STRING TaLang::__Symbol() {
	return Symbol();
}
STRING TaLang::__OrderSymbol() {
	return OrderSymbol();
}
double TaLang::__OrderSelect(int i1,int i2) {
	return OrderSelect(i1,i2);
}

double TaLang::__OrderSelect(int i1, int i2, int i3) {
	return OrderSelect(i1,i2,i3);
}
int TaLang::__OrdersTotal() {
	return OrdersTotal();
}
int TaLang::__OrderType() {
	return OrderType();
}
bool TaLang::__OrderModify(int id, double d1, double d2, double d3,
		TaDateTime* expire,const TaColorRef& clr) {
	clr.o();
	color lTaColor;
	return OrderModify(id,d1,d2,d3,expire,lTaColor); //TODO
}
bool TaLang::__OrderClose(int id, double lots, double price, int i2,
		const TaColorRef& clr) {
	clr.o();
	color lTaColor;
	return OrderClose(id,lots,price,i2,lTaColor); //TODO
}
double TaLang::__OrderLots() {
	return OrderLots();
}
double TaLang::__OrderOpenPrice() {
	return OrderOpenPrice();
}
double TaLang::__OrderStopLoss() {
	return OrderStopLoss();
}
double TaLang::__OrderTakeProfit() {
	return OrderTakeProfit();
}
STRING TaLang::__OrderComment() {
	return OrderComment();
}
int TaLang::__OrderTicket() {
	return OrderTicket();
}
int TaLang::__OrderMagicNumber() {
	return OrderMagicNumber();
}
int TaLang::__Period() {
	return Period();
}
void TaLang::__Print(STRING s1) {
	Print(s1);
}

void TaLang::__Print(STRING s1,int s2) {
	Print(StringConcatenate(s1,IntegerToString(s2)));
}
TaDateTime* TaLang::getTime(int idx,TaDateTime* rst) {
	datetime dt = Time[idx];
	MqlDateTime mdt;
	TimeToStruct(dt, mdt);
	rst.set(mdt.year,mdt.mon,mdt.day,mdt.hour,mdt.min,mdt.sec);
	return rst;
}
double TaLang::__MathAbs(double d1) {
	return MathAbs(d1);
}
double TaLang::__MathMax(double d1, double d2) {
	return MathMax(d1,d2);
}
STRING TaLang::__StringFind(STRING s1, STRING s2, int idx) {
	return StringFind(s1,s2,idx);
}
int TaLang::__StringLen(STRING s1) {
	return StringLen(s1);
}
STRING TaLang::__StringSubstr(STRING s1, int i1) {
	return StringSubstr(s1,i1);
}
STRING TaLang::__StringSubstr(STRING s1, int i1,int i2) {
	return StringSubstr(s1,i1,i2);
}
int TaLang::__StringToInteger(STRING s1) {
	return StringToInteger(s1);
}
double TaLang::__StringToDouble(STRING s1) {
	return StringToDouble(s1);
}
STRING TaLang::__StringTrim(STRING s1,bool emptyAsNull) {
	STRING rt = StringTrimRight(StringTrimLeft(s1));
	if(emptyAsNull && StringLen(rt) ==0) {
		rt = NULL;
	}
	return rt;

}
int TaLang::__StringSplit(STRING s1, int i1, STRING rst[]) {
	return StringSplit(s1,i1,rst);
}
int TaLang::__StringGetCharacter(STRING s1, int i1) {
	return StringGetCharacter(s1,i1);
}
int TaLang::getDigits() {
	return Digits;
}
double TaLang::__NormalizeDouble(double d1, int digits) {
	return NormalizeDouble(d1,digits);
}
int TaLang::__OrderSend(STRING symbol, int cmd, double lots,
		double price, int i3, double stoploss, double takeprofit,
		STRING comments, int magic, int i0, TaColorRef& clr) {
	return OrderSend(symbol,cmd,lots,price,i3,stoploss,takeprofit,comments,magic,i0,clr.obj()); //TODO
}
double TaLang::__AccountEquity() {
	return AccountEquity();
}
int TaLang::__DayOfYear() {
	return DayOfYear();
}
int TaLang::__DayOfWeek() {
	return DayOfWeek();
}

double TaLang::__iMA(STRING symbol, int i0, int idays, int i01, int mode,
		int type, int idx) {
	return iMA(symbol,i0,idays,i01,mode,type,idx);
}
#endif
