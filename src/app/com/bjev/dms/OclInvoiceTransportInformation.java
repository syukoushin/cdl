/**
 * OclInvoiceTransportInformation.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.bjev.dms;

public class OclInvoiceTransportInformation  implements java.io.Serializable {
    private com.bjev.dms.OclInvoiceMessage[] oclInvoiceMessage;

    private java.lang.String PASSWORD;

    private java.lang.String USERNAME;

    public OclInvoiceTransportInformation() {
    }

    public OclInvoiceTransportInformation(
           com.bjev.dms.OclInvoiceMessage[] oclInvoiceMessage,
           java.lang.String PASSWORD,
           java.lang.String USERNAME) {
           this.oclInvoiceMessage = oclInvoiceMessage;
           this.PASSWORD = PASSWORD;
           this.USERNAME = USERNAME;
    }


    /**
     * Gets the oclInvoiceMessage value for this OclInvoiceTransportInformation.
     * 
     * @return oclInvoiceMessage
     */
    public com.bjev.dms.OclInvoiceMessage[] getOclInvoiceMessage() {
        return oclInvoiceMessage;
    }


    /**
     * Sets the oclInvoiceMessage value for this OclInvoiceTransportInformation.
     * 
     * @param oclInvoiceMessage
     */
    public void setOclInvoiceMessage(com.bjev.dms.OclInvoiceMessage[] oclInvoiceMessage) {
        this.oclInvoiceMessage = oclInvoiceMessage;
    }

    public com.bjev.dms.OclInvoiceMessage getOclInvoiceMessage(int i) {
        return this.oclInvoiceMessage[i];
    }

    public void setOclInvoiceMessage(int i, com.bjev.dms.OclInvoiceMessage _value) {
        this.oclInvoiceMessage[i] = _value;
    }


    /**
     * Gets the PASSWORD value for this OclInvoiceTransportInformation.
     * 
     * @return PASSWORD
     */
    public java.lang.String getPASSWORD() {
        return PASSWORD;
    }


    /**
     * Sets the PASSWORD value for this OclInvoiceTransportInformation.
     * 
     * @param PASSWORD
     */
    public void setPASSWORD(java.lang.String PASSWORD) {
        this.PASSWORD = PASSWORD;
    }


    /**
     * Gets the USERNAME value for this OclInvoiceTransportInformation.
     * 
     * @return USERNAME
     */
    public java.lang.String getUSERNAME() {
        return USERNAME;
    }


    /**
     * Sets the USERNAME value for this OclInvoiceTransportInformation.
     * 
     * @param USERNAME
     */
    public void setUSERNAME(java.lang.String USERNAME) {
        this.USERNAME = USERNAME;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof OclInvoiceTransportInformation)) return false;
        OclInvoiceTransportInformation other = (OclInvoiceTransportInformation) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.oclInvoiceMessage==null && other.getOclInvoiceMessage()==null) || 
             (this.oclInvoiceMessage!=null &&
              java.util.Arrays.equals(this.oclInvoiceMessage, other.getOclInvoiceMessage()))) &&
            ((this.PASSWORD==null && other.getPASSWORD()==null) || 
             (this.PASSWORD!=null &&
              this.PASSWORD.equals(other.getPASSWORD()))) &&
            ((this.USERNAME==null && other.getUSERNAME()==null) || 
             (this.USERNAME!=null &&
              this.USERNAME.equals(other.getUSERNAME())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getOclInvoiceMessage() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getOclInvoiceMessage());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getOclInvoiceMessage(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getPASSWORD() != null) {
            _hashCode += getPASSWORD().hashCode();
        }
        if (getUSERNAME() != null) {
            _hashCode += getUSERNAME().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(OclInvoiceTransportInformation.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://dms.bjev.com", "oclInvoiceTransportInformation"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("oclInvoiceMessage");
        elemField.setXmlName(new javax.xml.namespace.QName("", "OclInvoiceMessage"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://dms.bjev.com", "oclInvoiceMessage"));
        elemField.setNillable(false);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("PASSWORD");
        elemField.setXmlName(new javax.xml.namespace.QName("", "PASSWORD"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("USERNAME");
        elemField.setXmlName(new javax.xml.namespace.QName("", "USERNAME"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
