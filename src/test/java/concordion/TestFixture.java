package concordion;

import com.ravlinko.concordion.extension.codemirror.CodeMirrorExtension;
import com.ravlinko.concordion.extension.foundation.ZurbFoundationExtension;
import com.ravlinko.concordion.extension.mockserver.MockServerExtension;

import org.concordion.api.extension.Extensions;
import org.concordion.integration.junit4.ConcordionRunner;
import org.junit.Ignore;
import org.junit.runner.RunWith;

@Ignore
@RunWith(ConcordionRunner.class)
@Extensions({CodeMirrorExtension.class, ZurbFoundationExtension.class, MockServerExtension.class})
public class TestFixture {

}
