package gov.va.cpe.vpr;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gov.va.cpe.vpr.pom.JSONViews;
import gov.va.cpe.vpr.pom.POMUtils;
import gov.va.hmp.healthtime.PointInTime;
import gov.va.hmp.web.converter.StringToJsonNodeConverter;

import org.junit.Test;
//import org.mockito.Mockito;



import com.fasterxml.jackson.databind.JsonNode;

public class VLERDocumentTest {
	/*@Before
	public void setUp(){
		
	}*/

	@Test
	public void testJSON(){
		POMUtils pomUtils = new POMUtils();
		
		//Create templateIds
		Map<String, Object> templateIdMap = new HashMap<String, Object>();
		templateIdMap.put("root", "15");
		VLERDocumentTemplateId templateId = new VLERDocumentTemplateId(templateIdMap);
		
		List<VLERDocumentTemplateId> templateIds = new ArrayList<VLERDocumentTemplateId>();
		templateIds.add(templateId);
		
		//Create JdsCode
		JdsCode code = new JdsCode();
		code.setCode("16");
		code.setSystem("17");
		code.setDisplay("18");
		
		//Create VLERDocumentSection		
		Map<String, Object> sectionMap = new HashMap<String, Object>();
		sectionMap.put("templateIds", templateIds);
		sectionMap.put("code", code);
		sectionMap.put("title", "19");
		sectionMap.put("text", "20");
		VLERDocumentSection section = new VLERDocumentSection(sectionMap);

		//Create VLERDocumentAuthor
		Map<String, Object> authorMap = new HashMap<String, Object>();
		authorMap.put("institution", "6");
		authorMap.put("name", "7");
		VLERDocumentAuthor author = new VLERDocumentAuthor(authorMap);
		
		//Create VLERDocument
		List<VLERDocumentAuthor> authorList = new ArrayList<VLERDocumentAuthor>();
		authorList.add(author);
		List<VLERDocumentSection> sectionList = new ArrayList<VLERDocumentSection>();
		sectionList.add(section);
		
		PointInTime time = new PointInTime(2014,8,8,8,8,8);
		
		Map<String, Object> documentMap = new HashMap<String, Object>();
		documentMap.put("uid", "1");
		documentMap.put("pid", "2");
		documentMap.put("localId", "3");
		documentMap.put("kind", "4");
		//documentMap.put("summary", "5"); //Already generated by superclass?
		documentMap.put("authorList", authorList);
		documentMap.put("creationTime", time);
		documentMap.put("documentUniqueId", "9");
		documentMap.put("homeCommunityId", "10");
		documentMap.put("mimeType", "11");
		documentMap.put("name", "12");
		documentMap.put("repositoryUniqueId", "13");
		documentMap.put("sourcePatientId", "14");
		documentMap.put("sections", sectionList);
		
		VLERDocument v = new VLERDocument(documentMap);
		
		String JSON = pomUtils.toJSON(v, JSONViews.JDBView.class);
		
		StringToJsonNodeConverter converter = new StringToJsonNodeConverter();
		
		JsonNode vlerDocumentNode = converter.convert(JSON);
		
		assertEquals("uid was not converted correctly to JSON",vlerDocumentNode.findValue("uid").asText(), documentMap.get("uid"));
		assertEquals("pid was not converted correctly to JSON",vlerDocumentNode.findValue("pid").asText(), documentMap.get("pid"));
		assertEquals("localId was not converted correctly to JSON",vlerDocumentNode.findValue("localId").asText(), documentMap.get("localId"));
		assertEquals("kind was not converted correctly to JSON",vlerDocumentNode.findValue("kind").asText(), documentMap.get("kind"));
		assertEquals("creationTime was not converted correctly to JSON",vlerDocumentNode.findValue("creationTime").asText(), documentMap.get("creationTime").toString());
		assertEquals("documentUniqueId was not converted correctly to JSON",vlerDocumentNode.findValue("documentUniqueId").asText(), documentMap.get("documentUniqueId"));
		assertEquals("homeCommunityId was not converted correctly to JSON",vlerDocumentNode.findValue("homeCommunityId").asText(), documentMap.get("homeCommunityId"));
		assertEquals("mimeType was not converted correctly to JSON",vlerDocumentNode.findValue("mimeType").asText(), documentMap.get("mimeType"));
		
		assertEquals("repositoryUniqueId was not converted correctly to JSON",vlerDocumentNode.findValue("repositoryUniqueId").asText(), documentMap.get("repositoryUniqueId"));
		assertEquals("sourcePatientId was not converted correctly to JSON",vlerDocumentNode.findValue("sourcePatientId").asText(), documentMap.get("sourcePatientId"));

		assertEquals("root was not converted correctly to JSON",vlerDocumentNode.findValue("root").asText(), templateIdMap.get("root"));

		assertEquals("code was not converted correctly to JSON",vlerDocumentNode.findValue("code").findValue("code").asText(), code.getCode());
		assertEquals("system was not converted correctly to JSON",vlerDocumentNode.findValue("system").asText(), code.getSystem());
		assertEquals("display was not converted correctly to JSON",vlerDocumentNode.findValue("display").asText(), code.getDisplay());
		
		assertEquals("title was not converted correctly to JSON",vlerDocumentNode.findValue("title").asText(), sectionMap.get("title"));
		assertEquals("text was not converted correctly to JSON",vlerDocumentNode.findValue("text").asText(), sectionMap.get("text"));

		assertEquals("institution was not converted correctly to JSON",vlerDocumentNode.findValue("institution").asText(), authorMap.get("institution"));
		
		List<String> nameList = vlerDocumentNode.findValuesAsText("name");
		assertTrue("authorList -> name was not converted correctly", nameList.contains(authorMap.get("name")));
		assertTrue("name was not converted correctly", nameList.contains(documentMap.get("name")));
	}
}
