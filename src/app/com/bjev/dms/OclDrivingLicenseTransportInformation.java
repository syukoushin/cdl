/**
 * OclDrivingLicenseTransportInformation.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.bjev.dms;

public class OclDrivingLicenseTransportInformation  implements java.io.Serializable {
    private com.bjev.dms.OclDrivingLicenseMessage[] oclDrivingLicenseMessage;

    private java.lang.String PASSWORD;

    private java.lang.String USERNAME;

    public OclDrivingLicenseTransportInformation() {
    }

    public OclDrivingLicenseTransportInformation(
           com.bjev.dms.OclDrivingLicenseMessage[] oclDrivingLicenseMessage,
           java.lang.String PASSWORD,
           java.lang.String USERNAME) {
           this.oclDrivingLicenseMessage = oclDrivingLicenseMessage;
           this.PASSWORD = PASSWORD;
           this.USERNAME = USERNAME;
    }


    /**
     * Gets the oclDrivingLicenseMessage value for this OclDrivingLicenseTransportInformation.
     * 
     * @return oclDrivingLicenseMessage
     */
    public com.bjev.dms.OclDrivingLicenseMessage[] getOclDrivingLicenseMessage() {
        return oclDrivingLicenseMessage;
    }


    /**
     * Sets the oclDrivingLicenseMessage value for this OclDrivingLicenseTransportInformation.
     * 
     * @param oclDrivingLicenseMessage
     */
    public void setOclDrivingLicenseMessage(com.bjev.dms.OclDrivingLicenseMessage[] oclDrivingLicenseMessage) {
        this.oclDrivingLicenseMessage = oclDrivingLicenseMessage;
    }

    public com.bjev.dms.OclDrivingLicenseMessage getOclDrivingLicenseMessage(int i) {
        return this.oclDrivingLicenseMessage[i];
    }

    public void setOclDrivingLicenseMessage(int i, com.bjev.dms.OclDrivingLicenseMessage _value) {
        this.oclDrivingLicenseMessage[i] = _value;
    }


    /**
     * Gets the PASSWORD value for this OclDrivingLicenseTransportInformation.
     * 
     * @return PASSWORD
     */
    public java.lang.String getPASSWORD() {
        return PASSWORD;
    }


    /**
     * Sets the PASSWORD value for this OclDrivingLicenseTransportInformation.
     * 
     * @param PASSWORD
     */
    public void setPASSWORD(java.lang.String PASSWORD) {
        this.PASSWORD = PASSWORD;
    }


    /**
     * Gets the USERNAME value for this OclDrivingLicenseTransportInformation.
     * 
     * @return USERNAME
     */
    public java.lang.String getUSERNAME() {
        return USERNAME;
    }


    /**
     * Sets the USERNAME value for this OclDrivingLicenseTransportInformation.
     * 
     * @param USERNAME
     */
    public void setUSERNAME(java.lang.String USERNAME) {
        this.USERNAME = USERNAME;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof OclDrivingLicenseTransportInformation)) return false;
        OclDrivingLicenseTransportInformation other = (OclDrivingLicenseTransportInformation) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.oclDrivingLicenseMessage==null && other.getOclDrivingLicenseMessage()==null) || 
             (this.oclDrivingLicenseMessage!=null &&
              java.util.Arrays.equals(this.oclDrivingLicenseMessage, other.getOclDrivingLicenseMessage()))) &&
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
        if (getOclDrivingLicenseMessage() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getOclDrivingLicenseMessage());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getOclDrivingLicenseMessage(), i);
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
        new org.apache.axis.description.TypeDesc(OclDrivingLicenseTransportInformation.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://dms.bjev.com", "oclDrivingLicenseTransportInformation"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("oclDrivingLicenseMessage");
        elemField.setXmlName(new javax.xml.namespace.QName("", "OclDrivingLicenseMessage"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://dms.bjev.com", "oclDrivingLicenseMessage"));
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
