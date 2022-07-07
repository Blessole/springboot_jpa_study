package jpabook.jpashop.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MemberRepository {
	
	private final EntityManager em;
	
	public void save(Member member) {
		em.persist(member);  //영속성
	}
	public Member findOne(Long id) {
		return em.find(Member.class, id);	//단건조회
	}
	
	public List<Member> findAll(){
		return em.createQuery("select m from Member m", Member.class).getResultList(); 	//from의 대상은 entity임. (테이블이 아님!)
	}
	
	public List<Member> findByName(String name){
		return em.createQuery("select m from Member m where m.name=:name", Member.class).setParameter("name", name).getResultList();
	}
}
