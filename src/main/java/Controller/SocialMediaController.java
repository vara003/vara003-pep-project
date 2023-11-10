package Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
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
        ctx.json(accountService.getAccountByUsername(addAccount.getUsername()));
        
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }


}