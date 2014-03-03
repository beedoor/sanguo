package com.game.sanguo.domain;

public class Configure {

	private ScanResource scanResource;
	private Long searchResource = 0L;

	public Configure() {
		super();
	}

	public Long getSearchResource() {
		return searchResource;
	}

	public void setSearchResource(Long searchResource) {
		this.searchResource = searchResource;
	}

	public ScanResource getScanResource() {
		return scanResource;
	}

	public void setScanResource(ScanResource scanResource) {
		this.scanResource = scanResource;
	}

	@Override
	public String toString() {
		return "Configure [scanResource=" + scanResource + ", searchResource=" + searchResource + "]";
	}

}
