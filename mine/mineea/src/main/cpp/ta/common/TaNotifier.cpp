#include "TaNotifier.h"
//TaNotifier
TaNotifier::TaNotifier() {

}
void TaNotifier::send(STRING message1, STRING message2) {
	send(TaLang::__StringConcatenate(message1, message2));
}
void TaNotifier::send(STRING message) {
	// doLog(message);
	TaLang::__SendNotification(message);
}
