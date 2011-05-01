package org.bpmnwithactiviti.chapter6.errorevent;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.bpmnwithactiviti.chapter6.ws.Customer;
import org.bpmnwithactiviti.chapter6.ws.CustomerServiceService;

public class RetrieveCustomerTask implements JavaDelegate {
  
  private CustomerServiceService customerService = new CustomerServiceService();

  @Override
  public void execute(DelegateExecution execution) throws Exception {
    Integer customerId = (Integer) execution.getVariable("customerNumber");
    Customer customer = null;
    if(customerId > 0) {
      customer = customerService.getCustomerServicePort().findCustomerById(customerId);
    } else {
      String customerName = (String) execution.getVariable("customerName");
      String contactPerson = (String) execution.getVariable("contactPerson");
      customer = customerService.getCustomerServicePort().findCustomer(customerName, contactPerson);
    }
    CustomerVariable variable = new CustomerVariable();
    if(customer != null) {
      variable.setCustomerFound(true);
      variable.setCustomerId(customer.getCustomerId());
      variable.setCustomerName(customer.getCustomerName());
      variable.setContactPerson(customer.getContactPerson());
      variable.setCustomerAddress(customer.getCustomerAddress());
    } else {
      variable.setCustomerFound(false);
    }
    execution.setVariable("customer", variable);
  }

}