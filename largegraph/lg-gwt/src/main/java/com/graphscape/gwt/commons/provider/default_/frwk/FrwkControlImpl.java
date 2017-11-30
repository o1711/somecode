/**
 *  Jan 31, 2013
 */
package com.graphscape.gwt.commons.provider.default_.frwk;

import com.graphscape.gwt.commons.Constants;
import com.graphscape.gwt.commons.frwk.BodyViewI;
import com.graphscape.gwt.commons.frwk.BottomViewI;
import com.graphscape.gwt.commons.frwk.ConsoleViewI;
import com.graphscape.gwt.commons.frwk.FrwkControlI;
import com.graphscape.gwt.commons.frwk.FrwkViewI;
import com.graphscape.gwt.commons.frwk.HeaderViewI;
import com.graphscape.gwt.commons.frwk.login.LoginControlI;
import com.graphscape.gwt.commons.frwk.login.LoginViewI;
import com.graphscape.gwt.commons.mvc.support.ControlSupport;
import com.graphscape.gwt.commons.provider.default_.frwk.FrwkView;
import com.graphscape.gwt.commons.provider.default_.frwk.console.ConsoleView;
import com.graphscape.gwt.core.ContainerI;
import com.graphscape.gwt.core.RootI;
import com.graphscape.gwt.core.commons.Path;

/**
 * @author wuzhen
 * 
 */
public class FrwkControlImpl extends ControlSupport implements FrwkControlI {

	/**
	 * @param c
	 * @param name
	 */
	public FrwkControlImpl(ContainerI c, String name) {
		super(c, name);

	}

	@Override
	public void open() {
		RootI root = this.getClient(true).getRoot();
		FrwkViewI fv = root.getChild(FrwkViewI.class, false);
		if (fv == null) {
			fv = new FrwkView(this.container);
			fv.parent(root);
		}
	}

	@Override
	public void addHeaderItem(Path path) {
		this.addHeaderItem(path, false);
	}

	@Override
	public void addHeaderItem(Path path, boolean left) {
		this.getFrwkView().getHeader().addItem(path, left);

	}

	/*
	 * Feb 1, 2013
	 */
	@Override
	public LoginViewI openLoginView(boolean show) {
		//
		LoginControlI lc = this.getControl(LoginControlI.class, true);
		return lc.openLoginView(show);

	}

	/*
	 * Feb 2, 2013
	 */
	@Override
	public HeaderViewI getHeaderView() {
		//
		return this.getFrwkView().getHeader();
	}

	@Override
	public BottomViewI getBottomView() {
		//
		return this.getFrwkView().getBottom();
	}

	/*
	 * Mar 30, 2013
	 */
	@Override
	public void addHeaderItemIfNotExist(Path path) {
		//
		this.getHeaderView().addItemIfNotExist(path);
	}

	@Override
	public void tryRemoveHeaderItem(Path path) {
		this.getHeaderView().tryRemoveItem(path);
	}

	@Override
	public ConsoleViewI openConsoleView(boolean show) {
		BodyViewI bv = this.getFrwkView().getBodyView();
		ConsoleViewI rt = bv.getOrCreateItem(Constants.P_CONSOLE_VIEW,
				new com.graphscape.gwt.commons.CreaterI<ConsoleViewI>() {

					@Override
					public ConsoleViewI create(ContainerI ct) {

						return new ConsoleView(ct);
					}
				}, show);
		return rt;
	}
}
