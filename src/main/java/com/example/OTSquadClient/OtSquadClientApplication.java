package com.example.OTSquadClient;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.consumingwebservice.wsdl.OTSquadResponse;
import com.example.consumingwebservice.wsdl.OTMember;
import com.example.consumingwebservice.wsdl.MemberSquadsResponse;
import com.example.consumingwebservice.wsdl.GetMemberSquads;
import com.example.consumingwebservice.wsdl.AddMemberToSquad;
import com.example.consumingwebservice.wsdl.AddMemberToSquadResponse;


@SpringBootApplication
public class OtSquadClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(OtSquadClientApplication.class, args);
	}

	public static final String REQUEST_GET_SQUAD = "getsquad";
	public static final String REQUEST_GET_SQUADS_FOR_MEMBER = "getsquadsformember";
	public static final String REQUEST_ADD_MEMBER_TO_SQUAD = "addmember";

	@Bean
	CommandLineRunner lookup(OTSquadClient quoteClient) {

		return args -> {

			String squadName = null;
			String memberName = null;

			String option = REQUEST_GET_SQUAD;

			if (args.length > 0) {
				option = args[0];
			}

			if(option.compareToIgnoreCase(REQUEST_GET_SQUADS_FOR_MEMBER)==0){
				memberName = args[1];
				MemberSquadsResponse response = quoteClient.getMemberSquads(memberName);
				if(response == null || response.getSquads().isEmpty()){
					System.out.println("\n\n"+ "No Squads found for :" + memberName + "\n\n");
				}
				else{
					System.out.println("\n\n"+memberName + " is in the following squad(s):" + "\n\n");
					for(String foundSquad:response.getSquads()){
						System.out.println(foundSquad);
					}
				}
			}
			else if (option.compareToIgnoreCase(REQUEST_GET_SQUAD)==0){
				squadName = args[1];
				OTSquadResponse response = quoteClient.getSquad(squadName);
				if(response.getOtsquad()==null){
					System.out.println("\n\n" + squadName + " squad  not found." + "\n\n");
				}
				else {
					System.out.println("\n\n"+"ID:" + response.getOtsquad().getId() + "\nTotal Members:" + response.getOtsquad().getTotalMembers());
					System.out.println(response.getOtsquad().getSquadName() + "\n Team members are:" + "\n\n");
					for (OTMember member : response.getOtsquad().getMembers()) {
						System.out.println(member.getName());
					}
				}
			}
			else if(option.compareToIgnoreCase(REQUEST_ADD_MEMBER_TO_SQUAD)==0){
				memberName = args[1];
				squadName = args[2];
				AddMemberToSquadResponse response = quoteClient.addMemberToSquad(memberName, squadName);
				System.out.println(response.getResult());
			}
		};
	}

}