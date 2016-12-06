package com.hpe.mybatis.ch1.main;

import org.apache.ibatis.session.SqlSession;

import com.hpe.mybatis.ch1.mapper.RoleMapper;
import com.hpe.mybatis.ch1.po.Role;
import com.hpe.mybatis.ch1.util.SqlSessionFactoryUtil;

public class Ch1Main {

	public static void main(String[] args) {
		SqlSession sqlSession = null;
		try {
			sqlSession = SqlSessionFactoryUtil.openSqlSession();
			RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
			Role role = new Role();
			role.setRoleName("testName1");
			role.setNote("testNote1");
			roleMapper.insertRole(role);
			
			roleMapper.deleteRole(1L);
			
			sqlSession.commit();
		} catch(Exception ex) {
			System.out.println(ex.getMessage());
			sqlSession.rollback();
		} finally {
			if (sqlSession != null) {
				sqlSession.close();
			}
		}
	}

}
