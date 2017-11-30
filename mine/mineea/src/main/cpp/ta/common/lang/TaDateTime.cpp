#include "TaDateTime.h"

void TaDateTime::set(TaDateTime* dt) {
	this->set(dt->year, dt->month, dt->day, dt->hour, dt->minites, dt->seconds);
}

void TaDateTime::set(int y, int m, int d, int h, int mi, int s) {
	this->year = y;
	this->month = m;
	this->day = d;
	this->hour = h;
	this->minites = mi;
	this->seconds = s;

}
STRING TaDateTime::toFormatedString() {
	return this->year + "." + this->month + "." + this->day + "." + this->hour
			+ "." + this->minites + "." + this->seconds;
}

bool TaDateTime::isEquals(TaDateTime * obj) {
	if (obj == NULL) {
		return false;
	}
	return this->year == obj->year //
	&& this->month == obj->month //
	&& this->day == obj->day //
	&& this->hour == obj->hour //
	&& this->minites == obj->minites //
	&& this->seconds == obj->seconds //
	;

}
