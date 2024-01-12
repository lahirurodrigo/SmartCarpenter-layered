package lk.ijse.SmartCarpenter.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.SmartCarpenter.bo.BOFactory;
import lk.ijse.SmartCarpenter.bo.custom.LoginBO;
import lk.ijse.SmartCarpenter.dto.CredentialsDto;
import lk.ijse.SmartCarpenter.dao.custom.impl.CredentialDAOImpl;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

public class LoginFormController {

    @FXML
    private JFXButton btnForgot;

    @FXML
    private JFXButton btnLogin;

    @FXML
    private AnchorPane loginAnchorPane;

    @FXML
    private JFXPasswordField txtPassword;

    @FXML
    private JFXTextField txtUsername;

    LoginBO loginBO = (LoginBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.LOGIN);

    @FXML
    void btnForgotOnAction(ActionEvent event) {

        try {
            CredentialsDto dto = loginBO.getCredentials();
            sendCredentials(dto);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void sendCredentials(CredentialsDto dto) {

        String senderEmail = "mhfurniture1995@gmail.com";
        String senderPassword = "tcjd zsfz tzxp kidy";

        String receiverEmail = "lahirumalshan1995@gmail.com";

        // SMTP server configuration
        String host = "smtp.gmail.com";
        int port = 587;

        // Email content
        String subject = "User credentials";
        String body = "Username: "+dto.getUserName()+"\nPassword: "+dto.getPassword();

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);

        // Create a Session with authentication
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });

        try {
            // Create a MimeMessage object
            Message message = new MimeMessage(session);

            // Set the sender's email address
            message.setFrom(new InternetAddress(senderEmail));

            // Set the recipient's email address
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receiverEmail));

            // Set email subject and body
            message.setSubject(subject);
            message.setText(body);

            // Send the email
            Transport.send(message);

            System.out.println("Email sent successfully!");

        } catch (MessagingException e) {
            System.out.println("Error sending email: " + e.getMessage());
        }
    }

    @FXML
    void txtPasswordOnAction(ActionEvent event) throws IOException {
        btnLoginOnAction(event);
    }

    @FXML
    void txtUsernameOnAction(ActionEvent event) {
        txtPassword.requestFocus();
    }

    @FXML
    void btnLoginOnAction(ActionEvent event) throws IOException {

        String username = txtUsername.getText();
        String password = txtPassword.getText();

        CredentialsDto dto = new CredentialsDto(username,password);

        try {
            boolean isVerified = loginBO.checkCredentials(dto);
            System.out.println(isVerified);

            if (isVerified){

                Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/dashboard_form.fxml"));

                Scene scene = new Scene(rootNode);
                Stage stage = (Stage) loginAnchorPane.getScene().getWindow();

                stage.setScene(scene);
            }else {
                new Alert(Alert.AlertType.WARNING,"Invalid credentials").showAndWait();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}

