package com.dragon.spider.message.req;

public final class LocationEvent extends BaseEvent {

	private double latitude;
	private double longitude;
	private double precision;

	public double getLatitude() {
		return latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public double getPrecision() {
		return precision;
	}

	public LocationEvent(double latitude, double longitude, double precision) {
		super();
		this.latitude = latitude;
		this.longitude = longitude;
		this.precision = precision;
	}

	@Override
	public String toString() {
		return "LocationEvent [latitude=" + latitude + ", longitude="
				+ longitude + ", precision=" + precision + ", toUserName="
				+ toUserName + ", fromUserName=" + fromUserName
				+ ", createTime=" + createTime + ", msgType=" + msgType + "]";
	}

}
