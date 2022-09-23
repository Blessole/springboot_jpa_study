package jpabook.jpashop.repository;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import jpabook.jpashop.domain.item.Item;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

	private final EntityManager em;
	
	//상품 신규등록
	public void save(Item item) {
		if (item.getId() == null) {			//이전에 이런 상품이 없었으면 null!
			em.persist(item);		// 신규등록~
		} else {
			/* Item merge = */ em.merge(item);			
			//이전에 이런 상품있었으면 요걸로 강제 update하라는거!(실무에서는 병합보단 변경감지 추천)
			//병합은 값이 안넘어오면 null로 업데이트 해버리기 때문
		}
	}
	
	//상품 단건 조회
	public Item findOne(Long id) {
		return em.find(Item.class, id);  // 단건은 이렇게만 적어도 되고, 여러개 조회는 sql문
	}
	
	// 상품 전체 조회
	public List<Item> findAll(){
		return em.createQuery("select i from Item i", Item.class).getResultList();
	}
}
