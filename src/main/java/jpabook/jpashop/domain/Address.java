package jpabook.jpashop.domain;

import javax.persistence.Embeddable;

import lombok.Getter;

@Embeddable // 내장될 수 있다 (member의address어노테이션과 연결됨)
@Getter // 값 타입은 변경 불가능하게 해야하므로 @Setter 제거해야 함
public class Address {
	private String city;
	private String street;
	private String zipcode;

	// 값 타입 변경 불가능하게 해야하므로 - 생성자에서 값을 모두 초기화 해서 변경 불가능한 클래스를 만들어야 함
	// JPA 스펙상, @Entity나 @Embeddable 타입은 기본 생성자를 public 또는 protected로 설정해야함(public보단 protected로 만드는게 더 안전)
	protected Address() {
	}

	public Address(String city, String street, String zipcode) {
		this.city = city;
		this.street = street;
		this.zipcode = zipcode;
	}

}
