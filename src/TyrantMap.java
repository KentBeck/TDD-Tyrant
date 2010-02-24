import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

class TyrantMap {
	private static final int GET_OPERATION = 0x30;
	private static final int PUT_OPERATION = 0x10;
	private Socket socket;
	private DataOutputStream writer;
	private DataInputStream reader;

	void put(byte[] key, byte[] value) throws UnknownHostException, IOException {
		writeOperation(PUT_OPERATION);
		writer.writeInt(key.length);
		writer.writeInt(value.length);
		writer.write(key);
		writer.write(value);
		int status = reader.read();
		if (status != 0)
			throw new RuntimeException(); 
	}

	private void writeOperation(int operation) throws IOException {
		writer.write(0xC8);
		writer.write(operation);
	}

	public byte[] get(byte[] key) throws IOException {
		writeOperation(GET_OPERATION);
		writer.writeInt(key.length);
		writer.write(key);
		int status = reader.read();
		if (status != 0)
			throw new RuntimeException();
		int length = reader.readInt();
		byte[] results = new byte[length];
		reader.read(results); // TODO check return value
		return results;
	}
	
	void open() throws UnknownHostException, IOException {
		socket = new Socket("localhost", 1978);
		writer = new DataOutputStream(socket.getOutputStream());
		reader = new DataInputStream(socket.getInputStream());
	}

	public void close() throws IOException {
		socket.close();
	}
}