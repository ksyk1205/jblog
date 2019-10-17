package kr.co.itcen.jblog.repository;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.co.itcen.jblog.vo.BlogVo;

@Repository
public class BlogDao {
	@Autowired
	private SqlSession sqlSession;

	public Boolean insert(String id) {
		int count = sqlSession.insert("blog.insert" , id);
		return count==1;
	}
	
	public  BlogVo get(String id) {
		BlogVo reasult = sqlSession.selectOne("blog.getId",id);
		return reasult;	
	}

	public Boolean update(BlogVo vo) {
		int count = sqlSession.update("blog.update",vo);
		return count==1;
	}


}
