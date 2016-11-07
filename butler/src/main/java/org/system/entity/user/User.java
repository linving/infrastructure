package org.system.entity.user;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.main.entity.BaseEntity;

public class User extends BaseEntity {

	public interface Login {
	};

	private Integer id;

	private String name;

	@NotBlank(message = "{user.password.notblank.valid}", groups = { Insert.class, Login.class })
	private String password;

	@NotBlank(message = "{user.phone.notblank.valid}", groups = { Insert.class, Login.class })
	private String phone;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Override
	@NotNull(message = "{page.empty}", groups = { SelectAll.class })
	public Integer getPage() {
		return super.getPage();
	}

	@Override
	@NotNull(message = "{rows.empty}", groups = { SelectAll.class })
	public Integer getRows() {
		return super.getRows();
	}
}