package kr.co.itcen.jblog.service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import kr.co.itcen.jblog.exception.FileuploadException;
import kr.co.itcen.jblog.repository.BlogDao;
import kr.co.itcen.jblog.repository.CategoryDao;
import kr.co.itcen.jblog.repository.PostDao;
import kr.co.itcen.jblog.vo.BlogVo;
import kr.co.itcen.jblog.vo.CategoryVo;
import kr.co.itcen.jblog.vo.PostVo;

@Service
public class BlogService {
	@Autowired
	private BlogDao blogDao;
	@Autowired
	private CategoryDao categoryDao;
	@Autowired
	private PostDao postDao;
	
	private static final String SAVE_PATH = "/uploads";
	private static final String URL_PREFIX ="/images";
	
	public BlogVo get(String id) {
		return blogDao.get(id);
	}

	public List<CategoryVo> getList(String id) {
		return categoryDao.getList(id);
	}

	public List<PostVo> getpost(Long categoryno) {
		return postDao.getList(categoryno);
	}

	public PostVo getpost(Long postNo, Long categoryno) {
		return postDao.getPost(postNo, categoryno);
	}

	public Boolean update(MultipartFile multipartFile, BlogVo vo){
		String url = "";

		try {
			if(multipartFile == null) {
				return false;
			}
			
			String originalFilename = multipartFile.getOriginalFilename();
			String saveFileName = generateSaveFilename(originalFilename.substring(originalFilename.lastIndexOf('.')+1));
			long fileSize = multipartFile.getSize();
			
		
			byte[] fileData = multipartFile.getBytes();
			OutputStream os =new FileOutputStream(SAVE_PATH +"/"+ saveFileName);
			os.write(fileData);
			os.close();
			//파일 저장/upload
			url = URL_PREFIX+"/" + saveFileName;
		} catch (IOException e) {
			throw new FileuploadException();
		}
		vo.setLogo(url);
		
		return blogDao.update(vo);
		
	}
	private String generateSaveFilename(String extName) {
		String filename = "";
		
		Calendar calendar = Calendar.getInstance();
		filename += calendar.get(Calendar.YEAR);
		filename += calendar.get(Calendar.MONTH);
		filename += calendar.get(Calendar.DATE);
		filename += calendar.get(Calendar.HOUR);
		filename += calendar.get(Calendar.MINUTE);
		filename += calendar.get(Calendar.SECOND);
		filename += calendar.get(Calendar.MILLISECOND);
		filename += ("." + extName);
		
		return filename;
	}

	public void insertpost(PostVo vo) {
		postDao.insertpost(vo);
		
	}

	public int addcategory(CategoryVo categoryvo) {
		return categoryDao.addcategory(categoryvo);
	}

	public Boolean delcategory(Long categoryno) {
		// TODO: 해당 category-no를 가진 post 전체 삭제
		postDao.delpost(categoryno);
		return categoryDao.delcategory(categoryno);
		
	}

	public List<CategoryVo> getcatelist(String id) {
		List<CategoryVo> list =categoryDao.getcatelist(id);
		return list;
	}

}
