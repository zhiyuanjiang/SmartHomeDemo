package data;


public class SensorData {
	private String id;
	private String ip;
	private double humi;
	private double temp;
	
	public SensorData() {}
	
	public SensorData(String id, String ip, double humi, double temp) {
		this.id = id;
		this.ip = ip;
		this.humi = humi;
		this.temp = temp;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getIp() {
		return ip;
	}
	
	public void setIp(String ip) {
		this.ip = ip;
	}
	
	public double getHumi() {
		return humi;
	}
	
	public void setHumi(double humi) {
		this.humi = humi;
	}
	
	public double getTemp() {
		return temp;
	}
	
	public void setTemp(double temp) {
		this.temp = temp;
	}
	
	public String toString() {
		return "id:"+id+"\n"+"ip:"+ip+"\n"+"h:"+humi+"\n"+"t:"+temp+"\n";
	}
}
