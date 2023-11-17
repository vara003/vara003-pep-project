package DAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.text.html.HTMLDocument.HTMLReader.PreAction;

import Model.Account;
import Model.Message;
import Util.ConnectionUtil;

public class MessageDAO {
    private Connection connection;

    public MessageDAO() {
        this.connection = ConnectionUtil.getConnection();
    }

    public Message CreateMessage(Message message) {
        try {
            String sql = "INSERT INTO Message (posted_by, message_text, time_posted_epoch) VALUES (?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setInt(1, message.getPosted_by());
                preparedStatement.setString(2, message.getMessage_text());
                preparedStatement.setLong(3, message.getTime_posted_epoch());

                int affectedRows = preparedStatement.executeUpdate();

                if (affectedRows == 0) {
                    throw new SQLException("Creating message failed, no rows affected.");
                }

                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        message.setMessage_id(generatedKeys.getInt(1));
                        return message;
                    } else {
                        throw new SQLException("Creating message failed, no ID obtained.");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
        return null;
    }


    //2


    public boolean deleteMessageById(int messageId) {
        String sql = "DELETE FROM Message WHERE message_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, messageId);
            int affectedRows=preparedStatement.executeUpdate();
            return affectedRows==1;
        }catch(SQLException e){
            e.printStackTrace();
            return false;
        }
    }
    // 6 upadate

    public Message updateMessage(Message message,int id) throws SQLException {
            String sql = "UPDATE Message Set posted_by=?, message_text=?, time_posted_epoch=? where message_id=?";
            Message a=getMessageById(id);
            try{
            PreparedStatement preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, a.getPosted_by());
                preparedStatement.setString(2, message.getMessage_text());
                preparedStatement.setLong(3, a.getTime_posted_epoch());
                preparedStatement.setInt(4,a.getMessage_id());
                int affectedRows = preparedStatement.executeUpdate();
                String sql1="SELECT * FROM message WHERE message_id=?";
                preparedStatement = connection.prepareStatement(sql1);
                preparedStatement.setInt(1, a.getMessage_id());
                ResultSet rs=preparedStatement.executeQuery();
                if(rs.next()){

                Message message1=new Message(rs.getInt("message_id"), rs.getInt("posted_by"), rs.getString("message_text"),rs.getLong("time_posted_epoch"));
                return message1;
            }
        }
            
        catch (SQLException e) {
            System.out.println(e.getMessage());// TODO: handle exception
        }
        
        return null;
    }


        

    // 3

    public List<Message> getMessagesByUser(int accountId) {
        List<Message> messages = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Message WHERE posted_by = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, accountId);
                ResultSet resultSet=preparedStatement.executeQuery();
                while(resultSet.next()){
                    messages.add(new Message(
                        resultSet.getInt("message_id"),
                        resultSet.getInt("posted_by"),
                        resultSet.getString("message_text"),
                        resultSet.getLong("time_posted_epoch")
                    ));
                }
                return messages; 
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
        return new ArrayList<>();
    }


    // (5)


    public Message getMessageById(int messageId) {
        String sql="SELECT * from message Where message_id=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1, messageId);
            ResultSet resultSet=preparedStatement.executeQuery();
            if(resultSet.next()){
                return new Message(
                    resultSet.getInt("message_id"),
                    resultSet.getInt("posted_by"),
                    resultSet.getString("message_text"),
                    resultSet.getLong("time_posted_epoch")
                );
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }


    //4


    public List<Message> getAllMessages() throws SQLException {
        List<Message> messages = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Message")) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int messageId = resultSet.getInt("message_id");
                    int accountId = resultSet.getInt("posted_by");
                    String messageText = resultSet.getString("message_text");
                    long timePostedEpoch = resultSet.getLong("time_posted_epoch");
                    Message message = new Message(messageId, accountId, messageText, timePostedEpoch);
                    messages.add(message);
                }
            }
        } catch (SQLException e) {
            throw e;
        }
        return messages;
    } 


    
    // 1


    
    public boolean doesUserExist(int id){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet=null;
        try {
            connection = ConnectionUtil.getConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM account WHERE account_id = ?");
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
            Account account1 = new Account(
                resultSet.getInt("account_id"),
                resultSet.getString("username"),
                resultSet.getString("password")
                );
                return account1!=null;
            } 
        }
        catch (SQLException e) {
            e.printStackTrace(); // Handle the exception according to your application's error handling strategy
        }
        return false;
    }


    // 2


    public Message findById( int messageId) throws SQLException{
        String sql= "Select * from account where account_id=?";
        Connection connection= ConnectionUtil.getConnection();
        PreparedStatement preparedStatement= connection.prepareStatement(sql);
        ResultSet resultSet=null;
        try{
            resultSet=preparedStatement.executeQuery();
            if(resultSet!=null){
                resultSet.getInt(messageId);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;

    } 
}

