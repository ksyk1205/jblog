package kr.co.itcen.jblog.repository;



import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.co.itcen.jblog.vo.UserVo;

@Repository
public class UserDao {
	
	@Autowired
	private SqlSession sqlSession;
	//회원 가입을 위한 insert
	public Boolean insert(UserVo vo) {//vo에 name, 비밀번호, 아이디
		int count = sqlSession.insert("user.insert",vo);
		return count==1;
	}
	//id 중복체크
	public UserVo get(String id) {
		UserVo result = sqlSession.selectOne("user.getById",id);
		return result;
	}
	
	//로그인을 위한
	public UserVo get(UserVo vo) {
		UserVo result = sqlSession.selectOne("user.getByIdAndPassword",vo);
		return result;
	}

}
