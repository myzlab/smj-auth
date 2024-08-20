package com.smartjob.auth.k.generated.mappers;

import com.myzlab.k.KRow;
import com.myzlab.k.annotations.Id;
import com.myzlab.k.annotations.KMetadata;
import java.time.LocalDateTime;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = true)
public class AppUser extends KRow {

	@Id
	@KMetadata(name = "ID")
	private Long id;

	@KMetadata(name = "EMAIL")
	private String email;

	@KMetadata(name = "PASSWORD")
	private String password;

	@KMetadata(name = "NAME")
	private String name;

	@KMetadata(name = "IS_ACTIVE")
	private Boolean isActive;

	@KMetadata(name = "LAST_LOGIN")
	private LocalDateTime lastLogin;

	@KMetadata(name = "CREATED_AT")
	private LocalDateTime createdAt;

	@KMetadata(name = "UPDATED_AT")
	private LocalDateTime updatedAt;

	public AppUser setId(Long id) {
		this.id = id;
		this.dirtyProperties.add("id");

		return this;
	}

	public AppUser setEmail(String email) {
		this.email = email;
		this.dirtyProperties.add("email");

		return this;
	}

	public AppUser setPassword(String password) {
		this.password = password;
		this.dirtyProperties.add("password");

		return this;
	}

	public AppUser setName(String name) {
		this.name = name;
		this.dirtyProperties.add("name");

		return this;
	}

	public AppUser setIsActive(Boolean isActive) {
		this.isActive = isActive;
		this.dirtyProperties.add("isActive");

		return this;
	}

	public AppUser setLastLogin(LocalDateTime lastLogin) {
		this.lastLogin = lastLogin;
		this.dirtyProperties.add("lastLogin");

		return this;
	}

	public AppUser setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
		this.dirtyProperties.add("createdAt");

		return this;
	}

	public AppUser setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
		this.dirtyProperties.add("updatedAt");

		return this;
	}

	@Override
	public Object getPrimaryKeyValue() {
		return this.id;
	}

}
