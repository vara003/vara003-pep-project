package Controller;

import java.util.List;

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
        // messageService = new MessageService();
    }
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/message",this::getAllMessages);
        app.post("/login",this::loginUser);
        app.post("/register",this::CreateHandler);
        return app;
    }
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

    //USER LOGIN TEST

    private void loginUser(Context ctx) throws JsonMappingException, JsonProcessingException{
        ObjectMapper ne = new ObjectMapper();
        Account account=ne.readValue(ctx.body(),Account.class);
        if(account.getUsername().isEmpty() || account.getPassword().isEmpty()){
            ctx.status(401);
            ctx.result("");
            return;
        }
        Account loggedInAccount = accountService.loginUser(account);
        if(loggedInAccount != null){
            ctx.status(200);
            ctx.json(loggedInAccount);
        }else{
            ctx.status(401);
            ctx.result("");
        }
        
    }


    // RETRIVE ALL MESSAGES TEXT


    private void getAllMessages(Context ctx) throws JsonProcessingException {
        // Retrieve all messages from the database using your MessageService or DAO
        List<Message> messages = messageService.getAllMessages();

        // Set the response status to 200
        ctx.status(200);

        // Convert the list of messages to JSON and send it in the response body
        ctx.json(messages);
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }
}