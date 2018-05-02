package com.wzf.mail;

import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class SimpleMailSender
{
  public boolean sendTextMail(MailSenderInfo mailInfo)
  {
    MyAuthenticator authenticator = null;
    Properties pro = mailInfo.getProperties();
    if (mailInfo.isValidate())
    {
      authenticator = new MyAuthenticator(mailInfo.getUserName(), mailInfo.getPassword());
    }

    Session sendMailSession = Session.getDefaultInstance(pro, authenticator);
    try
    {
      Message mailMessage = new MimeMessage(sendMailSession);

      Address from = new InternetAddress(mailInfo.getFromAddress());

      mailMessage.setFrom(from);

      Address to = new InternetAddress(mailInfo.getToAddress());
      mailMessage.setRecipient(Message.RecipientType.TO, to);

      mailMessage.setSubject(mailInfo.getSubject());

      mailMessage.setSentDate(new Date());

      String mailContent = mailInfo.getContent();
      mailMessage.setText(mailContent);

      Transport.send(mailMessage);
      return true;
    } catch (MessagingException ex) {
      ex.printStackTrace();
    }
    return false;
  }

  public static boolean sendHtmlMail(MailSenderInfo mailInfo)
  {
    MyAuthenticator authenticator = null;
    Properties pro = mailInfo.getProperties();

    if (mailInfo.isValidate()) {
      authenticator = new MyAuthenticator(mailInfo.getUserName(), mailInfo.getPassword());
    }

    Session sendMailSession = Session.getDefaultInstance(pro, authenticator);
    try
    {
      Message mailMessage = new MimeMessage(sendMailSession);

      Address from = new InternetAddress(mailInfo.getFromAddress());

      mailMessage.setFrom(from);

      Address to = new InternetAddress(mailInfo.getToAddress());

      mailMessage.setRecipient(Message.RecipientType.TO, to);

      mailMessage.setSubject(mailInfo.getSubject());

      mailMessage.setSentDate(new Date());

      Multipart mainPart = new MimeMultipart();

      BodyPart html = new MimeBodyPart();

      html.setContent(mailInfo.getContent(), "text/html; charset=utf-8");
      mainPart.addBodyPart(html);

      //添加附件  DataSource？
      BodyPart messageBodyPart;
      DataSource source;
      sun.misc.BASE64Encoder enc;
       //批量附件
      if(mailInfo.getAttachFileNames()!=null&&mailInfo.getAttachFileNames().length>0)
        for(String path:mailInfo.getAttachFileNames()){
       messageBodyPart= new MimeBodyPart();
       source = new FileDataSource(path);
      messageBodyPart.setDataHandler(new DataHandler(source));
      //通过下面的Base64编码的转换可以保证中文附件标题不会变成乱码
       enc = new sun.misc.BASE64Encoder();
      messageBodyPart.setFileName("=?GBK?B?"+enc.encode(path.substring(path.lastIndexOf('/')==0?path.lastIndexOf('\\'):path.lastIndexOf('/')+1).getBytes())+"?=");
      mainPart.addBodyPart(messageBodyPart);
      }
      
      mailMessage.setContent(mainPart);

      Transport.send(mailMessage);
      return true;
    } catch (MessagingException ex) {
      ex.printStackTrace();
    }
    return false;
  }
}