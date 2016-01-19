package org.bahmni.atomfeed.core.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;

public class EventSearchRequest {

	@NotNull(message = "Provide a valid component")
	private String component;

	@NotNull(message = "Provide a valid category")
	private String category;

	@NotNull(message = "Provide a valid startDate")
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private Date startDate;

	@NotNull(message = "Provide a valid endDate")
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private Date endDate;


	public String getComponent() {
		return component;
	}

	public void setComponent(String component) {
		this.component = component;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
}
