package org.acme.quickstart;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;

import org.apache.http.client.protocol.HttpClientContext;

@Path("/hello")
@Produces(MediaType.APPLICATION_JSON)
public class GreetingResource extends HttpClientContext {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String hello(@Context HttpServletRequest request) throws IOException, FirebaseAuthException{

        String idToken = request.getHeader("token");
        
        List<FirebaseApp> apps = FirebaseApp.getApps();
    
        if(apps.size() == 0){
            // 実行しているスレッドのクラスパス
            InputStream serviceAccount = GreetingResource.class.getClassLoader().getResourceAsStream("firebase-serviceAccount.json");

            // System.out.println(serviceAccount);
            
            // HttpTransport TRANSPORT = new NetHttpTransport();

            FirebaseOptions options = new FirebaseOptions.Builder()
                // .setHttpTransport(TRANSPORT)
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl("https://vue-project-20190225.firebaseio.com")
                .build();

            FirebaseApp.initializeApp(options);
        }

        String uid = null;
        try {
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);
            uid = decodedToken.getUid();
        } catch (Exception e) {
            throw new BadRequestException("トークンが無効です");
        }

        System.out.println(uid);
        
        return "hello";
    }
}