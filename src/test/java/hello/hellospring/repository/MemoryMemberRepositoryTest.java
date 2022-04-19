package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

public class MemoryMemberRepositoryTest {
    // 테스트 방법
    // 1) java의 메인 메서드 , 웹 어플리케이션의 컨트롤러를 통해서 해당 기능 실행
    // -> 반복실행 어려움. 시간 오래걸림

    // ->JUnit 프레임워크 사용

    // MemoryMemberRepository 클래스의 기능 작동을 확인하는 용도
    //    // 테스트 할 기능이 담긴 객체 생성
    MemoryMemberRepository repository = new MemoryMemberRepository();

    //AfterEach어노테이션 : 메소드가 하나씩 끝날때마다 clearStore가 실행된다.
    // 여기서 객체를 비워준다. map의 clear()메소드로

    @AfterEach
    public void afterEach(){
        repository.clearStore();// 테스트가 진행되고 끝날때마다 repository 저장소 싹 다 지움. 순서 상관없어짐
    }

    // 해당 메소드가 단위 테스트임을 명시
    // JUnit은 테스트 패키지 하위의 @Test 어노테이션이 붙은 메소드를
    // 단위메소드로 인식하여 독립적으로 실행하여 동작하는지 확인  있도록 한다.
    @Test
    public void save() {
        Member member = new Member();
        member.setName("spring");   // 이름을 세팅해준다.

        // ctrl+shift+enter 바로 다음행이동
        repository.save(member); // ++sequqnece로 id 세팅됨

        Member result = repository.findById(member.getId()).get(); //findById()메소드는 반환타입이 Optional이다.
        // Optional에서 값 꺼낼때에는 .get() 사용한다. 좋은 방법은 아니나, 테스트 코드이므로 사용.
        // result 참조변수와 내가 꺼낸 member가 같다면 true를 반환한다.
        System.out.println("result = " + (result == member));

        // result = true 반환

        // assert란 ? JUnit에서 테스트에 넣을 수 있는 정적 메서드 호출이다.
        // assertEquals(expected,actual) 형태로 두 객체가 같은지 확인하여, 같다면 테스트를 통과한다.

        //Assertions.assertEquals(result, member);    // true
        // Assertions.assertEquals(result, null);   // 빨간에러가 발생한다. 기대값과 Actual 다를때
        assertThat(member).isEqualTo(result);
        // 요즘엔 이걸 더 많이 쓴다. assertThat에서 member 객체가 result 객체와 같다.
        // alt+enter로 static으로 import하면 Assertions. 안써도 바로 사용가능하다.ㄴ
        //assertThat(member).isEqualTo(null); // 에러 발생한다.

    }



    @Test
    public void findByName(){
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();  // shift+F6 누르면 rename되어 어래도 모두 수정된다.
        member2.setName("spring2");
        repository.save(member2);

        // spring1, spring2라는 회원이 가입된다.

        Member result = repository.findByName("spring1").get();

        assertThat(result).isEqualTo(member1); // true
        //assertThat(result).isEqualTo(member2); // 에러발생한다.

    }

    @Test
    public void findAll(){
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        List<Member> result =  repository.findAll();

        assertThat(result.size()).isEqualTo(2); // isEqualTo : reulst 리스트의 개수 2개인지 확인
    }
    // class에서 실행시 오류가 발생한다. ? 왜?
    // findAll > findByName > save 순서로 실행된다.
    // findAll에서 spring1, spring2가 이미 저장되어있어서 이미 객체에
    //-> test 하나끝나면 clear를 해줘야한다!! 서로 의존성 없어야한다. 공용 데이터들을 싹 지워줘야한다.

    // test 코드를 먼저 작성후, 구현클래스를 만드는 것, 틀을 먼저 만드는것을 -> TDD 테스트주도개발이라한다.
}
