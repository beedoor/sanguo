package com.game.sanguo.domain;

import java.util.ArrayList;
import java.util.List;

public class SearchResult {
	private List<CityInfo> marketList = new ArrayList<CityInfo>();
	private List<CityInfo> goldList = new ArrayList<CityInfo>();
	private List<CityInfo> soliderList = new ArrayList<CityInfo>();
	private List<CityInfo> treasureList = new ArrayList<CityInfo>();

	private String sortType;
	
	public SearchResult(String sortType) {
		super();
		this.sortType = sortType;
	}

	public List<CityInfo> getMarketList() {
		return marketList;
	}

	public void addMarketInfo(CityInfo cityInfo) {
		List<CityInfo> tmpMarketList = new ArrayList<CityInfo>();
		tmpMarketList.addAll(marketList);
		tmpMarketList.add(cityInfo);
		SearchResultSorter.sort(tmpMarketList,sortType);
		marketList = tmpMarketList;
	}

	public void addGoIdnfo(CityInfo cityInfo) {
		List<CityInfo> tmpGoldList = new ArrayList<CityInfo>();
		tmpGoldList.addAll(goldList);
		tmpGoldList.add(cityInfo);
		SearchResultSorter.sort(tmpGoldList,sortType);
		goldList = tmpGoldList;
	}

	public void addSoliderInfo(CityInfo cityInfo) {
		List<CityInfo> tmpSoliderList = new ArrayList<CityInfo>();
		tmpSoliderList.addAll(goldList);
		tmpSoliderList.add(cityInfo);
		SearchResultSorter.sort(tmpSoliderList,sortType);
		soliderList = tmpSoliderList;
	}

	public void addTreasureInfo(CityInfo cityInfo) {
		List<CityInfo> tmpTreasureList = new ArrayList<CityInfo>();
		tmpTreasureList.addAll(treasureList);
		tmpTreasureList.add(cityInfo);
		SearchResultSorter.sort(tmpTreasureList,sortType);
		treasureList = tmpTreasureList;
	}

	public List<CityInfo> getGoldList() {
		return goldList;
	}

	public List<CityInfo> getSoliderList() {
		return soliderList;
	}

	public List<CityInfo> getTreasureList() {
		return treasureList;
	}

	public void clearGoldList() {
		this.goldList = new ArrayList<CityInfo>();
	}

	public void clearMarketList() {
		marketList = new ArrayList<CityInfo>();
	}

	public void clearTreasureList() {
		treasureList = new ArrayList<CityInfo>();
	}

	public void clearSoliderList() {
		soliderList = new ArrayList<CityInfo>();
	}

}