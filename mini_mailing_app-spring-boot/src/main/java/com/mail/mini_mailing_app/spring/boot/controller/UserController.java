package com.mail.mini_mailing_app.spring.boot.controller;

import com.mail.mini_mailing_app.spring.boot.data.dto.request.*;
import com.mail.mini_mailing_app.spring.boot.data.dto.response.ApiResponse;
import com.mail.mini_mailing_app.spring.boot.data.dto.response.AuthenticationResponse;
import com.mail.mini_mailing_app.spring.boot.data.dto.response.MailResponse;
import com.mail.mini_mailing_app.spring.boot.data.dto.response.UpdateUserResponse;
import com.mail.mini_mailing_app.spring.boot.data.model.Draft;
import com.mail.mini_mailing_app.spring.boot.data.model.Inbox;
import com.mail.mini_mailing_app.spring.boot.data.model.Sent;
import com.mail.mini_mailing_app.spring.boot.data.model.User;
import com.mail.mini_mailing_app.spring.boot.services.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/mailapp/user/")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterUserRequest request){
        String response = userService.registerUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @PostMapping("verify")
    public ResponseEntity<?> verify(@Valid @RequestBody VerificationRequest request){
        AuthenticationResponse response = userService.verifyUser(request);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
    }
    @PostMapping("resent_verification_token")
    public ResponseEntity<?> resendToken(@Valid @RequestParam String phoneNumber){
        String response = userService.resendVerificationToken(phoneNumber);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestParam String email, @Valid@RequestParam String password){
        AuthenticationResponse response = userService.login(email, password);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    @GetMapping("get/{userid}")
    public ResponseEntity<?> getUserById(@Valid @PathVariable Long userid){
        User user = userService.getUserById(userid);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }
    @PostMapping("send_mail")
    public ResponseEntity<?> sendMail(@Valid @RequestBody MailRequest request){
        MailResponse response = userService.sendMail(request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    @PostMapping("draft_mail")
    public ResponseEntity<?> draftMail(@Valid @RequestBody MailRequest request){
        MailResponse response = userService.draftMail(request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    @GetMapping("inbox/get")
    public ResponseEntity<?> getInboxById(@Valid @RequestParam long userId, @Valid @RequestParam long inboxId){
        Inbox response = userService.getInboxById(userId, inboxId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    @GetMapping("sent/get")
    public ResponseEntity<?> getSentById(@Valid @RequestParam long userId, @Valid @RequestParam long mailId){
        Sent response = userService.getSentMailById(userId, mailId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    @GetMapping("draft/get")
    public ResponseEntity<?> getDraftedMailById(@Valid @RequestParam long userId, @Valid @RequestParam long draftId){
        Draft response = userService.getDraftedMailById(userId, draftId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    @PutMapping("update_user")
    public ResponseEntity<?> updateUser(@Valid @RequestBody UpdateUserRequest request){
        UpdateUserResponse response = userService.updateUser(request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping(value = "upload_image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadImage(@Valid @ModelAttribute UploadImageRequest request){
        String response = userService.uploadImage(request);
        return ResponseEntity.ok(response);
    }
    @PostMapping("reset_password_sms")
    public ResponseEntity<?> sendResetPasswordSms(@Valid @RequestParam String phoneNumber){
        ApiResponse response = userService.sendResetPasswordSms(phoneNumber);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    @PutMapping("password/reset")
    public ResponseEntity<?> resetPassword(@Valid @RequestBody ResetPasswordRequest request){
        UpdateUserResponse response = userService.resetPassword(request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    @DeleteMapping("inbox/delete")
    public ResponseEntity<?> deleteInbox(@Valid @RequestParam long userId, @Valid @RequestParam long inboxId){
        userService.deleteInboxById(userId, inboxId);
        return ResponseEntity.ok("mail deleted successfully");
    }
    @DeleteMapping("sent/delete")
    public ResponseEntity<?> deleteSentMailById(@Valid @RequestParam long userId, @Valid @RequestParam long mailId){
        userService.deleteSentMailById(userId, mailId);
        return ResponseEntity.ok("mail deleted successfully");
    }
    @DeleteMapping("draft/delete")
    public ResponseEntity<?> deleteDraftById(@Valid @RequestParam long userId, @Valid @RequestParam long draftId){
        userService.deleteDraftById(userId, draftId);
        return ResponseEntity.ok("mail delete successfully");
    }
    @DeleteMapping("inbox/delete_all/{userid}")
    public ResponseEntity<?> deleteAllInbox(@Valid @PathVariable Long userid){
        userService.deleteAllInbox(userid);
        return ResponseEntity.ok("All Inbox deleted");
    }
    @DeleteMapping("sent/delete_all/{userid}")
    public ResponseEntity<?> deleteAllSent(@Valid @PathVariable Long userid){
        userService.deleteAllSent(userid);
        return ResponseEntity.ok("All sent mail deleted");
    }
    @DeleteMapping("drafts/delete_all/{userid}")
    public ResponseEntity<?> deleteAllDrafts(@Valid @PathVariable Long userid){
        userService.deleteAllDrafts(userid);
        return ResponseEntity.ok("All drafts deleted");
    }
    @DeleteMapping("delete/{userid}")
    public ResponseEntity<?> deleteUserById(@Valid @PathVariable Long userid){
        userService.deleteUserById(userid);
        return ResponseEntity.ok("Account deleted");
    }
 }
