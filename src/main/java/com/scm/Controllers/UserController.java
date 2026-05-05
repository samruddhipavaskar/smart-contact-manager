// package com.scm.Controllers;

// import java.util.List;
// import java.util.UUID;

// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.data.domain.Page;
// import org.springframework.security.core.Authentication;
// import org.springframework.stereotype.Controller;
// import org.springframework.ui.Model;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.web.multipart.MultipartFile;

// import com.scm.entities.Contact;
// import com.scm.entities.User;
// import com.scm.helpers.Helper;
// import com.scm.helpers.Message;
// import com.scm.helpers.MessageType;
// import com.scm.services.ContactService;
// import com.scm.services.ImageService;
// import com.scm.services.UserService;

// import jakarta.servlet.http.HttpSession;

// @Controller
// @RequestMapping("/user")
// public class UserController {

//     private Logger logger = LoggerFactory.getLogger(UserController.class);

//     @Autowired
//     private UserService userService;

//     @Autowired
//     private ContactService contactService;

//     @Autowired
//     private ImageService imageService;

//     // ── Dashboard ──────────────────────────────────────────────────────────────
//     @RequestMapping(value = "/dashboard")
//     public String userDashboard(Model model, Authentication authentication) {
//         String email = Helper.getEmailOfLoggedInUser(authentication);
//         User user = userService.getUserByEmail(email);

//         List<Contact> allContacts = contactService.getByUserId(user.getUserId());

//         long totalContacts = allContacts.size();
//         long favouriteContacts = allContacts.stream().filter(Contact::isFavorite).count();

//         // 5 most-recent contacts
//         Page<Contact> recentPage = contactService.getByUser(user, 0, 5, "name", "asc");

//         model.addAttribute("totalContacts", totalContacts);
//         model.addAttribute("favouriteContacts", favouriteContacts);
//         model.addAttribute("recentContacts", recentPage.getContent());

//         logger.info("Dashboard loaded for: {}", email);
//         return "user/dashboard";
//     }

//     // ── Profile View ───────────────────────────────────────────────────────────

//     @RequestMapping(value = "/profile")
//     public String userProfile() {

//         return "user/profile";
//     }

//     // ── Profile Update ─────────────────────────────────────────────────────────
//     @PostMapping(value = "/profile/update")
//     public String updateProfile(
//             @RequestParam(value = "name", required = false) String name,
//             @RequestParam(value = "phoneNumber", required = false) String phoneNumber,
//             @RequestParam(value = "about", required = false) String about,
//             @RequestParam(value = "profileImage", required = false) MultipartFile profileImage,
//             Authentication authentication,
//             HttpSession session) {

//         String email = Helper.getEmailOfLoggedInUser(authentication);
//         User user = userService.getUserByEmail(email);

//         if (name != null && !name.isBlank())       user.setName(name.trim());
//         if (phoneNumber != null)                   user.setPhoneNumber(phoneNumber.trim());
//         if (about != null)                         user.setAbout(about.trim());

//         if (profileImage != null && !profileImage.isEmpty()) {
//             try {
//                 String fileName = UUID.randomUUID().toString();
//                 String imageUrl = imageService.uploadImage(profileImage, fileName);
//                 user.setProfilePic(imageUrl);
//             } catch (Exception e) {
//                 logger.error("Profile image upload failed: {}", e.getMessage());
//                 session.setAttribute("message", Message.builder()
//                         .content("Profile updated but image upload failed.")
//                         .type(MessageType.red).build());
//                 userService.updateUser(user);
//                 return "redirect:/user/profile";
//             }
//         }

//         userService.updateUser(user);
//         session.setAttribute("message", Message.builder()
//                 .content("Profile updated successfully!")
//                 .type(MessageType.green).build());
//         return "redirect:/user/profile";
//     }
// }

package com.scm.Controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.scm.services.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    // user dashbaord page

    @RequestMapping(value = "/dashboard")
    public String userDashboard() {
        System.out.println("User dashboard");
        return "user/dashboard";
    }

    // user profile page

    @RequestMapping(value = "/profile")
    public String userProfile(Model model, Authentication authentication) {

        return "user/profile";
    }

    // user add contacts page

    // user view contacts

    // user edit contact

    // user delete contact

}
