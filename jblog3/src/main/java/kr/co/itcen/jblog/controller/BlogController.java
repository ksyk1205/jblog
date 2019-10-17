package kr.co.itcen.jblog.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import kr.co.itcen.jblog.service.BlogService;
import kr.co.itcen.jblog.vo.BlogVo;
import kr.co.itcen.jblog.vo.CategoryVo;
import kr.co.itcen.jblog.vo.PostVo;
import kr.co.itcen.jblog.vo.UserVo;

@Controller
@RequestMapping("/{id:(?!assets)(?!images).*}")//assets,images가 아닌 것들만 id에 맵핑
public class BlogController {
	
	@Autowired
	private BlogService blogService;
	
	
	
	//1)jblog3/id
	//2)jblog3/id/pathNo1//카테고리 링크
	//3)jblog3/id/pathNo1/pathNo2 // post링크
	@RequestMapping( {"", "/{pathNo1}", "/{pathNo1}/{pathNo2}" } )
	public String index( 
		@PathVariable String id,
		@PathVariable Optional<Long> pathNo1,
		@PathVariable Optional<Long> pathNo2,
		Model model, UserVo vo) {
		
		
		List<CategoryVo> list = blogService.getList(id);
		model.addAttribute("list",list);
		Long categoryno= 0L;
		if(!list.isEmpty()) {
			categoryno= list.get(0).getNo();//[0][1][2]...
		}
		
		Long postNo = 0L;
		
		//2),3)일떄만 동작
		if(pathNo2.isPresent()) { //pathNo2가 존재할 때  
			categoryno=pathNo1.get(); 
			postNo=pathNo2.get();
		}else if(pathNo1.isPresent()) { 
			categoryno=pathNo1.get();
		}	
		
		
		List <PostVo> postlist = blogService.getpost(categoryno);
		model.addAttribute("postlist",postlist);
		
		
		PostVo postvo;
		if(postNo==0 && (!postlist.isEmpty())) { //postNo가 초기값이고 postlist가 비어있지 않을 때
			//postNo==0 -> 나갔다 들어왔을 때 0으로 세팅
			//2번째는 postlist가 있지만 리스트가 있는지 없는지를 비교
			postNo=postlist.get(0).getNo();
			postvo=blogService.getpost(postNo,categoryno);
		}else {//
			postvo=blogService.getpost(postNo,categoryno);
			
		}
		
		BlogVo blogvo = blogService.get(id);
		model.addAttribute("blogvo",blogvo);
		model.addAttribute("postvo",postvo);
		model.addAttribute("id",id);
		
		System.out.println(vo.getId());
		
		
		if(id.equals(vo.getId()))
			model.addAttribute("isMe",true);
		
		return "blog/blog-main";
	}
	//자기 자신 블로그 기본 관리 화면 
	@RequestMapping("/admin/basic")
	public String blogmanagement(@PathVariable String id ,Model model){
		BlogVo vo =	blogService.get(id);
		
		model.addAttribute("blogvo", vo);
		
		return "blog/blog-admin-basic";
		
	}
	//블로그 기본관리 화면에서 title과 logo수정 
	@RequestMapping(value="/admin/basic",method = RequestMethod.POST )
	public String blogmanagement(
			@RequestParam(value="logo-file",required=false) MultipartFile multipartFile,
			@PathVariable String id, 
			BlogVo vo){//파일 파라미터 받고 title받고
						//서비스호출,파일 업로드한 URL을 db에 저장
		vo.setId(id);
		blogService.update(multipartFile,vo);
		return "redirect:/"+id;
		
	}
	
	//블로그 기본 관리 화면에서 카테고리 수정 
	@RequestMapping("/admin/category")
	public String categorymanagement(@PathVariable String id, Model model) {
		List<CategoryVo> catelist = blogService.getcatelist(id);
		model.addAttribute("catelist",catelist);
		
		BlogVo vo =	blogService.get(id);
		model.addAttribute("blogvo", vo);
		return "blog/blog-admin-category";
		
	}
	
	//블로그 기본관리 화면에서 포스트 작성 
	@RequestMapping("/admin/write")
	public String  write(@PathVariable String id, Model model) {
		List<CategoryVo> catevo = blogService.getList(id);
		model.addAttribute("catevo",catevo);
		BlogVo vo =	blogService.get(id);
		
		model.addAttribute("blogvo", vo);
		return "blog/blog-admin-write";
	}
	
	//카테고리 no에 포스트 작성
	@RequestMapping(value="/admin/write",method = RequestMethod.POST)
	public String write(@PathVariable String id , PostVo vo , @RequestParam("category") Long no) {
		
		vo.setCategory_no(no);
		blogService.insertpost(vo);
		
		return "redirect:/"+id;
	}
}


