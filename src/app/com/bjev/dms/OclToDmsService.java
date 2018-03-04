/**
 * OclToDmsService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.bjev.dms;

public interface OclToDmsService extends java.rmi.Remote {
    public com.bjev.dms.OclInvoiceAndDrivingLicenseBack[] saveoclinvoice(com.bjev.dms.OclInvoiceTransportInformation oclinvoicedata) throws java.rmi.RemoteException;
    public com.bjev.dms.OclCarSalesBack[] savecarsales(com.bjev.dms.OclCarTransportInformation oclcardata) throws java.rmi.RemoteException;
    public com.bjev.dms.OclInvoiceAndDrivingLicenseBack[] saveocldriving(com.bjev.dms.OclDrivingLicenseTransportInformation ocldrivingdata) throws java.rmi.RemoteException;
}
