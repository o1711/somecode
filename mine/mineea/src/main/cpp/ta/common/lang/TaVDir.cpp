#include "TaVDir.h"

TaVDir::TaVDir(short pValue) {
	this->value = pValue;
}
TaVDir::~TaVDir() {
}
bool TaVDir::isUp() const {
	return this->value > 0;
}

bool TaVDir::isDown() const {
	return this->value < 0;
}

bool TaVDir::isSignEquals(double vector) const {
	if (this->isUp()) {
		return vector > 0;
	} else {
		return vector < 0;
	}
}

double TaVDir::multiple(double vector) const {
	if (this->isUp()) {
		return vector;
	} else {
		return -vector;
	}
}

bool TaVDir::isAllSignEquals(double& vectors[]) const {
	for (int i = 0; i < TaSystem::size(vectors); i++) {
		if (!isSignEquals(vectors[i])) {
			return false;
		}
	}
	return true;
}
