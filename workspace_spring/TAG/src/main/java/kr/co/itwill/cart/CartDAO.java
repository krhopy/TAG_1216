package kr.co.itwill.cart;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CartDAO {
	
	public CartDAO() {
		System.out.println("------- CartDAO() 객체 생성됨 ");
	}//CartDAO() end
	
	@Autowired
	SqlSession sqlSession;
	
	
//  [장바구니 리스트] 시작 - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -  //
	public List<Map<String, String>> mycartList(String m_id){
		
		HashMap<String, String> map  = new HashMap<>();
		map.put("m_id", m_id);
		
		return sqlSession.selectList("cart.mycartList", map);
	}//mycartList() end
	
	
//  [장바구니 리스트] 시작 - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -  //
	public List<Map<String, String>> deliveryFee(String m_id){		
		HashMap<String, String> map  = new HashMap<>();
		map.put("m_id", m_id);
		
		return sqlSession.selectList("cart.deliveryFee", m_id);
	}//mycartList() end
	


}//class end
