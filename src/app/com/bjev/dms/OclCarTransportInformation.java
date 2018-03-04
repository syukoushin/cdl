/**
 * OclCarTransportInformation.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.bjev.dms;

public class OclCarTransportInformation  implements java.io.Serializable {
    private com.bjev.dms.OclCarSalesMessage[] oclCarSalesMessage;

    private java.lang.String PASSWORD;

    private java.lang.String USERNAME;

    public OclCarTransportInformation() {
    }

    public OclCarTransportInformation(
           com.bjev.dms.OclCarSalesMessage[] oclCarSalesMessage,
           java.lang.String PASSWORD,
           java.lang.String USERNAME) {
           this.oclCarSalesMessage = oclCarSalesMessage;
           this.PASSWORD = PASSWORD;
           this.USERNAME = USERNAME;
    }


    /**
     * Gets the oclCarSalesMessage value for this OclCarTransportInformation.
     * 
     * @return oclCarSalesMessage
     */
    public com.bjev.dms.OclCarSalesMessage[] getOclCarSalesMessage() {
        return oclCarSalesMessage;
    }


    /**
     * Sets the oclCarSalesMessage value for this OclCarTransportInformation.
     * 
     * @param oclCarSalesMessage
     */
    public void setOclCarSalesMessage(com.bjev.dms.OclCarSalesMessage[] oclCarSalesMessage) {
        this.oclCarSalesMessage = oclCarSalesMessage;
    }

    public com.bjev.dms.OclCarSalesMessage getOclCarSalesMessage(int i) {
        return this.oclCarSalesMessage[i];
    }

    public void setOclCarSalesMessage(int i, com.bjev.dms.OclCarSalesMessage _value) {
        this.oclCarSalesMessage[i] = _value;
    }


    /**
     * Gets the PASSWORD value for this OclCarTransportInformation.
     * 
     * @return PASSWORD
     */
    public java.lang.String getPASSWORD() {
        return PASSWORD;
    }


    /**
     * Sets the PASSWORD value for this OclCarTransportInformation.
     * 
     * @param PASSWORD
     */
    public void setPASSWORD(java.lang.String PASSWORD) {
        this.PASSWORD = PASSWORD;
    }


    /**
     * Gets the USERNAME value for this OclCarTransportInformation.
     * 
     * @return USERNAME
     */
    public java.lang.String getUSERNAME() {
        return USERNAME;
    }


    /**
     * Sets the USERNAME value for this OclCarTransportInformation.
     * 
     * @param USERNAME
     */
    public void setUSERNAME(java.lang.String USERNAME) {
        this.USERNAME = USERNAME;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof OclCarTransportInformation)) return false;
        OclCarTransportInformation other = (OclCarTransportInformation) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.oclCarSalesMessage==null && other.getOclCarSalesMessage()==null) || 
             (this.oclCarSalesMessage!=null &&
              java.util.Arrays.equals(this.oclCarSalesMessage, other.getOclCarSalesMessage()))) &&
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
        if (getOclCarSalesMessage() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getOclCarSalesMessage());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getOclCarSalesMessage(), i);
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
        new org.apache.axis.description.TypeDesc(OclCarTransportInformation.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://dms.bjev.com", "oclCarTransportInformation"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("oclCarSalesMessage");
        elemField.setXmlName(new javax.xml.namespace.QName("", "OclCarSalesMessage"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://dms.bjev.com", "oclCarSalesMessage"));
        elemField.setNillable(false);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("PASSWORD");
        elemField.setXmlName(new javax.xml.namespace.QName("", "PASSWORD"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("USERNAME");
        elemField.setXmlName(new javax.xml.namespace.QName("", "USERNAME"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
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
