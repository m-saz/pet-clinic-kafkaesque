package no.group.petclinic.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
 
public class OwnersPageImpl<OwnerSlim> extends PageImpl<OwnerSlim> {
 
    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public OwnersPageImpl(@JsonProperty("content") List<OwnerSlim> content,
                        @JsonProperty("number") int number,
                        @JsonProperty("size") int size,
                        @JsonProperty("totalElements") Long totalElements,
                        @JsonProperty("pageable") JsonNode pageable,
                        @JsonProperty("last") boolean last,
                        @JsonProperty("totalPages") int totalPages,
                        @JsonProperty("sort") JsonNode sort,
                        @JsonProperty("first") boolean first,
                        @JsonProperty("numberOfElements") int numberOfElements) {
 
        super(content, PageRequest.of(number, size), totalElements);
    }
    
    public OwnersPageImpl(Page<OwnerSlim> page) {
        super(page.getContent(), page.getPageable(), page.getTotalElements());
    }
 
    public OwnersPageImpl(List<OwnerSlim> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }
 
    public OwnersPageImpl(List<OwnerSlim> content) {
        super(content);
    }
 
    public OwnersPageImpl() {
        super(new ArrayList<>());
    }
}