package com.smartjob.auth.k.generated.repository;

import com.myzlab.k.KCrudRepository;
import com.myzlab.k.KBuilder;
import com.myzlab.k.KTable;
import com.myzlab.k.KTableColumn;
import com.smartjob.auth.k.generated.mappers.Phone;
import com.smartjob.auth.k.generated.metadata.PhoneMetadata;
import static com.smartjob.auth.k.generated.metadata.Tables.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PhoneRepository extends KCrudRepository<Phone, Long> {

	private final KBuilder k;

	@Override
	public KTable getMetadata() {
		return PHONE;
	}

	@Override
	public Class getKRowClass() {
		return Phone.class;
	}

	@Override
	public Class<?> getKMetadataClass() {
		return PhoneMetadata.class;
	}

	@Override
	public KTableColumn getKTableColumnId() {
		return PHONE.ID;
	}

	@Override
	public KBuilder getK() {
		return k;
	}
}