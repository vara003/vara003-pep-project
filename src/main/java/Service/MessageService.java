package Service;

import java.util.List;

import DAO.MessageDAO;
import Model.Message;

public class MessageService {
    private MessageDAO messageDAO;

    public MessageService(MessageDAO messageDAO) {
        this.messageDAO = messageDAO;
    }

    public void createMessage(Message message) {
        // Additional validation logic can be added here
        messageDAO.createMessage(message);
    }

    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }

    public Message getMessageById(int messageId) {
        return messageDAO.getMessageById(messageId);
    }

    public void deleteMessage(int messageId) {
        messageDAO.deleteMessage(messageId);
    }

    public void updateMessageText(int messageId, String newText) {
        // Additional validation logic can be added here
        messageDAO.updateMessageText(messageId, newText);
    }

    public List<Message> getMessagesByUserId(int userId) {
        return messageDAO.getMessagesByUserId(userId);
    }
}