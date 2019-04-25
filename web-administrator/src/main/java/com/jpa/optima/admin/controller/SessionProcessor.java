package com.jpa.optima.admin.controller;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Holder;

import org.bellatrix.services.ws.access.Access;
import org.bellatrix.services.ws.access.AccessService;
import org.bellatrix.services.ws.access.ValidateCredentialRequest;
import org.bellatrix.services.ws.access.ValidateCredentialResponse;
import org.bellatrix.services.ws.groups.Group;
import org.bellatrix.services.ws.groups.GroupService;
import org.bellatrix.services.ws.groups.LoadGroupsByIDRequest;
import org.bellatrix.services.ws.groups.LoadGroupsByIDResponse;
import org.bellatrix.services.ws.members.LoadMembersByUsernameRequest;
import org.bellatrix.services.ws.members.LoadMembersResponse;
import org.bellatrix.services.ws.members.Member;
import org.bellatrix.services.ws.members.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jpa.optima.admin.model.Login;

@Component
public class SessionProcessor {

	@Autowired
	private ContextLoader contextLoader;

	public com.jpa.optima.admin.model.Member doLogin(Login request) throws MalformedURLException {
		URL url = new URL(contextLoader.getHostWSUrl() + "access?wsdl");
		QName qName = new QName(contextLoader.getHostWSPort(), "AccessService");
		AccessService as = new AccessService(url, qName);
		Access client = as.getAccessPort();

		org.bellatrix.services.ws.access.Header headerAccess = new org.bellatrix.services.ws.access.Header();
		headerAccess.setToken(contextLoader.getHeaderToken());
		Holder<org.bellatrix.services.ws.access.Header> headerAccessAuth = new Holder<org.bellatrix.services.ws.access.Header>();
		headerAccessAuth.value = headerAccess;

		ValidateCredentialRequest vcr = new ValidateCredentialRequest();
		vcr.setAccessTypeID(contextLoader.getMemberWebCredentialTypeID());
		vcr.setUsername(request.getUsername());
		vcr.setCredential(request.getPassword());
		ValidateCredentialResponse response = client.validateCredential(headerAccessAuth, vcr);

		if (response.getStatus().getMessage().equalsIgnoreCase("VALID")) {
			URL urlMember = new URL(contextLoader.getHostWSUrl() + "members?wsdl");
			QName qNameMember = new QName(contextLoader.getHostWSPort(), "MemberService");
			MemberService ms = new MemberService(urlMember, qNameMember);
			LoadMembersByUsernameRequest loadMembersByUsername = new LoadMembersByUsernameRequest();
			loadMembersByUsername.setUsername(request.getUsername());
			Member memberClient = ms.getMemberPort();
			org.bellatrix.services.ws.members.Header headerMember = new org.bellatrix.services.ws.members.Header();
			headerMember.setToken(contextLoader.getHeaderToken());
			Holder<org.bellatrix.services.ws.members.Header> memberHeaderAuth = new Holder<org.bellatrix.services.ws.members.Header>();
			memberHeaderAuth.value = headerMember;
			LoadMembersResponse memberResponse = memberClient.loadMembersByUsername(memberHeaderAuth,
					loadMembersByUsername);

			URL urlGroup = new URL(contextLoader.getHostWSUrl() + "groups?wsdl");
			QName qNameGroup = new QName(contextLoader.getHostWSPort(), "GroupService");
			GroupService gs = new GroupService(urlGroup, qNameGroup);
			Group groupClient = gs.getGroupPort();
			LoadGroupsByIDRequest groupbyID = new LoadGroupsByIDRequest();
			groupbyID.setId(memberResponse.getMembers().get(0).getGroupID());

			org.bellatrix.services.ws.groups.Header headerGroup = new org.bellatrix.services.ws.groups.Header();
			headerGroup.setToken(contextLoader.getHeaderToken());
			Holder<org.bellatrix.services.ws.groups.Header> groupHeaderAuth = new Holder<org.bellatrix.services.ws.groups.Header>();
			groupHeaderAuth.value = headerGroup;
			LoadGroupsByIDResponse groupResponse = groupClient.loadGroupsByID(groupHeaderAuth, groupbyID);

			com.jpa.optima.admin.model.Member memberView = new com.jpa.optima.admin.model.Member();
			memberView.setName(memberResponse.getMembers().get(0).getName());
			memberView.setID(memberResponse.getMembers().get(0).getId());
			memberView.setUsername(memberResponse.getMembers().get(0).getUsername());
			memberView.setEmail(memberResponse.getMembers().get(0).getEmail());
			memberView.setGroupID(memberResponse.getMembers().get(0).getGroupID());
			memberView.setGroupName(groupResponse.getGroups().getName());
			memberView.setStatus(response.getStatus().getMessage());

			return memberView;

		} else if (response.getStatus().getMessage().equalsIgnoreCase("BLOCKED")) {
			com.jpa.optima.admin.model.Member memberView = new com.jpa.optima.admin.model.Member();
			memberView.setStatus(response.getStatus().getMessage());
			memberView.setDescription("Member Blocked");

			return memberView;
		} else {
			com.jpa.optima.admin.model.Member memberView = new com.jpa.optima.admin.model.Member();
			memberView.setStatus(response.getStatus().getMessage());
			memberView.setDescription("Invalid Username/Password");

			return memberView;
		}

	}

}
