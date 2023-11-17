package Controller;

import java.sql.SQLException;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Model.Message;
import io.javalin.Javalin;
import io.javalin.http.Context;
import Service.AccountService;
import Service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    AccountService accountService;
    MessageService messageService;
    public SocialMediaController(){
        accountService = new AccountService();
        messageService=new MessageService();
    }
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/login",this::loginUser);
        app.post("/register",this::CreateHandler); 
        app.post("/messages",this::Createmessage);
        app.patch("/messages/{message_id}",this::UpdateMessage);
        app.get("/messages/{message_id}",this::getMessageById);
        app.delete("/messages/{message_id}", this::deleteMessageById);
        app.get("/messages",this::getAllMessages);
        app.get("/accounts/{account_Id}/messages",this::getMessagesByUser);
        //As a user, I should be to submit a PATCH request on the endpoint PATCH localhost:8080/messages/{message_id}.
        return app;
    }
    // delete(2)
    private void deleteMessageById(Context ctx) throws SQLException {
        try {
            int messageId = Integer.parseInt(ctx.pathParam("message_id"));
            Message existingMessage=messageService.getMessageById(messageId);
            //Message deletedMessage = messageService.deleteMessageById(messageId);
            if (messageService.deleteMessageById(messageId)) {
                ctx.json(existingMessage);
            } 
            
        } catch (NumberFormatException e) {
            ctx.status(400);
        }
        ctx.status(200);
    }

    // 1
    private void Createmessage(Context ctx) throws JsonProcessingException, SQLException{
        ObjectMapper ob=new ObjectMapper();
        Message message=ob.readValue(ctx.body(),Message.class);
        try{
            Message v=messageService.CreateMessage(message);

        if (isValidMessage(message)) {
            ctx.status(200);
            ctx.json(v);
        } 

        else {
            ctx.status(400);
        }
    }catch(SQLException e){
        System.out.println(e.getMessage());
    }
    }
        private boolean isValidMessage(Message message) throws SQLException {
            if(message==null){
                System.out.println("1");
                return false;
            }
            if(message.getMessage_text().isEmpty()){
                System.out.println("2");
                return false;
            }
            
            if(!messageService.doesUserExist(message.getPosted_by())){
                System.out.println("3");
                return false;
                
            }
            if(message.getMessage_text().length()>=255){
                System.out.println("4");
                return false;
            }
            return true;
        }


    //8


    private void CreateHandler(Context ctx) throws JsonMappingException, JsonProcessingException{
        ObjectMapper om=new ObjectMapper();
        Account account=om.readValue(ctx.body(),Account.class);
        if(account.getUsername().isEmpty()){
            ctx.status(400);
            ctx.result("");
            return;
        }
        else if(accountService.getAccountByUsername(account.getUsername())!=null){
            ctx.status(400);
            ctx.result("");
            return;
        }
        else if(account.getPassword().length()<4){
            ctx.status(400);
            ctx.result("");
            return;
        }
        Account addAccount=accountService.registerUser(account);
        ctx.status(200);
        ctx.json(accountService.getAccountByUsername(account.getUsername()));
    }

    // 7

    private void loginUser(Context ctx) throws SQLException, JsonProcessingException{
        ObjectMapper ne = new ObjectMapper();
        Account account=ne.readValue(ctx.body(),Account.class);
        Account loggedInAccount=accountService.loginUser(account);
        //if(account.getUsername().isEmpty() || account.getPassword().isEmpty()){
        if(loggedInAccount != null){
            ctx.status(200);
        ctx.json(ne.writeValueAsString(loggedInAccount));

        }
        else{
            ctx.status(401);
            ctx.result("");
        }
    }


    // 6 update


    private void UpdateMessage(Context ctx) throws JsonMappingException, JsonProcessingException, SQLException{
        try{
            int messageId=Integer.parseInt(ctx.pathParam("message_id"));
            ObjectMapper ob=new ObjectMapper();
            Message message=ob.readValue(ctx.body(),Message.class);
            System.out.println(message);
            String newMessageText = message.getMessage_text();
        if (newMessageText == null || newMessageText.trim().isEmpty() || newMessageText.length() >= 255 || messageService.getMessageById(messageId)==null) {
            ctx.status(400);
            return;
        }

        Message existingMessage=messageService.UpdateMessage(message,messageId);
        System.out.println(existingMessage);
        ctx.status(200);
        ctx.json(existingMessage);
        }catch(NumberFormatException e){
            ctx.status(400).result("");
        } 
    }


    // 3 

    private void getMessagesByUser(Context ctx){

        
            int accountId= Integer.parseInt(ctx.pathParam("account_Id"));
            List<Message> messages = messageService.getAllMessagesUser(accountId);
            ctx.json(messages).status(200);
        }
        
    

    // 5  

    private void getAllMessages(Context ctx) throws SQLException{
        List<Message> message=messageService.getAllMessages();
        ctx.status(200);
        ctx.json(message);
    }

    // 6


    private void getMessageById(Context ctx) throws JsonMappingException,JsonProcessingException{
        try{
            int messageId= Integer.parseInt(ctx.pathParam("message_id"));
            Message message = messageService.getMessageById(messageId);
            if(message !=null){
                ctx.json(message);
                
            }else{
                ctx.status(404);
            
            }
            ctx.status(200);
        }catch(NumberFormatException e){
            ctx.status(400);
            return;
        }
    }


    // 4

    /*private void postMessage(Context ctx){
        try{
            Message message=new ObjectMapper().readValue(ctx.body(),Message.class);
            if(message.getMessage_text()!=null && message.getMessage_text().length()>=4){
                Message newMessage=messageService.CreateMessage(message);
                if(newMessage!=null){
                    ctx.json(newMessage).status(200);
                }else{
                    ctx.status(500).result("Error posting the message");
                }
            }else{
                ctx.status(200).result("Invalid message data");
            }
        }catch(Exception e){
            e.printStackTrace();
            ctx.status(200).result("");
        }
    }*/
    /*
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    //private void exampleHandler(Context context) {
        //context.json("sample text");
    //}
}


   


  