package jpabook.jpashop.domain.item;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;

@Entity
@DiscriminatorValue("M") //싱글테이블 상속이라 구분하기 위한게 필요함
@Getter @Setter
public class Movie extends Item {

	private String director;
	private String actor;
}
