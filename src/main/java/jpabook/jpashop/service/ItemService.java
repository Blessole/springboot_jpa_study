package jpabook.jpashop.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {
	
	private final ItemRepository itemRepository;
	
	@Transactional    // 이건 데이터 쓰는용이니까 다시 transac 덮어씌워줘야해! 전체 적용된게 readOnly니까~
	public void saveItem(Item item) {
		itemRepository.save(item);
	}
	
	@Transactional
	public Item updateItem(Long itemId, Book param) {
		//변경 감지 (엔티티 변경시에는 꼭 변경감지를 사용하기!) - dirty checking
		Item findItem = itemRepository.findOne(itemId);
		
		// findItem.change(price,name,stockQuantity);
		// 밑에처럼 set 사용하는 것보다 위에처럼 메소드 하나 만들어서 변경해주는게 더 좋음
		// 추적이 쉬워지기 때문! 
		
		findItem.setPrice(param.getPrice());
		findItem.setName(param.getName());
		findItem.setStockQuantity(param.getStockQuantity());
		
		return findItem;
	}
	
	//변경감지2 코드 - 훨씬 더 좋은 코드
	//return 할게 없으니까 void! (Item이 아님)
	@Transactional
	public void updateItem2(Long itemId, int price, String name, int stockQuantity) {
		Item findItem = itemRepository.findOne(itemId);
		findItem.setPrice(price);
		findItem.setName(name);
		findItem.setStockQuantity(stockQuantity);
	}
	
	
	public List<Item> findItems(){
		return itemRepository.findAll();
	}
	
	public Item findOne(Long itemId) {
		return itemRepository.findOne(itemId);
	}
}
