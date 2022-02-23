package com.formahei.service;

import com.formahei.dao.RequestDAO;
import com.formahei.entity.RepairRequest;

import java.util.List;

public class RequestService {
    RequestDAO requestDAO;
    public RequestService(RequestDAO requestDAO){
        this.requestDAO = requestDAO;
    }

    public int addRequest(RepairRequest request) {
        return requestDAO.addRequest(request);
    }

    public void updateRequestByManager(int id, String status, int price) {
        requestDAO.updateRequestByManager(id, status, price);
    }

    public RepairRequest findRequestByID(int id) {
        return requestDAO.findRequestByID(id);
    }

    public void updateRequestByMaster(int id, String state) {
        requestDAO.updateRequestByMaster(id, state);
    }

    public List<RepairRequest> findAllRequests(String orderBy, int page, int amount) {
        return requestDAO.findAllRequests(orderBy, page, amount);
    }

    public int getNumberOfResponsesRows() {
        return requestDAO.findAllRequests().size()+1;
    }
}
