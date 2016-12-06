package com.hpe.mybatis.ch1.util;

import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

import com.hpe.mybatis.ch1.mapper.RoleMapper;
import com.hpe.mybatis.ch1.po.Role;

public class SqlSessionFactoryUtil {
	/**
	 * SqlSessionFactory对象
	 */
	private static SqlSessionFactory sqlSessionFactory = null;
	/**
	 * 类线程锁
	 */
	private static final Class<?> CLASS_LOCK = SqlSessionFactoryUtil.class;
	
	/**
	 * 私有化构造器
	 */
	private SqlSessionFactoryUtil() {
		
	}
	
	/**
	 * 构建SqlSessionFactory
	 */
	public static SqlSessionFactory initSqlSessionFactory() {
		//构建数据库连接池
		PooledDataSource dataSource = new PooledDataSource();
		dataSource.setDriver("com.mysql.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://localhost:3306/mybatis");
		dataSource.setUsername("root");
		dataSource.setPassword("1231527");
		//构建数据库事务方式
		TransactionFactory transactionFactory = new JdbcTransactionFactory();
		//创建了数据库运行环境
		Environment environment = new Environment("development", transactionFactory, dataSource);
		//构建Configuration对象
		Configuration configuration = new Configuration(environment);
		//注册一个MyBatis上下文别名
		configuration.getTypeAliasRegistry().registerAlias("role", Role.class);
		//加入一个映射器
		configuration.addMapper(RoleMapper.class);
		//使用SqlSessionFactoryBuilder构建SqlSessionFactory
		synchronized(CLASS_LOCK) {
			if (sqlSessionFactory == null) {
				sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);
			}
		}
		
		return sqlSessionFactory;
	}
	
	/**
	 * 打开SqlSession
	 */
	public static SqlSession openSqlSession() {
		if(sqlSessionFactory == null) {
			initSqlSessionFactory();
		}
		return sqlSessionFactory.openSession();
	}
}
