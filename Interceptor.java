package com.bel.esign.interceptor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.bel.esign.helper.PartnerValidation;
import com.bel.esign.service.implementation.CustomRestTemplateConfiguration;
import com.bel.esign.util.AspConfiguration;

@Component
public class Interceptor implements HandlerInterceptor {

	@Autowired
	private CustomRestTemplateConfiguration customRestTemplateConfiguration;

	@Autowired
	private PartnerValidation partnerValidation;

	HttpServletResponse response;

	@Value("${apiPath}")
	public String apiPath;

	@Value("${apiPort}")
	public String apiPort;

	public static final String AUTHORIZATION = "Authorization";
	public static final String CALLEDAPI = "CalledAPi";

//	@Value("${IS_LDAP_ENABLED}")
//	private boolean IS_LDAP_ENABLED;
//
//	@Value("${IS_PAN_ENABLED}")
//	private boolean IS_PAN_ENABLED;
//
//	@Value("${IS_ONLINE_AADHAR_ENABLED}")
//	private boolean IS_ONLINE_AADHAR_ENABLED;

	Logger log = LoggerFactory.getLogger(Interceptor.class);

	private static final List<String> serviceCalledApi = Arrays.asList("/getesignresponse", "/getdelpoymentmode",
			"/organization/createNewOrganization", "/getAccountByAccountNumber", "/updateVideoeKYCVerificationStatus","/esign/asp/documentSigningWebSocket","/esign/asp/documentStatusUpdation"
			,"/customer/createcustomer","/customer/createorganisationcustomer","/createcustomer","/createorganisationcustomer");

	private static final List<String> singlePageApplicationApi = Arrays.asList(
			"/createcustomerforsinglepageapplication", "/getAllOnBoardedPartnerForSinglePageApplication",
			"/uploaddocument", "/signdocumentforsinglepageapplication", "/downloadcustomerdocument",
			"/downloadsigneddocument");

	private static final List<String> videoEkycApi = Arrays.asList("/updateVideoeKYCVerificationStatus");

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String userManagementInterceptorUrl = "https://" + apiPath + ":" + apiPort
				+ "/esign/usermanagement/interceptor";

		customRestTemplateConfiguration.setDomainName(request.getServerName());
		customRestTemplateConfiguration.setIpaddress(request.getRemoteAddr());
		customRestTemplateConfiguration.setAspId(request.getHeader("aspId"));

		if (request.getRequestURI().contains(videoEkycApi.get(0))) {
			if (request.getHeader("secretKey").equals("A2F477B2FD9BF270EAB784A59C74E785EA09B047")) {
				log.info("Verified headers");
				return true;
			} else {
				response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
				return false;
			}
		}

		if (request.getRequestURI().contains(serviceCalledApi.get(0))
				|| request.getRequestURI().contains(serviceCalledApi.get(1))
				|| request.getRequestURI().contains(serviceCalledApi.get(2))
				|| request.getRequestURI().contains(serviceCalledApi.get(3))
				|| request.getRequestURI().contains(serviceCalledApi.get(4))
				|| request.getRequestURI().contains(serviceCalledApi.get(5))
				|| request.getRequestURI().contains(serviceCalledApi.get(6))
				|| request.getRequestURI().contains(serviceCalledApi.get(7))
				|| request.getRequestURI().contains(serviceCalledApi.get(8))
				|| request.getRequestURI().contains(serviceCalledApi.get(9))
				|| request.getRequestURI().contains(serviceCalledApi.get(10))
				|| request.getRequestURI().contains(singlePageApplicationApi.get(1))
				|| request.getRequestURI().contains(singlePageApplicationApi.get(4))
				|| request.getRequestURI().contains(singlePageApplicationApi.get(5))) {
			log.info("This Api has free pass as it Service to service call : {}", request.getRequestURI());
			return true;
		}
		if (request.getRequestURI().contains(singlePageApplicationApi.get(0))
				|| request.getRequestURI().contains(singlePageApplicationApi.get(2))
				|| request.getRequestURI().contains(singlePageApplicationApi.get(3))
//				|| request.getRequestURI().contains(singlePageApplicationApi.get(4))
//				|| request.getRequestURI().contains(singlePageApplicationApi.get(5))
		) {
			log.info("This Api has free pass as it Service to service call : {}", request.getRequestURI());
			if (partnerValidation.validatePartner(customRestTemplateConfiguration.getAspId()) == true) {
				return true;
			} else {
				response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
				return false;
			}
		}

		try {
			HttpHeaders header = new HttpHeaders();
			header.add("Accept", MediaType.APPLICATION_JSON.toString());
			header.add("Content-Type", MediaType.APPLICATION_JSON.toString());
			header.add(AUTHORIZATION, request.getHeader(AUTHORIZATION));
			header.setContentType(MediaType.APPLICATION_JSON);
			header.add(CALLEDAPI, request.getRequestURI());
			customRestTemplateConfiguration.setRequest(request.getHeader("Authorization"));
			String author = request.getHeader(AUTHORIZATION);
			String uri = request.getRequestURI();
			HttpEntity<String> entity = new HttpEntity<String>(header);
			ResponseEntity<Boolean> responseEntity = customRestTemplateConfiguration.restTemplate()
					.exchange(userManagementInterceptorUrl, HttpMethod.GET, entity, Boolean.class);
			customRestTemplateConfiguration.setUserName(responseEntity.getHeaders().get("userName").get(0));
			customRestTemplateConfiguration.setUserId(responseEntity.getHeaders().get("userId").get(0));
			HttpStatus authResponseStatus = responseEntity.getStatusCode();
			if (authResponseStatus.value() != HttpServletResponse.SC_OK) {
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				return false;
			} else
				return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("Service Call Failed  " + e.getMessage());
			String message = e.getMessage();
			if (message.contains("403 Forbidden")) {
				response.setStatus(HttpServletResponse.SC_FORBIDDEN);

			} else {
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			}
			return false;
		}

	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

//		if (request.getRequestURI().contains(serviceCalledApi.get(1))) {
//			log.info("Inside post handle method");
//			response.addHeader("IS_LDAP_ENABLED", String.valueOf(IS_LDAP_ENABLED));
//			response.addHeader("IS_PAN_ENABLED", String.valueOf(IS_PAN_ENABLED));
//			response.addHeader("IS_ONLINE_AADHAR_ENABLED", String.valueOf(IS_ONLINE_AADHAR_ENABLED));
//		}

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception exception) throws Exception {
	}

	private String getRequestAddress(HttpServletRequest request) {
		String ipAddress = request.getHeader("HTTP_X_FORWARDED_FOR");

		if (ipAddress == null) {
			ipAddress = request.getRemoteAddr();
		}
		return ipAddress;
	}

	private Boolean validateLocalAddress(HttpServletRequest request) {
		String ip = getRequestAddress(request);
		if (ip == null) {
			log.error("Ip Address could not be fetched from request.For time being its been validated as true");
			return true;
		}
		List<String> defaultIpList = getDefaultIpList(request);
		for (String defaultIp : defaultIpList) {
			if (defaultIp.compareTo(ip) == 0)
				return true;
		}
		return false;
	}

	private List<String> getDefaultIpList(HttpServletRequest request) {
		List<String> ipList = new ArrayList<>();
		ipList.add(request.getLocalAddr());
		return ipList;
	}
}
