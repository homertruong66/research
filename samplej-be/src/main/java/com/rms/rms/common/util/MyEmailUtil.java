package com.rms.rms.common.util;

import com.rms.rms.common.dto.SubsEmailTemplateDto;
import com.rms.rms.service.model.Email;
import org.apache.http.util.Asserts;

/**
 * homertruong
 */

public class MyEmailUtil {

    public static Email createEmailFromSubsEmailTemplate (SubsEmailTemplateDto subsEmailTemplateDto,
                                                          String emailString,
                                                          String name) {
        Asserts.notNull(subsEmailTemplateDto, "subsEmailTemplateDto");
        Asserts.notNull(emailString, "emailString");

        Email email = new Email();
        email.setSubject(subsEmailTemplateDto.getTitle());
        email.setHtml(subsEmailTemplateDto.getContent());
        email.setTo((
                        new Email.Recipient() {
                            {
                                this.setEmail(emailString);
                                this.setName(name);
                                this.setType(Type.TO);
                            }
                        }
                    ));

        return email;

    }

    public static Email createEmailFromEmailTemplate (String title, String content, String emailString, String name) {
        Asserts.notNull(title, "title");
        Asserts.notNull(content, "content");
        Asserts.notNull(emailString, "emailString");

        Email email = new Email();
        email.setSubject(title);
        email.setHtml(content);
        email.setTo((
                new Email.Recipient() {
                    {
                        this.setEmail(emailString);
                        this.setName(name);
                        this.setType(Type.TO);
                    }
                }
        ));

        return email;
    }

}
