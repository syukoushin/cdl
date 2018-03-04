package com.ibm.cdl.manage.service;

import com.ibm.cdl.manage.pojo.User;
import com.ibm.core.orm.Page;
import java.util.List;

public interface UserService {



	/**
	 * pc端登录用
	 * @param userCode
	 * @param type
	 * @return
	 */
	public User getUserByUserCodeAndTypeForPc(String userCode,String type);
	
	/**
	 * 登录
	 * @param userCode
	 * @param type
	 * @return
	 */
	public User getUserByUserCodeAndType(String userCode,String type);
	
	/**
     * 查找实体列表
     */
    Page<User> findPage(User entity, Page<User> page,User currentUser);
	
    /**
	 * 更新实体
	 * @param moaUser
	 */
	public void updateEntity(User moaUser) ;
    
	/**
	 * 根据userCode 查询用户
	 * @param userCode
	 * @return
	 */
	public User getUserByUserCode(String userCode);
	
	
	/**
	 * 判断用户是否存在
	 * @param userCode
	 * @return
	 */
	public boolean checkExistUserCode(String userCode);
	

	/**
	 * 根据主键查找MOAUSER
	 * @param id
	 * @return
	 */
	public User queryUserById(String id);
	
	/**
	 * 根据用户名查询用户对象
	 * @param loginName
	 * @return
	 */
	public User queryUserByLoginName(String loginName);
	
	
	/**
	 * 根据主键删除用户
	 * @param ids
	 */
	public void delEntity(String ids);
	
	
	/**
	 * 根据公司删除 人员
	 * @param gid
	 */
	public void delByPartGroupId(String gid);
	
	public Object get(String id);
	
	
	/**
	 * 添加用户  当前级别的用户只能添加下一级的用户
	 * @param moaUser 添加的用户信息
	 * @param currentUser  当前用户信息
	 */
	public void saveEntity(User moaUser,User currentUser);

	/**
	 * 保存用户
	 * @param moaUser
	 */
	public void saveEntity(User moaUser);

	/**
	 * 查询所有用户
	 * @return
	 */
	public List<User> findAll();
	
}
