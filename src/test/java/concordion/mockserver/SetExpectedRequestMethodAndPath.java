package concordion.mockserver;

import com.ravlinko.concordion.extension.codemirror.CodeMirrorExtension;
import com.ravlinko.concordion.extension.foundation.ZurbFoundationExtension;
import com.ravlinko.concordion.extension.mockserver.MockServerExtension;

import org.concordion.api.extension.Extension;
import org.concordion.api.extension.Extensions;
import org.concordion.integration.junit4.ConcordionRunner;
import org.junit.runner.RunWith;

import pl.pragmatists.concordion.rest.RestExtension;

@RunWith(ConcordionRunner.class)
@Extensions({CodeMirrorExtension.class, ZurbFoundationExtension.class, MockServerExtension.class})
public class SetExpectedRequestMethodAndPath {
	@Extension
	private RestExtension restExtension = new RestExtension().host("localhost").port(1080);
}
