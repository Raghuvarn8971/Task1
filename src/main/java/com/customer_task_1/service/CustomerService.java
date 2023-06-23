package com.customer_task_1.service;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.customer_task_1.Dto.CustomerReport;
import com.customer_task_1.entity.Customer;
import com.customer_task_1.repository.CustomerRepository;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class CustomerService {
	@Autowired
	private CustomerRepository customerRepository;

	public List<CustomerReport> generateReport() throws IOException {
		List<Customer> customers = readCustomerDataFromCSV();
		
		
		Map<String, Double> revenuePerCustomer = calculateRevenuePerCustomer(customers);
		List<CustomerReport> report = generateReportSortedByRevenue(revenuePerCustomer);
		return report;
	}

	private List<Customer> readCustomerDataFromCSV() throws IOException {
		List<Customer> customers = new ArrayList<>();
		
		Reader reader = new FileReader("D://csvsample.csv");  //csv filepath is hardCoded
		
        CSVParser csvParser = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(reader);
		for (CSVRecord csvRecord : csvParser) {   

			Customer customer = new Customer();
			
			customer.setCustomerId(csvRecord.get("Customer ID"));
			
			customer.setCustomerName(csvRecord.get("Customer Name"));
			
			customer.setProductName(csvRecord.get("Product Name"));
			
			customer.setProductPrice(Double.parseDouble(csvRecord.get("Product Price")));
			
      		customer.setQuantitySold(Double.parseDouble(csvRecord.get("Quantity Sold")));
      		
      		customerRepository.save(customer);
			
      		customers.add(customer);
		}
		return customers;
	}

	private Map<String, Double> calculateRevenuePerCustomer(List<Customer> customers) {
		Map<String, Double> revenuePerCustomer = new HashMap<>();
		for (Customer customer : customers) {
			String customerId = customer.getCustomerId();
			double totalRevenue = customer.getProductPrice() * customer.getQuantitySold();
			revenuePerCustomer.merge(customerId, totalRevenue, Double::sum);
		}
		
		return revenuePerCustomer;
	}

	private List<CustomerReport> generateReportSortedByRevenue(Map<String, Double> revenuePerCustomer) {
		List<CustomerReport> report = new ArrayList<>();
		revenuePerCustomer.entrySet().stream().sorted(Map.Entry.comparingByValue((r1, r2) -> Double.compare(r2, r1)))
				.forEachOrdered(entry -> {
					String customerId = entry.getKey();
					Double totalRevenue = entry.getValue();
					List<Customer> customer = customerRepository.findByCustomerId(customerId);
					List<Customer> uniqueCustomer =customer.stream().collect(Collectors.toMap(Customer::getCustomerName, customr -> customr, (c1, c2) -> c1))
							.values().stream().collect(Collectors.toList());
					for(Customer cust1: uniqueCustomer ) {
						report.add(new CustomerReport(customerId, cust1.getCustomerName(), totalRevenue));
					}
										
				});
		
		return report;
	}
	
}
