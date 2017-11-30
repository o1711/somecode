
#include "TaStringRefMap.h"


STRING TaStringRefMap_Entry::toString(){

	TaRef ref(new TaStringBuffer());
	TaStringBuffer * sb = ref.obj();
	sb->append(this->key);
	sb->append("=");
	sb->append(this->value);
	STRING rt =	sb->toString();
	return rt;

}
TaStringRefMap::TaStringRefMap() {
	this->entryList = new TaRefList();
	TaRef ref1(this->entryList);
	this->ref_entryList = ref1;
	this->cache_KeyList = new TaRefList();
	TaRef ref2(this->cache_KeyList);
	this->ref_cache_KeyList = ref2;
}

TaStringRefMap::~TaStringRefMap() {
}

TaObject* TaStringRefMap::put(STRING key, TaObject* ele) {
	TaRef ref(ele);
	TaRef oldRef =put(key,ref);
	return oldRef.obj();
}

TaRef TaStringRefMap::put(STRING key, TaRef& ele) {
	TaRef rt = this->unPut(key);
	TaRef enRef(new TaStringRefMap_Entry());
	TaStringRefMap_Entry* en = enRef.obj();
	en->key = key;
	en->value = ele;
	this->entryList->addRef(enRef);
	this->clearCache();
	return rt;

}
TaRef TaStringRefMap::unPut(STRING key) {
	TaRef rt = NULL;
	for (int i = 0; i < this->entryList->getSize(); i++) {

		TaStringRefMap_Entry* en = this->entryList->get(i).obj();
		if(en == NULL){
			continue;
		}
		if(en->key == key){
			rt = en->value;
			TaRef nref;
			this->entryList->set(i,nref);
			//delete en;
			break;
		}
	}
	this->clearCache();
	return rt;
}

TaObject* TaStringRefMap::getObj(STRING key){
	TaRef ref =this->getRef(key);
	return ref.obj();
}

TaRef TaStringRefMap::getRef(STRING key) {
	TaRef rt;
	for (int i = 0; i < this->entryList->getSize(); i++) {
		TaStringRefMap_Entry* en = this->entryList->get(i).obj();
		if(en->key == key){
			rt = en->value;
			break;
		}
	}
	return rt;
}

int TaStringRefMap::getSize() {
	return this->entryList->getSize();
}

void TaStringRefMap::clear(bool destroy){
	this->entryList->clear(false);
}

void TaStringRefMap::clearCache(){
	if(this->cache_KeyList == NULL){
		return;
	}

	this->cache_KeyList = NULL;
	TaRef ref;
	this->ref_cache_KeyList = ref;
}

TaRefList* TaStringRefMap::getKeyList(){
	if(this->cache_KeyList != NULL){
		return this->cache_KeyList;
	}
	this->cache_KeyList = new TaRefList();
	TaRef ref(this->cache_KeyList);
	this->ref_cache_KeyList	= ref;

	for(int i=0;i<this->entryList->getSize();i++){

		TaStringRefMap_Entry* en = this->entryList->get(i).obj();
		this->cache_KeyList->add(new TaString(en->key));
	}
	return this->cache_KeyList;
}

STRING TaStringRefMap::toString(){
	return this->entryList->toString();
}
