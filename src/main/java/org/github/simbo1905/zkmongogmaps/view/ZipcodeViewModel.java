package org.github.simbo1905.zkmongogmaps.view;

import java.util.List;

import org.github.simbo1905.zkmongogmaps.app.Zipcode;
import org.github.simbo1905.zkmongogmaps.app.ZipcodeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.select.annotation.WireVariable;

import com.google.common.collect.Lists;

public class ZipcodeViewModel {

	@WireVariable
	protected ZipcodeRepository zipcodeRepository = null;
	
	int pageSize = 10;
	int activePage = 0;
	
	public long getTotalSize()
	{
		return zipcodeRepository.count();
	}
	
	public Integer getPageSize()
	{
		return pageSize;
	}

	@NotifyChange("zipcodes")
	public void setActivePage(int activePage)
	{
		this.activePage = activePage;
	}

	public List<Zipcode> getZipcodes()
	{
		Pageable pageable = new PageRequest(activePage,pageSize);
		Page<Zipcode> page = zipcodeRepository.findAll(pageable);
		return Lists.newArrayList(page.iterator());
	}
	
	protected Zipcode pickedZipcode = null;

	public Zipcode getPickedZipcode() {
		return pickedZipcode;
	}

	public void setPickedZipcode(Zipcode pickedZipcode) {
		this.pickedZipcode = pickedZipcode;
	}

}
