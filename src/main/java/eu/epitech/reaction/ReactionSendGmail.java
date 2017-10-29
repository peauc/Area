package eu.epitech.reaction;

import com.google.api.client.util.Base64;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Message;
import eu.epitech.API.ApiGGmail;
import eu.epitech.API.ApiUtils;
import eu.epitech.FieldType;
import org.json.JSONObject;
import org.pmw.tinylog.Logger;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


public class ReactionSendGmail extends AReaction {
    public ReactionSendGmail() {
        this.api = ApiUtils.Name.GOOGLE_GMAIL;
        this.name = "GOOGLE GMAIL : Send mail";
        this.description = "Create a new event in the associated Calendar account";
        this.requiredActionFields = new ArrayList<>();
        this.requiredActionFields.add("subject");
        this.requiredActionFields.add("text");
        this.requiredConfigFields = new HashMap<>();
        this.requiredConfigFields.put("email", FieldType.EMAIL);
        this.config = null;
    }

    public static MimeMessage createEmail(String to,
                                          String from,
                                          String subject,
                                          String bodyText)
            throws MessagingException {
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);

        MimeMessage email = new MimeMessage(session);

        email.setFrom(new InternetAddress(from));
        email.addRecipient(javax.mail.Message.RecipientType.TO,
                new InternetAddress(to));
        email.setSubject(subject);
        email.setText(bodyText);
        return email;
    }

    /**
     * Create a message from an email.
     *
     * @param emailContent Email to be set to raw of message
     * @return a message containing a base64url encoded email
     * @throws IOException
     * @throws MessagingException
     */
    public static Message createMessageWithEmail(MimeMessage emailContent)
            throws MessagingException, IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        emailContent.writeTo(buffer);
        byte[] bytes = buffer.toByteArray();
        String encodedEmail = Base64.encodeBase64URLSafeString(bytes);
        Message message = new Message();
        message.setRaw(encodedEmail);
        return message;
    }

    /**
     * Send an email from the user's mailbox to its recipient.
     *
     * @param service Authorized Gmail API instance.
     * @param userId User's email address. The special value "me"
     * can be used to indicate the authenticated user.
     * @param emailContent Email to be sent.
     * @return The sent message
     * @throws MessagingException
     * @throws IOException
     */
    public static Message sendMessage(Gmail service,
                                      String userId,
                                      MimeMessage emailContent)
            throws MessagingException, IOException {
        Message message = createMessageWithEmail(emailContent);
        message = service.users().messages().send(userId, message).execute();

        System.out.println("Message id: " + message.getId());
        System.out.println(message.toPrettyString());
        return message;
    }

    @Override
    public void execute(Map<ApiUtils.Name, String> tokens, JSONObject actionOutput) {

        try {
            MimeMessage message = createEmail(config.getString("email"),
                    "java.area.epitech@gmail.com",
                    actionOutput.getString("subject"),
                    actionOutput.getString("text"));
            sendMessage(ApiGGmail.getGmailService(), "me", message);
        } catch (MessagingException e) {
            Logger.error("Error occurred during Mime message creation");
            Logger.debug(e);
        } catch (IOException e) {
            Logger.error("Error occurred during Mail creation");
            Logger.debug(e);
        }
    }
}
