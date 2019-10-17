package kr.co.itcen.jblog.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.co.itcen.jblog.vo.CategoryVo;


@Repository
public class CategoryDao {
	@Autowired
	private SqlSession sqlSession;
	
	public void default_insert(String blog_id) {
		sqlSession.insert("category.default_insert",blog_id);
		
	}

	public List<CategoryVo> getList(String blog_id) {
		List<CategoryVo> list = sqlSession.selectList("category.getId",blog_id);
		return list;
	}

	public int addcategory(CategoryVo categoryvo) {
		return sqlSession.insert("category.addcategory", categoryvo);
	}

	public Boolean delcategory(Long categoryno) {
		int count = sqlSession.delete("category.delcategory",categoryno);
		return count ==1;
	}

	public List<CategoryVo> getcatelist(String id) {
		return sqlSession.selectList("category.postcount",id);
	}
	



}
