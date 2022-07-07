package jpabook.jpashop.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import javax.persistence.EntityManager;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class MemberServiceTest {

	@Autowired
	MemberService memberService;
	@Autowired
	MemberRepository memberRepository;
	@Autowired
	EntityManager em;
	
	@Test
	public void 회원가입() throws Exception{
		//given
		Member member = new Member();
		member.setName("kim");
		//when
		Long savedId = memberService.join(member);
		//then
		em.flush(); 	// 영속성 컨텍스트에 있는 변경/등록 내용을 DB에 반영해줌
		assertEquals(member, memberRepository.findOne(savedId));
	}

	@Test(expected = IllegalStateException.class)
	public void 중복_회원_예외() throws Exception{
		//given
		Member member1 = new Member();
		member1.setName("kim");
		
		Member member2 = new Member();
		member2.setName("kim");
		//when
		memberService.join(member1);
		memberService.join(member2); //동일 이름이므로 예외 발생해야 함!
		//then
		fail("예외가 발생해야 한다.");
	}

}
