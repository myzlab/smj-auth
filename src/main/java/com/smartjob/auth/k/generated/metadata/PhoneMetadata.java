package com.smartjob.auth.k.generated.metadata;

import com.myzlab.k.KEdge;
import com.myzlab.k.KJoinDefinition;
import com.myzlab.k.KRow;
import com.myzlab.k.KTable;
import com.myzlab.k.KTableColumn;
import static com.smartjob.auth.k.generated.metadata.Tables.*;
import com.smartjob.auth.k.generated.mappers.AppUser;
import com.smartjob.auth.k.generated.mappers.Phone;
import java.time.LocalDateTime;

public class PhoneMetadata extends KTable {

	private static PhoneMetadata instance = null;

	public final KTableColumn ID = new KTableColumn(this, "id", Long.class);
	public final KTableColumn APP_USER_ID = new KTableColumn(this, "app_user_id", Long.class);
	public final KTableColumn NUMBER = new KTableColumn(this, "number", String.class);
	public final KTableColumn CREATED_AT = new KTableColumn(this, "created_at", LocalDateTime.class);
	public final KTableColumn UPDATED_AT = new KTableColumn(this, "updated_at", LocalDateTime.class);
	public final KTableColumn CITY_CODE = new KTableColumn(this, "city_code", String.class);
	public final KTableColumn COUNTRY_CODE = new KTableColumn(this, "country_code", String.class);

	private PhoneMetadata() {
		super("auth", "phone", "ph");
	}

	private PhoneMetadata(
		final String schema,
		final String name,
		final String alias
	) {
		super(schema, name, alias);
	}

	public static PhoneMetadata getInstance() {
		if (instance != null) {
			return instance;
		}

		instance = new PhoneMetadata();

		return instance;
	}

	public KJoinDefinition joinAppUser() {
		return APP_USER.on(
			this.APP_USER_ID.eq(APP_USER.ID),
			KEdge.getInstance(Phone.class, this.alias, AppUser.class, APP_USER.getAlias(), "appUser")
		);
	}

	public PhoneMetadata alias(
		final String alias
	) {
		return new PhoneMetadata(this.schema, this.name, alias);
	}

	@Override
	protected Class<? extends KRow> getKRowClass() {
		return Phone.class;
	}
}
