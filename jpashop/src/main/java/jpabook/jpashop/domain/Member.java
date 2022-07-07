package jpabook.jpashop.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Member {
	
	@Id @GeneratedValue
	@Column(name="member_id")
	private Long id;
	
	private String name;
	
	@Embedded
	private Address address;
	
	@OneToMany(mappedBy = "member") //orders 테이블의 member에 의해서 매핑됨(관계 주인은 orders-member!)
	private List<Order> orders = new ArrayList<>();   //1:다 양방향 매핑
}
