package com.wzf.mail;

public class MailSender
{
  private String Host = "smtp.163.com";
  private String Port = "25";
  private String username;
  private String password;
  private String To;
  private String Title;
  private String Content;
  private boolean IsText = false;
  private String[] attachFileNames;

  public MailSender(String host, String port, String username, String password, String to, String title, String content, boolean IsText)
  {
    this.Host = host;
    this.Port = port;
    this.username = username;
    this.password = password;
    this.To = to;
    this.Title = title;
    this.Content = content;
    IsText = IsText;
    sendmail();
  }

  public MailSender(String username, String password, String to, String title, String content)
  {
    this.username = username;
    this.password = password;
    this.To = to;
    this.Title = title;
    this.Content = content;
    sendmail();
  }
  public MailSender(String host, String port, String username, String password, String to, String title, String content, boolean IsText,String[] attachFileNames)
  {
    this.Host = host;
    this.Port = port;
    this.username = username;
    this.password = password;
    this.To = to;
    this.Title = title;
    this.Content = content;
    this.attachFileNames=attachFileNames;
    IsText = IsText;
    sendmail();
  }

  public MailSender(String username, String password, String to, String title, String content,String[] attachFileNames)
  {
    this.username = username;
    this.password = password;
    this.To = to;
    this.Title = title;
    this.Content = content;
    this.attachFileNames=attachFileNames;
    sendmail();
  }
  public void sendmail()
  {
    MailSenderInfo mailInfo = new MailSenderInfo();
    mailInfo.setMailServerHost(this.Host);
    mailInfo.setMailServerPort(this.Port);
    mailInfo.setValidate(true);
    mailInfo.setUserName(this.username);
    mailInfo.setPassword(this.password);
    mailInfo.setFromAddress(this.username);
    mailInfo.setToAddress(this.To);
    mailInfo.setSubject(this.Title);
    mailInfo.setContent(this.Content);
    mailInfo.setAttachFileNames(this.attachFileNames);

    SimpleMailSender sms = new SimpleMailSender();
    if (this.IsText) sms.sendTextMail(mailInfo);
    else SimpleMailSender.sendHtmlMail(mailInfo);
  }
}