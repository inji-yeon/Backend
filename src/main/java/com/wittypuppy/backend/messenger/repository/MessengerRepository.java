package com.wittypuppy.backend.messenger.repository;

import com.wittypuppy.backend.messenger.entity.Chatroom;
import com.wittypuppy.backend.messenger.entity.Job;
import com.wittypuppy.backend.messenger.entity.Messenger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("Messenger_MessengerRepository")
public interface MessengerRepository extends JpaRepository<Messenger, Long> {
    Optional<Messenger> findByEmployee_EmployeeCode(Long employeeCode);
    Optional<Messenger> findByEmployee_EmployeeCodeAndChatroomList_ChatroomCode(Long employeeCode, Long chatroomCode);
}