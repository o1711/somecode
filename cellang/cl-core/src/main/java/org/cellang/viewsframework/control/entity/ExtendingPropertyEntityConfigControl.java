package org.cellang.viewsframework.control.entity;

import java.util.List;

import org.cellang.core.entity.CorpInfoEntity;
import org.cellang.core.entity.EntityOp;
import org.cellang.core.entity.EntitySession;
import org.cellang.core.entity.EntitySessionFactory;
import org.cellang.core.entity.ExtendingPropertyEntity;
import org.cellang.viewsframework.View;
import org.cellang.viewsframework.control.Action;
import org.cellang.viewsframework.control.HasActions;
import org.cellang.viewsframework.ext.CorpNameExtExtProperty;

public class ExtendingPropertyEntityConfigControl extends EntityConfigControl<CorpInfoEntity> implements HasActions {

	EntitySessionFactory esf;

	public ExtendingPropertyEntityConfigControl(EntitySessionFactory entityService) {
		this.esf = entityService;
		this.addExtendingProperty(new CorpNameExtExtProperty(), true);
	}

	private EntityOp<Void> deleteAllOp = new EntityOp<Void>() {

		@Override
		public Void execute(EntitySession es) {
			es.delete(ExtendingPropertyEntity.class);
			return null;
		}
	};

	@Override
	public List<Action> getActions(Object context, List<Action> al) {
		if (!(context instanceof View)) {
			return al;
		}

		al.add(new Action() {

			@Override
			public String getName() {
				return "Delete All";
			}

			@Override
			public void perform() {
				ExtendingPropertyEntityConfigControl.this.deleteAll();
			}
		});

		return al;
	}

	protected void deleteAll() {
		this.esf.execute(this.deleteAllOp);
	}
}
