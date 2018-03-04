/**
 * OclToDmsServiceImplServiceSoapBindingStub.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.bjev.dms;

public class OclToDmsServiceImplServiceSoapBindingStub extends org.apache.axis.client.Stub implements com.bjev.dms.OclToDmsService {
    private java.util.Vector cachedSerClasses = new java.util.Vector();
    private java.util.Vector cachedSerQNames = new java.util.Vector();
    private java.util.Vector cachedSerFactories = new java.util.Vector();
    private java.util.Vector cachedDeserFactories = new java.util.Vector();

    static org.apache.axis.description.OperationDesc [] _operations;

    static {
        _operations = new org.apache.axis.description.OperationDesc[3];
        _initOperationDesc1();
    }

    private static void _initOperationDesc1(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("saveoclinvoice");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://dms.bjev.com", "oclinvoicedata"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://dms.bjev.com", "oclInvoiceTransportInformation"), com.bjev.dms.OclInvoiceTransportInformation.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://dms.bjev.com", "oclInvoiceAndDrivingLicenseBackInformation"));
        oper.setReturnClass(com.bjev.dms.OclInvoiceAndDrivingLicenseBack[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://dms.bjev.com", "OclInvoiceInformation"));
        param = oper.getReturnParamDesc();
        param.setItemQName(new javax.xml.namespace.QName("", "OclInvoiceAndDrivingBackS"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[0] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("savecarsales");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://dms.bjev.com", "oclcardata"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://dms.bjev.com", "oclCarTransportInformation"), com.bjev.dms.OclCarTransportInformation.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://dms.bjev.com", "oclCarSalesBackInformation"));
        oper.setReturnClass(com.bjev.dms.OclCarSalesBack[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://dms.bjev.com", "OclCarSalesInformation"));
        param = oper.getReturnParamDesc();
        param.setItemQName(new javax.xml.namespace.QName("", "OclCarSalesBack"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[1] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("saveocldriving");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://dms.bjev.com", "ocldrivingdata"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://dms.bjev.com", "oclDrivingLicenseTransportInformation"), com.bjev.dms.OclDrivingLicenseTransportInformation.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://dms.bjev.com", "oclInvoiceAndDrivingLicenseBackInformation"));
        oper.setReturnClass(com.bjev.dms.OclInvoiceAndDrivingLicenseBack[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://dms.bjev.com", "OclDrivingLicenseInformation"));
        param = oper.getReturnParamDesc();
        param.setItemQName(new javax.xml.namespace.QName("", "OclInvoiceAndDrivingBackS"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[2] = oper;

    }

    public OclToDmsServiceImplServiceSoapBindingStub() throws org.apache.axis.AxisFault {
         this(null);
    }

    public OclToDmsServiceImplServiceSoapBindingStub(java.net.URL endpointURL, javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
         this(service);
         super.cachedEndpoint = endpointURL;
    }

    public OclToDmsServiceImplServiceSoapBindingStub(javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
        if (service == null) {
            super.service = new org.apache.axis.client.Service();
        } else {
            super.service = service;
        }
        ((org.apache.axis.client.Service)super.service).setTypeMappingVersion("1.2");
            java.lang.Class cls;
            javax.xml.namespace.QName qName;
            javax.xml.namespace.QName qName2;
            java.lang.Class beansf = org.apache.axis.encoding.ser.BeanSerializerFactory.class;
            java.lang.Class beandf = org.apache.axis.encoding.ser.BeanDeserializerFactory.class;
            java.lang.Class enumsf = org.apache.axis.encoding.ser.EnumSerializerFactory.class;
            java.lang.Class enumdf = org.apache.axis.encoding.ser.EnumDeserializerFactory.class;
            java.lang.Class arraysf = org.apache.axis.encoding.ser.ArraySerializerFactory.class;
            java.lang.Class arraydf = org.apache.axis.encoding.ser.ArrayDeserializerFactory.class;
            java.lang.Class simplesf = org.apache.axis.encoding.ser.SimpleSerializerFactory.class;
            java.lang.Class simpledf = org.apache.axis.encoding.ser.SimpleDeserializerFactory.class;
            java.lang.Class simplelistsf = org.apache.axis.encoding.ser.SimpleListSerializerFactory.class;
            java.lang.Class simplelistdf = org.apache.axis.encoding.ser.SimpleListDeserializerFactory.class;
            qName = new javax.xml.namespace.QName("http://dms.bjev.com", "oclCarSalesBack");
            cachedSerQNames.add(qName);
            cls = com.bjev.dms.OclCarSalesBack.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://dms.bjev.com", "oclCarSalesBackInformation");
            cachedSerQNames.add(qName);
            cls = com.bjev.dms.OclCarSalesBack[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://dms.bjev.com", "oclCarSalesBack");
            qName2 = new javax.xml.namespace.QName("", "OclCarSalesBack");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://dms.bjev.com", "oclCarSalesMessage");
            cachedSerQNames.add(qName);
            cls = com.bjev.dms.OclCarSalesMessage.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://dms.bjev.com", "oclCarTransportInformation");
            cachedSerQNames.add(qName);
            cls = com.bjev.dms.OclCarTransportInformation.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://dms.bjev.com", "oclDrivingLicenseMessage");
            cachedSerQNames.add(qName);
            cls = com.bjev.dms.OclDrivingLicenseMessage.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://dms.bjev.com", "oclDrivingLicenseTransportInformation");
            cachedSerQNames.add(qName);
            cls = com.bjev.dms.OclDrivingLicenseTransportInformation.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://dms.bjev.com", "oclInvoiceAndDrivingLicenseBack");
            cachedSerQNames.add(qName);
            cls = com.bjev.dms.OclInvoiceAndDrivingLicenseBack.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://dms.bjev.com", "oclInvoiceAndDrivingLicenseBackInformation");
            cachedSerQNames.add(qName);
            cls = com.bjev.dms.OclInvoiceAndDrivingLicenseBack[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://dms.bjev.com", "oclInvoiceAndDrivingLicenseBack");
            qName2 = new javax.xml.namespace.QName("", "OclInvoiceAndDrivingBackS");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://dms.bjev.com", "oclInvoiceMessage");
            cachedSerQNames.add(qName);
            cls = com.bjev.dms.OclInvoiceMessage.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://dms.bjev.com", "oclInvoiceTransportInformation");
            cachedSerQNames.add(qName);
            cls = com.bjev.dms.OclInvoiceTransportInformation.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

    }

    protected org.apache.axis.client.Call createCall() throws java.rmi.RemoteException {
        try {
            org.apache.axis.client.Call _call = super._createCall();
            if (super.maintainSessionSet) {
                _call.setMaintainSession(super.maintainSession);
            }
            if (super.cachedUsername != null) {
                _call.setUsername(super.cachedUsername);
            }
            if (super.cachedPassword != null) {
                _call.setPassword(super.cachedPassword);
            }
            if (super.cachedEndpoint != null) {
                _call.setTargetEndpointAddress(super.cachedEndpoint);
            }
            if (super.cachedTimeout != null) {
                _call.setTimeout(super.cachedTimeout);
            }
            if (super.cachedPortName != null) {
                _call.setPortName(super.cachedPortName);
            }
            java.util.Enumeration keys = super.cachedProperties.keys();
            while (keys.hasMoreElements()) {
                java.lang.String key = (java.lang.String) keys.nextElement();
                _call.setProperty(key, super.cachedProperties.get(key));
            }
            // All the type mapping information is registered
            // when the first call is made.
            // The type mapping information is actually registered in
            // the TypeMappingRegistry of the service, which
            // is the reason why registration is only needed for the first call.
            synchronized (this) {
                if (firstCall()) {
                    // must set encoding style before registering serializers
                    _call.setEncodingStyle(null);
                    for (int i = 0; i < cachedSerFactories.size(); ++i) {
                        java.lang.Class cls = (java.lang.Class) cachedSerClasses.get(i);
                        javax.xml.namespace.QName qName =
                                (javax.xml.namespace.QName) cachedSerQNames.get(i);
                        java.lang.Object x = cachedSerFactories.get(i);
                        if (x instanceof Class) {
                            java.lang.Class sf = (java.lang.Class)
                                 cachedSerFactories.get(i);
                            java.lang.Class df = (java.lang.Class)
                                 cachedDeserFactories.get(i);
                            _call.registerTypeMapping(cls, qName, sf, df, false);
                        }
                        else if (x instanceof javax.xml.rpc.encoding.SerializerFactory) {
                            org.apache.axis.encoding.SerializerFactory sf = (org.apache.axis.encoding.SerializerFactory)
                                 cachedSerFactories.get(i);
                            org.apache.axis.encoding.DeserializerFactory df = (org.apache.axis.encoding.DeserializerFactory)
                                 cachedDeserFactories.get(i);
                            _call.registerTypeMapping(cls, qName, sf, df, false);
                        }
                    }
                }
            }
            return _call;
        }
        catch (java.lang.Throwable _t) {
            throw new org.apache.axis.AxisFault("Failure trying to get the Call object", _t);
        }
    }

    public com.bjev.dms.OclInvoiceAndDrivingLicenseBack[] saveoclinvoice(com.bjev.dms.OclInvoiceTransportInformation oclinvoicedata) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[0]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://sap.com/xi/WebService/soap1.1");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://dms.bjev.com", "saveoclinvoice"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {oclinvoicedata});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.bjev.dms.OclInvoiceAndDrivingLicenseBack[]) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.bjev.dms.OclInvoiceAndDrivingLicenseBack[]) org.apache.axis.utils.JavaUtils.convert(_resp, com.bjev.dms.OclInvoiceAndDrivingLicenseBack[].class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.bjev.dms.OclCarSalesBack[] savecarsales(com.bjev.dms.OclCarTransportInformation oclcardata) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[1]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://sap.com/xi/WebService/soap1.1");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://dms.bjev.com", "savecarsales"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {oclcardata});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.bjev.dms.OclCarSalesBack[]) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.bjev.dms.OclCarSalesBack[]) org.apache.axis.utils.JavaUtils.convert(_resp, com.bjev.dms.OclCarSalesBack[].class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.bjev.dms.OclInvoiceAndDrivingLicenseBack[] saveocldriving(com.bjev.dms.OclDrivingLicenseTransportInformation ocldrivingdata) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[2]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://sap.com/xi/WebService/soap1.1");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://dms.bjev.com", "saveocldriving"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {ocldrivingdata});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.bjev.dms.OclInvoiceAndDrivingLicenseBack[]) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.bjev.dms.OclInvoiceAndDrivingLicenseBack[]) org.apache.axis.utils.JavaUtils.convert(_resp, com.bjev.dms.OclInvoiceAndDrivingLicenseBack[].class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

}
