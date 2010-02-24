import static org.junit.Assert.assertArrayEquals;

import java.io.IOException;
import java.net.UnknownHostException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TyrantTest {
	private TyrantMap tyrant;

	@Test public void putThenGet() throws UnknownHostException, IOException {
		byte[] key = new byte[] {'k', 'e', 'y'};
		byte[] value = new byte[] {'v', 'a', 'l', 'u', 'e'};
		tyrant.put(key, value);
		byte[] actual = tyrant.get(key);
		assertArrayEquals(value, actual);
	}

	@Before public void openTyrant() throws UnknownHostException, IOException {
		tyrant = new TyrantMap();
		tyrant.open();
	}

	@After public void closeTyrant() throws IOException {
		tyrant.close();
	}
}
