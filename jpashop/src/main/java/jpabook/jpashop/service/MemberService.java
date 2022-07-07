package jpabook.jpashop.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
//JPA에서 데이터 조회수정 같은거 하려면 트랜젝션을 거쳐야 함! 꼭 넣어주기!
//데이터 읽기,조회 에서는 readOnly=true를 넣어주는게 오류가 덜 남 > 이 서비스 안에 조회용 메서드가 더 많으므로 여기다 readOnly 넣어주기.
//입력용 메서드에서는 @Transactional 다시 덮어씌우기!
@RequiredArgsConstructor //final이 있는 필드만 가지고 생성자를 만들어줌
public class MemberService {
	
	private final MemberRepository memberRepository;

	//생성자 injection
	//@Autowired 생성자가 하나일 경우에는 자동으로 autowired 해줌! 굳이 쓸 필요 없음
/*	public MemberService(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}
*/	//@RequiredArgsConstructor 들어가면 생성자 안만들어도 됨
	
	//회원가입
	@Transactional  //전체적으로 readOnly가 적용되어있는데, 이러면 데이터 쓰기가 안됨! 이 메소드는 다시 일반 transactional로 덮어씌워줘야함
	public Long join(Member member) {
		validateDuplicateMember(member);  //중복회원 검증
		memberRepository.save(member);
		return member.getId();
	}

	private void validateDuplicateMember(Member member) {
			List<Member> findMembers = memberRepository.findByName(member.getName());
			if (!findMembers.isEmpty()) {
				throw new IllegalStateException("이미 존재하는 회원입니다.");
			}
	}
	
	//회원 전체조회
	public List<Member> findMembers(){
		return memberRepository.findAll();
	}
	
	//회원 단건조회
	public Member findOne(Long memberId) {
		return memberRepository.findOne(memberId);
	}
}
