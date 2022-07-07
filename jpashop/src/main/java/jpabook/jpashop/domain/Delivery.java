package jpabook.jpashop.domain;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Delivery {
	
	@Id @GeneratedValue
	@Column(name = "delivery_id")
	private Long id;
	
	@OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY)
	private Order order;
	
	@Embedded
	private Address address;
	
	@Enumerated(EnumType.STRING)
	 //EnumType.ORDINAL : 0,1,2,3,4 숫자로 들어감 - ready(0), comp(1) 뭐 이런 식
	//								  > 이건 중간에 다른 상태값이 들어가면 아예 오류나버림!
	//따라서, EnumType.STRING 을 사용하는 것을 권장
	private DeliveryStatus status; //ready, comp
}
