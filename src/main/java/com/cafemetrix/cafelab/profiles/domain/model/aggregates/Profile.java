package com.cafemetrix.cafelab.profiles.domain.model.aggregates;

import com.cafemetrix.cafelab.profiles.domain.model.commands.CreateProfileCommand;
import com.cafemetrix.cafelab.profiles.domain.model.valueobjects.EmailAddress;
import com.cafemetrix.cafelab.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;

@Entity
public class Profile extends AuditableAbstractAggregateRoot<Profile> {
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "address", column = @Column(name = "email_address"))})
    private EmailAddress emailAddress;

    private String name;
    private String password;
    private String role;
    private String cafeteriaName;
    private String experience;
    private String profilePicture;
    private String paymentMethod;
    private boolean isFirstLogin;
    private String plan;
    private boolean hasPlan;

    /** FK opcional hacia {@code users.id} (IAM); permite resolver el perfil aunque el username JWT no sea el email. */
    @Column(name = "user_id")
    private Long iamUserId;

    
    public Profile(String name, String email, String password, String role, String cafeteriaName, String experience, String profilePicture, String paymentMethod, boolean isFirstLogin, String plan, boolean hasPlan) {
        this.name = name;
        this.emailAddress = new EmailAddress(email);
        this.password = password;
        this.role = role;
        this.cafeteriaName = cafeteriaName;
        this.experience = experience;
        this.profilePicture = profilePicture;
        this.paymentMethod = paymentMethod;
        this.isFirstLogin = isFirstLogin;
        this.plan = plan;
        this.hasPlan = hasPlan;
    }

    
    public Profile() {
        this.emailAddress = new EmailAddress();
    }

    
    public Profile(CreateProfileCommand command) {
        this.name = command.name();
        this.emailAddress = new EmailAddress(command.email());
        this.role = command.role();
        this.cafeteriaName = command.cafeteriaName();
        this.experience = command.experience();
        this.profilePicture = command.profilePicture();
        this.paymentMethod = command.paymentMethod();
        this.isFirstLogin = command.isFirstLogin();
        this.plan = command.plan();
        this.hasPlan = command.hasPlan();
    }

    
    public Profile(String name, String email, String role, String cafeteriaName, String experience, String profilePicture, String paymentMethod, boolean isFirstLogin, String plan, boolean hasPlan) {
        this.name = name;
        this.emailAddress = new EmailAddress(email);
        this.role = role;
        this.cafeteriaName = cafeteriaName;
        this.experience = experience;
        this.profilePicture = profilePicture;
        this.paymentMethod = paymentMethod;
        this.isFirstLogin = isFirstLogin;
        this.plan = plan;
        this.hasPlan = hasPlan;
    }

    /**
     * Email address getter
     * @return Email address
     */
    public String getEmailAddress() {
        return emailAddress.address();
    }

    
    public void updateName(String name) {
        this.name = name;
    }

    
    public void updateEmailAddress(String email) {
        this.emailAddress = new EmailAddress(email);
    }

    
    public void updatePassword(String password) {
        this.password = password;
    }

    
    public void updateRole(String role) {
        this.role = role;
    }

    
    public void updateCafeteriaName(String cafeteriaName) {
        this.cafeteriaName = cafeteriaName;
    }

    
    public void updateExperience(String experience) {
        this.experience = experience;
    }

    
    public void updateProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    
    public void updatePaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    
    public void updatePlan(String plan) {
        this.plan = plan;
    }

    
    public void updateFirstLoginStatus(boolean isFirstLogin) {
        this.isFirstLogin = isFirstLogin;
    }

    
    public void updateHasPlanStatus(boolean hasPlan) {
        this.hasPlan = hasPlan;
    }

    
    public String getName() {
        return name;
    }

    
    public String getPassword() {
        return password;
    }

    
    public String getRole() {
        return role;
    }

    
    public String getCafeteriaName() {
        return cafeteriaName;
    }

    
    public String getExperience() {
        return experience;
    }

    
    public String getProfilePicture() {
        return profilePicture;
    }

    
    public String getPaymentMethod() {
        return paymentMethod;
    }

    
    public boolean isFirstLogin() {
        return isFirstLogin;
    }

    
    public String getPlan() {
        return plan;
    }

    
    public boolean hasPlan() {
        return hasPlan;
    }

    public Long getIamUserId() {
        return iamUserId;
    }

    public void setIamUserId(Long iamUserId) {
        this.iamUserId = iamUserId;
    }

}
