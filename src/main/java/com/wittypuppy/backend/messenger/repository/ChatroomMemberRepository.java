package com.wittypuppy.backend.messenger.repository;

import com.wittypuppy.backend.messenger.entity.ChatroomMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("Messenger_ChatroomMemberRepository")
public interface ChatroomMemberRepository extends JpaRepository<ChatroomMember, Long> {
    Optional<ChatroomMember> findByChatroomCodeAndEmployee_EmployeeCode(Long chatroomCode, Long employeeCode);

    Optional<ChatroomMember> findByChatroomMemberCodeAndChatroomCode(Long chatroomMember, Long chatroomCode);

    List<ChatroomMember> findAllByChatroomCodeAndChatroomMemberTypeIn(Long chatroomCode, List<String> chatroomMemberTypeList);
}
