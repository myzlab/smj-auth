package com.smartjob.auth.k.generated.metadata;

import com.myzlab.k.KRow;
import com.myzlab.k.KTable;
import com.myzlab.k.KTableColumn;
import com.smartjob.auth.k.generated.mappers.Param;
import java.time.LocalDateTime;

public class ParamMetadata extends KTable {

	private static ParamMetadata instance = null;

	public final KTableColumn ID = new KTableColumn(this, "id", Long.class);
	public final KTableColumn VALUE = new KTableColumn(this, "value", String.class);
	public final KTableColumn CREATED_AT = new KTableColumn(this, "created_at", LocalDateTime.class);
	public final KTableColumn UPDATED_AT = new KTableColumn(this, "updated_at", LocalDateTime.class);

	private ParamMetadata() {
		super("auth", "param", "pa");
	}

	private ParamMetadata(
		final String schema,
		final String name,
		final String alias
	) {
		super(schema, name, alias);
	}

	public static ParamMetadata getInstance() {
		if (instance != null) {
			return instance;
		}

		instance = new ParamMetadata();

		return instance;
	}

	public ParamMetadata alias(
		final String alias
	) {
		return new ParamMetadata(this.schema, this.name, alias);
	}

	@Override
	protected Class<? extends KRow> getKRowClass() {
		return Param.class;
	}
}
