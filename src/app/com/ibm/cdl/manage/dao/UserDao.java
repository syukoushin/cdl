package com.ibm.cdl.manage.dao;

import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import com.ibm.cdl.manage.pojo.User;
import com.ibm.core.orm.Page;
import com.ibm.core.orm.hibernate.SessionFactoryDao;

@Repository("userDao")
public class UserDao extends SessionFactoryDao<User, String> {
	
	
	/**
	 * 根据id查找MoaUser
	 * @param id
	 * @return
	 */
	public User queryUserById(String id){
		return this.findUniqueBy("id", id);
	}
	
	/**
	 * 查询用户列表
	 */
	public Page<User> queryMoaUserList(Page<User> page , User entity) {
		String hql = "from User moaUser, Dept dept where moaUser.deptCode=dept.deptCode ";
		if(StringUtils.isNotEmpty(entity.getDeptCode())){
			hql += " and dept.deptCode = '"+entity.getDeptCode()+"'";
		}
		hql += "order by moaUser.jobLevel,moaUser.seq";
		return findPage(page,hql);
	}
	
	/**
	 * 根据用户名查询用户对象
	 * @param loginName
	 * @return
	 */
	public User queryUserByLoginName(String loginName){
		String hql = "from User where userCode='"+loginName+"'";
		return findUnique(hql);
	}


	/**
	 * 根据全部获取用户信息
	 */
	public List<User> getAll(){
		return find(" from User order by seq asc");
	}
	
	
	/**
	 * 删除用户表所有数据
	 */
	public void deleteUser() {
		String hql = "delete from User";
		batchExecute(hql);
	}
	
	
	public List<User> findUserByUserCode(String userCode) {
		 return find("from User where userCode = ?",new Object[]{userCode});
	}
	
	
	public User queryUser(String userCode) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(User.class);
		dCriteria.add(Restrictions.eq("userCode", userCode));
		List list = dCriteria.getExecutableCriteria(getSession()).list();
		if (list == null || list.size() == 0) return null;
		return (User) list.get(0);
	}
	
}
