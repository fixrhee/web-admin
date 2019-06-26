package com.jpa.optima.admin.controller;

import org.bellatrix.services.ws.access.Access;
import org.bellatrix.services.ws.access.AccessService;
import org.bellatrix.services.ws.access.AccessTypeRequest;
import org.bellatrix.services.ws.access.ChangeCredentialRequest;
import org.bellatrix.services.ws.access.CreateCredentialRequest;
import org.bellatrix.services.ws.access.CredentialStatusRequest;
import org.bellatrix.services.ws.access.CredentialStatusResponse;
import org.bellatrix.services.ws.access.Exception_Exception;
import org.bellatrix.services.ws.access.LoadAccessTypeResponse;
import org.bellatrix.services.ws.access.ResetCredentialRequest;
import org.bellatrix.services.ws.access.TransactionException_Exception;
import org.bellatrix.services.ws.access.UnblockCredentialRequest;
import org.bellatrix.services.ws.access.ValidateCredentialRequest;
import org.bellatrix.services.ws.access.ValidateCredentialResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jpa.optima.admin.model.AccessType;
import com.jpa.optima.admin.model.ChangeCredential;
import com.jpa.optima.admin.model.Member;
import com.jpa.optima.admin.model.UpgradeMember;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.namespace.QName;
import javax.xml.ws.Holder;

@Component
public class AccessProcessor {
	@Autowired
	private ContextLoader contextLoader;

	public void changeCredential(ChangeCredential changeCredential)
			throws MalformedURLException, TransactionException_Exception {
		URL url = new URL(contextLoader.getHostWSUrl() + "access?wsdl");
		QName qName = new QName(contextLoader.getHostWSPort(), "AccessService");
		AccessService service = new AccessService(url, qName);
		Access client = service.getAccessPort();

		org.bellatrix.services.ws.access.Header headerAccess = new org.bellatrix.services.ws.access.Header();
		headerAccess.setToken(contextLoader.getHeaderToken());
		Holder<org.bellatrix.services.ws.access.Header> accessHeaderAuth = new Holder<org.bellatrix.services.ws.access.Header>();
		accessHeaderAuth.value = headerAccess;

		ChangeCredentialRequest chgCredential = new ChangeCredentialRequest();
		chgCredential.setAccessTypeID(contextLoader.getMemberTrxCredentialTypeID());
		chgCredential.setUsername(changeCredential.getMsisdn());
		chgCredential.setOldCredential(changeCredential.getOldCredential());
		chgCredential.setNewCredential(changeCredential.getNewCredential());
		client.changeCredential(accessHeaderAuth, chgCredential);
	}

	public void unblockCredential(UpgradeMember upgrade) throws Exception_Exception, MalformedURLException {
		URL url = new URL(contextLoader.getHostWSUrl() + "access?wsdl");
		QName qName = new QName(contextLoader.getHostWSPort(), "AccessService");
		AccessService service = new AccessService(url, qName);
		Access client = service.getAccessPort();

		org.bellatrix.services.ws.access.Header headerAccess = new org.bellatrix.services.ws.access.Header();
		headerAccess.setToken(contextLoader.getHeaderToken());
		Holder<org.bellatrix.services.ws.access.Header> accessHeaderAuth = new Holder<org.bellatrix.services.ws.access.Header>();
		accessHeaderAuth.value = headerAccess;

		UnblockCredentialRequest unblockCredentialReq = new UnblockCredentialRequest();
		unblockCredentialReq.setAccessTypeID(contextLoader.getMemberTrxCredentialTypeID());
		unblockCredentialReq.setUsername(upgrade.getMsisdn());

		try {
			client.unblockCredential(accessHeaderAuth, unblockCredentialReq);
		} catch (TransactionException_Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void resetCredential(UpgradeMember upgrade) throws Exception_Exception, MalformedURLException,
			DatatypeConfigurationException, TransactionException_Exception {
		URL url = new URL(contextLoader.getHostWSUrl() + "access?wsdl");
		QName qName = new QName(contextLoader.getHostWSPort(), "AccessService");
		AccessService service = new AccessService(url, qName);
		Access client = service.getAccessPort();

		org.bellatrix.services.ws.access.Header headerAccess = new org.bellatrix.services.ws.access.Header();
		headerAccess.setToken(contextLoader.getHeaderToken());
		Holder<org.bellatrix.services.ws.access.Header> accessHeaderAuth = new Holder<org.bellatrix.services.ws.access.Header>();
		accessHeaderAuth.value = headerAccess;

		CredentialStatusRequest csr = new CredentialStatusRequest();
		csr.setAccessTypeID(contextLoader.getMemberTrxCredentialTypeID());
		csr.setUsername(upgrade.getUsername());
		CredentialStatusResponse csrp = client.credentialStatus(accessHeaderAuth, csr);

		if (csrp.getStatus().getMessage() == "CREDENTIAL_INVALID") {
			CreateCredentialRequest ccr = new CreateCredentialRequest();
			ccr.setAccessTypeID(contextLoader.getMemberTrxCredentialTypeID());
			ccr.setUsername(upgrade.getUsername());
			ccr.setCredential(Utils.GenerateRandomNumber(6));
			client.createCredential(accessHeaderAuth, ccr);

			CreateCredentialRequest crcr = new CreateCredentialRequest();
			crcr.setAccessTypeID(contextLoader.getMemberWebCredentialTypeID());
			crcr.setUsername(upgrade.getUsername());
			crcr.setCredential(Utils.GenerateRandomNumber(6));
			client.createCredential(accessHeaderAuth, crcr);
		}

		ResetCredentialRequest rcr = new ResetCredentialRequest();
		rcr.setAccessTypeID(contextLoader.getMemberTrxCredentialTypeID());
		rcr.setUsername(upgrade.getUsername());
		rcr.setEmail(upgrade.getEmail());
		client.resetCredential(accessHeaderAuth, rcr);

	}

	public CredentialStatusResponse credentialStatus(String username) throws MalformedURLException {
		URL url = new URL(contextLoader.getHostWSUrl() + "access?wsdl");
		QName qName = new QName(contextLoader.getHostWSPort(), "AccessService");
		AccessService service = new AccessService(url, qName);
		Access client = service.getAccessPort();

		org.bellatrix.services.ws.access.Header headerAccess = new org.bellatrix.services.ws.access.Header();
		headerAccess.setToken(contextLoader.getHeaderToken());
		Holder<org.bellatrix.services.ws.access.Header> accessHeaderAuth = new Holder<org.bellatrix.services.ws.access.Header>();
		accessHeaderAuth.value = headerAccess;

		CredentialStatusRequest csr = new CredentialStatusRequest();
		csr.setUsername(username);
		csr.setAccessTypeID(contextLoader.getMemberTrxCredentialTypeID());
		CredentialStatusResponse csres = client.credentialStatus(accessHeaderAuth, csr);

		return csres;
	}

	public void createCredential(Member createCredential, Integer accessTypeID) throws MalformedURLException {
		try {
			URL url = new URL(contextLoader.getHostWSUrl() + "access?wsdl");
			QName qName = new QName(contextLoader.getHostWSPort(), "AccessService");
			AccessService service = new AccessService(url, qName);
			Access client = service.getAccessPort();

			org.bellatrix.services.ws.access.Header headerAccess = new org.bellatrix.services.ws.access.Header();
			headerAccess.setToken(contextLoader.getHeaderToken());
			Holder<org.bellatrix.services.ws.access.Header> accessHeaderAuth = new Holder<org.bellatrix.services.ws.access.Header>();
			accessHeaderAuth.value = headerAccess;

			CreateCredentialRequest ccRes = new CreateCredentialRequest();
			ccRes.setAccessTypeID(accessTypeID);
			ccRes.setUsername(createCredential.getUsername().replace(",", ""));
			ccRes.setCredential(createCredential.getCredential());

			client.createCredential(accessHeaderAuth, ccRes);
		} catch (Exception_Exception e) {
			e.printStackTrace();
		}
	}

	public void createCredential(String username, String credential, Integer accessTypeID)
			throws MalformedURLException {
		try {
			URL url = new URL(contextLoader.getHostWSUrl() + "access?wsdl");
			QName qName = new QName(contextLoader.getHostWSPort(), "AccessService");
			AccessService service = new AccessService(url, qName);
			Access client = service.getAccessPort();

			org.bellatrix.services.ws.access.Header headerAccess = new org.bellatrix.services.ws.access.Header();
			headerAccess.setToken(contextLoader.getHeaderToken());
			Holder<org.bellatrix.services.ws.access.Header> accessHeaderAuth = new Holder<org.bellatrix.services.ws.access.Header>();
			accessHeaderAuth.value = headerAccess;

			CreateCredentialRequest ccRes = new CreateCredentialRequest();
			ccRes.setAccessTypeID(accessTypeID);
			ccRes.setUsername(username);
			ccRes.setCredential(credential);

			client.createCredential(accessHeaderAuth, ccRes);
		} catch (Exception_Exception e) {
			e.printStackTrace();
		}
	}

	public ValidateCredentialResponse validateCredential(String username, String credential)
			throws MalformedURLException {
		URL url = new URL(contextLoader.getHostWSUrl() + "access?wsdl");
		QName qName = new QName(contextLoader.getHostWSPort(), "AccessService");
		AccessService service = new AccessService(url, qName);
		Access client = service.getAccessPort();

		org.bellatrix.services.ws.access.Header headerAccess = new org.bellatrix.services.ws.access.Header();
		headerAccess.setToken(contextLoader.getHeaderToken());
		Holder<org.bellatrix.services.ws.access.Header> accessHeaderAuth = new Holder<org.bellatrix.services.ws.access.Header>();
		accessHeaderAuth.value = headerAccess;

		ValidateCredentialRequest validateCredentialReq = new ValidateCredentialRequest();
		validateCredentialReq.setAccessTypeID(contextLoader.getMemberTrxCredentialTypeID());
		validateCredentialReq.setCredential(credential);
		validateCredentialReq.setUsername(username);

		ValidateCredentialResponse validateCredentialRes = client.validateCredential(accessHeaderAuth,
				validateCredentialReq);

		return validateCredentialRes;
	}

	public String loadAccessType() throws MalformedURLException, Exception_Exception {
		URL url = new URL(contextLoader.getHostWSUrl() + "access?wsdl");
		QName qName = new QName(contextLoader.getHostWSPort(), "AccessService");
		AccessService service = new AccessService(url, qName);
		Access client = service.getAccessPort();

		org.bellatrix.services.ws.access.Header headerAccess = new org.bellatrix.services.ws.access.Header();
		headerAccess.setToken(contextLoader.getHeaderToken());
		Holder<org.bellatrix.services.ws.access.Header> accessHeaderAuth = new Holder<org.bellatrix.services.ws.access.Header>();
		accessHeaderAuth.value = headerAccess;

		AccessTypeRequest req = new AccessTypeRequest();
		LoadAccessTypeResponse loadAccessTypeResponse = client.loadAccessType(accessHeaderAuth, req);

		Map<String, Object> trxMap = new HashMap<String, Object>();
		trxMap.put("data", loadAccessTypeResponse.getAccessType());
		trxMap.put("recordsTotal", loadAccessTypeResponse.getAccessType().size());
		trxMap.put("recordsFiltered", loadAccessTypeResponse.getAccessType().size());
		return Utils.toJSON(trxMap);

	}

	public List<String> listAccessType() throws MalformedURLException, Exception_Exception {
		URL url = new URL(contextLoader.getHostWSUrl() + "access?wsdl");
		QName qName = new QName(contextLoader.getHostWSPort(), "AccessService");
		AccessService service = new AccessService(url, qName);
		Access client = service.getAccessPort();

		org.bellatrix.services.ws.access.Header headerAccess = new org.bellatrix.services.ws.access.Header();
		headerAccess.setToken(contextLoader.getHeaderToken());
		Holder<org.bellatrix.services.ws.access.Header> accessHeaderAuth = new Holder<org.bellatrix.services.ws.access.Header>();
		accessHeaderAuth.value = headerAccess;

		AccessTypeRequest req = new AccessTypeRequest();
		LoadAccessTypeResponse loadAccessTypeResponse = client.loadAccessType(accessHeaderAuth, req);

		List<String> accessList = new LinkedList<String>();
		if (loadAccessTypeResponse.getAccessType().size() > 0) {
			int i;
			for (i = 0; i < loadAccessTypeResponse.getAccessType().size(); i++) {
				accessList.add(loadAccessTypeResponse.getAccessType().get(i).getId() + " - "
						+ loadAccessTypeResponse.getAccessType().get(i).getName());
			}
		}
		return accessList;
	}

	public LoadAccessTypeResponse loadAccessTypeByID(Integer id) throws MalformedURLException, Exception_Exception {
		URL url = new URL(contextLoader.getHostWSUrl() + "access?wsdl");
		QName qName = new QName(contextLoader.getHostWSPort(), "AccessService");
		AccessService service = new AccessService(url, qName);
		Access client = service.getAccessPort();

		org.bellatrix.services.ws.access.Header headerAccess = new org.bellatrix.services.ws.access.Header();
		headerAccess.setToken(contextLoader.getHeaderToken());
		Holder<org.bellatrix.services.ws.access.Header> accessHeaderAuth = new Holder<org.bellatrix.services.ws.access.Header>();
		accessHeaderAuth.value = headerAccess;

		AccessTypeRequest req = new AccessTypeRequest();
		req.setId(id);
		LoadAccessTypeResponse loadAccessTypeResponse = client.loadAccessType(accessHeaderAuth, req);

		return loadAccessTypeResponse;

	}

	public void createAccessType(AccessType req) throws MalformedURLException, TransactionException_Exception {
		URL url = new URL(contextLoader.getHostWSUrl() + "access?wsdl");
		QName qName = new QName(contextLoader.getHostWSPort(), "AccessService");
		AccessService service = new AccessService(url, qName);
		Access client = service.getAccessPort();

		org.bellatrix.services.ws.access.Header headerAccess = new org.bellatrix.services.ws.access.Header();
		headerAccess.setToken(contextLoader.getHeaderToken());
		Holder<org.bellatrix.services.ws.access.Header> accessHeaderAuth = new Holder<org.bellatrix.services.ws.access.Header>();
		accessHeaderAuth.value = headerAccess;

		AccessTypeRequest accessTypeRequest = new AccessTypeRequest();
		accessTypeRequest.setId(req.getAccessTypeID());
		accessTypeRequest.setName(req.getName());
		accessTypeRequest.setDescription(req.getDescription());
		accessTypeRequest.setInternalName(req.getInternalName());

		client.createAccessType(accessHeaderAuth, accessTypeRequest);
	}

	public void editAcessType(AccessType req) throws MalformedURLException, TransactionException_Exception {
		URL url = new URL(contextLoader.getHostWSUrl() + "access?wsdl");
		QName qName = new QName(contextLoader.getHostWSPort(), "AccessService");
		AccessService service = new AccessService(url, qName);
		Access client = service.getAccessPort();

		org.bellatrix.services.ws.access.Header headerAccess = new org.bellatrix.services.ws.access.Header();
		headerAccess.setToken(contextLoader.getHeaderToken());
		Holder<org.bellatrix.services.ws.access.Header> accessHeaderAuth = new Holder<org.bellatrix.services.ws.access.Header>();
		accessHeaderAuth.value = headerAccess;

		AccessTypeRequest accessTypeRequest = new AccessTypeRequest();
		accessTypeRequest.setId(req.getAccessTypeID());
		accessTypeRequest.setName(req.getName());
		accessTypeRequest.setDescription(req.getDescription());
		accessTypeRequest.setInternalName(req.getInternalName());

		client.updateAccessType(accessHeaderAuth, accessTypeRequest);
	}

}
