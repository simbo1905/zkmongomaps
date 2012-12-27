package org.github.simbo1905.zkmongogmaps.view;

import java.util.List;

import org.github.simbo1905.zkmongogmaps.app.City;
import org.github.simbo1905.zkmongogmaps.app.CityRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.select.annotation.WireVariable;

import com.google.common.collect.Lists;

public class CityViewModel {

	@WireVariable
	protected CityRepository cityRepository = null;
	
	int pageSize = 10;
	int activePage = 0;
	
	public long getTotalSize()
	{
		return cityRepository.count();
	}
	
	public Integer getPageSize()
	{
		return pageSize;
	}

	@NotifyChange("cities")
	public void setActivePage(int activePage)
	{
		this.activePage = activePage;
	}

	public List<City> getCities()
	{
		Pageable pageable = new PageRequest(activePage,pageSize);
		Page<City> page = cityRepository.findAll(pageable);
		return Lists.newArrayList(page.iterator());
	}
	
	protected City pickedCity = null;

	public City getPickedCity() {
		return pickedCity;
	}

	public void setPickedCity(City pickedCity) {
		this.pickedCity = pickedCity;
	}
	
	@GlobalCommand("cityChange") public void changedCity(@BindingParam("data") City city) {
		return;
	}
	
}
