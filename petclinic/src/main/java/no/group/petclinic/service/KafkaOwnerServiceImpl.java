package no.group.petclinic.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import no.group.petclinic.dto.OwnerSlim;
import no.group.petclinic.dto.OwnersPageImpl;
import no.group.petclinic.dto.OwnersPageRequest;
import no.group.petclinic.entity.Owner;
import no.group.petclinic.kafka.OwnerTopicConstants;

@Service
@RequiredArgsConstructor
public class KafkaOwnerServiceImpl implements KafkaOwnerService{

	private final ReplyingKafkaTemplate<String, OwnersPageRequest, OwnersPageImpl<OwnerSlim>> pagedOwnersTemplate;
	private final Logger LOG = LoggerFactory.getLogger(getClass());
	
	@Override
	public OwnersPageImpl<OwnerSlim> getOwners(int page, int size, String keyword) {
		
		OwnersPageRequest request = new OwnersPageRequest(keyword, page, size);
		ProducerRecord<String, OwnersPageRequest> record =
				new ProducerRecord<>(OwnerTopicConstants.OWNERS, request);
		RequestReplyFuture<String, OwnersPageRequest, OwnersPageImpl<OwnerSlim>> future = 
				pagedOwnersTemplate.sendAndReceive(record);
		ConsumerRecord<String, OwnersPageImpl<OwnerSlim>> response = null;
		try {
			response = future.get();
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		OwnersPageImpl<OwnerSlim> owners = fixOwnersMapping(response.value());
		return owners;
	}
	
	@Override
	public OwnersPageImpl<OwnerSlim> getOwners(int page, int size) {
		return getOwners(page, size, null);
	}

	@Override
	public void saveOwner(Owner owner) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Owner getOwner(Integer ownerId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteOwner(Integer ownerId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateOwner(Integer ownerId, Owner owner) {
		// TODO Auto-generated method stub
		
	}
	
	private OwnersPageImpl<OwnerSlim> fixOwnersMapping(OwnersPageImpl<OwnerSlim> owners) {
		List tempList = owners.getContent();
		List<OwnerSlim> ownerList = new ArrayList<>();
		for(int i = 0; i<tempList.size(); i++) {
			Map<String, Object> tempMap = (LinkedHashMap<String,Object>) tempList.get(i);
			ownerList.add(new OwnerSlim(
					(Integer) tempMap.get("id"), 
					(String) tempMap.get("firstName"), 
					(String) tempMap.get("lastName"), 
					(String) tempMap.get("phoneNumber"), 
					(String) tempMap.get("email")));
		}
		OwnersPageImpl<OwnerSlim> result = new OwnersPageImpl<OwnerSlim>(
										ownerList,
										PageRequest.of(owners.getNumber(), owners.getSize()),
										owners.getTotalElements());
		return result;
	}

}
