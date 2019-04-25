package com.jpa.optima.admin.controller;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.ws.Holder;
import org.bellatrix.services.ws.menu.LoadMenuByGroupsRequest;
import org.bellatrix.services.ws.menu.LoadMenuByGroupsResponse;
import org.bellatrix.services.ws.menu.Menu;
import org.bellatrix.services.ws.menu.MenuService;
import org.bellatrix.services.ws.notifications.Notification;
import org.bellatrix.services.ws.notifications.NotificationRequest;
import org.bellatrix.services.ws.notifications.NotificationResponse;
import org.bellatrix.services.ws.notifications.NotificationService;
import org.bellatrix.services.ws.transfertypes.LoadTransferTypesRequest;
import org.bellatrix.services.ws.transfertypes.LoadTransferTypesResponse;
import org.bellatrix.services.ws.transfertypes.TransferType;
import org.bellatrix.services.ws.transfertypes.TransferTypeService;
import org.bellatrix.services.ws.webservices.CreateWebserviceRequest;
import org.bellatrix.services.ws.webservices.LoadWebserviceRequest;
import org.bellatrix.services.ws.webservices.LoadWebserviceResponse;
import org.bellatrix.services.ws.webservices.TransactionException_Exception;
import org.bellatrix.services.ws.webservices.Webservice;
import org.bellatrix.services.ws.webservices.WebservicePermissionRequest;
import org.bellatrix.services.ws.webservices.WebserviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jpa.optima.admin.model.AdminMenu;

@Component
public class WebserviceProcessor {

	@Autowired
	private ContextLoader contextLoader;

	public String loadAllWebservice(Integer currentPage, Integer pageSize) throws MalformedURLException {
		URL url = new URL(contextLoader.getHostWSUrl() + "webservices?wsdl");
		QName qName = new QName(contextLoader.getHostWSPort(), "WebserviceService");
		WebserviceService service = new WebserviceService(url, qName);
		Webservice client = service.getWebservicePort();

		org.bellatrix.services.ws.webservices.Header headerWebservice = new org.bellatrix.services.ws.webservices.Header();
		headerWebservice.setToken(contextLoader.getHeaderToken());
		Holder<org.bellatrix.services.ws.webservices.Header> webserviceHeaderAuth = new Holder<org.bellatrix.services.ws.webservices.Header>();
		webserviceHeaderAuth.value = headerWebservice;

		LoadWebserviceRequest loadWebserviceRequest = new LoadWebserviceRequest();
		loadWebserviceRequest.setCurrentPage(currentPage);
		loadWebserviceRequest.setPageSize(pageSize);
		LoadWebserviceResponse response = client.loadWebservices(webserviceHeaderAuth, loadWebserviceRequest);

		Map<String, Object> trxMap = new HashMap<String, Object>();
		trxMap.put("data", response.getWebservice());
		trxMap.put("recordsTotal", response.getTotalRecords());
		trxMap.put("recordsFiltered", response.getTotalRecords());
		return Utils.toJSON(trxMap);
	}

	public String loadWebservicePermission(Integer id) throws MalformedURLException {
		URL url = new URL(contextLoader.getHostWSUrl() + "webservices?wsdl");
		QName qName = new QName(contextLoader.getHostWSPort(), "WebserviceService");
		WebserviceService service = new WebserviceService(url, qName);
		Webservice client = service.getWebservicePort();

		org.bellatrix.services.ws.webservices.Header headerWebservice = new org.bellatrix.services.ws.webservices.Header();
		headerWebservice.setToken(contextLoader.getHeaderToken());
		Holder<org.bellatrix.services.ws.webservices.Header> webserviceHeaderAuth = new Holder<org.bellatrix.services.ws.webservices.Header>();
		webserviceHeaderAuth.value = headerWebservice;

		LoadWebserviceRequest loadWebserviceRequest = new LoadWebserviceRequest();
		loadWebserviceRequest.setId(id);

		LoadWebserviceResponse response = client.loadWebservicesPermission(webserviceHeaderAuth, loadWebserviceRequest);

		Map<String, Object> trxMap = new HashMap<String, Object>();
		trxMap.put("data", response.getWebservice());
		trxMap.put("recordsTotal", response.getWebservice().size());
		trxMap.put("recordsFiltered", response.getWebservice().size());
		return Utils.toJSON(trxMap);

	}

	public LoadWebserviceResponse loadwebserviceByID(Integer id) throws MalformedURLException {
		URL url = new URL(contextLoader.getHostWSUrl() + "webservices?wsdl");
		QName qName = new QName(contextLoader.getHostWSPort(), "WebserviceService");
		WebserviceService service = new WebserviceService(url, qName);
		Webservice client = service.getWebservicePort();

		org.bellatrix.services.ws.webservices.Header headerWebservice = new org.bellatrix.services.ws.webservices.Header();
		headerWebservice.setToken(contextLoader.getHeaderToken());
		Holder<org.bellatrix.services.ws.webservices.Header> webserviceHeaderAuth = new Holder<org.bellatrix.services.ws.webservices.Header>();
		webserviceHeaderAuth.value = headerWebservice;

		LoadWebserviceRequest loadWebserviceRequest = new LoadWebserviceRequest();
		loadWebserviceRequest.setId(id);

		return client.loadWebservices(webserviceHeaderAuth, loadWebserviceRequest);
	}

	public void createWebservice(com.jpa.optima.admin.model.Webservice req)
			throws MalformedURLException, TransactionException_Exception {
		URL url = new URL(contextLoader.getHostWSUrl() + "webservices?wsdl");
		QName qName = new QName(contextLoader.getHostWSPort(), "WebserviceService");
		WebserviceService service = new WebserviceService(url, qName);
		Webservice client = service.getWebservicePort();

		org.bellatrix.services.ws.webservices.Header headerWebservice = new org.bellatrix.services.ws.webservices.Header();
		headerWebservice.setToken(contextLoader.getHeaderToken());
		Holder<org.bellatrix.services.ws.webservices.Header> webserviceHeaderAuth = new Holder<org.bellatrix.services.ws.webservices.Header>();
		webserviceHeaderAuth.value = headerWebservice;

		CreateWebserviceRequest webserviceRequest = new CreateWebserviceRequest();
		webserviceRequest.setActive(req.isEnabled());
		webserviceRequest.setHash(req.getHash());
		webserviceRequest.setName(req.getName());
		webserviceRequest.setPassword(req.getPassword());
		webserviceRequest.setSecureTransaction(req.isSecure());
		webserviceRequest.setUsername(req.getUsername());

		client.createWebservices(webserviceHeaderAuth, webserviceRequest);
	}

	public void editWebservice(com.jpa.optima.admin.model.Webservice req)
			throws MalformedURLException, TransactionException_Exception {
		URL url = new URL(contextLoader.getHostWSUrl() + "webservices?wsdl");
		QName qName = new QName(contextLoader.getHostWSPort(), "WebserviceService");
		WebserviceService service = new WebserviceService(url, qName);
		Webservice client = service.getWebservicePort();

		org.bellatrix.services.ws.webservices.Header headerWebservice = new org.bellatrix.services.ws.webservices.Header();
		headerWebservice.setToken(contextLoader.getHeaderToken());
		Holder<org.bellatrix.services.ws.webservices.Header> webserviceHeaderAuth = new Holder<org.bellatrix.services.ws.webservices.Header>();
		webserviceHeaderAuth.value = headerWebservice;

		CreateWebserviceRequest webserviceRequest = new CreateWebserviceRequest();
		webserviceRequest.setId(req.getId());
		webserviceRequest.setActive(req.isEnabled());
		webserviceRequest.setHash(req.getHash());
		webserviceRequest.setName(req.getName());
		webserviceRequest.setPassword(req.getPassword());
		webserviceRequest.setSecureTransaction(req.isSecure());
		webserviceRequest.setUsername(req.getUsername());

		client.updateWebservices(webserviceHeaderAuth, webserviceRequest);
	}

	public void addWebservicePermission(com.jpa.optima.admin.model.WebservicePermission req)
			throws MalformedURLException, TransactionException_Exception {
		URL url = new URL(contextLoader.getHostWSUrl() + "webservices?wsdl");
		QName qName = new QName(contextLoader.getHostWSPort(), "WebserviceService");
		WebserviceService service = new WebserviceService(url, qName);
		Webservice client = service.getWebservicePort();

		org.bellatrix.services.ws.webservices.Header headerWebservice = new org.bellatrix.services.ws.webservices.Header();
		headerWebservice.setToken(contextLoader.getHeaderToken());
		Holder<org.bellatrix.services.ws.webservices.Header> webserviceHeaderAuth = new Holder<org.bellatrix.services.ws.webservices.Header>();
		webserviceHeaderAuth.value = headerWebservice;

		Integer groupID = Integer.valueOf(req.getGroup().split("-")[0].trim());

		WebservicePermissionRequest webserviceRequest = new WebservicePermissionRequest();
		webserviceRequest.setGroupID(groupID);
		webserviceRequest.setWebserviceID(req.getWsID());
		client.addWebservicePermission(webserviceHeaderAuth, webserviceRequest);
	}

	public void deleteWebservicePermission(Integer id) throws MalformedURLException, TransactionException_Exception {
		URL url = new URL(contextLoader.getHostWSUrl() + "webservices?wsdl");
		QName qName = new QName(contextLoader.getHostWSPort(), "WebserviceService");
		WebserviceService service = new WebserviceService(url, qName);
		Webservice client = service.getWebservicePort();

		org.bellatrix.services.ws.webservices.Header headerWebservice = new org.bellatrix.services.ws.webservices.Header();
		headerWebservice.setToken(contextLoader.getHeaderToken());
		Holder<org.bellatrix.services.ws.webservices.Header> webserviceHeaderAuth = new Holder<org.bellatrix.services.ws.webservices.Header>();
		webserviceHeaderAuth.value = headerWebservice;

		WebservicePermissionRequest webserviceRequest = new WebservicePermissionRequest();
		webserviceRequest.setId(id);
		client.deleteWebservicePermission(webserviceHeaderAuth, webserviceRequest);
	}
}
