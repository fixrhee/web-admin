package com.jpa.optima.admin.controller;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import javax.xml.namespace.QName;
import javax.xml.ws.Holder;

import org.bellatrix.services.ws.transfertypes.BrokerRequest;
import org.bellatrix.services.ws.transfertypes.FeeByMemberRequest;
import org.bellatrix.services.ws.transfertypes.FeeByMemberResponse;
import org.bellatrix.services.ws.transfertypes.FeeRequest;
import org.bellatrix.services.ws.transfertypes.LoadBrokerRequest;
import org.bellatrix.services.ws.transfertypes.LoadBrokerResponse;
import org.bellatrix.services.ws.transfertypes.LoadFeesByIDRequest;
import org.bellatrix.services.ws.transfertypes.LoadFeesByIDResponse;
import org.bellatrix.services.ws.transfertypes.LoadFeesByTransferTypeRequest;
import org.bellatrix.services.ws.transfertypes.LoadFeesByTransferTypeResponse;
import org.bellatrix.services.ws.transfertypes.LoadTransferTypesByIDRequest;
import org.bellatrix.services.ws.transfertypes.LoadTransferTypesByIDResponse;
import org.bellatrix.services.ws.transfertypes.LoadTransferTypesByUsernameRequest;
import org.bellatrix.services.ws.transfertypes.LoadTransferTypesByUsernameResponse;
import org.bellatrix.services.ws.transfertypes.LoadTransferTypesRequest;
import org.bellatrix.services.ws.transfertypes.LoadTransferTypesResponse;
import org.bellatrix.services.ws.transfertypes.TransactionException_Exception;
import org.bellatrix.services.ws.transfertypes.TransferType;
import org.bellatrix.services.ws.transfertypes.TransferTypeNotificationRequest;
import org.bellatrix.services.ws.transfertypes.TransferTypeNotificationResponse;
import org.bellatrix.services.ws.transfertypes.TransferTypePermissionRequest;
import org.bellatrix.services.ws.transfertypes.TransferTypePermissionResponse;
import org.bellatrix.services.ws.transfertypes.TransferTypeRequest;
import org.bellatrix.services.ws.transfertypes.TransferTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jpa.optima.admin.model.Broker;
import com.jpa.optima.admin.model.Fee;
import com.jpa.optima.admin.model.MemberFiltering;
import com.jpa.optima.admin.model.TransferNotification;

@Component
public class TransferTypeProcessor {

	@Autowired
	private ContextLoader contextLoader;

	public String loadTransferType(Integer currentPage, Integer pageSize) throws MalformedURLException {
		URL url = new URL(contextLoader.getHostWSUrl() + "transfertypes?wsdl");
		QName qName = new QName(contextLoader.getHostWSPort(), "TransferTypeService");
		TransferTypeService service = new TransferTypeService(url, qName);
		TransferType client = service.getTransferTypePort();

		org.bellatrix.services.ws.transfertypes.Header headerTransferType = new org.bellatrix.services.ws.transfertypes.Header();
		headerTransferType.setToken(contextLoader.getHeaderToken());
		Holder<org.bellatrix.services.ws.transfertypes.Header> transferTypeHeaderAuth = new Holder<org.bellatrix.services.ws.transfertypes.Header>();
		transferTypeHeaderAuth.value = headerTransferType;

		LoadTransferTypesRequest transferTypesRequest = new LoadTransferTypesRequest();
		transferTypesRequest.setCurrentPage(currentPage);
		transferTypesRequest.setPageSize(pageSize);
		LoadTransferTypesResponse loadTransferTypesResponse = client.loadTransferTypes(transferTypeHeaderAuth,
				transferTypesRequest);

		Map<String, Object> trxMap = new HashMap<String, Object>();
		trxMap.put("data", loadTransferTypesResponse.getTransferType());
		trxMap.put("recordsTotal", loadTransferTypesResponse.getTotalRecords());
		trxMap.put("recordsFiltered", loadTransferTypesResponse.getTotalRecords());
		return Utils.toJSON(trxMap);
	}

	public LoadTransferTypesByIDResponse loadTransferType(Integer id) throws MalformedURLException {
		URL url = new URL(contextLoader.getHostWSUrl() + "transfertypes?wsdl");
		QName qName = new QName(contextLoader.getHostWSPort(), "TransferTypeService");
		TransferTypeService service = new TransferTypeService(url, qName);
		TransferType client = service.getTransferTypePort();

		org.bellatrix.services.ws.transfertypes.Header headerTransferType = new org.bellatrix.services.ws.transfertypes.Header();
		headerTransferType.setToken(contextLoader.getHeaderToken());
		Holder<org.bellatrix.services.ws.transfertypes.Header> transferTypeHeaderAuth = new Holder<org.bellatrix.services.ws.transfertypes.Header>();
		transferTypeHeaderAuth.value = headerTransferType;

		LoadTransferTypesByIDRequest transferTypesRequest = new LoadTransferTypesByIDRequest();
		transferTypesRequest.setId(id);
		LoadTransferTypesByIDResponse loadTransferTypesResponse = client.loadTransferTypesByID(transferTypeHeaderAuth,
				transferTypesRequest);

		return loadTransferTypesResponse;
	}

	public List<String> loadTransferType(String username) throws MalformedURLException {
		URL url = new URL(contextLoader.getHostWSUrl() + "transfertypes?wsdl");
		QName qName = new QName(contextLoader.getHostWSPort(), "TransferTypeService");
		TransferTypeService service = new TransferTypeService(url, qName);
		TransferType client = service.getTransferTypePort();

		org.bellatrix.services.ws.transfertypes.Header headerTransferType = new org.bellatrix.services.ws.transfertypes.Header();
		headerTransferType.setToken(contextLoader.getHeaderToken());
		Holder<org.bellatrix.services.ws.transfertypes.Header> transferTypeHeaderAuth = new Holder<org.bellatrix.services.ws.transfertypes.Header>();
		transferTypeHeaderAuth.value = headerTransferType;

		LoadTransferTypesByUsernameRequest transferTypesRequest = new LoadTransferTypesByUsernameRequest();
		transferTypesRequest.setUsername(username);
		LoadTransferTypesByUsernameResponse loadTransferTypesResponse = client
				.loadTransferTypesByUsername(transferTypeHeaderAuth, transferTypesRequest);

		List<String> trxList = new LinkedList<String>();
		if (loadTransferTypesResponse.getTransferTypes().size() > 0) {
			int i;
			for (i = 0; i < loadTransferTypesResponse.getTransferTypes().size(); i++) {
				trxList.add(loadTransferTypesResponse.getTransferTypes().get(i).getId() + " - "
						+ loadTransferTypesResponse.getTransferTypes().get(i).getName());
			}
		}
		
		return trxList;
	}

	public String loadTransferTypePermission(Integer id) throws MalformedURLException, TransactionException_Exception {
		URL url = new URL(contextLoader.getHostWSUrl() + "transfertypes?wsdl");
		QName qName = new QName(contextLoader.getHostWSPort(), "TransferTypeService");
		TransferTypeService service = new TransferTypeService(url, qName);
		TransferType client = service.getTransferTypePort();

		org.bellatrix.services.ws.transfertypes.Header headerTransferType = new org.bellatrix.services.ws.transfertypes.Header();
		headerTransferType.setToken(contextLoader.getHeaderToken());
		Holder<org.bellatrix.services.ws.transfertypes.Header> transferTypeHeaderAuth = new Holder<org.bellatrix.services.ws.transfertypes.Header>();
		transferTypeHeaderAuth.value = headerTransferType;

		TransferTypePermissionRequest transferTypePermissionRequest = new TransferTypePermissionRequest();
		transferTypePermissionRequest.setTransferTypeID(id);
		TransferTypePermissionResponse response = client.loadTransferTypePermissions(transferTypeHeaderAuth,
				transferTypePermissionRequest);

		Map<String, Object> trxMap = new HashMap<String, Object>();
		trxMap.put("data", response.getPermission());
		trxMap.put("recordsTotal", response.getPermission().size());
		trxMap.put("recordsFiltered", response.getPermission().size());
		return Utils.toJSON(trxMap);
	}

	public String loadFee(Integer id) throws MalformedURLException, TransactionException_Exception {
		URL url = new URL(contextLoader.getHostWSUrl() + "transfertypes?wsdl");
		QName qName = new QName(contextLoader.getHostWSPort(), "TransferTypeService");
		TransferTypeService service = new TransferTypeService(url, qName);
		TransferType client = service.getTransferTypePort();

		org.bellatrix.services.ws.transfertypes.Header headerTransferType = new org.bellatrix.services.ws.transfertypes.Header();
		headerTransferType.setToken(contextLoader.getHeaderToken());
		Holder<org.bellatrix.services.ws.transfertypes.Header> transferTypeHeaderAuth = new Holder<org.bellatrix.services.ws.transfertypes.Header>();
		transferTypeHeaderAuth.value = headerTransferType;

		LoadFeesByTransferTypeRequest loadFeesByTransferTypeRequest = new LoadFeesByTransferTypeRequest();
		loadFeesByTransferTypeRequest.setId(id);
		loadFeesByTransferTypeRequest.setShowAllStatus(true);
		LoadFeesByTransferTypeResponse loadFeesByTransferTypeResponse = client
				.loadFeesByTransferType(transferTypeHeaderAuth, loadFeesByTransferTypeRequest);

		Map<String, Object> trxMap = new HashMap<String, Object>();
		trxMap.put("data", loadFeesByTransferTypeResponse.getFees());
		trxMap.put("recordsTotal", loadFeesByTransferTypeResponse.getFees().size());
		trxMap.put("recordsFiltered", loadFeesByTransferTypeResponse.getFees().size());
		return Utils.toJSON(trxMap);
	}

	public LoadFeesByIDResponse loadFeeByID(Integer id) throws MalformedURLException, TransactionException_Exception {
		URL url = new URL(contextLoader.getHostWSUrl() + "transfertypes?wsdl");
		QName qName = new QName(contextLoader.getHostWSPort(), "TransferTypeService");
		TransferTypeService service = new TransferTypeService(url, qName);
		TransferType client = service.getTransferTypePort();

		org.bellatrix.services.ws.transfertypes.Header headerTransferType = new org.bellatrix.services.ws.transfertypes.Header();
		headerTransferType.setToken(contextLoader.getHeaderToken());
		Holder<org.bellatrix.services.ws.transfertypes.Header> transferTypeHeaderAuth = new Holder<org.bellatrix.services.ws.transfertypes.Header>();
		transferTypeHeaderAuth.value = headerTransferType;

		LoadFeesByIDRequest loadFeesByIDRequest = new LoadFeesByIDRequest();
		loadFeesByIDRequest.setId(id);
		LoadFeesByIDResponse loadFeesByIDResponse = client.loadFeesByID(transferTypeHeaderAuth, loadFeesByIDRequest);
		return loadFeesByIDResponse;
	}

	public TransferTypeNotificationResponse loadTransferNotificationByID(Integer id)
			throws MalformedURLException, TransactionException_Exception {
		URL url = new URL(contextLoader.getHostWSUrl() + "transfertypes?wsdl");
		QName qName = new QName(contextLoader.getHostWSPort(), "TransferTypeService");
		TransferTypeService service = new TransferTypeService(url, qName);
		TransferType client = service.getTransferTypePort();

		org.bellatrix.services.ws.transfertypes.Header headerTransferType = new org.bellatrix.services.ws.transfertypes.Header();
		headerTransferType.setToken(contextLoader.getHeaderToken());
		Holder<org.bellatrix.services.ws.transfertypes.Header> transferTypeHeaderAuth = new Holder<org.bellatrix.services.ws.transfertypes.Header>();
		transferTypeHeaderAuth.value = headerTransferType;

		TransferTypeNotificationRequest transferTypeNotificationRequest = new TransferTypeNotificationRequest();
		transferTypeNotificationRequest.setId(id);
		TransferTypeNotificationResponse response = client.loadTransferTypeNotification(transferTypeHeaderAuth,
				transferTypeNotificationRequest);

		return response;
	}

	public String loadTransferNotification(Integer id) throws MalformedURLException, TransactionException_Exception {
		URL url = new URL(contextLoader.getHostWSUrl() + "transfertypes?wsdl");
		QName qName = new QName(contextLoader.getHostWSPort(), "TransferTypeService");
		TransferTypeService service = new TransferTypeService(url, qName);
		TransferType client = service.getTransferTypePort();

		org.bellatrix.services.ws.transfertypes.Header headerTransferType = new org.bellatrix.services.ws.transfertypes.Header();
		headerTransferType.setToken(contextLoader.getHeaderToken());
		Holder<org.bellatrix.services.ws.transfertypes.Header> transferTypeHeaderAuth = new Holder<org.bellatrix.services.ws.transfertypes.Header>();
		transferTypeHeaderAuth.value = headerTransferType;

		TransferTypeNotificationRequest transferTypeNotificationRequest = new TransferTypeNotificationRequest();
		transferTypeNotificationRequest.setTransferTypeID(id);
		TransferTypeNotificationResponse response = client.loadTransferTypeNotification(transferTypeHeaderAuth,
				transferTypeNotificationRequest);
		Map<String, Object> trxMap = new HashMap<String, Object>();
		trxMap.put("data", response.getNotification());
		trxMap.put("recordsTotal", response.getNotification().size());
		trxMap.put("recordsFiltered", response.getNotification().size());
		return Utils.toJSON(trxMap);
	}

	public void createTransferType(com.jpa.optima.admin.model.TransferType type)
			throws MalformedURLException, TransactionException_Exception {
		URL url = new URL(contextLoader.getHostWSUrl() + "transfertypes?wsdl");
		QName qName = new QName(contextLoader.getHostWSPort(), "TransferTypeService");
		TransferTypeService service = new TransferTypeService(url, qName);
		TransferType client = service.getTransferTypePort();

		org.bellatrix.services.ws.transfertypes.Header headerTransferType = new org.bellatrix.services.ws.transfertypes.Header();
		headerTransferType.setToken(contextLoader.getHeaderToken());
		Holder<org.bellatrix.services.ws.transfertypes.Header> transferTypeHeaderAuth = new Holder<org.bellatrix.services.ws.transfertypes.Header>();
		transferTypeHeaderAuth.value = headerTransferType;

		TransferTypeRequest transferTypeRequest = new TransferTypeRequest();
		transferTypeRequest.setDescription(type.getDescription());

		Integer fromAccountID = Integer.valueOf(type.getFromAccountName().split("-")[0].trim());
		transferTypeRequest.setFromAccountID(fromAccountID);

		Integer toAccountID = Integer.valueOf(type.getToAccountName().split("-")[0].trim());
		transferTypeRequest.setToAccountID(toAccountID);
		transferTypeRequest.setName(type.getName());
		transferTypeRequest.setMaxAmount(type.getMaxAmount());
		transferTypeRequest.setMaxCount(type.getMaxCount());
		transferTypeRequest.setMinAmount(type.getMinAmount());

		client.createTransferTypes(transferTypeHeaderAuth, transferTypeRequest);
	}

	public void editTransferType(com.jpa.optima.admin.model.TransferType type)
			throws MalformedURLException, TransactionException_Exception {
		URL url = new URL(contextLoader.getHostWSUrl() + "transfertypes?wsdl");
		QName qName = new QName(contextLoader.getHostWSPort(), "TransferTypeService");
		TransferTypeService service = new TransferTypeService(url, qName);
		TransferType client = service.getTransferTypePort();

		org.bellatrix.services.ws.transfertypes.Header headerTransferType = new org.bellatrix.services.ws.transfertypes.Header();
		headerTransferType.setToken(contextLoader.getHeaderToken());
		Holder<org.bellatrix.services.ws.transfertypes.Header> transferTypeHeaderAuth = new Holder<org.bellatrix.services.ws.transfertypes.Header>();
		transferTypeHeaderAuth.value = headerTransferType;

		TransferTypeRequest transferTypeRequest = new TransferTypeRequest();
		transferTypeRequest.setDescription(type.getDescription());

		Integer fromAccountID = Integer.valueOf(type.getFromAccountName().split("-")[0].trim());
		transferTypeRequest.setFromAccountID(fromAccountID);

		Integer toAccountID = Integer.valueOf(type.getToAccountName().split("-")[0].trim());
		transferTypeRequest.setId(type.getId());
		transferTypeRequest.setToAccountID(toAccountID);
		transferTypeRequest.setName(type.getName());
		transferTypeRequest.setMaxAmount(type.getMaxAmount());
		transferTypeRequest.setMaxCount(type.getMaxCount());
		transferTypeRequest.setMinAmount(type.getMinAmount());

		client.updateTransferTypes(transferTypeHeaderAuth, transferTypeRequest);
	}

	public void createTransferTypePermission(Integer groupID, Integer trasferTypeID)
			throws MalformedURLException, TransactionException_Exception {
		URL url = new URL(contextLoader.getHostWSUrl() + "transfertypes?wsdl");
		QName qName = new QName(contextLoader.getHostWSPort(), "TransferTypeService");
		TransferTypeService service = new TransferTypeService(url, qName);
		TransferType client = service.getTransferTypePort();

		org.bellatrix.services.ws.transfertypes.Header headerTransferType = new org.bellatrix.services.ws.transfertypes.Header();
		headerTransferType.setToken(contextLoader.getHeaderToken());
		Holder<org.bellatrix.services.ws.transfertypes.Header> transferTypeHeaderAuth = new Holder<org.bellatrix.services.ws.transfertypes.Header>();
		transferTypeHeaderAuth.value = headerTransferType;

		TransferTypePermissionRequest request = new TransferTypePermissionRequest();
		request.setGroupID(groupID);
		request.setTransferTypeID(trasferTypeID);
		client.createTransferTypePermissions(transferTypeHeaderAuth, request);
	}

	public void deleteTransferTypePermission(Integer id) throws MalformedURLException, TransactionException_Exception {
		URL url = new URL(contextLoader.getHostWSUrl() + "transfertypes?wsdl");
		QName qName = new QName(contextLoader.getHostWSPort(), "TransferTypeService");
		TransferTypeService service = new TransferTypeService(url, qName);
		TransferType client = service.getTransferTypePort();

		org.bellatrix.services.ws.transfertypes.Header headerTransferType = new org.bellatrix.services.ws.transfertypes.Header();
		headerTransferType.setToken(contextLoader.getHeaderToken());
		Holder<org.bellatrix.services.ws.transfertypes.Header> transferTypeHeaderAuth = new Holder<org.bellatrix.services.ws.transfertypes.Header>();
		transferTypeHeaderAuth.value = headerTransferType;

		TransferTypePermissionRequest request = new TransferTypePermissionRequest();
		request.setId(id);
		client.deleteTransferTypePermissions(transferTypeHeaderAuth, request);
	}

	public void createFee(FeeRequest fees) throws MalformedURLException, TransactionException_Exception {
		URL url = new URL(contextLoader.getHostWSUrl() + "transfertypes?wsdl");
		QName qName = new QName(contextLoader.getHostWSPort(), "TransferTypeService");
		TransferTypeService service = new TransferTypeService(url, qName);
		TransferType client = service.getTransferTypePort();

		org.bellatrix.services.ws.transfertypes.Header headerTransferType = new org.bellatrix.services.ws.transfertypes.Header();
		headerTransferType.setToken(contextLoader.getHeaderToken());
		Holder<org.bellatrix.services.ws.transfertypes.Header> transferTypeHeaderAuth = new Holder<org.bellatrix.services.ws.transfertypes.Header>();
		transferTypeHeaderAuth.value = headerTransferType;

		client.createFees(transferTypeHeaderAuth, fees);

	}

	public void editFee(FeeRequest fees) throws MalformedURLException, TransactionException_Exception {
		URL url = new URL(contextLoader.getHostWSUrl() + "transfertypes?wsdl");
		QName qName = new QName(contextLoader.getHostWSPort(), "TransferTypeService");
		TransferTypeService service = new TransferTypeService(url, qName);
		TransferType client = service.getTransferTypePort();

		org.bellatrix.services.ws.transfertypes.Header headerTransferType = new org.bellatrix.services.ws.transfertypes.Header();
		headerTransferType.setToken(contextLoader.getHeaderToken());
		Holder<org.bellatrix.services.ws.transfertypes.Header> transferTypeHeaderAuth = new Holder<org.bellatrix.services.ws.transfertypes.Header>();
		transferTypeHeaderAuth.value = headerTransferType;

		client.updateFees(transferTypeHeaderAuth, fees);
	}

	public void createBroker(Broker req) throws TransactionException_Exception, MalformedURLException {
		URL url = new URL(contextLoader.getHostWSUrl() + "transfertypes?wsdl");
		QName qName = new QName(contextLoader.getHostWSPort(), "TransferTypeService");
		TransferTypeService service = new TransferTypeService(url, qName);
		TransferType client = service.getTransferTypePort();

		org.bellatrix.services.ws.transfertypes.Header headerTransferType = new org.bellatrix.services.ws.transfertypes.Header();
		headerTransferType.setToken(contextLoader.getHeaderToken());
		Holder<org.bellatrix.services.ws.transfertypes.Header> transferTypeHeaderAuth = new Holder<org.bellatrix.services.ws.transfertypes.Header>();
		transferTypeHeaderAuth.value = headerTransferType;

		BrokerRequest brokerRequest = new BrokerRequest();
		brokerRequest.setName(req.getName());
		brokerRequest.setDescription(req.getDescription());
		brokerRequest.setEnabled(req.isEnabled());
		brokerRequest.setFeeID(req.getFeeID());
		brokerRequest.setFromAccountID(-1);
		brokerRequest.setFromMember("-1");
		brokerRequest.setToMember(req.getToMember());
		brokerRequest.setDeductAllFee(req.isDeductAllFee());
		brokerRequest.setFixedAmount(req.getFixedAmount());
		brokerRequest.setPercentage(new BigDecimal(req.getPercentage()));
		brokerRequest.setToAccountID(Integer.valueOf(req.getToAccountName().split("-")[0].trim()));

		client.createBrokers(transferTypeHeaderAuth, brokerRequest);

	}

	public void editBroker(Broker req) throws TransactionException_Exception, MalformedURLException {
		URL url = new URL(contextLoader.getHostWSUrl() + "transfertypes?wsdl");
		QName qName = new QName(contextLoader.getHostWSPort(), "TransferTypeService");
		TransferTypeService service = new TransferTypeService(url, qName);
		TransferType client = service.getTransferTypePort();

		org.bellatrix.services.ws.transfertypes.Header headerTransferType = new org.bellatrix.services.ws.transfertypes.Header();
		headerTransferType.setToken(contextLoader.getHeaderToken());
		Holder<org.bellatrix.services.ws.transfertypes.Header> transferTypeHeaderAuth = new Holder<org.bellatrix.services.ws.transfertypes.Header>();
		transferTypeHeaderAuth.value = headerTransferType;

		BrokerRequest brokerRequest = new BrokerRequest();
		brokerRequest.setName(req.getName());
		brokerRequest.setDescription(req.getDescription());
		brokerRequest.setEnabled(req.isEnabled());
		brokerRequest.setId(req.getId());
		brokerRequest.setFeeID(req.getFeeID());
		brokerRequest.setFromAccountID(-1);
		brokerRequest.setFromMember("-1");
		brokerRequest.setToMember(req.getToMember());
		brokerRequest.setDeductAllFee(req.isDeductAllFee());
		brokerRequest.setFixedAmount(req.getFixedAmount());
		brokerRequest.setPercentage(new BigDecimal(req.getPercentage()));
		brokerRequest.setToAccountID(Integer.valueOf(req.getToAccountName().split("-")[0].trim()));

		client.editBrokers(transferTypeHeaderAuth, brokerRequest);

	}

	public LoadBrokerResponse loadBrokerByID(Integer id) throws MalformedURLException {
		URL url = new URL(contextLoader.getHostWSUrl() + "transfertypes?wsdl");
		QName qName = new QName(contextLoader.getHostWSPort(), "TransferTypeService");
		TransferTypeService service = new TransferTypeService(url, qName);
		TransferType client = service.getTransferTypePort();

		org.bellatrix.services.ws.transfertypes.Header headerTransferType = new org.bellatrix.services.ws.transfertypes.Header();
		headerTransferType.setToken(contextLoader.getHeaderToken());
		Holder<org.bellatrix.services.ws.transfertypes.Header> transferTypeHeaderAuth = new Holder<org.bellatrix.services.ws.transfertypes.Header>();
		transferTypeHeaderAuth.value = headerTransferType;

		LoadBrokerRequest lbr = new LoadBrokerRequest();
		lbr.setId(id);
		LoadBrokerResponse response = client.loadBrokers(transferTypeHeaderAuth, lbr);
		return response;
	}

	public String loadBroker(Integer feeID) throws MalformedURLException {
		URL url = new URL(contextLoader.getHostWSUrl() + "transfertypes?wsdl");
		QName qName = new QName(contextLoader.getHostWSPort(), "TransferTypeService");
		TransferTypeService service = new TransferTypeService(url, qName);
		TransferType client = service.getTransferTypePort();

		org.bellatrix.services.ws.transfertypes.Header headerTransferType = new org.bellatrix.services.ws.transfertypes.Header();
		headerTransferType.setToken(contextLoader.getHeaderToken());
		Holder<org.bellatrix.services.ws.transfertypes.Header> transferTypeHeaderAuth = new Holder<org.bellatrix.services.ws.transfertypes.Header>();
		transferTypeHeaderAuth.value = headerTransferType;

		LoadBrokerRequest lbr = new LoadBrokerRequest();
		lbr.setFeeID(feeID);
		LoadBrokerResponse response = client.loadBrokers(transferTypeHeaderAuth, lbr);
		Map<String, Object> trxMap = new HashMap<String, Object>();
		trxMap.put("data", response.getBrokers());
		trxMap.put("recordsTotal", response.getBrokers().size());
		trxMap.put("recordsFiltered", response.getBrokers().size());
		return Utils.toJSON(trxMap);
	}

	public String loadFeeByMember(Integer feeID) throws MalformedURLException, TransactionException_Exception {
		URL url = new URL(contextLoader.getHostWSUrl() + "transfertypes?wsdl");
		QName qName = new QName(contextLoader.getHostWSPort(), "TransferTypeService");
		TransferTypeService service = new TransferTypeService(url, qName);
		TransferType client = service.getTransferTypePort();

		org.bellatrix.services.ws.transfertypes.Header headerTransferType = new org.bellatrix.services.ws.transfertypes.Header();
		headerTransferType.setToken(contextLoader.getHeaderToken());
		Holder<org.bellatrix.services.ws.transfertypes.Header> transferTypeHeaderAuth = new Holder<org.bellatrix.services.ws.transfertypes.Header>();
		transferTypeHeaderAuth.value = headerTransferType;

		FeeByMemberRequest feeByMemberRequest = new FeeByMemberRequest();
		feeByMemberRequest.setFeeID(feeID);
		FeeByMemberResponse response = client.loadFeeByMember(transferTypeHeaderAuth, feeByMemberRequest);
		Map<String, Object> trxMap = new HashMap<String, Object>();
		trxMap.put("data", response.getFeeByMember());
		trxMap.put("recordsTotal", response.getFeeByMember().size());
		trxMap.put("recordsFiltered", response.getFeeByMember().size());
		return Utils.toJSON(trxMap);
	}

	public void deleteFeeByMember(Integer id) throws MalformedURLException, TransactionException_Exception {
		URL url = new URL(contextLoader.getHostWSUrl() + "transfertypes?wsdl");
		QName qName = new QName(contextLoader.getHostWSPort(), "TransferTypeService");
		TransferTypeService service = new TransferTypeService(url, qName);
		TransferType client = service.getTransferTypePort();

		org.bellatrix.services.ws.transfertypes.Header headerTransferType = new org.bellatrix.services.ws.transfertypes.Header();
		headerTransferType.setToken(contextLoader.getHeaderToken());
		Holder<org.bellatrix.services.ws.transfertypes.Header> transferTypeHeaderAuth = new Holder<org.bellatrix.services.ws.transfertypes.Header>();
		transferTypeHeaderAuth.value = headerTransferType;

		FeeByMemberRequest feeByMemberRequest = new FeeByMemberRequest();
		feeByMemberRequest.setId(id);
		client.deleteFeeByMember(transferTypeHeaderAuth, feeByMemberRequest);
	}

	public void createTransferNotification(TransferNotification req)
			throws TransactionException_Exception, MalformedURLException {
		URL url = new URL(contextLoader.getHostWSUrl() + "transfertypes?wsdl");
		QName qName = new QName(contextLoader.getHostWSPort(), "TransferTypeService");
		TransferTypeService service = new TransferTypeService(url, qName);
		TransferType client = service.getTransferTypePort();

		org.bellatrix.services.ws.transfertypes.Header headerTransferType = new org.bellatrix.services.ws.transfertypes.Header();
		headerTransferType.setToken(contextLoader.getHeaderToken());
		Holder<org.bellatrix.services.ws.transfertypes.Header> transferTypeHeaderAuth = new Holder<org.bellatrix.services.ws.transfertypes.Header>();
		transferTypeHeaderAuth.value = headerTransferType;

		TransferTypeNotificationRequest notifReq = new TransferTypeNotificationRequest();
		Integer notifID = Integer.valueOf(req.getNotification().split("-")[0].trim());

		notifReq.setEnabled(req.isEnabled());
		notifReq.setNotificationID(notifID);
		notifReq.setNotificationType(req.getNotificationType());
		notifReq.setTransferTypeID(req.getTransferTypeID());

		client.createTransferTypeNotification(transferTypeHeaderAuth, notifReq);

	}

	public void editTransferNotification(TransferNotification req)
			throws TransactionException_Exception, MalformedURLException {
		URL url = new URL(contextLoader.getHostWSUrl() + "transfertypes?wsdl");
		QName qName = new QName(contextLoader.getHostWSPort(), "TransferTypeService");
		TransferTypeService service = new TransferTypeService(url, qName);
		TransferType client = service.getTransferTypePort();

		org.bellatrix.services.ws.transfertypes.Header headerTransferType = new org.bellatrix.services.ws.transfertypes.Header();
		headerTransferType.setToken(contextLoader.getHeaderToken());
		Holder<org.bellatrix.services.ws.transfertypes.Header> transferTypeHeaderAuth = new Holder<org.bellatrix.services.ws.transfertypes.Header>();
		transferTypeHeaderAuth.value = headerTransferType;

		TransferTypeNotificationRequest notifReq = new TransferTypeNotificationRequest();
		Integer notifID = Integer.valueOf(req.getNotification().split("-")[0].trim());

		notifReq.setId(req.getId());
		notifReq.setTransferTypeID(req.getTransferTypeID());
		notifReq.setEnabled(req.isEnabled());
		notifReq.setNotificationID(notifID);
		notifReq.setNotificationType(req.getNotificationType());
		notifReq.setEnabled(req.isEnabled());
		client.updateTransferTypeNotification(transferTypeHeaderAuth, notifReq);

	}

	public void deleteTransferNotification(Integer id) throws TransactionException_Exception, MalformedURLException {
		URL url = new URL(contextLoader.getHostWSUrl() + "transfertypes?wsdl");
		QName qName = new QName(contextLoader.getHostWSPort(), "TransferTypeService");
		TransferTypeService service = new TransferTypeService(url, qName);
		TransferType client = service.getTransferTypePort();

		org.bellatrix.services.ws.transfertypes.Header headerTransferType = new org.bellatrix.services.ws.transfertypes.Header();
		headerTransferType.setToken(contextLoader.getHeaderToken());
		Holder<org.bellatrix.services.ws.transfertypes.Header> transferTypeHeaderAuth = new Holder<org.bellatrix.services.ws.transfertypes.Header>();
		transferTypeHeaderAuth.value = headerTransferType;

		TransferTypeNotificationRequest notifReq = new TransferTypeNotificationRequest();
		notifReq.setId(id);
		client.deleteTransferTypeNotification(transferTypeHeaderAuth, notifReq);
	}

	public void createFeeByMember(MemberFiltering req) throws TransactionException_Exception, MalformedURLException {
		URL url = new URL(contextLoader.getHostWSUrl() + "transfertypes?wsdl");
		QName qName = new QName(contextLoader.getHostWSPort(), "TransferTypeService");
		TransferTypeService service = new TransferTypeService(url, qName);
		TransferType client = service.getTransferTypePort();

		org.bellatrix.services.ws.transfertypes.Header headerTransferType = new org.bellatrix.services.ws.transfertypes.Header();
		headerTransferType.setToken(contextLoader.getHeaderToken());
		Holder<org.bellatrix.services.ws.transfertypes.Header> transferTypeHeaderAuth = new Holder<org.bellatrix.services.ws.transfertypes.Header>();
		transferTypeHeaderAuth.value = headerTransferType;

		FeeByMemberRequest feeByMemberRequest = new FeeByMemberRequest();
		feeByMemberRequest.setDestination(req.isDestination());
		feeByMemberRequest.setFeeID(req.getFeeID());
		feeByMemberRequest.setUsername(req.getUsername());

		client.createFeeByMember(transferTypeHeaderAuth, feeByMemberRequest);
	}
}
