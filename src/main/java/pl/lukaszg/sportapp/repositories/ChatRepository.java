package pl.lukaszg.sportapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.lukaszg.sportapp.model.Chat;

public interface ChatRepository extends JpaRepository<Chat, Long> {
}
