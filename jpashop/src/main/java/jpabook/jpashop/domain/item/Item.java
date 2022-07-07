package jpabook.jpashop.domain.item;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import jpabook.jpashop.domain.Category;
import jpabook.jpashop.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) //상속 관계 - 싱글테이블
@DiscriminatorColumn(name = "dtype")  //상속관계 매핑용
@Getter @Setter
public class Item {
	
	@Id @GeneratedValue
	@Column(name = "item_id")
	private Long id;
	
	private String name;
	private int price;
	private int stockQuantity; //재고
	
	@ManyToMany(mappedBy = "items")
	private List<Category> categories = new ArrayList<>();
	
	//==비즈니스로직==//
	
	// 재고(stock) 증가
	public void addStock(int quantity) {
		this.stockQuantity += quantity;
	}
	// 재고(stock) 감소
	public void removeStock(int quantity) {
		int restStock = this.stockQuantity-quantity;
		if (restStock < 0) {
			throw new NotEnoughStockException("need more stock");
		}
		this.stockQuantity = restStock;
	}
}
