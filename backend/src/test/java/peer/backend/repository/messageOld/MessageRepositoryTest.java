package peer.backend.repository.messageOld;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.time.LocalDate;
import java.util.List;
import javax.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import peer.backend.entity.messageOld.Message;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import peer.backend.entity.user.User;
import peer.backend.repository.user.UserRepository;

@DisplayName("Message Repository 테스트")
//@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest
class MessageRepositoryTest {

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    UserRepository userRepository;

    User user0, user1, user2, user3;

    @BeforeEach
    void setting() {
        user0 = User.builder()
            .password("password")
            .name("John")
            .email("john@example.com")
            .nickname("userzero")
            .isAlarm(true)
            .address("123 Main St")
            .certification(true)
            .company("ABC Inc.")
            .introduce("Hello, I'm John.")
            .peerLevel(2L)
            .representAchievement("Achievement XYZ")
            .build();

        user1 = User.builder()
            .password("password1")
            .name("User One")
            .email("user1@example.com")
            .nickname("userone")
            .isAlarm(true)
            .address("123 First St")
            .certification(true)
            .company("Company A")
            .introduce("Hello, I'm User One.")
            .peerLevel(1L)
            .representAchievement("Achievement ABC")
            .build();

        user2 = User.builder()
            .password("password2")
            .name("User Two")
            .email("user2@example.com")
            .nickname("usertwo")
            .isAlarm(true)
            .address("456 Second St")
            .certification(true)
            .company("Company B")
            .introduce("Hello, I'm User Two.")
            .peerLevel(2L)
            .representAchievement("Achievement DEF")
            .build();

        user3 = User.builder()
            .password("password3")
            .name("User Three")
            .email("user3@example.com")
            .nickname("userthree")
            .isAlarm(false)
            .address("789 Third St")
            .certification(false)
            .company("Company C")
            .introduce("Hello, I'm User Three.")
            .peerLevel(3L)
            .representAchievement("Achievement GHI")
            .build();

        userRepository.save(user0);
        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);

    }

//    @Test
//    public void userCountTest()
//    {
//        assertThat(userRepository.count()).isEqualTo(4);
//    }

    @Test
    public void findBySenderOrReceiverTest() {
        Message message1 = Message.builder()
            .content("예시1")
            .sender(user0)
            .receiver(user1)
            .build();

        Message message2 = Message.builder()
            .content("예시1")
            .sender(user0)
            .receiver(user1)
            .build();

        Message message3 = Message.builder()
            .content("예시1")
            .sender(user1)
            .receiver(user2)
            .build();

        Message message4 = Message.builder()
            .content("예시1")
            .sender(user1)
            .receiver(user2)
            .build();

        Message message5 = Message.builder()
            .content("예시1")
            .sender(user2)
            .receiver(user1)
            .build();

        Message message6 = Message.builder()
            .content("예시1")
            .sender(user2)
            .receiver(user0)
            .build();

        messageRepository.save(message1);
        messageRepository.save(message2);
        messageRepository.save(message3);
        messageRepository.save(message4);
        messageRepository.save(message5);
        messageRepository.save(message6);
//        assertThat(messageRepository.count()).isEqualTo(6);
        List<Message> messages = messageRepository.findBySenderOrReceiver(user0, user0);
        for (Message message : messages) {
            System.out.println("id = " + message.getId());
            System.out.println("send = " + message.getSender().getNickname());
            System.out.println("rec = " + message.getReceiver().getNickname());
        }
        assertThat(messageRepository.findBySenderOrReceiver(user0, user0).size()).isEqualTo(3);
    }
}
