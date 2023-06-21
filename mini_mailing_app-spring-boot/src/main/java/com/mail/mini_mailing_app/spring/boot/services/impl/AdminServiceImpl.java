package com.mail.mini_mailing_app.spring.boot.services.impl;

import com.mail.mini_mailing_app.spring.boot.data.dto.request.RegisterAdminRequest;
import com.mail.mini_mailing_app.spring.boot.data.model.AppUser;
import com.mail.mini_mailing_app.spring.boot.data.model.Role;
import com.mail.mini_mailing_app.spring.boot.data.model.User;
import com.mail.mini_mailing_app.spring.boot.data.repository.AdminRepository;
import com.mail.mini_mailing_app.spring.boot.exception.AlreadyExistsException;
import com.mail.mini_mailing_app.spring.boot.exception.EmailAppException;
import com.mail.mini_mailing_app.spring.boot.services.AdminService;
import com.mail.mini_mailing_app.spring.boot.services.AppUserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final AdminRepository adminRepository;
    private final AppUserService appUserService;
    @Override
    public String registerAdmin(RegisterAdminRequest request) {
        AppUser appUser = appUserService.getBy(request.getPhoneNumber()).orElse(null);
        if(appUser != null)
            throw new AlreadyExistsException("An app user with this account already exists");
        else if(adminRepository.count() > 1)
            throw new EmailAppException("There is already an admin");
        else{
            AppUser userDetails = new AppUser();
            userDetails.setFirstName(request.getFirstName());
            userDetails.setLastName(request.getLastName());
            userDetails.setPhoneNumber(request.getPhoneNumber());
            userDetails.setPassword(request.getPassword());
            userDetails.setRole(Role.ADMIN);

            User user = new User();
            user.setUserDetails(userDetails);
        }
        return null;
    }
}
