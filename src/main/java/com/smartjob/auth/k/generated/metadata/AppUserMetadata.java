package com.smartjob.auth.k.generated.metadata;

import com.myzlab.k.KJoinDefinition;
import com.myzlab.k.KRow;
import com.myzlab.k.KTable;
import com.myzlab.k.KTableColumn;
import static com.smartjob.auth.k.generated.metadata.Tables.*;
import com.smartjob.auth.k.generated.mappers.AppUser;
import java.time.LocalDateTime;

public class AppUserMetadata extends KTable {

	private static AppUserMetadata instance = null;

	public final KTableColumn ID = new KTableColumn(this, "id", Long.class);
	public final KTableColumn EMAIL = new KTableColumn(this, "email", String.class);
	public final KTableColumn PASSWORD = new KTableColumn(this, "password", String.class);
	public final KTableColumn NAME = new KTableColumn(this, "name", String.class);
	public final KTableColumn IS_ACTIVE = new KTableColumn(this, "is_active", Boolean.class);
	public final KTableColumn LAST_LOGIN = new KTableColumn(this, "last_login", LocalDateTime.class);
	public final KTableColumn CREATED_AT = new KTableColumn(this, "created_at", LocalDateTime.class);
	public final KTableColumn UPDATED_AT = new KTableColumn(this, "updated_at", LocalDateTime.class);

	private AppUserMetadata() {
		super("auth", "app_user", "au");
	}

	private AppUserMetadata(
		final String schema,
		final String name,
		final String alias
	) {
		super(schema, name, alias);
	}

	public static AppUserMetadata getInstance() {
		if (instance != null) {
			return instance;
		}

		instance = new AppUserMetadata();

		return instance;
	}

	public KJoinDefinition joinPhone() {
		return PHONE.on(
			PHONE.APP_USER_ID.eq(this.ID)
		);
	}

	public AppUserMetadata alias(
		final String alias
	) {
		return new AppUserMetadata(this.schema, this.name, alias);
	}

	@Override
	protected Class<? extends KRow> getKRowClass() {
		return AppUser.class;
	}
}
