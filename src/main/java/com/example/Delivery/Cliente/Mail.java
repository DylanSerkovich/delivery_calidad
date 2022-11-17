/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.Delivery.Cliente;

import java.util.Properties;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author INTEL
 */
public class Mail {

    private Properties propiedad = new Properties();
    private javax.mail.Session session;

    private void iniciar() {
        String usuarioCorreo = "enviacms123@gmail.com";
        propiedad.put("mail.smtp.host", "smtp.gmail.com");
        propiedad.setProperty("mail.smtp.ssl.protocols", "TLSv1.2");
        propiedad.setProperty("mail.smtp.starttls.enable", "true");
        propiedad.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        propiedad.setProperty("mail.smtp.port", "587");
        propiedad.setProperty("mail.smtp.user", usuarioCorreo);
        propiedad.setProperty("mail.smtp.auth", "true");

        session = Session.getDefaultInstance(propiedad);
    }

    public void enviarCorreo(String direccionDestino, String usuario,  String clave) {
        iniciar();
        String usuarioCorreo = "enviacms123@gmail.com";
        String claveCorreo = "afjnvpalmokwlvrj";
        try {
            //Mensaje texto
            BodyPart texto = new MimeBodyPart();
            texto.setText("Gracias por su preferencia");
            MimeMessage mensaje = new MimeMessage(session);
            mensaje.setFrom(new InternetAddress(usuarioCorreo));
            mensaje.addRecipient(Message.RecipientType.TO, new InternetAddress(direccionDestino));
            mensaje.setSubject("Recuperacion de contraseña");
            mensaje.setText("Nombre de usuario: " + usuario+"\nContraseña: "+ clave+ "\n\n-------------------------------"
                    + "\nMensaje enviado - Restaurante Las Gaviotas.");
            //Enviar mensaje
            Transport t = session.getTransport("smtp");
            t.connect(usuarioCorreo, claveCorreo);
            t.sendMessage(mensaje, mensaje.getAllRecipients());
            t.close();
        } catch (MessagingException e) {
            e.printStackTrace();
            return;
        }
    }
}
