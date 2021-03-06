package Project.chat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import javax.websocket.Session;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 채팅 사용자와 담당자를 연결하기 위한 클래스
 */
public class ChatSession
{
    private long sessionId;
    private String customerUsername;
    private Session customer;
    private String representativeUsername;
    private Session representative;
    private ChatMessage creationMessage;
    private final List<ChatMessage> chatLog = new ArrayList<>();


    /**
     * Getter / Setter
     */

    public long getSessionId() {return sessionId; }

    public void setSessionId(long sessionId) { this.sessionId = sessionId; }

    public String getCustomerUsername() { return customerUsername; }

    public void setCustomerUsername(String customerUsername) { this.customerUsername = customerUsername; }

    public Session getCustomer() { return customer; }

    public void setCustomer(Session customer) { this.customer = customer; }

    public String getRepresentativeUsername() { return representativeUsername; }

    public void setRepresentativeUsername(String representativeUsername) { this.representativeUsername = representativeUsername; }

    public Session getRepresentative() { return representative; }

    public void setRepresentative(Session representative) { this.representative = representative; }

    public ChatMessage getCreationMessage() { return creationMessage; }

    public void setCreationMessage(ChatMessage creationMessage) { this.creationMessage = creationMessage; }


    /**
     * 메시지 로그기록
     */
    @JsonIgnore
    public void log(ChatMessage message) {
        this.chatLog.add(message);
    }

    /**
     * 메시지 로그 기록을 파일 형태로 출력
     */
    @JsonIgnore
    public void writeChatLog(File file) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
        mapper.configure(JsonGenerator.Feature.AUTO_CLOSE_TARGET, false);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        try(FileOutputStream stream = new FileOutputStream(file)) {
            mapper.writeValue(stream, this.chatLog);
        }
    }
}
