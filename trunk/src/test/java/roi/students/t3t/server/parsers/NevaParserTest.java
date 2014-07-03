package roi.students.t3t.server.parsers;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

public class NevaParserTest {

	@Test
	public void testGetList() {
		NevaParser nevaParser = new NevaParser();
		List list = nevaParser.getList(null);

		assertTrue(Boolean.TRUE);

	}

	@Test
	public void testBuildUrl() {
		// fail("Not yet implemented");
	}

}
