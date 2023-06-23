package com.customer_task_1.Dto;

public class CustomerReport {
    private String customerId;
    private String customerName;
    private double totalRevenue;
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public double getTotalRevenue() {
		return totalRevenue;
	}
	public void setTotalRevenue(double totalRevenue) {
		this.totalRevenue = totalRevenue;
	}
	
	public CustomerReport(String customerId, String customerName, double totalRevenue) {
		super();
		this.customerId = customerId;
		this.customerName = customerName;
		this.totalRevenue = totalRevenue;
	}
	public CustomerReport() {};

}

