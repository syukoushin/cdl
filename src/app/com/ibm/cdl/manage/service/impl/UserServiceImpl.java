package com.ibm.cdl.manage.service.impl;

import com.ibm.cdl.datamap.constants.Constants;
import com.ibm.cdl.manage.dao.UserDao;
import com.ibm.cdl.manage.pojo.User;
import com.ibm.cdl.manage.service.UserService;
import com.ibm.core.orm.Page;
import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("userService")
public class UserServiceImpl implements UserService {
	
	
	@Autowired
	private UserDao userDao;
	
	public Page<User> findPage(User entity, Page<User> page,User currentUser) {
		StringBuilder hql = new StringBuilder();
		Map<String,Object> pMap = new HashMap<String,Object>();
		hql.append("from User where 1 = 1 ");
		
		// cardno不为空的场合
		if( entity.getUserCode() != null && !"".equals(entity.getUserCode())){
			hql.append(" and userCode= :userCode");
			pMap.put("userCode", entity.getUserCode());
		}
		// 姓名
		if(entity.getUserName() != null && !"".equals(entity.getUserName())){
			hql.append(" and userName like :name");
			pMap.put("name", "%"+entity.getUserName()+"%");
		}
		if(entity.getType() != null && !"".equals(entity.getType())){
			hql.append(" and jobLevel = :type");
			pMap.put("type", entity.getType());
		}

//		// 判断创建人
//		if(Constants.USER_ADMIN.equals(currentUser.getJobLevel())){
//
//		} else if(Constants.USER_SECOND.equals(currentUser.getJobLevel())){
//			hql.append(" and groupId =:groupId ");
//			pMap.put("groupId", currentUser.getGroupId());
//		} else if(Constants.USER_THIRD.equals(currentUser.getJobLevel())){
//			hql.append(" and groupId =:groupId ");
//			pMap.put("groupId", currentUser.getGroupId());
//			hql.append(" and (createBy =:createBy or userCode = :createBy)");
//			pMap.put("createBy", currentUser.getUserCode());
//		}

		hql.append(" order by createTime desc");
		return userDao.findPage(page, hql.toString(),pMap);
	}

	public User getUserByUserCode(String userCode) {
		StringBuilder sb = new StringBuilder();
		sb.append("from User where 1=1 and userCode=:userCode");
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("userCode", userCode);
		Query query = userDao.createQuery(sb.toString(), map);
		List<User> uList = query.list();
		if(uList.size() > 0){
			User u = uList.get(0);
			return u;
		} else {
			return null;
		}
	}
	
	public User getUserByUserCodeAndType(String userCode,String type) {
		StringBuilder sb = new StringBuilder();
		sb.append("from User where 1=1 and userCode=:userCode and type=:type");
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("userCode", userCode);
		map.put("type",type);
		Query query = userDao.createQuery(sb.toString(), map);
		List<User> uList = query.list();
		if(uList.size() > 0){
			User u = uList.get(0);
			return u;
		} else {
			return null;
		}
	}
	
	public User getUserByUserCodeAndTypeForPc(String userCode,String type) {
		StringBuilder sb = new StringBuilder();
		sb.append("from User where 1=1 and userCode=:userCode and type = :type");
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("userCode", userCode);
		map.put("type",type);
		Query query = userDao.createQuery(sb.toString(), map);
		List<User> uList = query.list();
		if(uList.size() > 0){
			User u = uList.get(0);
			return u;
		} else {
			return null;
		}
	}

	/**
	 * 根据公司删除 人员
	 * @param gid
	 */
	public void delByPartGroupId(String gid) {
		Map<String,Object> values  = new HashMap<String, Object>();
		values.put("gid", gid);
		userDao.batchSqlExecute("delete from user where GROUP_ID = :gid", values);
	}

	/**
	 * 判断用户是否存在
	 */
	public boolean checkExistUserCode(String userCode) {
		List<User> uList = userDao.findBy("userCode", userCode);
		if(uList.size() > 0){
			return true;
		} else {
			return false;
		}
	}



	/**
	 * 根据用户名查询用户对象
	 * @param loginName
	 * @return
	 */
	public User queryUserByLoginName(String loginName){
		return userDao.queryUserByLoginName(loginName);
	}

	/**
	 * 后台保存用户
	 * @param entity
	 * @param currentUser  当前用户信息
	 */
	public void saveEntity(User entity,User currentUser){

		// 判断当前用户的级别，只能创建下一级别的用户, 顶级管理员的情况
		if(Constants.USER_ADMIN.equals(currentUser.getJobLevel())){
			entity.setJobLevel(Constants.USER_DEPT);

			// 分公司管理员的情况
		} else if(Constants.USER_DEPT.equals(currentUser.getJobLevel())){
			entity.setJobLevel(Constants.USER_DEPT);
		}
		entity.setCreateBy(currentUser.getUserCode());
		userDao.save(entity);
	}
	
	public List<User> findAll() {
		return userDao.getAll();
	}
	

	public void updateEntity(User entity) {
		userDao.update(entity);
	}

	public void saveEntity(User moaUser) {
		userDao.save(moaUser);
	}

	public User queryUserByUserCode(String userCode){
		return userDao.queryUser(userCode);
	}

	public User queryUserById(String id) {
		return userDao.queryUserById(id);
	}

	public void delEntity(String ids) {
		userDao.delete(ids);
	}


	public Object get(String id) {
		return userDao.get(id);
	}

}
