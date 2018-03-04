package com.bjev.dms;

public class OclToDmsServiceProxy implements com.bjev.dms.OclToDmsService {
  private String _endpoint = null;
  private com.bjev.dms.OclToDmsService oclToDmsService = null;
  
  public OclToDmsServiceProxy() {
    _initOclToDmsServiceProxy();
  }
  
  public OclToDmsServiceProxy(String endpoint) {
    _endpoint = endpoint;
    _initOclToDmsServiceProxy();
  }
  
  private void _initOclToDmsServiceProxy() {
    try {
      oclToDmsService = (new com.bjev.dms.OclToDmsServiceImplServiceLocator()).getHTTP_Port();
      if (oclToDmsService != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)oclToDmsService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)oclToDmsService)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (oclToDmsService != null)
      ((javax.xml.rpc.Stub)oclToDmsService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public com.bjev.dms.OclToDmsService getOclToDmsService() {
    if (oclToDmsService == null)
      _initOclToDmsServiceProxy();
    return oclToDmsService;
  }
  
  public com.bjev.dms.OclInvoiceAndDrivingLicenseBack[] saveoclinvoice(com.bjev.dms.OclInvoiceTransportInformation oclinvoicedata) throws java.rmi.RemoteException{
    if (oclToDmsService == null)
      _initOclToDmsServiceProxy();
    return oclToDmsService.saveoclinvoice(oclinvoicedata);
  }
  
  public com.bjev.dms.OclCarSalesBack[] savecarsales(com.bjev.dms.OclCarTransportInformation oclcardata) throws java.rmi.RemoteException{
    if (oclToDmsService == null)
      _initOclToDmsServiceProxy();
    return oclToDmsService.savecarsales(oclcardata);
  }
  
  public com.bjev.dms.OclInvoiceAndDrivingLicenseBack[] saveocldriving(com.bjev.dms.OclDrivingLicenseTransportInformation ocldrivingdata) throws java.rmi.RemoteException{
    if (oclToDmsService == null)
      _initOclToDmsServiceProxy();
    return oclToDmsService.saveocldriving(ocldrivingdata);
  }
  
  
}