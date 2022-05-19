package no.group.petclinic.kafka;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.kafka.common.header.Headers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;

import no.group.petclinic.dto.OwnerSlim;

public class PageDeserializer<T> extends JsonDeserializer<T>{

	private static final String CONTENT = "content";
    private static final String NUMBER = "number";
    private static final String SIZE = "size";
    private static final String TOTAL_ELEMENTS = "totalElements";
    private ObjectMapper mapper; 
    
	@Override
	public T deserialize(String topic, Headers headers, byte[] data) {
		
		int pageNumber = 0;
		int pageSize = 0;
		long totalElements = 0L;
		List<OwnerSlim> owners = new ArrayList<OwnerSlim>();
		ObjectReader reader = mapper.readerFor(new TypeReference<List<OwnerSlim>>() {});
		
		JsonNode pageNode = null;
		try {
			pageNode = mapper.readTree(data);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		pageNumber = pageNode.get(NUMBER).asInt();
		pageSize = pageNode.get(SIZE).asInt();
		totalElements = pageNode.get(TOTAL_ELEMENTS).asLong();
		try {
			owners = reader.readValue(pageNode.get(CONTENT));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Page<OwnerSlim> page = 
				new PageImpl<>(owners, PageRequest.of(pageNumber, pageSize), totalElements);
		return (T) page;
	}

	@Override
	public void configure(Map<String, ?> config, boolean isKey) {
		 this.mapper = new ObjectMapper();
	}
    
}
