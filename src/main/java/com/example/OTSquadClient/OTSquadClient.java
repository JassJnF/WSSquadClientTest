package com.example.OTSquadClient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

import com.example.consumingwebservice.wsdl.OTSquadResponse;
import com.example.consumingwebservice.wsdl.GetOTSquad;
import com.example.consumingwebservice.wsdl.MemberSquadsResponse;
import com.example.consumingwebservice.wsdl.GetMemberSquads;
import com.example.consumingwebservice.wsdl.AddMemberToSquad;
import com.example.consumingwebservice.wsdl.AddMemberToSquadResponse;

public class OTSquadClient extends WebServiceGatewaySupport {

    private static final Logger log = LoggerFactory.getLogger(OTSquadClient.class);

    public OTSquadResponse getSquad(String squad) {

        GetOTSquad request = new GetOTSquad();
        request.setSquadName(squad);
        log.info("Requesting squad " + request.getSquadName());

        OTSquadResponse response = (OTSquadResponse) getWebServiceTemplate()
                .marshalSendAndReceive("http://localhost:8080/ws/opentable", request, null);

        return response;
    }

    public MemberSquadsResponse getMemberSquads(String memberName) {

        GetMemberSquads request = new GetMemberSquads();
        request.setMemberName(memberName);
        log.info("Requesting squads for " + request.getMemberName());

        MemberSquadsResponse response = (MemberSquadsResponse) getWebServiceTemplate()
                .marshalSendAndReceive("http://localhost:8080/ws/opentable", request, null);

        return response;
    }

    public AddMemberToSquadResponse addMemberToSquad(String memberName, String squadName){
        AddMemberToSquad request = new AddMemberToSquad();
        request.setMemberName(memberName);
        request.setSquadName(squadName);

        AddMemberToSquadResponse response = (AddMemberToSquadResponse) getWebServiceTemplate()
                .marshalSendAndReceive("http://localhost:8080/ws/opentable", request, null);

        return response;
    }

}