package messaging;

public class Response {

	private boolean hostResponded;
	private boolean clientResponded;
	private boolean result;

	public Response(boolean hostResponded, boolean clientResponded, boolean result) {
		this.hostResponded = hostResponded;
		this.clientResponded = clientResponded;
		this.result = result;
	}

	public boolean getHostResponded() {
		return hostResponded;
	}

	public boolean getClientResponded() {
		return clientResponded;
	}

	public boolean getResult() {
		return result;
	}

}

