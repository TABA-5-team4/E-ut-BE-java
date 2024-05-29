package taba.team4.eut.biz.chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import taba.team4.eut.biz.chat.entity.ChatVoiceEntity;

@Repository
public interface ChatVoiceRepository extends JpaRepository<ChatVoiceEntity, Long>{

}
