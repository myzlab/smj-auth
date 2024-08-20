package com.smartjob.auth.k.generated.repository;

import com.myzlab.k.KCrudRepository;
import com.myzlab.k.KBuilder;
import com.myzlab.k.KTable;
import com.myzlab.k.KTableColumn;
import com.smartjob.auth.k.generated.mappers.AppUser;
import com.smartjob.auth.k.generated.metadata.AppUserMetadata;
import static com.smartjob.auth.k.generated.metadata.Tables.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AppUserRepository extends KCrudRepository<AppUser, Long> {

	private final KBuilder k;

	@Override
	public KTable getMetadata() {
		return APP_USER;
	}

	@Override
	public Class getKRowClass() {
		return AppUser.class;
	}

	@Override
	public Class<?> getKMetadataClass() {
		return AppUserMetadata.class;
	}

	@Override
	public KTableColumn getKTableColumnId() {
		return APP_USER.ID;
	}

	@Override
	public KBuilder getK() {
		return k;
	}
}