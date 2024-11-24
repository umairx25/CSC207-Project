package ai;

public class MessageProcessor {
    private final ChatService chatService;

    public MessageProcessor(ChatService chatService) {
        this.chatService = chatService;
    }

    public void processMessage(String userMessage, ChatService.ChatCallback callback) {
        chatService.processMessage(userMessage, callback);
    }
}
