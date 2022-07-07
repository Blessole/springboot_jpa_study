package jpabook.jpashop.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="orders")  //이름 명명 안해주면 관례에 따라 order라고 만들어져버림
@Getter @Setter
public class Order {
	
	@Id @GeneratedValue
	@Column(name="order_id")
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="member_id") 		// 관계 주인!
	private Member member;
	
	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL)  
	//orderItem의 order 에 의해 매핑됨! (관계주인 : orderItem-order)
	//cascadeType.all : persist 하면 묶여있는게 죄다 persist됨~ 뭐 이런식 
	//> 여러군데서 다 갖다쓰는 중요한 엔티티일 경우, 캐스케이드 절대 금지, 그냥 레파지토리 각각 만들어서 persist 매번 해주는게 좋음
	private List<OrderItem> orderItems = new ArrayList<>();
	
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "delivery_id")		// 관계 주인!
	private Delivery delivery;
	
	private LocalDateTime orderDate;  //Date 말고 LocalDateTime 쓰면 하이버네이트가 알아서 처리해줌!
	
	@Enumerated(EnumType.STRING)  //Delivery.java에 설명 있음
	private OrderStatus status; //주문 상태 : Order, Cancel 두가지 넣을거임
	
	
	//== 연관관계 메서드 ==//
	public void setMember(Member member) {
		this.member = member;
		member.getOrders().add(this);
	}
	public void addOrderItem(OrderItem orderItem) {
		orderItems.add(orderItem);
		orderItem.setOrder(this);
	}
	public void setDelivery(Delivery delivery) {
		this.delivery = delivery;
		delivery.setOrder(this);
	}
	
	//== 생성 메서드 ==//
	public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems) {
		Order order = new Order();
		order.setMember(member);
		order.setDelivery(delivery);
		for (OrderItem orderItem : orderItems) {
			order.addOrderItem(orderItem);
		}
		order.setStatus(OrderStatus.ORDER);
		order.setOrderDate(LocalDateTime.now());
		return order;
	}
	
	//==비즈니스 로직 ==//
	//주문취소 할 경우
	public void cancel() {
		if(delivery.getStatus() == DeliveryStatus.COMP) {
			throw new IllegalStateException("이미 배송 완료된 상품은 취소가 불가능 합니다.");
		}
		this.setStatus(OrderStatus.CANCEL);
		for (OrderItem orderItem : orderItems) {
			orderItem.cancel();
		}
	}
	
	//== 조회 로직 ==//
	// 전체주문 가격 조회
	public int getTotalPrice() {
		int totalPrice = 0;
		for (OrderItem orderItem : orderItems) {
			totalPrice += orderItem.getTotalPrice();
		}
		return totalPrice;
		// return orderItems.stream().mapToInt(OrderItem::getTotalPrice).sum();
	}
}
