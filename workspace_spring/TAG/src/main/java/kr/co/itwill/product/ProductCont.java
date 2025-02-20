 package kr.co.itwill.product;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;


import kr.co.itwill.QnA.QnADTO;

import kr.co.itwill.concert.ConcertDTO;

@Controller
public class ProductCont {

	public ProductCont() {
		System.out.println("------- ProductCont() 객체 생성됨 ");
	}//ProductCont() end
	
	@Autowired
	ProductDAO productDao;
	
	
	
//  [상품리스트 - 전체보기] 시작 - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -  //
	

    @RequestMapping("/list.do")
    public ModelAndView list(HttpServletRequest req) {
    	
    	//System.out.println(req.getParameter("category"));
    	String category = req.getParameter("category");// A C M P
 
    	
        ModelAndView mav = new ModelAndView();
        mav.setViewName("product/list");
        
        int totalRowCount = 0;
        if(category==null) {
        	totalRowCount = productDao.total(); // 카테고리 전체 총 글갯수
        }else {
        	totalRowCount = productDao.categoryTotal(category); // 카테고리별 글 개수
        }//if end
        
        // 페이징 파트
        int numPerPage = 9; // 한 페이지당 레코드(글) 갯수
        int pagePerBlock = 5; // 페이지 리스트 (블럭당 페이지 수)

        // 현재 페이지 번호 (문자형)
        String pageNum = req.getParameter("pageNum");
        //System.out.println(pageNum);
        
        if (pageNum == null) {
            pageNum = "1";
        } // if end

        int currentPage = Integer.parseInt(pageNum);
        int startRow = (currentPage - 1) * numPerPage + 1;
        int endRow = currentPage * numPerPage;
        double totcnt = (double) totalRowCount / numPerPage;
        int totalPage = (int) Math.ceil(totcnt);
        double d_page = (double) currentPage / pagePerBlock;
        int Pages = (int)Math.ceil(d_page)-1;
        int startPage = Pages * pagePerBlock+1;
        int endPage = startPage + pagePerBlock-1;
        
        List list = null;
        if (totalRowCount > 0) {
        	if (category==null) {
        		list = productDao.list(startRow, endRow);
        		//System.out.println(productDao.list(startRow, endRow));
        	}else {
                list = productDao.list2(startRow, endRow, category);//1, 5, M
                //System.out.println(productDao.list2(startRow, endRow, category));
        	}
        } else {
            list = Collections.emptyList(); // 안 넣어도 상관 없음
        } // if end
    	
        //System.out.println(productDao.list(startRow, endRow));
        
 	    mav.addObject("total", totalRowCount);
 	    mav.addObject("category", category);
 	    mav.addObject("categoryAll", productDao.categoryAll());
        mav.addObject("list", list);
        mav.addObject("pageNum", currentPage);

        mav.addObject("count", totalRowCount);
        mav.addObject("totalPage", totalPage);
        mav.addObject("startPage", startPage);
        mav.addObject("endPage", endPage);
        
        return mav;
        
    }//list() end
    
    
    
 // [상품리스트 - 콘서트 카테고리] - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
	
 	@RequestMapping("/listConcert")
 	public ModelAndView concertList(HttpServletRequest req) {
 		//System.out.println("잘 도착 했다.");
 		
 		String c_no = req.getParameter("c_no");
 		//System.out.println(c_no);
 		
 		ModelAndView mav = new ModelAndView();
 		mav.setViewName("product/listConcert");
 		
 		int totalRowCount = productDao.concertTotal(c_no);
 		//System.out.println(totalRowCount);
 		
 		
        // 페이징 파트
        int numPerPage = 9; // 한 페이지당 레코드(글) 갯수
        int pagePerBlock = 5; // 페이지 리스트 (블럭당 페이지 수)

        // 현재 페이지 번호 (문자형)
        String pageNum = req.getParameter("pageNum");
        //System.out.println(pageNum);
        
        if (pageNum == null) {
            pageNum = "1";
        } // if end

        int currentPage = Integer.parseInt(pageNum);
        int startRow = (currentPage - 1) * numPerPage + 1;
        int endRow = currentPage * numPerPage;
        double totcnt = (double) totalRowCount / numPerPage;
        int totalPage = (int) Math.ceil(totcnt);
        double d_page = (double) currentPage / pagePerBlock;
        int Pages = (int)Math.ceil(d_page)-1;
        int startPage = Pages * pagePerBlock+1;
        int endPage = startPage + pagePerBlock-1;
        
        List list = null;
        if (totalRowCount > 0) {
        	list = productDao.concertList(startRow, endRow, c_no);//1, 5, M
        } else {
            list = Collections.emptyList(); // 안 넣어도 상관 없음
        } // if end
    	
 	    mav.addObject("total", totalRowCount);
 	    mav.addObject("c_no", c_no);
 	    mav.addObject("categoryAll", productDao.categoryAll());
        mav.addObject("list", list);
        mav.addObject("pageNum", currentPage);

        mav.addObject("count", totalRowCount);
        mav.addObject("totalPage", totalPage);
        mav.addObject("startPage", startPage);
        mav.addObject("endPage", endPage);
 	
 		
 		mav.addObject("categoryAll", productDao.categoryAll());
 		mav.addObject("concertlist", productDao.concert());
 		
 		//System.out.println("컨트롤러 확인" + productDao.concert());
 		//System.out.println(productDao.list());
 		
 		return mav;
 	}//listConcert() end    
 	

    
//  [상품검색] 시작  - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -  //
    
	@RequestMapping("/search")
	public ModelAndView search(@RequestParam(defaultValue = "") String pro_name) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("product/list");
		mav.addObject("list", productDao.search(pro_name));
		
		mav.addObject("pro_name", pro_name);
		
		return mav;
	}//search() end

	

//  [상품리스트 - 음반 카테고리] 시작  - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
    
	@RequestMapping("/music")
    public ModelAndView music(String category) {
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("product/list");
		
		System.out.println(category);
   
	    return mav;
	}//search() end	
	
//  - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //

	
	@RequestMapping("/product/{pro_no}")
	public ModelAndView productdetail(@PathVariable int pro_no, HttpSession session) {
		ModelAndView mav = new ModelAndView();
		
		String m_id = (String)session.getAttribute("s_m_id");
		
		mav.setViewName("/product/detail");
		//System.out.println(pro_no);
		mav.addObject("proDetail", productDao.proDetail(pro_no));
		mav.addObject("pro_qnacnt",productDao.pro_qnacnt(pro_no));
		mav.addObject("qnalist",productDao.qnalist(pro_no));
		
		if(!(m_id == null)) {
			mav.addObject("likechk", productDao.likechk(m_id, pro_no));
		} 
		
		return mav;
	}// end
	
	@ResponseBody
	@RequestMapping(value = "/product/qnainsert", method = RequestMethod.POST)
	public int qnainsert(@ModelAttribute QnADTO dto) {
		return productDao.qnainsert(dto);
	}//end
	
	@ResponseBody
	@RequestMapping(value = "/product/qnadetail", method = RequestMethod.POST)
	public Map<String, Object> qnadetail(@RequestParam int q_no, @RequestParam int passwd) {		
		Map<String, Object> map = new HashMap<String, Object>();
		
		QnADTO dto = productDao.qnadetail(q_no, passwd); 

		map.put("subject", dto.getSubject());
		map.put("edit", dto.getEdit());
		map.put("content", dto.getContent());
		map.put("regdate", dto.getRegdate());
		
		return map;
	}//end
	
	@ResponseBody
	@RequestMapping(value = "/product/addcart", method = RequestMethod.POST)
	public int addcart(@RequestParam int cnt, @RequestParam int pro_no, @RequestParam String m_id) {
		return productDao.addcart(cnt, pro_no, m_id);
	}
	
	@ResponseBody
	@RequestMapping(value = "/product/like", method = RequestMethod.POST)
	public int like(@RequestParam int pro_no, @RequestParam String m_id) {
		return productDao.like(pro_no, m_id);
	}
	
	@ResponseBody
	@RequestMapping(value = "/product/unlike", method = RequestMethod.POST)
	public int unlike(@RequestParam int pro_no, @RequestParam String m_id) {
		return productDao.unlike(pro_no, m_id);
	}	
	
	//order - payment
	
	@RequestMapping("/product/order")
	public ModelAndView order(){
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/product/order");
		
		return mav;
	}
	
}//class end

