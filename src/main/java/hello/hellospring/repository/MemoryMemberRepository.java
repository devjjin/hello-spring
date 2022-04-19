package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import java.util.*;

// 인터페이스 상속 오버라이딩 implements
public class MemoryMemberRepository implements MemberRepository{

    private static Map<Long, Member> store = new HashMap<>();
    private static long sequence = 0L;

    @Override
    public Member save(Member member) {
        member.setId(++sequence);   // 시퀀스 값을 하나 올려서 아이디값 세팅해준다.
        store.put(member.getId(), member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<Member> findByName(String name) {
        return store.values().stream()
                .filter(member -> member.getName().equals(name))
                .findAny();
    }

    // 자바8에 Stream 추가로 map 안에 있는 값 출력
    // map 요소 순회하는 방법 :  values() 의 반환형은 collection interface
    // filter 요소들을 조건에 따라 걸러내는 작업으로
    // member.getName의 아이디와 파라미터의 name 이같다면 스트림의 요소 하나를 반환(findAny()) 메소드사용


    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }
    
    // map 싹 비워주는 역할
    public void clearStore(){
        store.clear();
    }
}
