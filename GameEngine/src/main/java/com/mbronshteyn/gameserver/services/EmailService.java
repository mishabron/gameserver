package com.mbronshteyn.gameserver.services;

import com.mbronshteyn.gameserver.mail.Mail;
import org.springframework.stereotype.Service;

public interface EmailService {
    public void sendEmail(final Mail mail);
}
