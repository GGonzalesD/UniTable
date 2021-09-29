package com.unitable.unitableprojectupc.repository;

import org.springframework.stereotype.Repository;

import com.unitable.unitableprojectupc.entities.Chat;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long>{
	Chat findChatById(Long id);
}
