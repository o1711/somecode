package org.cellang.viewsframework.control.entity;

import java.util.List;

import org.cellang.core.entity.BalanceSheetReportEntity;
import org.cellang.core.entity.CashFlowStatementReportEntity;
import org.cellang.core.entity.CorpInfoEntity;
import org.cellang.core.entity.CustomizedReportEntity;
import org.cellang.core.entity.EntityOp;
import org.cellang.core.entity.EntitySession;
import org.cellang.core.entity.EntitySessionFactory;
import org.cellang.core.entity.IncomeStatementReportEntity;
import org.cellang.core.entity.InterestedCorpEntity;
import org.cellang.corpsviewer.corpdata.ItemDefines;
import org.cellang.viewsframework.View;
import org.cellang.viewsframework.control.Action;
import org.cellang.viewsframework.control.HasActions;
import org.cellang.viewsframework.control.SelectionListener;
import org.cellang.viewsframework.ext.CorpP_EBITDAExtendingProperty;
import org.cellang.viewsframework.ext.CorpP_EExtendingProperty;
import org.cellang.viewsframework.ext.CorpROEExtendingPropertyDefine;
import org.cellang.viewsframework.ops.OperationContext;
import org.cellang.viewsframework.report.ReportTableView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @see EntityConfigManager
 * @author wu
 *
 */
public class CorpInfoEntityConfigControl extends EntityConfigControl<CorpInfoEntity> implements HasActions {

	private static final Logger LOG = LoggerFactory.getLogger(CorpInfoEntityConfigControl.class);
	EntitySessionFactory entitySessions;
	OperationContext oc;

	public CorpInfoEntityConfigControl(OperationContext oc, EntitySessionFactory entitySessions) {
		this.oc = oc;
		this.entitySessions = entitySessions;

		this.addExtendingProperty(new CorpP_EExtendingProperty(1), true);
		this.addExtendingProperty(new CorpP_EExtendingProperty(5), true);
		this.addExtendingProperty(new CorpP_EBITDAExtendingProperty(1), true);
		this.addExtendingProperty(new CorpP_EBITDAExtendingProperty(5), true);
		this.addExtendingProperty(new CorpROEExtendingPropertyDefine(1), true);
		this.addExtendingProperty(new CorpROEExtendingPropertyDefine(5), true);

	}

	@Override
	public <T> T getDelegate(Class<T> cls) {
		if (HasActions.class.equals(cls)) {
			return (T) this;
		} else if (SelectionListener.class.equals(cls)) {
			return (T) this;
		} else if (EntitySessionFactory.class.equals(cls)) {
			return (T) this.entitySessions;
		}
		return null;
	}

	@Override
	public List<Action> getActions(Object context, List<Action> al) {
		if (!(context instanceof CorpInfoEntity)) {
			return al;
		}
		al.add(new Action() {

			@Override
			public String getName() {
				return "Interest";
			}

			@Override
			public void perform() {
				CorpInfoEntityConfigControl.this.addToInterested((CorpInfoEntity) context);
			}
		});
		al.add(new Action() {

			@Override
			public String getName() {
				return "B/S";
			}

			@Override
			public void perform() {
				CorpInfoEntityConfigControl.this.openBSReport((CorpInfoEntity) context);
			}
		});
		al.add(new Action() {

			@Override
			public String getName() {
				return "I/S";
			}

			@Override
			public void perform() {
				CorpInfoEntityConfigControl.this.openISReport((CorpInfoEntity) context);
			}
		});

		al.add(new Action() {

			@Override
			public String getName() {
				return "CF/S";
			}

			@Override
			public void perform() {
				CorpInfoEntityConfigControl.this.openCFSReport((CorpInfoEntity) context);
			}
		});

		al.add(new Action() {

			@Override
			public String getName() {
				return "Customized Report";
			}

			@Override
			public void perform() {
				CorpInfoEntityConfigControl.this.openCustomizedReport((CorpInfoEntity) context);
			}
		});

		return al;
	}

	protected void openCustomizedReport(CorpInfoEntity context) {
		
	}

	protected void openBSReport(CorpInfoEntity context) {
		
	}

	protected void openISReport(CorpInfoEntity context) {
		
	}

	protected void openCFSReport(CorpInfoEntity context) {
		
	}

	protected void addToInterested(CorpInfoEntity ce) {

		if (LOG.isDebugEnabled()) {
			LOG.debug("add corp:" + ce.getCode() + " as interested.");
		}
		this.entitySessions.execute(new EntityOp<Void>() {

			@Override
			public Void execute(EntitySession es) {
				InterestedCorpEntity ic = new InterestedCorpEntity();
				ic.setId(ce.getId());//
				ic.setCorpId(ce.getId());//
				es.save(ic);
				return null;
			}
		});
	}

}
