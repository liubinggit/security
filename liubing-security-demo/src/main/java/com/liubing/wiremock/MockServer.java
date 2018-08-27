package com.liubing.wiremock;

import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.ClassPathResource;
import com.github.tomakehurst.wiremock.client.WireMock;

public class MockServer {

	public static void main(String[] args) throws IOException {
		WireMock.configureFor(8062);
		WireMock.removeAllMappings();

		mock("/order/1", "01.txt");

	}

	private static void mock(String url, String filePath) throws IOException {
		ClassPathResource resource = new ClassPathResource("mock/response/" + filePath);
		String content = StringUtils.join(FileUtils.readLines(resource.getFile(), "UTF-8").toArray(), "\n");
		WireMock.stubFor(WireMock.get(WireMock.urlEqualTo(url))
				.willReturn(WireMock.aResponse().withBody(content).withStatus(200)));

	}

}
