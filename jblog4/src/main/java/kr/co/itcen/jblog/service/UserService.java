package kr.co.itcen.jblog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.itcen.jblog.repository.BlogDao;
import kr.co.itcen.jblog.repository.CategoryDao;
import kr.co.itcen.jblog.repository.UserDao;
import kr.co.itcen.jblog.vo.UserVo;

@Service
public class UserService {
	@Autowired
	private UserDao userDao;
	@Autowired
	private BlogDao blogDao;
	@Autowired
	private CategoryDao categoryDao;
	
	//회원가입을 위한 insert
	public void join(UserVo vo) {
		userDao.insert(vo);
		//회원가입했을 때 블로그 추가 
		blogDao.insert(vo.getId());
		//회원가입했을 때 default 값 카테고리 추가 
		categoryDao.default_insert(vo.getId());
	}
	//id 중복체크를 위한 
	public Boolean existUser(String id) {
		return userDao.get(id)!=null;
	}
	//로그인을 위한
	public UserVo getUser(UserVo vo) {
		return userDao.get(vo);
	}

}
