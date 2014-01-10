package ouc.sei.beans;

import org.apache.http.client.HttpClient;

public class ClientBean {
	private int time;
	private HttpClient client;
	public void setTime(int time) {
		this.time = time;
	}
	public int getTime() {
		return time;
	}
	public void setClient(HttpClient client) {
		this.client = client;
	}
	public HttpClient getClient() {
		return client;
	}
	public ClientBean(int time,HttpClient client){
		this.time=time;
		this.client=client;
	}

}
