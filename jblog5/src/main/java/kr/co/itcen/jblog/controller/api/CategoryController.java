package kr.co.itcen.jblog.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import kr.co.itcen.jblog.dto.JSONResult;
import kr.co.itcen.jblog.service.BlogService;
import kr.co.itcen.jblog.vo.CategoryVo;


@RestController("categoryAPiController")
@RequestMapping("/api/{id:(?!assets).*}")
public class CategoryController {
	@Autowired
	private BlogService blogService;
	
	//category 추가하기 
	@PostMapping("/category")
	 public JSONResult addCategory(@PathVariable String id,CategoryVo categoryvo ) {
		 categoryvo.setBlog_id(id);
		 System.out.println("categoryVO: " + categoryvo);
		 
		 // database insert - select last_insert_id() <- no
		 blogService.addcategory(categoryvo);
		 categoryvo.setCount(0L);
		 return JSONResult.success(categoryvo);
	 }
	//category 제거 하기 
	@RequestMapping(value = "/removecategory", method = RequestMethod.POST)
	@ResponseBody
	public JSONResult delcategory(CategoryVo vo) {
		System.out.println(vo);
		Boolean exist=blogService.delcategory(vo.getNo());
		
		return JSONResult.success(exist);
				
		
	}
}
