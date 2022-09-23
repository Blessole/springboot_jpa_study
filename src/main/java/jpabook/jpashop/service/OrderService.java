package jpabook.jpashop.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import jpabook.jpashop.domain.Delivery;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import jpabook.jpashop.repository.MemberRepository;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.OrderSearch;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

	private final OrderRepository orderRepository;
	private final MemberRepository memberRepository;
	private final ItemRepository itemRepository;
	private final EntityManager em;
	
	//주문
	@Transactional
	public Long order(Long memberId, Long itemId, int count) {
		
		//엔티티 조회
		Member member = memberRepository.findOne(memberId);
		Item item = itemRepository.findOne(itemId);
		
		//배송정보 생성
		Delivery delivery = new Delivery();
		delivery.setAddress(member.getAddress());
		
		//주문 상품 생성
		OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);
		
		//주문 생성
		Order order = Order.createOrder(member, delivery, orderItem);
		
		//주문 저장
		orderRepository.save(order);
		
		return order.getId();
	}
	//취소
	@Transactional
	public void cancelOrder(Long orderId) {
		//주문 엔티티 조회
		Order order = orderRepository.findOne(orderId);
		//주문 취소
		order.cancel();
	}
	
	//검색
	public List<Order> findOrders(OrderSearch orderSearch) {
		return orderRepository.findAllByString(orderSearch);
	}
	
	/*
	public List<Order> findAllByCriteria(OrderSearch orderSearch) {
		 CriteriaBuilder cb = em.getCriteriaBuilder();
		 CriteriaQuery<Order> cq = cb.createQuery(Order.class);
		 Root<Order> o = cq.from(Order.class);
		 Join<Order, Member> m = o.join("member", JoinType.INNER); //회원과 조인
		 List<Predicate> criteria = new ArrayList<>();
		 //주문 상태 검색
		 if (orderSearch.getOrderStatus() != null) {
		 Predicate status = cb.equal(o.get("status"),
		orderSearch.getOrderStatus());
		 criteria.add(status);
		 }
		 //회원 이름 검색
		 if (StringUtils.hasText(orderSearch.getMemberName())) {
		 Predicate name =
		 cb.like(m.<String>get("name"), "%" +
		orderSearch.getMemberName() + "%");
		 criteria.add(name);
		 }
		 cq.where(cb.and(criteria.toArray(new Predicate[criteria.size()])));
		 TypedQuery<Order> query = em.createQuery(cq).setMaxResults(1000); //최대1000건
		 return query.getResultList();
		}
	*/
}
