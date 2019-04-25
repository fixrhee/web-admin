package com.jpa.optima.admin.controller;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.ws.Holder;

import org.bellatrix.services.ws.members.LoadMembersByExternalIDRequest;
import org.bellatrix.services.ws.members.LoadMembersByUsernameRequest;
import org.bellatrix.services.ws.members.LoadMembersResponse;
import org.bellatrix.services.ws.members.Member;
import org.bellatrix.services.ws.members.MemberService;
import org.bellatrix.services.ws.menu.LoadMenuByGroupsRequest;
import org.bellatrix.services.ws.menu.LoadMenuByGroupsResponse;
import org.bellatrix.services.ws.menu.Menu;
import org.bellatrix.services.ws.menu.MenuService;
import org.bellatrix.services.ws.notifications.Notification;
import org.bellatrix.services.ws.notifications.NotificationRequest;
import org.bellatrix.services.ws.notifications.NotificationResponse;
import org.bellatrix.services.ws.notifications.NotificationService;
import org.bellatrix.services.ws.notifications.TransactionException_Exception;
import org.bellatrix.services.ws.transfertypes.LoadTransferTypesRequest;
import org.bellatrix.services.ws.transfertypes.LoadTransferTypesResponse;
import org.bellatrix.services.ws.transfertypes.TransferType;
import org.bellatrix.services.ws.transfertypes.TransferTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jpa.optima.admin.model.AdminMenu;

@Component
public class NotificationProcessor {

	@Autowired
	private ContextLoader contextLoader;

	public String loadAllNotification(Integer currentPage, Integer pageSize) throws MalformedURLException {
		URL url = new URL(contextLoader.getHostWSUrl() + "notifications?wsdl");
		QName qName = new QName(contextLoader.getHostWSPort(), "NotificationService");
		NotificationService service = new NotificationService(url, qName);
		Notification client = service.getNotificationPort();

		org.bellatrix.services.ws.notifications.Header headerNotification = new org.bellatrix.services.ws.notifications.Header();
		headerNotification.setToken(contextLoader.getHeaderToken());
		Holder<org.bellatrix.services.ws.notifications.Header> notificationHeaderAuth = new Holder<org.bellatrix.services.ws.notifications.Header>();
		notificationHeaderAuth.value = headerNotification;

		NotificationRequest notificationRequest = new NotificationRequest();
		notificationRequest.setCurrentPage(currentPage);
		notificationRequest.setPageSize(pageSize);

		NotificationResponse response = client.loadNotifications(notificationHeaderAuth, notificationRequest);

		Map<String, Object> trxMap = new HashMap<String, Object>();
		trxMap.put("data", response.getNotification());
		trxMap.put("recordsTotal", response.getNotification().size());
		trxMap.put("recordsFiltered", response.getNotification().size());
		return Utils.toJSON(trxMap);
	}

	public List<String> getListNotification(Integer currentPage, Integer pageSize) throws MalformedURLException {
		URL url = new URL(contextLoader.getHostWSUrl() + "notifications?wsdl");
		QName qName = new QName(contextLoader.getHostWSPort(), "NotificationService");
		NotificationService service = new NotificationService(url, qName);
		Notification client = service.getNotificationPort();

		org.bellatrix.services.ws.notifications.Header headerNotification = new org.bellatrix.services.ws.notifications.Header();
		headerNotification.setToken(contextLoader.getHeaderToken());
		Holder<org.bellatrix.services.ws.notifications.Header> notificationHeaderAuth = new Holder<org.bellatrix.services.ws.notifications.Header>();
		notificationHeaderAuth.value = headerNotification;

		NotificationRequest notificationRequest = new NotificationRequest();
		notificationRequest.setCurrentPage(currentPage);
		notificationRequest.setPageSize(pageSize);

		NotificationResponse response = client.loadNotifications(notificationHeaderAuth, notificationRequest);

		List<String> memberList = new LinkedList<String>();
		if (response.getNotification().size() > 0) {
			for (int i = 0; i < response.getNotification().size(); i++) {
				String composeAccount = response.getNotification().get(i).getId() + " - "
						+ response.getNotification().get(i).getName();
				memberList.add(composeAccount);
			}
			return memberList;
		} else {
			return null;
		}
	}

	public void createNotification(com.jpa.optima.admin.model.Notification req)
			throws MalformedURLException, TransactionException_Exception {
		URL url = new URL(contextLoader.getHostWSUrl() + "notifications?wsdl");
		QName qName = new QName(contextLoader.getHostWSPort(), "NotificationService");
		NotificationService service = new NotificationService(url, qName);
		Notification client = service.getNotificationPort();

		org.bellatrix.services.ws.notifications.Header headerNotification = new org.bellatrix.services.ws.notifications.Header();
		headerNotification.setToken(contextLoader.getHeaderToken());
		Holder<org.bellatrix.services.ws.notifications.Header> notificationHeaderAuth = new Holder<org.bellatrix.services.ws.notifications.Header>();
		notificationHeaderAuth.value = headerNotification;

		NotificationRequest notificationRequest = new NotificationRequest();
		notificationRequest.setModuleURL(req.getDestination());
		notificationRequest.setName(req.getName());
		client.createNotifications(notificationHeaderAuth, notificationRequest);
	}

	public void editNotification(com.jpa.optima.admin.model.Notification req)
			throws MalformedURLException, TransactionException_Exception {
		URL url = new URL(contextLoader.getHostWSUrl() + "notifications?wsdl");
		QName qName = new QName(contextLoader.getHostWSPort(), "NotificationService");
		NotificationService service = new NotificationService(url, qName);
		Notification client = service.getNotificationPort();

		org.bellatrix.services.ws.notifications.Header headerNotification = new org.bellatrix.services.ws.notifications.Header();
		headerNotification.setToken(contextLoader.getHeaderToken());
		Holder<org.bellatrix.services.ws.notifications.Header> notificationHeaderAuth = new Holder<org.bellatrix.services.ws.notifications.Header>();
		notificationHeaderAuth.value = headerNotification;

		NotificationRequest notificationRequest = new NotificationRequest();
		notificationRequest.setModuleURL(req.getDestination());
		notificationRequest.setName(req.getName());
		notificationRequest.setId(req.getId());
		client.editNotifications(notificationHeaderAuth, notificationRequest);
	}

	public NotificationResponse loadNotificationByID(Integer id) throws MalformedURLException {
		URL url = new URL(contextLoader.getHostWSUrl() + "notifications?wsdl");
		QName qName = new QName(contextLoader.getHostWSPort(), "NotificationService");
		NotificationService service = new NotificationService(url, qName);
		Notification client = service.getNotificationPort();

		org.bellatrix.services.ws.notifications.Header headerNotification = new org.bellatrix.services.ws.notifications.Header();
		headerNotification.setToken(contextLoader.getHeaderToken());
		Holder<org.bellatrix.services.ws.notifications.Header> notificationHeaderAuth = new Holder<org.bellatrix.services.ws.notifications.Header>();
		notificationHeaderAuth.value = headerNotification;

		NotificationRequest notificationRequest = new NotificationRequest();
		notificationRequest.setId(id);

		return client.loadNotifications(notificationHeaderAuth, notificationRequest);
	}
}
