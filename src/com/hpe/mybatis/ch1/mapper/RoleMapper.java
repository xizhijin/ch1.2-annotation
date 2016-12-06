package com.hpe.mybatis.ch1.mapper;

import org.apache.ibatis.annotations.Select;

import com.hpe.mybatis.ch1.po.Role;

public interface RoleMapper {
	@Select (value="select id, rolename, note from t_role where id = #{id}")
	public Role getRole(Long id);
	@Select (value="delete from t_role where id = #{id}")
	public void deleteRole(Long id);
	@Select (value="insert into t_role(rolename, note) values (#{roleName}, #{note})")
	public void insertRole(Role role);
}
