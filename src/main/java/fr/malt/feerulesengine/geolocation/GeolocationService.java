package fr.malt.feerulesengine.geolocation;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Objects;

@Service
public class GeolocationService {

    private static final String IPSTACK_BASE_URL = "http://api.ipstack.com/";
    private static final String ACCESS_KEY = "access_key";

    @Value("${IPSTACK_API_KEY}")
    private String API_KEY;

    private RestTemplate restTemplate;

    public GeolocationService() {
        this.restTemplate = new RestTemplate();
    }

    public String ipToCountryCode(String ip) {
        String url = UriComponentsBuilder.fromHttpUrl(IPSTACK_BASE_URL + ip)
                .queryParam(ACCESS_KEY, API_KEY)
                .toUriString();
        ResponseEntity<IpstackResponse> response = restTemplate.getForEntity(url, IpstackResponse.class);

        if (HttpStatus.OK.equals(response.getStatusCode())) {
            return Objects.requireNonNull(response.getBody()).getCountryCode();
        }

        throw new ExternalServiceException(
                String.format(
                        "An error occurred while calling ipstack external service. ErrorCode was %s",
                        response.getStatusCode().getReasonPhrase()));
    }


}
