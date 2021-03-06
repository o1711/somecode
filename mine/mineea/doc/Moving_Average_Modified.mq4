//+------------------------------------------------------------------+
//|                                      Moving Average_Мodified.mq4 |
//|                      Copyright © 2013, MetaQuotes Software Corp. |
//|                                      Modified by BARS            |
//+------------------------------------------------------------------+
#define MAGICMA  20050610
//-----------------------------------------
extern int     StopLoss           = 500;
extern int     TakeProfit         = 500;
extern double  Lots               = 0.1;
extern double  MaximumRisk        = 0.02;
extern double  DecreaseFactor     = 3;
extern int     MovingPeriod_Open  = 12;
extern int     MovingPeriod_Close = 21;
extern int     MovingShift        = 1;
extern color   BuyColor           = clrCornflowerBlue;
extern color   SellColor          = clrSalmon;
//---
double SL=0,TP=0;
//-- Include modules --
#include <stderror.mqh>
#include <stdlib.mqh>
//+------------------------------------------------------------------+
//|                                                                  |
//+------------------------------------------------------------------+
void start()
  {
//--- If there are more than 100 bars in the chart and the trade flow is free
   if(Bars<100 || IsTradeAllowed()==false)
      return;
//--- If the calculated lot size is in line with the current deposit amount
   if(CalculateCurrentOrders(Symbol())==0)
      CheckForOpen();   // start working
   else
      CheckForClose();  // otherwise, close positions
  }
//+------------------------------------------------------------------+
//| Determines open positions                                        |
//+------------------------------------------------------------------+
int CalculateCurrentOrders(string symbol)
  {
   int buys=0,sells=0;
   for(int i=0; i<OrdersTotal(); i++)
     {
      if(OrderSelect(i,SELECT_BY_POS,MODE_TRADES)==false)
         break;
      if(OrderSymbol()==Symbol() && OrderMagicNumber()==MAGICMA)
        {
         if(OrderType()==OP_BUY) buys++;
         if(OrderType()==OP_SELL) sells++;
        }
     }
//---- return orders volume
   if(buys>0)
      return(buys);
   else
      return(-sells);
  }
//+------------------------------------------------------------------+
//| Calculates the optimum lot size                                  |
//+------------------------------------------------------------------+
double LotsOptimized()
  {
   double lot=Lots;
   int    orders=HistoryTotal(); // history orders total
   int    losses=0;              // number of loss orders without a break
//---- select lot size
   lot=NormalizeDouble(AccountFreeMargin()*MaximumRisk/1000.0,1);
//---- calcuulate number of loss orders without a break
   if(DecreaseFactor>0)
     {
      for(int i=orders-1;i>=0;i--)
        {
         if(OrderSelect(i,SELECT_BY_POS,MODE_HISTORY)==false)
           {
            Print("Error in history!");
            break;
           }
         if(OrderSymbol()!=Symbol() || OrderType()>OP_SELL)
            continue;
         //----
         if(OrderProfit()>0)
            break;
         if(OrderProfit()<0)
            losses++;
        }
      if(losses>1)
         lot=NormalizeDouble(lot-lot*losses/DecreaseFactor,1);
     }
//---- return lot size
   if(lot<0.1)
      lot=0.1;
   return(lot);
  }
//+------------------------------------------------------------------+
//| Position opening function                                        |
//+------------------------------------------------------------------+
void CheckForOpen()
  {
   double ma;
   int    res;
//---- go trading only for first tiks of new bar
   if(Volume[0]>1)
      return;
//---- get Moving Average 
   ma=iMA(NULL,0,MovingPeriod_Open,MovingShift,MODE_SMA,PRICE_CLOSE,0);
//---- sell conditions
   if(Open[1]>ma && Close[1]<ma)
     {
      if(StopLoss>0)
         SL=Bid+Point*StopLoss;
      if(TakeProfit>0)
         TP=Bid-Point*TakeProfit;
      res=WHCOrderSend(Symbol(),OP_SELL,LotsOptimized(),Bid,3,SL,TP,"Moving Average",MAGICMA,0,SellColor);
      if(res<0)
        {
         Print("Error when opening a SELL order #",GetLastError());
         Sleep(10000);
         return;
        }
     }
//---- buy conditions
   if(Open[1]<ma && Close[1]>ma)
     {
      SL=0;TP=0;
      if(StopLoss>0)
         SL=Ask-Point*StopLoss;
      if(TakeProfit>0)
         TP=Ask+Point*TakeProfit;
      res=WHCOrderSend(Symbol(),OP_BUY,LotsOptimized(),Ask,3,SL,TP,"Moving Average",MAGICMA,0,BuyColor);
      if(res<0)
        {
         Print("Error when opening a BUY order #",GetLastError());
         Sleep(10000);
         return;
        }
     }
//----
  }
//+------------------------------------------------------------------+
//| Position closing function                                        |
//+------------------------------------------------------------------+
void CheckForClose()
  {
   double ma;
//---- go trading only for first tiks of new bar
//(start working at the first tick of the new bar)
   if(Volume[0]>1) return;
//---- get Moving Average 
   ma=iMA(NULL,0,MovingPeriod_Close,MovingShift,MODE_SMA,PRICE_CLOSE,0);
//----
   for(int i=0;i<OrdersTotal();i++)
     {
      if(OrderSelect(i,SELECT_BY_POS,MODE_TRADES)==false) break;
      if(OrderMagicNumber()!=MAGICMA || OrderSymbol()!=Symbol()) continue;
      //---- check order type 
      if(OrderType()==OP_BUY)
        {
         if(Open[1]>ma && Close[1]<ma)
            OrderClose(OrderTicket(),OrderLots(),Bid,3,BuyColor);     break;
        }
      if(OrderType()==OP_SELL)
        {
         if(Open[1]<ma && Close[1]>ma)
            OrderClose(OrderTicket(),OrderLots(),Ask,3,SellColor);     break;
        }
     }
  }
//+-------------------------------------------------------------------+
//| Opens positions in Market Execution mode                          |
//+-------------------------------------------------------------------+
int WHCOrderSend(string    symbol,
                 int       cmd,
                 double    volume,
                 double    price,
                 int       slippage,
                 double    stoploss,
                 double    takeprofit,
                 string    comment,
                 int       magic,
                 datetime  expiration,
                 color     arrow_color)
  {
   int ticket=OrderSend(symbol,cmd,volume,price,slippage,0,0,comment,magic,expiration,arrow_color);
   int check=-1;
   if(ticket>0 && (stoploss!=0 || takeprofit!=0))
     {
      if(!OrderModify(ticket,price,stoploss,takeprofit,expiration,arrow_color))
        {
         check=GetLastError();
         if(check!=ERR_NO_MQLERROR)
            Print("OrderModify error: ",ErrorDescription(check));
        }
     }
   else
     {
      check=GetLastError();
      if(check!=ERR_NO_ERROR)
         Print("OrderSend error: ",ErrorDescription(check));
     }
   return(ticket);
  }
//+------------------------------------------------------------------+
