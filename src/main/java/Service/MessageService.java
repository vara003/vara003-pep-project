package Service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import DAO.MessageDAO;
import Model.Message;
import Util.ConnectionUtil;
import net.bytebuddy.agent.VirtualMachine.ForHotSpot.Connection;

public class MessageService {
    private MessageDAO messageDAO;
    public MessageService(){
        messageDAO=new MessageDAO();
    }

    public MessageService(MessageDAO messageDAO) {
        this.messageDAO = messageDAO;
    }

    //  1

    public Message CreateMessage(Message message) {
        // Additional validation logic can be added here
        return messageDAO.CreateMessage(message);
    }
    // (3)
    public List<Message> getAllMessagesUser(int messageId) {
        return messageDAO.getMessagesByUser(messageId);
    }


    // 5

    public Message getMessageById(int messageId) {
        return messageDAO.getMessageById(messageId);
    }
    //(2)

        public boolean deleteMessageById(int message_id) throws SQLException {
            return messageDAO.deleteMessageById(message_id);
            
        }

    // 7

    public Message UpdateMessage(Message message,int id) throws SQLException {
        return messageDAO.updateMessage(message,id);
    }
    // 1

    public boolean doesUserExist(int id) throws SQLException{
        return messageDAO.doesUserExist(id);
    }

    // 4

    public List<Message> getAllMessages() throws SQLException{
        return messageDAO.getAllMessages();
    }
}