package com.scm.services;
 
public interface EmailService {
 
    // Existing — unchanged — called during user registration
    void sendEmail(String to, String subject, String body);
 
    // Updated signature — now takes parameters instead of no-arg
    void sendEmailWithHtml(String to, String subject, String htmlContent);
 
    // Future use
    void sendEmailWithAttachment();
}



// package com.scm.services;

// public interface EmailService {

//     //
//     void sendEmail(String to, String subject, String body);

//     //
//     void sendEmailWithHtml();

//     //
//     void sendEmailWithAttachment();

// }