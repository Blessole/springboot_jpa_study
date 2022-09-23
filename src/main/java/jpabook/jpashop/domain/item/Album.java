package jpabook.jpashop.domain.item;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;

@Entity
@DiscriminatorValue("A") //싱글테이블 상속이라 구분하기 위한게 필요함
@Getter @Setter
public class Album extends Item {
	private String artist;
	private String etc;
}
