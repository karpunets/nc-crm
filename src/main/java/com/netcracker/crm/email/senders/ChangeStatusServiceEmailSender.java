package com.netcracker.crm.email.senders;


import com.netcracker.crm.email.builder.EmailBuilder;
import com.netcracker.crm.email.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;

/**
 * @author Melnyk_Dmytro
 * @version 1.0
 * @since 14.04.2017
 */

@Service
@Scope("prototype")
public class ChangeStatusServiceEmailSender extends AbstractEmailSender {

    //Name of template html file for email letter
    private String changeStatusServiceTempl ;
    //Subject for email letter
    private String changeStatusServiceSubj ;

    @Autowired
    private EmailBuilder builder;

    @Autowired
    private JavaMailSenderImpl sender;

    public String getChangeStatusServiceTempl() {
        return changeStatusServiceTempl;
    }

    public void setChangeStatusServiceTempl(String changeStatusServiceTempl) {
        this.changeStatusServiceTempl = changeStatusServiceTempl;
    }

    public String getChangeStatusServiceSubj() {
        return changeStatusServiceSubj;
    }

    public void setChangeStatusServiceSubj(String changeStatusServiceSubj) {
        this.changeStatusServiceSubj = changeStatusServiceSubj;
    }

    public ChangeStatusServiceEmailSender() {
    }

    public void send(User user, ServiceEntity serviceEntity) throws MessagingException {
        String template = getTemplate(changeStatusServiceTempl);
        template = replaceFields(template, user, serviceEntity);
        builder.setSubject(changeStatusServiceSubj);
        builder.setAddress(user.getEmail());
        builder.setContent(template);
        sender.send(builder.generateMessage());
    }

    private String replaceFields(String templ, User user, ServiceEntity serviceEntity) {
        return templ.replaceAll("%name%", user.getName())
                .replaceAll("%surname%", user.getSurname())
                .replaceAll("%service%", serviceEntity.getName())
                .replaceAll("%status%", serviceEntity.getStatus());
    }

}
