package com.teamtrace.realland.service.impl;

import com.teamtrace.realland.api.request.*;
import com.teamtrace.realland.api.response.ClientLoginResponse;
import com.teamtrace.realland.api.response.CreationResponse;
import com.teamtrace.realland.api.response.UpdateResponse;
import com.teamtrace.realland.model.Client;
import com.teamtrace.realland.repository.ClientRepository;
import com.teamtrace.realland.service.ClientService;
import com.teamtrace.realland.util.SessionUtil;
import com.teamtrace.realland.util.SimpleOTPGenerator;
import com.teamtrace.realland.util.UUIDUtil;
import com.teamtrace.realland.util.constant.Statuses;
import com.teamtrace.realland.util.constant.SystemParameters;
import com.teamtrace.realland.util.password.PasswordHandler;
import com.teamtrace.realland.util.password.PasswordHandlerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.GeneralSecurityException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ClientServiceImpl implements ClientService {
    private static Logger logger = LoggerFactory.getLogger(ClientServiceImpl.class);

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    private PasswordHandlerFactory passwordHandlerFactory;
    @Autowired
    private SessionUtil sessionUtil;

    public CreationResponse createClient(ClientCreateRequest request) {
        CreationResponse response = new CreationResponse();

        List<Client> clients = clientRepository.findClientByLoginName(request.getLoginName());
        if (!clients.isEmpty()) {
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            logger.info("Create client, login name already exists. {}", request.getLoginName());
            response.setMessage(request.getResourceBundle().getString("login_name_not_available"));

            return response;
        }

        Client client = new Client();
        client.setChannelId(request.getChannelId());
        client.setReference("test");
        client.setEmail(request.getEmail());
        client.setLoginName(request.getLoginName());
        client.setPassword(request.getPassword());
        client.setMobile(request.getMobile());
        client.setFullName(request.getFullName());
        client.setAddress(request.getAddress());
        client.setProfileImageUrl(request.getProfileImageUrl());
        client.setNotificationEnable(true);
        client.setAuthType(request.getAuthType());
        client.setDeviceType(request.getDeviceType());
        client.setStatus(Statuses.PENDING);
        client.setCreatedDate(new Date());

        String otp = SimpleOTPGenerator.random(SystemParameters.OTP_LENGTH);

        client.setOtp(otp);
        client.setOtpStatus(Statuses.PENDING);

        try {
            client = clientRepository.save(client);

            response.setStatus(Statuses.RESPONSE_STATUS_SUCCESS);
            response.setId(client.getClientId());
            //response.setData(ModelToApiConverter.convert(client));
            logger.info("Create client, create client. id : {}", client.getClientId());
        } catch (Exception e) {
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            response.setMessage(request.getResourceBundle().getString("error_occurred"));
            logger.info("Create client, create client not successful. error : {}", e.getMessage());

            return response;
        }

        //todo send otp

        return response;
    }

    public UpdateResponse confirmOtp(OtpConfirmationRequest request) {
        UpdateResponse response = new UpdateResponse();

        List<Client> clients = clientRepository.findClientByLoginName(request.getLoginName());
        if (clients == null || clients.isEmpty()) {
            logger.info("Client otp confirmation, invalid user. login name : {}", request.getLoginName());

            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            response.setMessage(request.getResourceBundle().getString("invalid_mobile"));

            return response;
        }
        Client client = clients.get(0);

        if (client.getOtp() == null || !client.getOtp().equals(request.getOtp())) {
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            response.setMessage(request.getResourceBundle().getString("invalid_login_name"));
            logger.info("Client otp confirmation, invalid OTP. login name : {}", request.getLoginName());

            return response;
        }

        client.setOtp(null);
        client.setStatus(Statuses.APPROVED);
        client.setOtpStatus(Statuses.APPROVED);

        try {
            clientRepository.save(client);
            response.setStatus(Statuses.RESPONSE_STATUS_SUCCESS);
            response.setId(client.getClientId());

            logger.info("Client mobile confirm successfully. Id : {}", client.getClientId());
        } catch (Exception e) {
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            response.setMessage(request.getResourceBundle().getString("error_occurred"));

            logger.error("Client mobile not confirmed. error : {}", e.getMessage());
        }

        return response;
    }

    public UpdateResponse resendOtp(ResendOtpRequest request) {
        UpdateResponse response = new UpdateResponse();

        List<Client> clients =
                clientRepository.findClientByLoginNameAndMobile(request.getLoginName(), request.getMobile());
        if (clients == null || clients.isEmpty()) {
            logger.info("Client otp resend, invalid user. mobile number : {}", request.getLoginName());

            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            response.setMessage(request.getResourceBundle().getString("invalid_login_name"));

            return response;
        }
        Client client = clients.get(0);

        String otp = SimpleOTPGenerator.random(SystemParameters.OTP_LENGTH);

        client.setOtp(otp);

        try {
            clientRepository.save(client);
            response.setStatus(Statuses.RESPONSE_STATUS_SUCCESS);
            response.setId(client.getClientId());

            logger.info("Client otp resend successfully. Id : {}", client.getClientId());
        } catch (Exception e) {
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            response.setMessage(request.getResourceBundle().getString("error_occurred"));

            logger.error("Client otp not resend. Id : {}", e.getMessage());
        }

        //todo send otp
        return response;
    }

    public UpdateResponse updateClientBasicDetails(ClientBasicDetailsUpdateRequest request) {
        UpdateResponse response = new UpdateResponse();

        Optional<Client> optional = clientRepository.findById(request.getClient().getClientId());

        if (!optional.isPresent()) {
            logger.info("Invalid client. id : {}", request.getClient().getClientId());
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            return response;
        }

        Client old = optional.get();
        old.setFullName(request.getFullName());
        old.setAddress(request.getAddress());

        try {
            clientRepository.save(old);

            response.setStatus(Statuses.RESPONSE_STATUS_SUCCESS);
            logger.info("Update client basic details successful. id : {}", old.getClientId());
        } catch (Exception e) {
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            response.setMessage(request.getResourceBundle().getString("error_occurred"));
            logger.info("Update  client basic details not successful. error : {}", e.getMessage());
        }

        return response;
    }

    public UpdateResponse updateClientProfileImage(ClientProfileImageUpdate request) {
        UpdateResponse response = new UpdateResponse();

        Optional<Client> optional = clientRepository.findById(request.getClient().getClientId());

        if (!optional.isPresent()) {
            logger.info("Invalid client. id : {}", request.getClient().getClientId());
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            return response;
        }

        Client old = optional.get();
        old.setProfileImageUrl(request.getProfileImageUrl());

        try {
            clientRepository.save(old);

            response.setStatus(Statuses.RESPONSE_STATUS_SUCCESS);
            logger.info("Update client profile image successful. id : {}", old.getClientId());
        } catch (Exception e) {
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            response.setMessage(request.getResourceBundle().getString("error_occurred"));
            logger.info("Update  client profile image not successful. error : {}", e.getMessage());
        }

        return response;
    }

    public UpdateResponse changePassword(ClientChangePasswordRequest request) {
        UpdateResponse response = new UpdateResponse();

        Optional<Client> optional = clientRepository.findById(request.getClient().getClientId());

        if (!optional.isPresent()) {
            logger.info("Invalid client. id : {}", request.getClient().getClientId());
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            return response;
        }

        Client old = optional.get();
        if (!request.getOldPassword().equals(old.getPassword())) {
            logger.info("Invalid password. id : {}", request.getClient().getClientId());
            response.setMessage("Invalid password.");
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            return response;
        }


        old.setPassword(request.getNewPassword());

        try {
            clientRepository.save(old);

            response.setStatus(Statuses.RESPONSE_STATUS_SUCCESS);
            logger.info("Change client password successful. id : {}", old.getClientId());
        } catch (Exception e) {
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            response.setMessage(request.getResourceBundle().getString("error_occurred"));
            logger.info("Change client password not successful. error : {}", e.getMessage());
        }

        return response;
    }

    public UpdateResponse approveClient(StatusUpdateRequest request) {
        UpdateResponse response = new UpdateResponse();

        Optional<Client> optional = clientRepository.findById(request.getPrimaryId());

        if (!optional.isPresent()) {
            logger.info("Invalid client. id : {}", request.getPrimaryId());
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            return response;
        }

        Client old = optional.get();
        if (!(old.getStatus() == Statuses.PENDING ||
                old.getStatus() == Statuses.APPROVED ||
                old.getStatus() == Statuses.SUSPENDED)) {
            logger.info("Can't approve client. id : {}", request.getPrimaryId());
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            return response;
        }

        old.setStatus(Statuses.APPROVED);

        try {
            clientRepository.save(old);

            response.setStatus(Statuses.RESPONSE_STATUS_SUCCESS);
            logger.info("Approve client successful. id : {}", old.getClientId());
        } catch (Exception e) {
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            response.setMessage(request.getResourceBundle().getString("error_occurred"));
            logger.info("Approve client not successful. error : {}", e.getMessage());
        }

        return response;
    }

    public UpdateResponse suspendClient(StatusUpdateRequest request) {
        UpdateResponse response = new UpdateResponse();

        Optional<Client> optional = clientRepository.findById(request.getPrimaryId());

        if (!optional.isPresent()) {
            logger.info("Invalid client. id : {}", request.getPrimaryId());
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            return response;
        }

        Client old = optional.get();
        if (!(old.getStatus() == Statuses.PENDING ||
                old.getStatus() == Statuses.APPROVED ||
                old.getStatus() == Statuses.SUSPENDED)) {
            logger.info("Can't suspend client. id : {}", request.getPrimaryId());
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            return response;
        }

        old.setStatus(Statuses.SUSPENDED);

        try {
            clientRepository.save(old);

            response.setStatus(Statuses.RESPONSE_STATUS_SUCCESS);
            logger.info("Suspend client successful. id : {}", old.getClientId());
        } catch (Exception e) {
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            response.setMessage(request.getResourceBundle().getString("error_occurred"));
            logger.info("Suspend client not successful. error : {}", e.getMessage());
        }

        return response;
    }

    public UpdateResponse deleteClient(StatusUpdateRequest request) {
        UpdateResponse response = new UpdateResponse();

        Optional<Client> optional = clientRepository.findById(request.getPrimaryId());

        if (!optional.isPresent()) {
            logger.info("Invalid client. id : {}", request.getPrimaryId());
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            return response;
        }

        Client old = optional.get();
        old.setStatus(Statuses.DELETED);

        try {
            clientRepository.save(old);

            response.setStatus(Statuses.RESPONSE_STATUS_SUCCESS);
            logger.info("Delete client successful. id : {}", old.getClientId());
        } catch (Exception e) {
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            response.setMessage(request.getResourceBundle().getString("error_occurred"));
            logger.info("Delete client not successful. error : {}", e.getMessage());
        }

        return response;
    }

    public ClientLoginResponse loginClient(ClientLoginRequest request) {
        ClientLoginResponse response = new ClientLoginResponse();

        List<Client> clients = clientRepository.findClientByLoginName(request.getLoginName());

        if (clients.isEmpty()) {
            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            logger.info("Login client, client not found. username : {}", request.getLoginName());
            response.setMessage(request.getResourceBundle().getString("login_name_not_available"));

            return response;
        }
        Client client = clients.get(0);

        try {
            boolean validPasswordNew = passwordHandlerFactory.getPasswordHandler(PasswordHandler.MD5)
                    .validatePassword(request.getLoginName(), request.getPassword(), client.getPassword());
            boolean validPassword = request.getPassword().equals(client.getPassword());
            if (!validPassword) {
                logger.info("Login client, invalid password for client. username : {}", request.getLoginName());

                response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
                response.setMessage(request.getResourceBundle().getString("invalid_login_name_or_password"));

                return response;
            }

        } catch (GeneralSecurityException e) {
            logger.info("Login client, error occurred. error : {}", e.getMessage());

            response.setStatus(Statuses.RESPONSE_STATUS_FAIL);
            response.setMessage(request.getResourceBundle().getString("error_occurred"));

            return response;
        }

        String sessionId = UUIDUtil.generateToken();

        response.setStatus(Statuses.RESPONSE_STATUS_SUCCESS);
        response.setClientId(client.getClientId());
        response.setFullName(client.getFullName());
        response.setProfileImageUrl(client.getProfileImageUrl());
        response.setSessionId(sessionId);

        sessionUtil.addClientSession(sessionId, client);

        return response;
    }
}
