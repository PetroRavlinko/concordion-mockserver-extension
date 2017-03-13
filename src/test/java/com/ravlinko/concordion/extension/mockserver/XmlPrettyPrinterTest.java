package com.ravlinko.concordion.extension.mockserver;

import com.ravlinko.concordion.extension.mockserver.utils.XmlPrettyPrinter;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class XmlPrettyPrinterTest {

	@Test
	public void shouldFormatXml() {
		XmlPrettyPrinter unit = new XmlPrettyPrinter();
		String inputXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><oxip version=\"7.0\">    <request>        <reqClientAuth returnToken=\"Y\">            <user>ProxyTest</user>            <password>5ymph0nY123</password>        </reqClientAuth>        <reqAccountGetFreebets>            <token>2FJhG5keF7cLxu0Vol5P5V0H1f2Nbd/KIukZ9w1H3hdym1MJLPVnCiDjbmYtbUjsssJkWbQWKajqCsPl2WxAuZSgyqmhb3tffONLfclyBkO0t/KNy76xdTGytpw3</token><freebetTokenType>ACCESS</freebetTokenType></reqAccountGetFreebets></request></oxip>";
		String expectedXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
				"<oxip version=\"7.0\">\n" +
				"    <request>\n" +
				"        <reqClientAuth returnToken=\"Y\">\n" +
				"            <user>ProxyTest</user>\n" +
				"            <password>5ymph0nY123</password>\n" +
				"        </reqClientAuth>\n" +
				"        <reqAccountGetFreebets>\n" +
				"            <token>2FJhG5keF7cLxu0Vol5P5V0H1f2Nbd/KIukZ9w1H3hdym1MJLPVnCiDjbmYtbUjsssJkWbQWKajqCsPl2WxAuZSgyqmhb3tffONLfclyBkO0t/KNy76xdTGytpw3</token>\n" +
				"            <freebetTokenType>ACCESS</freebetTokenType>\n" +
				"        </reqAccountGetFreebets>\n" +
				"    </request>\n" +
				"</oxip>\n";
		assertEquals(expectedXml, unit.prettyPrint(inputXml));
	}
}