package com.smartjob.auth.k.generated.mappers;

import com.myzlab.k.KRow;
import com.myzlab.k.annotations.Id;
import com.myzlab.k.annotations.KMetadata;
import java.time.LocalDateTime;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = true)
public class Phone extends KRow {

	@Id
	@KMetadata(name = "ID")
	private Long id;

	@KMetadata(name = "APP_USER_ID")
	private Long appUserId;

	@KMetadata(name = "NUMBER")
	private String number;

	@KMetadata(name = "CREATED_AT")
	private LocalDateTime createdAt;

	@KMetadata(name = "UPDATED_AT")
	private LocalDateTime updatedAt;

	@KMetadata(name = "CITY_CODE")
	private String cityCode;

	@KMetadata(name = "COUNTRY_CODE")
	private String countryCode;

	private AppUser appUser;

	public Phone setId(Long id) {
		this.id = id;
		this.dirtyProperties.add("id");

		return this;
	}

	public Phone setAppUserId(Long appUserId) {
		this.appUserId = appUserId;
		this.dirtyProperties.add("appUserId");

		return this;
	}

	public Phone setNumber(String number) {
		this.number = number;
		this.dirtyProperties.add("number");

		return this;
	}

	public Phone setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
		this.dirtyProperties.add("createdAt");

		return this;
	}

	public Phone setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
		this.dirtyProperties.add("updatedAt");

		return this;
	}

	public Phone setCityCode(String cityCode) {
		this.cityCode = cityCode;
		this.dirtyProperties.add("cityCode");

		return this;
	}

	public Phone setCountryCode(String countryCode) {
		this.countryCode = countryCode;
		this.dirtyProperties.add("countryCode");

		return this;
	}

	public Phone setAppUser(AppUser appUser) {
		this.appUser = appUser;

		return this;
	}

	@Override
	public Object getPrimaryKeyValue() {
		return this.id;
	}

}
