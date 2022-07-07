package jpabook.jpashop.domain.item;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;

@Entity
@DiscriminatorValue("B") //싱글테이블 상속이라 구분하기 위한게 필요함
@Getter @Setter
public class Book extends Item {

	private String author;
	private String isbn;
	
}
