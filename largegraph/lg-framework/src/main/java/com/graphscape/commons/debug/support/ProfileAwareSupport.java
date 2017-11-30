package com.graphscape.commons.debug.support;

import java.util.ArrayList;
import java.util.List;

import com.graphscape.commons.debug.ProfileAwareI;
import com.graphscape.commons.debug.ProfileI;
import com.graphscape.commons.lang.support.HasAttributeSupport;

public class ProfileAwareSupport extends HasAttributeSupport implements ProfileAwareI {

	protected ProfileI profile;

	protected List<Object> childToBeApplied = new ArrayList<Object>();

	@Override
	public ProfileI getProfile() {

		return this.profile;
	}

	@Override
	public void setProfile(ProfileI tracer) {
		this.profile = tracer;
		if (this.profile == null) {
			return;
		}

		this.profile.applyAll(this.childToBeApplied);
		this.childToBeApplied.clear();

	}
	
	
	protected void beforeProfile(String id) {
		if (this.profile == null) {
			return;
		}
		this.profile.beforeExecute(id);
	}

	protected void afterProfile() {
		if (this.profile == null) {
			return;
		}
		this.profile.afterExecute();
	}

	protected <T> T applyProfile(T obj) {
		if (this.profile == null) {
			this.childToBeApplied.add(obj);
			return obj;
		}

		this.profile.apply(obj);
		return obj;

	}

}
