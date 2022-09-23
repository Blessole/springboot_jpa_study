/*
 * package jpabook.jpashop;
 * 
 * import org.assertj.core.api.Assertions; import org.junit.jupiter.api.Test;
 * import org.junit.runner.RunWith; import
 * org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.boot.test.context.SpringBootTest; import
 * org.springframework.test.annotation.Rollback; import
 * org.springframework.test.context.junit4.SpringRunner; import
 * org.springframework.transaction.annotation.Transactional;
 * 
 * import jpabook.jpashop.domain.Member;
 * 
 * @RunWith(SpringRunner.class) //JUnit한테 Spring과 관련된걸로 test할거란걸 알려주기 위함
 * 
 * @SpringBootTest public class MemberRepositoryTest {
 * 
 * @Autowired MemberRepository memberRepository;
 * 
 * @Test
 * 
 * @Transactional //springframework에 종속되어있는걸로 사용하기! - 쓸 수 있는 옵션이 javax보다 더 많음
 * 
 * @Rollback(false) // 이거 안적어두면 Test 코드의 경우 실행시켜도 마지막에 rollback 시켜서 데이터를 지워버림
 * public void testMember() throws Exception{ //given Member member = new
 * Member(); member.setUsername("memberA");
 * 
 * //when Long savedId = memberRepository.save(member); Member findMember =
 * memberRepository.find(savedId);
 * 
 * //then Assertions.assertThat(findMember.getId()).isEqualTo(member.getId());
 * Assertions.assertThat(findMember.getUsername()).isEqualTo(member.getUsername(
 * )); Assertions.assertThat(findMember).isEqualTo(member);
 * System.out.println("findMember == member : "+ (findMember == member)); }
 * 
 * }
 */