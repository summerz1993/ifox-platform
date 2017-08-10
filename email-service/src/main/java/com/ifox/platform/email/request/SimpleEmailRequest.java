package com.ifox.platform.email.request;

import java.util.Arrays;
import java.util.Date;

/**
 * 简单邮件发送请求
 * @author Yeager
 */
public class SimpleEmailRequest extends BaseEmailRequest {

    private String replyTo;

    private String[] to;

    private String[] cc;

    private String[] bcc;

    private Date sentDate;

    private String subject;

    private String text;

    public String getReplyTo() {
        return replyTo;
    }

    public void setReplyTo(String replyTo) {
        this.replyTo = replyTo;
    }

    public String[] getTo() {
        return to;
    }

    public void setTo(String[] to) {
        this.to = to;
    }

    public String[] getCc() {
        return cc;
    }

    public void setCc(String[] cc) {
        this.cc = cc;
    }

    public String[] getBcc() {
        return bcc;
    }

    public void setBcc(String[] bcc) {
        this.bcc = bcc;
    }

    public Date getSentDate() {
        return sentDate;
    }

    public void setSentDate(Date sentDate) {
        this.sentDate = sentDate;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "SimpleEmailRequest{" +
            "replyTo='" + replyTo + '\'' +
            ", to=" + Arrays.toString(to) +
            ", cc=" + Arrays.toString(cc) +
            ", bcc=" + Arrays.toString(bcc) +
            ", sentDate=" + sentDate +
            ", subject='" + subject + '\'' +
            ", text='" + text + '\'' +
            "} " + super.toString();
    }
}
