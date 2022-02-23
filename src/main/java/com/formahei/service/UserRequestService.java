package com.formahei.service;

import com.formahei.dao.RequestDAO;
import com.formahei.dao.UserRequestDAO;

public class UserRequestService {
    UserRequestDAO requestDAO;
    public UserRequestService(UserRequestDAO requestDAO){
        this.requestDAO = requestDAO;
    }


    public void addUserRequest(int idRequest, String userLogin, String client) {
        requestDAO.addUserRequest(idRequest, userLogin, client);
    }
}
