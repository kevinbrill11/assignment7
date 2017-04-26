package assignment7;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.Observable;
import java.util.Observer;

public class ClientObserver extends ObjectOutputStream implements Observer {
	public ClientObserver(OutputStream out) throws Exception {
		super(out);
	}
	
	@Override
	public void update(Observable o, Object arg) {
		Message message = (Message) arg;
		System.out.println("Observer got: " + message.getCode() + " " + message.getMessage());
		try {
			writeObject(message); //outToClient.writeObject
		} catch (IOException e) {
			System.out.println("ClientObserver sending exception");
			e.printStackTrace();
		}
	}

}
