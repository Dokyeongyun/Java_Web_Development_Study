package Project.chat;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.websocket.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;


/**
 * ChatMessage 클래스를 인코딩/디코딩하는 클래스
 */
public class ChatMessageCodec implements Encoder.BinaryStream<ChatMessage>, Decoder.BinaryStream<ChatMessage> {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    static {
        MAPPER.findAndRegisterModules();
        MAPPER.configure(JsonGenerator.Feature.AUTO_CLOSE_TARGET, false);
    }

    /**
     * ChatMessage와 OutputStream을 전달받아 메시지를 JSON으로 인코딩 후 OutputStream에 기록
     */
    @Override
    public void encode(ChatMessage chatMessage, OutputStream outputStream) throws EncodeException, IOException {
        try {
            ChatMessageCodec.MAPPER.writeValue(outputStream, chatMessage);
        }
        catch(JsonGenerationException | JsonMappingException e) {
            e.printStackTrace();
            throw new EncodeException(chatMessage, e.getMessage(), e);
        }
    }

    /**
     * InputStream을 전달받아 JSON ChatMessage를 읽고 역직렬화
     */
    @Override
    public ChatMessage decode(InputStream inputStream) throws DecodeException, IOException {
        try {
            return ChatMessageCodec.MAPPER.readValue(inputStream, ChatMessage.class);
        } catch(JsonParseException | JsonMappingException e) {
            e.printStackTrace();
            throw new DecodeException((ByteBuffer)null, e.getMessage(), e);
        }
    }

    @Override
    public void init(EndpointConfig endpointConfig) { }

    @Override
    public void destroy() { }
}
